import numpy as np
from voyager import Index, Space
from sentence_transformers import SentenceTransformer
from preprocess import preprocessJob
import asyncio 

model = SentenceTransformer('all-MiniLM-L6-v2')

async def createIndex(jobs): 
    """
    Create a Voyager index from job embeddings asynchronously
    Returns tuple: (index, list of job IDs)
    """
    vectors = []
    job_ids = []

    for idx, job in enumerate(jobs):
        # Concatenate embeddings from both fields
        combined_emb = np.concatenate([
            job["descriptionEmbeddings"],
            job["skillsEmbeddings"]
        ])
        vectors.append(combined_emb)
        job_ids.append(idx)  # Using list index as ID

# Convert to numpy array
    vectors_np = np.array(vectors).astype(np.float32)

# Create Voyager index with cosine similarity
    index = Index(
        space=Space.Cosine,
        num_dimensions=vectors_np.shape[1],
        M=16,
        ef_construction=200
    )
    loop = asyncio.get_running_loop() 
    await loop.run_in_executor(None, index.add_items, vectors_np, job_ids)

    return index, job_ids

async def getRecommendedJobs(query, index, job_ids, jobs, top_n=5): 

    """
    Get job recommendations for a query string asynchronously
    Returns list of recommended job dictionaries
    """
    # Preprocess and embed the query
    temp_job = {
        "jobDescription": query,
        "skills": [query]  # Simple approach for query processing
    }
    temp_job = preprocessJob(temp_job)

    # Generate embeddings
    loop = asyncio.get_running_loop() 
    desc_emb = await loop.run_in_executor(None, model.encode, temp_job["processed_description"]) 
    skills_emb = await loop.run_in_executor(None, model.encode, temp_job["processed_skills"]) 

    # Create query vector (same format as index vectors)
    query_vector = np.concatenate([desc_emb, skills_emb]).astype(np.float32)

    # Query the index
    neighbor_ids, _ = await loop.run_in_executor(None, index.query, query_vector, top_n)

    # Get corresponding job objects
    recommendations = [jobs[idx] for idx in neighbor_ids]

    return recommendations


async def addItemToIndex(job, index, job_ids, jobs): 
    """
    Adds a new job to the existing Voyager index asynchronously.
    Args:
        job (dict): The new job dictionary (already processed and with embeddings).
        index (voyager.Index): The existing Voyager index.
        job_ids (list): List of job IDs currently in the index.
        jobs (list): List of all job dictionaries (to append the new job).
    Returns:
        tuple: Updated index and job_ids list.
    """
    # 1. Extract embeddings from the new job
    description_embedding = job["descriptionEmbeddings"]
    skills_embedding = job["skillsEmbeddings"]
    combined_embedding = np.concatenate([description_embedding, skills_embedding]).astype(np.float32)

    # 2. Determine a new ID for the job (using the next available index)
    new_job_id = len(job_ids)   

    # 3. Add the new item to the Voyager index
    loop = asyncio.get_running_loop() 
    await loop.run_in_executor(None, index.add_item, combined_embedding, new_job_id) 

    # 4. Update job_ids and jobs list to reflect the new job
    job_ids.append(new_job_id)
    jobs.append(job) # Add the job to the jobs list as well so recommendations can find it

    return index, job_ids, jobs
