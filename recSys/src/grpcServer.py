import asyncio
import grpc
from concurrent import futures
import os  # Import the 'os' module
from dotenv import load_dotenv
from bson.objectid import ObjectId
from recommendation import getRecommendedJobs
from grpc_generated import recommendation_pb2 as recommendation_pb2
from grpc_generated import recommendation_pb2_grpc as recommendation_pb2_grpc
from grpc_generated import ats_pb2 as ats_pb2
from grpc_generated import ats_pb2_grpc as ats_pb2_grpc
from ats import calculate_ats_score  # Import the updated function
from mongo import getClient

load_dotenv() # Load environment variables

class JobRecommendationServicer(recommendation_pb2_grpc.RecommendationServiceServicer):
    def __init__(self, index, job_ids, processed_jobs):
        self.index = index
        self.job_ids = job_ids
        self.processed_jobs = processed_jobs

    async def GetJobRecommendations(self, request, context):
        try:
            print("Received recommendation request")
            query = request.JobId
            recommendations = await getRecommendedJobs(query, self.index, self.job_ids, self.processed_jobs, top_n=3)
            rec_ids = [str(job.get('_id')) for job in recommendations]
            print("Recommmendations: ")
            print(rec_ids)
            response = recommendation_pb2.RecommendationResponse(
                recommendedJobIds=rec_ids
            )
            return response
        except Exception as e:
            print(f"Error in GetJobRecommendations: {e}")
            return recommendation_pb2.RecommendationResponse(recommendedJobIds=[])


class ATSServiceServicer(ats_pb2_grpc.ATSServiceServicer):
    async def CalculateATSScore(self, request, context):
        try:
            print("Received ATS score request")
            job_id = request.JobId
            user_id = request.UserId

            job = await self.get_job_from_db(job_id)
            resume_text = await self.get_resume_from_db(user_id)

            if not resume_text:
                print("Resume not found")
                return ats_pb2.ATSScoreResponse(score=-1)
            if not job:
                print("Job not found")
                return ats_pb2.ATSScoreResponse(score=-1)

            score, feedback = await calculate_ats_score(job, resume_text)
            stringified_feedback = {k: str(v) for k, v in feedback.items()}
            response = ats_pb2.ATSScoreResponse(score=score, feedback=stringified_feedback)
            print(f"Full gRPC Response Object: {response}")
            return response

        except Exception as e:
            print(f"Error calculating ATS score in server: {e}")
            return ats_pb2.ATSScoreResponse(score=-1)
    async def get_job_from_db(self, job_id):
        client = await getClient()
        db = client[os.getenv("DB_NAME")]
        jobs_collection = db["jobs"]
        try:
            # Convert job_id to ObjectId
            try:
                job_id_object = ObjectId(job_id)
            except Exception as e:
                print(f"Invalid job_id format: {e}") #handle this case.
                return None

            job = await jobs_collection.find_one({"_id": job_id_object})
            return job
        except Exception as e:
            print(f"Error fetching job from DB: {e}")
            return None


    async def get_resume_from_db(self, user_id):
        client = await getClient()
        db = client[os.getenv("DB_NAME")]
        resumes_collection = db["users"]  # Corrected Collection name
        try:
            # Convert user_id to ObjectId
            try:
                user_id_object = ObjectId(user_id)
            except Exception as e:
                print(f"Invalid user_id format: {e}")
                return None

            resume = await resumes_collection.find_one({"_id": user_id_object})

            if resume:
                return resume.get("resume", "")
            else:
                return None
        except Exception as e:
            print(f"Error fetching resume from DB: {e}")
            return None

async def startRecommendationServer(index, job_ids, processed_jobs):
    server = grpc.aio.server()
    recommendation_pb2_grpc.add_RecommendationServiceServicer_to_server(
        JobRecommendationServicer(index, job_ids, processed_jobs), server
    )
    ats_pb2_grpc.add_ATSServiceServicer_to_server(ATSServiceServicer(), server)  # Register ATSService
    server.add_insecure_port(f'[::]:50052')
    await server.start()
    print(f"GRPC Server started,")
    await server.wait_for_termination()