import asyncio
from sentence_transformers import SentenceTransformer

model = SentenceTransformer('all-MiniLM-L6-v2')

async def getEmbeddings(job):
    loop = asyncio.get_running_loop() 
    descriptionEmbeddings = await loop.run_in_executor(None, model.encode, job.get("processed_description")) 
    skillsEmbeddings = await loop.run_in_executor(None, model.encode, job.get("processed_skills")) 
    job["descriptionEmbeddings"] = descriptionEmbeddings
    job["skillsEmbeddings"] = skillsEmbeddings
    return job
