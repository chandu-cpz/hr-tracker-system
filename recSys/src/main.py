import asyncio
from mongo import getJobs, getClient, watchStream
from preprocess import preprocessJob
from embeddings import getEmbeddings
from recommendation import createIndex, getRecommendedJobs
from grpcServer import startRecommendationServer 
async def main():
    client = await getClient() 
    jobs = await getJobs(client) 
    processed_jobs = []
    for job in jobs:
        job = preprocessJob(job)
        job = await getEmbeddings(job) 
        processed_jobs.append(job)
    
    print(len(processed_jobs))
    index, job_ids = await createIndex(processed_jobs) 

    sample_query = "Looking for a software engineer job with python and machine learning skills"
    recommendations = await getRecommendedJobs(sample_query, index, job_ids, processed_jobs, top_n=3)  

    if recommendations:
        print("\nRecommended Jobs:")
        for job in recommendations:
            print(
                f"- {job.get('jobTitle', 'N/A')} at {job.get('companyName', 'N/A')}")
    else:
        print("No recommendations found for the sample query.")
    
    await asyncio.gather(
        startRecommendationServer(index, job_ids, processed_jobs),
        watchStream(client, index, job_ids, processed_jobs)
    )

if __name__ == "__main__":
    asyncio.run(main())

