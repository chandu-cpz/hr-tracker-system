import asyncio
import grpc 
from concurrent import futures
from recommendation import getRecommendedJobs
from grpc_generated import recommendation_pb2 as recommendation__pb2
from grpc_generated import recommendation_pb2_grpc as recommendation_pb2_grpc
class JobRecommendationServicer(recommendation_pb2_grpc.RecommendationServiceServicer):
    def __init__(self, index, job_ids, processed_jobs):
        self.index = index
        self.job_ids = job_ids
        self.processed_jobs = processed_jobs

    async def GetJobRecommendations(self, request, context):
        try:
            print("Received recommendation request")
            print(type(request))
            query = request.JobId
            recommendations = await getRecommendedJobs(query, self.index, self.job_ids, self.processed_jobs, top_n=3)
            rec_ids = []
            for jobs in recommendations:
                rec_ids.append(str(jobs.get('_id')))
            print("Recommmendations: ")
            print(rec_ids)
            response = recommendation__pb2.RecommendationResponse(
                recommendedJobIds=rec_ids
            )

            return response
        except Exception as e:
            print(e)
            return recommendation__pb2.RecommendationResponse(
                recommendedJobIds=[]
            )


async def startRecommendationServer(index, job_ids, processed_jobs):
    server = grpc.aio.server()
    recommendation_pb2_grpc.add_RecommendationServiceServicer_to_server(
        JobRecommendationServicer(index, job_ids, processed_jobs), server)
    server.add_insecure_port(f'[::]:50052') # Use port from .env, default 50051
    await server.start()
    print(f"GRPC Server started,")
    await server.wait_for_termination()
