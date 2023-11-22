import express from "express";
import { logOut, loginUser, autoLoginUser } from "../../controllers/auth/login.controller.js";
import { checkAuth } from "../../middlewares/checkAuth.middleware.js";

const router = express.Router();

router.post("/", loginUser);
router.get("/", logOut);
router.get("/auto", checkAuth, autoLoginUser);

export const loginRouter = router;
