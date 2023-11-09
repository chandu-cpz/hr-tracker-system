import express from 'express'
const router = express.Router()
import { signUpController } from '../../controllers/auth/signup.controller.js';

router.post("/", signUpController)

export const signUpRouter = router;