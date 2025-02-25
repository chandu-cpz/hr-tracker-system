# Import necessary libraries
import pymongo
import os
from dotenv import load_dotenv
from recommendation import addItemToIndex
from pymongo.errors import PyMongoError  
import logging

load_dotenv()

async def getClient():  
    print(os.getenv("DB_URI"))
    client = pymongo.AsyncMongoClient(os.getenv("DB_URI"))
    return client

async def getJobs(client):  
    db = client[os.getenv("DB_NAME")]
    jobs = db["jobs"]
    jobList = await jobs.find().to_list(length=None)
    print(len(jobList))
    return jobList

async def watchStream(client, index, job_ids, jobs):  
    print("Watching for stream changes...")

    db = client[os.getenv("DB_NAME")]
    jobs_collection = db["jobs"]
    try:
        async with await jobs_collection.watch([{"$match": {"operationType": "insert"}}]) as stream:
            async for insert_change in stream:
                newJob = insert_change["fullDocument"]
                print("Got a new Job")
                print(newJob)
                await addItemToIndex(newJob,index,job_ids,jobs)
    except pymongo.errors.PyMongoError as e:
        print(e)