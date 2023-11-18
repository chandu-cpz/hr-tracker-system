import express from "express";
import { updateUser } from "../../controllers/auth/editprofile.controller.js";
import { checkAuth } from "../../middlewares/checkAuth.middleware.js";


const router = express.Router();

router.patch("/", checkAuth, updateUser)

export const updateUserRouter = router;