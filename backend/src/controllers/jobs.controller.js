import { Job } from "../models/jobs.model.js";
import { User } from "../models/user.model.js";
import validator from "validator";
import express from "express";

const router = express.Router();

export async function getSingleJob(req, res) {
    console.log("============================================================");
    console.log(
        "A request is made to get details of Job with jobId: " +
        req.params.jobId
    );
    const { jobId } = req.params;
    const job = await Job.findById(jobId);
    console.log("Sending back data of " + job.jobTitle);
    res.status(200).json(job);
    console.log("============================================================");
}

export async function saveJob(req, res) {
    const userId = req.body.user._id;
    const jobId = req.body._id;
    console.log("A request is made to save job with jobId: " + jobId);
    try {
        // Update the user's savedJob field with the new job ID
        const user = await User.findOneAndUpdate(
            { _id: userId },
            { $addToSet: { savedJobs: jobId } }, // Use $addToSet to avoid duplicates
            { new: true }
        );

        res.status(200).json({ user });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: "Internal Server Error" });
    }
}

export async function deleteSavedJob(req, res) {
    const userId = req.body.user._id;
    const jobId = req.body.jobId;

    try {
        // Find the user and pull the jobId from their savedJobs array
        const user = await User.findOneAndUpdate(
            { _id: userId },
            { $pull: { savedJobs: jobId } },
            { new: true }
        );

        res.status(200).json({ user });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: "Internal Server Error" });
    }
}

export async function getJobs(req, res) {
    console.log("================================");
    console.log("(getJobs) : getting jobs");
    console.log(req.query)
    try {
        let query = Job.find();

        // Sorting
        if (req.query.sort) {
            const sortField = req.query.sort;
            const sortOrder =
                req.query.sortOrder &&
                    req.query.sortOrder.toLowerCase() === "desc"
                    ? -1
                    : 1;
            query = query.sort({ [sortField]: sortOrder });
        }

        console.log(req.query.jobType)

        // Pagination
        const page = req.query.page || 1;
        const limit = 8;
        const skip = (page - 1) * limit;
        query = query.skip(skip).limit(limit);

        if (req.query.page) {
            const total = await Job.countDocuments();
            if (skip >= total) {
                return res.status(500).send("Internal Server Error");
            }
        }

        if (req.query.jobTitle) {
            query = query.where("jobTitle").equals(req.query.jobTitle);
        }

        if (req.query.location) {
            query = query.where("location").equals(req.query.location);
        }

        if (req.query.experience) {
            query = query.where("experience").equals(req.query.experience);
        }

        if (req.query.jobType) {
            query = query.where('jobtype').in(req.query.jobType);
        }

        if (req.query.minSalary && req.query.maxSalary) {

            query = query.where('salary').gte(parseInt(req.query.minSalary))
                .lte(parseInt(req.query.maxSalary));

        }

        let locations, titles, experience;
        try {

            locations = await Job.distinct("location");
            titles = await Job.distinct("jobTitle");
            experience = await Job.distinct("experience");
        } catch (err) {
            console.log(err);
        }
        // Execute the query

        let response = {};

        if (locations.length > 0) {
            response.location = locations;
        }

        if (titles.length > 0) {
            response.jobTitle = titles;
        }

        if (experience.length > 0) {
            response.experience = experience;
        }

        const jobs = await query.populate({
            path: 'postedBy',
            select: 'companyImage',
        }).exec();
        console.log("Jobs sent successfully");
        response.jobs = jobs;
        const totalJobs = await jobs.length;
        const totalDocs = await Job.countDocuments(query.getQuery());
        response.totalPages = Math.ceil(totalDocs / limit);
        res.json(response);
    } catch (error) {
        console.error(error);
        res.status(500).send("Internal Server Error");
    }
}

export async function openJobsCount(req, res) {
    console.log("================================================");
    console.log(
        `(openJobsCount Controller)A request is made to get count of open jobs ${new Date().toLocaleString()}`
    );
    try {
        const openJobsCount = await Job.countDocuments({ isOpen: true });
        res.status(200).json({ openJobsCount });
        console.log("================================================");
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: "Internal Server Error" });
    }
    console.log("================================================");
}

export async function addJob(req, res) {
    console.log("================================================");
    console.log(
        `(addJob Controller): a new job is being added on ${new Date().toLocaleString()}`
    );
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
        experience,
        jobType
    } = req.body;
    console.log(
        {
            jobTitle,
            jobDescription,
            companyName,
            responsibilities,
            qualifications,
            location,
            salary,
            skills,
            isOpen,
            experience,
        })
    const postedBy = req.body.user._id;

    // Validate required fields
    if (
        !jobTitle ||
        !jobDescription ||
        !companyName ||
        !responsibilities ||
        !qualifications ||
        !location ||
        !salary
    ) {
        return res.status(400).json({ error: "Required fields missing" });
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
        postedBy,
        experience,
        jobType, //
    };
    //Remove null value filleds from object
    Object.keys(jobData).forEach((key) => {
        if (jobData[key] === null || jobData[key] === undefined) {
            delete jobData[key];
        }
    });

    //validtors

    if (!jobData?.jobTitle?.trim()) return res.send({ error: "jobTitle is required" });
    else {

        if (!validator.isLength(jobData.jobTitle, { min: 5, max: 50 }))
            return res.send({ error: "Invalid job title" });
    }

    if (!jobData?.jobDescription?.trim()) return res.send({ error: "jobDescription is required" });

    if (!jobData?.companyName?.trim()) return res.send({ error: "companyName is required" });
    else {

        if (!validator.isLength(jobData.companyName, { min: 3, max: 50 }))
            return res.send({ error: "Invalid company name" });
    }

    if (!jobData?.responsibilities?.trim()) return res.send({ error: "responsibilities is required" });
    if (!jobData?.qualifications?.trim()) return res.send({ error: "qualifications is required" });
    if (!jobData?.location?.trim()) return res.send({ error: "location is required" });

    // Insert job
    try {
        const job = await Job.create(jobData);
        //Log the info:
        console.log("The job has been created: ", job);

        // Send response
        res.status(201).json(job);
        console.log("================================================");
    } catch (err) {
        console.error(err);
        return res.status(500).json({ error: "Error creating job" });
    }

    //send back some response ;
}
