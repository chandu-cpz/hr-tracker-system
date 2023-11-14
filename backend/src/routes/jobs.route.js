import express from "express";
export const jobsRouter = express.Router();
import { checkAuth } from "../middlewares/checkAuth.middleware.js";
import { requiredRole } from "../middlewares/requiredRole.middleware.js";
import { getJobs } from "../controllers/jobs.controller.js";
import { addJob } from "../controllers/jobs.controller.js";

jobsRouter.get("/", getJobs);

jobsRouter.post("/", checkAuth, requiredRole, addJob);