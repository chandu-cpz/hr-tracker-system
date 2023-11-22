
import { Job } from "../models/jobs.model.js";
import { Application } from "../models/application.model.js"
import sendMail from "../utils/nodeMailer.js";
import { generateAcceptedEmail, generateApplicationEmail, generateRejectedEmail } from "../utils/emailGenerator.js";

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
		accepted: "PENDING",
		appliedBy: req.body.user._id,
		postedBy: postedById,
	}
	const newApplication = await Application.create(application);
	console.log(newApplication);
	const createdApplication = await Application.findById(newApplication._id).populate("jobId")
	sendMail(req.body.user.email, "New Application Recieved", generateApplicationEmail(createdApplication));
	res.send(newApplication);
}

export async function getApplicants(req, res) {
	console.log("================================")
	console.log("(getApplicants Controller): getting applicants");

	const applications = await Application.find({ accepted: "PENDING", postedBy: req.body.user._id }).populate('appliedBy');
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
	const applications = await Application.find({ accepted: "ACCEPTED", postedBy: req.body.user._id }).populate('appliedBy');
	console.log(applications);
	res.json(applications);
}

export async function acceptApplication(req, res) {
	console.log("================================")
	console.log("(acceptApplication Controller): Accepting application with Id", req.body);
	const { applicationId } = req.body
	const application = await Application.findByIdAndUpdate(
		applicationId,
		{ accepted: "ACCEPTED" },
		{
			new: true,
			populate: [
				{
					path: 'appliedBy',
					select: 'fullName email'
				},
				{
					path: 'jobId',
					select: 'jobTitle'
				}
			]
		}
	);
	console.log(application);
	sendMail(application.appliedBy.email, "Hurray, Application Accepted", generateAcceptedEmail(application));
	res.json(application);
}

export async function rejectApplication(req, res) {
	console.log("================================")
	console.log("(rejectApplication Controller): Rejecting application with Id", req.body);
	const { applicationId } = req.body
	const application = await Application.findByIdAndUpdate(
		applicationId,
		{ accepted: "REJECTED" },
		{
			new: true,
			populate: [
				{
					path: 'appliedBy',
					select: 'fullName email'
				},
				{
					path: 'jobId',
					select: 'jobTitle'
				}
			]
		}
	);
	console.log(application);
	sendMail(application.appliedBy.email, "Application Update", generateRejectedEmail(application));
	res.json(application);
}

export async function getApplicationCount(req, res) {
	console.log("================================")
	console.log("(applicationCount): getting application counts");
	if (req.body.user.role === "HR") {
		const hrId = req.body.user._id;
		let pending, accepted, rejected;
		let maleCount = 0;
		let femaleCount = 0;
		let othersCount = 0;
		try {
			await Application.find({ postedBy: hrId })
				.then(applications => {
					pending = applications.filter(app => app.accepted === 'PENDING').length;
					accepted = applications.filter(app => app.accepted === 'ACCEPTED').length;
					rejected = applications.filter(app => app.accepted === 'REJECTED').length;
				})
			const result = await Application.aggregate([
				{
					$match: {
						postedBy: hrId,
						accepted: "ACCEPTED"
					}
				},
				{
					$lookup: {
						from: "users",
						localField: "appliedBy",
						foreignField: "_id",
						as: "applicant"
					}
				},
				{
					$unwind: "$applicant"
				},
				{
					$group: {
						_id: "$applicant.gender",
						count: { $sum: 1 }
					}
				}
			])

			result.forEach(item => {
				if (item._id === 'M') {
					maleCount = item.count;
				}
				if (item._id === 'F') {
					femaleCount = item.count;
				}
				if (item._id === 'O') {
					othersCount = item.count;
				}
			})

			const jobTitlesInfo = await Application.aggregate([
				{
					$match: {
						postedBy: hrId,
						accepted: "ACCEPTED"
					}
				},
				{
					$lookup: {
						from: "jobs",
						localField: "jobId",
						foreignField: "_id",
						as: "job"
					}
				},
				{
					$unwind: "$job"
				},
				{
					$group: {
						_id: "$job.jobTitle",
						count: { $sum: 1 }
					}
				}
			]);
			console.log(jobTitlesInfo)

			res.json({
				applications: pending,
				employees: accepted,
				rejected: rejected,
				male: maleCount,
				female: femaleCount,
				others: othersCount,
				stats: jobTitlesInfo
			});
			console.log("================================")
		}
		catch (err) {
			console.log(err.message);
		}
	}
	else {
		const userId = req.body.user._id;
		let pending, rejected, accepted;
		try {
			await Application.find({ appliedBy: userId })
				.then(applications => {
					pending = applications.filter(app => app.accepted === 'PENDING').length;
					accepted = applications.filter(app => app.accepted === 'ACCEPTED').length;
					rejected = applications.filter(app => app.accepted === 'REJECTED').length;
				})
			res.json({
				applications: pending,
				accepted: accepted,
				rejected: rejected,
			});
			console.log("================================")
		}
		catch (err) {
			console.log(err.message);
		}
	}

}