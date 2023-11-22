import express from "express";
import { checkAuth } from "../middlewares/checkAuth.middleware.js";
import { requiredRole } from "../middlewares/requiredRole.middleware.js";
import { createApplication, getApplicants, getSingleApplicationDetails, getEmployees, acceptApplication, rejectApplication, getApplicationCount } from "../controllers/applications.controller.js";


const router = express.Router();

router.post("/", checkAuth, requiredRole("USER"), createApplication);

router.get("/applicants", checkAuth, requiredRole("HR"), getApplicants);

router.post("/details", checkAuth, requiredRole("HR"), getSingleApplicationDetails);

router.get("/employees", checkAuth, requiredRole("HR"), getEmployees);

router.post("/accept", checkAuth, requiredRole("HR"), acceptApplication);

router.post("/reject", checkAuth, requiredRole("HR"), rejectApplication);

router.get("/count", checkAuth, getApplicationCount)

export const applicationRouter = router;