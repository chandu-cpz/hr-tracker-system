import { Job } from "../models/jobs.model.js";

export async function getJobs(req, res, next) {
    console.log("The getJobs controller is triggered")
    //Logic to get jobs if any filers on type of jobs then do so in query ;

    const JobsPosted = await Job.find({ isOpen: true });
    res.status(200).json(JobsPosted);

    //also send only 10 jobs or so ..

    // do pagination;
}

export async function openJobsCount(req, res, next){
    try {
        const openJobsCount = await Job.countDocuments({ isOpen: true });
        res.status(200).json({ openJobsCount });
      } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Internal Server Error' });
    }      
}


export async function addJob(req, res, next) {
    console.log("The addJob controller is triggered");
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
    } = req.body;

    const postedBy = req.body.user._id;

    // Validate required fields
    if (!jobTitle || !jobDescription || !companyName || !responsibilities || !qualifications || !location || !salary) {
        return res.status(400).json({ error: 'Required fields missing' });
    }

    // Prepare job object
    const jobData = {
        jobTitle,
        jobDescription,
        companyName,
        responsibilities,
        qualifications,
        location,
        salary,
        skills, // optional
        isOpen, // optional, defaults to true
        postedBy
    };
    //Remove null value filleds from object
    Object.keys(jobData).forEach(key => {
        if (jobData[key] === null || jobData[key] === undefined) {
            delete jobData[key];
        }
    });

    // Insert job 
    try {
        const job = await Job.create(jobData);
        //Log the info:
        console.log("The job has been created: ", job)

        // Send response
        res.status(201).json(job);

    } catch (err) {
        console.error(err);
        res.status(500).json({ error: 'Error creating job' });
    }

    //send back some response ;
}
