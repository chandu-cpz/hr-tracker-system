import express from "express";
export const jobsRouter = express.Router();
import { checkAuth } from "../middlewares/checkAuth.middleware.js";
import { requiredRole } from "../middlewares/requiredRole.middleware.js";
import { getJobs, getSingleJob, openJobsCount } from "../controllers/jobs.controller.js";
import { addJob } from "../controllers/jobs.controller.js";

jobsRouter.get("/", getJobs);

jobsRouter.get("/open", openJobsCount);

jobsRouter.get("/details/:jobId", getSingleJob);

jobsRouter.post("/", checkAuth, requiredRole("HR"), addJob);
