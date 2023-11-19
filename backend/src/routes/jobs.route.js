import express from "express";
export const jobsRouter = express.Router();
import { checkAuth } from "../middlewares/checkAuth.middleware.js";
import { requiredRole } from "../middlewares/requiredRole.middleware.js";
import { getJobs, getSingleJob, openJobsCount, saveJob } from "../controllers/jobs.controller.js";
import { addJob } from "../controllers/jobs.controller.js";

jobsRouter.get("/", getJobs);

jobsRouter.get("/:jobId", getSingleJob);

jobsRouter.post("/savejob",checkAuth,saveJob);

jobsRouter.get("/open", openJobsCount);

jobsRouter.post("/", checkAuth, requiredRole, addJob);
