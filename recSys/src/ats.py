import asyncio
import re  # For regular expressions
import aiohttp # Asynchronous HTTP requests
import io # For handling byte streams
import pdfminer.high_level # PDF parsing
from preprocess import preprocess_text  # Use your existing preprocessing
from collections import Counter
import math #for cosine similarity
from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity


model = SentenceTransformer('all-MiniLM-L6-v2') # Load sentence transformer model

async def calculate_ats_score(job, resume_url):  # Changed resume_text to resume_url
    """
    Calculates an ATS score based on keyword matching, skills weighting, and cosine similarity of embeddings.
    Fetches and parses a PDF resume from a URL.
    """
    try:
        job_description = job.get("jobDescription", "")
        job_title = job.get("jobTitle", "")
        skills_list = job.get("skills", [])

        # Combine job title and description for keyword extraction
        job_text = job_title + " " + job_description

        processed_job_text = preprocess_text(job_text)

        # Extract keywords from the job description
        keywords = extract_keywords(processed_job_text)

        # Fetch the PDF resume and extract text
        resume_text = await fetch_and_extract_text_from_pdf(resume_url)
        if not resume_text:
            print("Failed to extract text from PDF resume.")
            return 0.0, {}  # Or handle the error differently

        processed_resume_text = preprocess_text(resume_text)

        # Calculate keyword match score
        keyword_match_score = calculate_keyword_match_score(keywords, processed_resume_text)

        # Calculate skills match score (weighted)
        skills_match_score = calculate_skills_match_score(skills_list, processed_resume_text)

        # Calculate cosine similarity score
        cosine_sim_score = await calculate_cosine_similarity_score(job_text, resume_text)

        # Combine the scores with weights (adjust weights as needed)
        total_score = (0.4 * keyword_match_score + 0.4 * skills_match_score + 0.2 * cosine_sim_score)

        # Generate feedback
        feedback = generate_feedback(keywords, skills_list, processed_resume_text, job_text, resume_text)
        # print(f"Type of feedback: {type(feedback)}")
        return total_score, feedback
    except Exception as e:
        print(f"Error calculating ATS score: {e}")
        return 0.0, {"error": str(e)} # Return 0 instead of raising error


async def fetch_and_extract_text_from_pdf(url):
    """
    Fetches a PDF from a URL and extracts the text content.
    """
    try:
        async with aiohttp.ClientSession() as session:
            async with session.get(url) as response:
                if response.status == 200:
                    pdf_data = await response.read() # Get the PDF content as bytes

                    # Use pdfminer.high_level to extract text from the PDF data
                    with io.BytesIO(pdf_data) as pdf_file:
                        text = pdfminer.high_level.extract_text(pdf_file)
                    return text
                else:
                    print(f"Error fetching PDF: {response.status}")
                    return None
    except Exception as e:
        print(f"Error fetching or extracting PDF: {e}")
        return None


def extract_keywords(text):
    """
    Extracts keywords from the job description text.  (Example: simple split)
    You can use more advanced keyword extraction techniques here
    (e.g., TF-IDF, RAKE, YAKE).
    """
    return set(text.split()) # Remove duplicates


def calculate_keyword_match_score(keywords, resume_text):
    """
    Calculates a score based on the number of keywords found in the resume.
    """
    if not keywords:
        return 0.0

    matched_keywords = 0
    for keyword in keywords:
        if re.search(r'\b' + re.escape(keyword) + r'\b', resume_text, re.IGNORECASE):  # Word boundary match
            matched_keywords += 1

    return (matched_keywords / len(keywords)) * 100  # Percentage

def calculate_skills_match_score(skills, resume_text):
    """
    Calculates a weighted score based on the presence of required skills in the resume.
    """
    if not skills:
        return 0.0

    matched_skills = 0
    for skill in skills:
        if re.search(r'\b' + re.escape(skill) + r'\b', resume_text, re.IGNORECASE):
            matched_skills += 1 #can weight the skills with a dictionary

    return (matched_skills / len(skills)) * 100

import asyncio
from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np  # Import numpy

model = SentenceTransformer('all-MiniLM-L6-v2')

import asyncio
from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np  # Import numpy

model = SentenceTransformer('all-MiniLM-L6-v2')

async def calculate_cosine_similarity_score(job_text, resume_text):
    """
    Calculates cosine similarity between job description and resume using sentence embeddings.
    """
    try:
        #Check if any are empty and return 0
        if not job_text or not resume_text:
            return 0.0
        loop = asyncio.get_running_loop()
        job_embedding = await loop.run_in_executor(None, model.encode, job_text)
        resume_embedding = await loop.run_in_executor(None, model.encode, resume_text)

        # Ensure they are numpy arrays and have the correct data type and dimensions
        job_embedding = np.array(job_embedding).astype(np.float32)
        resume_embedding = np.array(resume_embedding).astype(np.float32)

        #Reshape to fit the dimensions
        if job_embedding.ndim == 1: #check dimensions before reshaping and reshape only if 1 dim
            job_embedding = job_embedding.reshape(1, -1)
        if resume_embedding.ndim == 1:#check dimensions before reshaping and reshape only if 1 dim
            resume_embedding = resume_embedding.reshape(1, -1)


        similarity_score = cosine_similarity(job_embedding, resume_embedding)[0][0]
        return similarity_score * 100
    except Exception as e:
        print(f"Error calculating cosine similarity: {e}")
        return 0.0

def generate_feedback(keywords, skills, resume_text, job_text, original_resume_text):
    """
    Generates personalized feedback based on missing keywords and skills.
    """
    missing_keywords = [keyword for keyword in keywords if not re.search(r'\b' + re.escape(keyword) + r'\b', resume_text, re.IGNORECASE)]
    missing_skills = [skill for skill in skills if not re.search(r'\b' + re.escape(skill) + r'\b', resume_text, re.IGNORECASE)]

    feedback = {}

    if missing_keywords:
        feedback["missing_keywords"] = missing_keywords
        feedback["keyword_suggestion"] = "Consider adding these keywords to your resume to better match the job description: " + ", ".join(missing_keywords)

    if missing_skills:
        feedback["missing_skills"] = missing_skills
        feedback["skills_suggestion"] = "Highlight these skills in your resume, providing specific examples of how you've used them: " + ", ".join(missing_skills)

    # Additional feedback based on resume content quality (example - can add more checks)
    if len(original_resume_text) < 200:  # Arbitrary threshold
        feedback["resume_length"] = "Your resume seems a bit short.  Consider adding more detail about your experience and accomplishments."
    
    # Check for quantifiable results.  This is very basic, can be improved with NLP
    if not re.search(r'\d+%', original_resume_text) and not re.search(r'\$\d+', original_resume_text) and not re.search(r'\d+ [a-zA-Z]+', original_resume_text):
        feedback["quantifiable_results"] = "Add quantifiable results. Provide specific numbers and metrics to demonstrate your achievements in previous roles. Example: 'Increased sales by 20% in Q4' or 'Managed a team of 10 engineers'."

    # Future Improvements (examples)
    feedback["future_improvements"] = [
        "Consider obtaining certifications relevant to your field.",
        "Build a portfolio showcasing your projects and accomplishments.",
        "Network with professionals in your industry to gain insights and opportunities."
    ]

    return feedback