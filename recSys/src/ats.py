import asyncio
import re  # For regular expressions
import aiohttp # Asynchronous HTTP requests
import io # For handling byte streams
import pdfminer.high_level # PDF parsing
from preprocess import preprocess_text  # Use your existing preprocessing

async def calculate_ats_score(job, resume_url):  # Changed resume_text to resume_url
    """
    Calculates an ATS score based on keyword matching.
    Fetches and parses a PDF resume from a URL.
    """
    try:
        job_description = job.get("jobDescription", "")
        job_title = job.get("jobTitle", "")

        # Combine job title and description for keyword extraction
        job_text = job_title + " " + job_description

        processed_job_text = preprocess_text(job_text)

        # Extract keywords from the job description
        keywords = extract_keywords(processed_job_text)

        # Fetch the PDF resume and extract text
        resume_text = await fetch_and_extract_text_from_pdf(resume_url)
        if not resume_text:
            print("Failed to extract text from PDF resume.")
            return 0.0 # Or handle the error differently

        processed_resume_text = preprocess_text(resume_text)


        # Calculate the score based on keyword matches
        score = calculate_keyword_match_score(keywords, processed_resume_text)

        return score
    except Exception as e:
        print(f"Error calculating ATS score: {e}")
        return 0.0 # Return 0 instead of raising error


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