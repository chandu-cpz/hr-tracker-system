import express from "express";
const router = express.Router();
import { createUser } from "../../controllers/auth/signup.controller.js";

router.post("/", createUser);

export const signUpRouter = router;
