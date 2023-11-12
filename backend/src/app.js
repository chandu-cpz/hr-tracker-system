import express from "express";
import cors from "cors";
import cookieParser from "cookie-parser";
import { signUpRouter } from "./routes/auth/signup.route.js";
import { loginRouter } from "./routes/auth/login.route.js";
import { jobsRouter } from "./routes/jobs.route.js";

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

app.on("error", (error) => {
    console.log("ERRR: ", error);
    throw error;
});

export { app };
