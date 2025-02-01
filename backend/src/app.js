import express from "express";
import cors from "cors";
import cookieParser from "cookie-parser";
import { signUpRouter } from "./routes/auth/signup.route.js";
import { loginRouter } from "./routes/auth/login.route.js";
import { jobsRouter } from "./routes/jobs.route.js";
import { updateUserRouter } from "./routes/auth/editprofile.route.js";
import cloudinaryRouter from "./routes/cloudinary.route.js";
import { applicationRouter } from "./routes/application.route.js";
const app = express();

app.use(
    cors({
        origin: process.env.CORS_ORIGIN,
        credentials: true,
    })
);

app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(express.static("public"));
app.use(cookieParser());

app.use("/api/signup", signUpRouter);

app.use("/api/login", loginRouter);
app.use("/api/jobs", jobsRouter);

app.use("/api/signed-upload", cloudinaryRouter);
app.use("/api/updateuser", updateUserRouter);
app.use("/api/application", applicationRouter);

app.on("error", (error) => {
    console.log("ERRR: ", error);
    throw error;
});

export { app };
