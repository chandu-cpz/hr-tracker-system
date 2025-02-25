import spacy
from spacy.lang.en.examples import sentences 
import asyncio 

nlp = spacy.load("en_core_web_sm")

def preprocess_text(text):
    if not text:
        return ""

    text = text.lower()
    doc = nlp(text)

    lemmatized_tokens = [token.lemma_ for token in doc if not token.is_stop and token.is_alpha]

    return " ".join(lemmatized_tokens)

def preprocessJob(job):
    job_description = job.get("jobDescription", "")
    print(job_description)

    skills_list = job.get("skills", [])

    processed_description = preprocess_text(job_description)
    skills_text = " ".join(skills_list)
    processed_skills = preprocess_text(skills_text)

    job["processed_description"] = processed_description
    job["processed_skills"] = processed_skills

    return job

