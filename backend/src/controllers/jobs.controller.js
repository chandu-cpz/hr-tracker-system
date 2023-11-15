import { query } from "express";
import { Job } from "../models/jobs.model.js";
import { User } from "../models/user.model.js";

export async function getJobs(req, res, next) {
    // Logic to get all open jobs
    const jobsPosted = await Job.find({ isOpen: true });
    console.log(jobsPosted);

    //SORT BY SALARY
    let query = Job.find();
    if (req.query.sort) {
        query = query.sort(req.query.sort);
    }

    //PAGENATION
    const page = req.query.page || 1;
    const limit = 10;
    const skip = (page - 1) * limit;
    query = query.skip(skip).limit(limit);

    if (req.query.page) {
        const total = await Job.countDocuments();
        if (skip >= total) {
            res.status(500).send("Internal Server Error");
        }
    }

    //FILTERING BY JOBS SKILLS
    if (req.query.skills) {
        const skills = req.query.skills.split(",");
        query = query.where("skills").in(skills);
    }

    try {
        const jobs = await query.exec();
        res.json(jobs);
    } catch (error) {
        console.error(error);
        res.status(500).send("Internal Server Error");
    }
}

export async function addJob(req, res, next) {
    //Logic to add jobs and insert into database
    const {
        jobTitle,
        jobDescription,
        companyName,
        responsibilities,
        qualifications,
        location,
        salary,
        skills,
        isOpen,
        postedBy,
    } = req.body;

    let Jobdata = {
        jobTitle,
        jobDescription,
        companyName,
        responsibilities,
        qualifications,
        location,
        salary,
        skills,
        isOpen,
        postedBy,
    };
    const Jobs = await Job.create(Jobdata);
    res.status(200).json(Jobdata);

    //send back some response ;
}
