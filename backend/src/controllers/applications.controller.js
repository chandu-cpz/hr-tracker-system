
import { Job } from "../models/jobs.model.js";
import { Application } from "../models/application.model.js"

export async function createApplication(req, res) {
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
        postedBy: postedById,
    }
    const newApplication = await Application.create(application);
    console.log(newApplication);
    res.send(newApplication);
}

export async function getApplicants(req, res) {
    console.log("================================")
    console.log("(getApplicants Controller): getting applicants");

    const applications = await Application.find({ accepted: false, postedBy: req.body.user._id }).populate('appliedBy');
    console.log(applications);
    res.json(applications);
}


export async function getSingleApplicationDetails(req, res) {
    console.log("================================")
    console.log("(getSingleApplicationDetails Controller): Getting application details" + req.body.applicationId);
    const { applicationId } = req.body
    const applications = await Application.findById(applicationId).populate('appliedBy');
    console.log(applications);
    res.json(applications);
}

export async function getEmployees(req, res) {
    console.log("================================")
    console.log("(getEmployees Controller): getting employees");
    const applications = await Application.find({ accepted: true, postedBy: req.body.user._id }).populate('appliedBy');
    console.log(applications);
    res.json(applications);
}

export async function acceptApplication(req, res) {
    console.log("================================")
    console.log("(acceptApplication Controller): Accepting application with Id", req.body);
    const { applicationId } = req.body
    const application = await Application.findByIdAndUpdate(applicationId, { accepted: true }, { new: true });
    res.json(application);
}

export async function rejectApplication(req, res) {
    console.log("================================")
    console.log("(rejectApplication Controller): Rejecting application with Id", req.body);
    const { applicationId } = req.body
    const application = await Application.findByIdAndUpdate(applicationId, { accepted: false }, { new: true });
    res.json(application);
}