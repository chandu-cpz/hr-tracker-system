import express from "express";
import { checkAuth } from "../middlewares/checkAuth.middleware.js";
import { requiredRole } from "../middlewares/requiredRole.middleware.js";
import createApplication from "../controllers/applications.controller.js";

const router = express.Router();

router.post("/", checkAuth, requiredRole("USER"), createApplication);

export const applicationRouter = router;