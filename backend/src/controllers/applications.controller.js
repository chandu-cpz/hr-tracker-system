
import { Job } from "../models/jobs.model.js";
import { Application } from "../models/application.model.js"

export default async function createApplication(req, res) {
    console.log("================================")
    console.log("(createApplication Controller): creating a new application");

    const {
        resume,
        jobId,
    } = req.body;
    const job = await Job.findById(jobId).populate('postedBy');
    const postedById = job.postedBy._id;

    console.log("Creating an application with following data")
    const application = {
        resume,
        jobId,
        accepted: false,
        appliedBy: req.body.user._id,
        postedById,
    }
    const newApplication = await Application.create(application);
    console.log(newApplication);
    res.send(newApplication);
}