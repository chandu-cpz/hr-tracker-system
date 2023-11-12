import { Job } from "../models/jobs.model.js";
export async function getJobs(req, res, next) {
    //Logic to get jobs if any filers on type of jobs then do so in query ;

    const JobsPosted = await Job.find({ isOpen: true });
    res.status(200).json(JobsPosted);

    //also send only 10 jobs or so ..

    // do pagination;
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
