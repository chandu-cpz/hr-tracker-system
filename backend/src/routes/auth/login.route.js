import express from "express";
import { loginController } from "../../controllers/auth/login.controller.js";

const router = express.Router();

router.post("/", loginController);

export const loginRouter = router;