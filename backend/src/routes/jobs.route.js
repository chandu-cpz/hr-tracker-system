import express from "express";
export const jobsRouter = express.Router();
import { checkAuth } from "../middlewares/checkAuth.middleware.js";
import { requiredRole } from "../middlewares/requiredRole.middleware.js";
import {
    deleteSavedJob,
    getJobs,
    getSingleJob,
    openJobsCount,
    saveJob,
    recommendJobs,
    calculateATS
} from "../controllers/jobs.controller.js";
import { addJob } from "../controllers/jobs.controller.js";

jobsRouter.get("/", getJobs);

jobsRouter.post("/savejob", checkAuth, saveJob);

jobsRouter.delete("/savejob", checkAuth, deleteSavedJob);

jobsRouter.get("/open", openJobsCount);

jobsRouter.get("/:jobId", getSingleJob);

jobsRouter.get("/details/:jobId", getSingleJob);

jobsRouter.post("/", checkAuth, requiredRole("HR"), addJob);

jobsRouter.post("/recommendations",recommendJobs)

jobsRouter.post('/calculate-ats', calculateATS); 