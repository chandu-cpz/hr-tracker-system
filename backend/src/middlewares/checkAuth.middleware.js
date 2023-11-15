// authMiddleware
import { User } from "../models/user.model.js";
import jwt from "jsonwebtoken";

export async function checkAuth(req, res, next) {
    console.log("Checking if user is authorized (checkAuth middleware)")
    const token = req.cookies.token;

    if (!token) {
        return res
            .status(401)
            .json({ message: "No token in cookies, Please Login" });
    }

    try {
        const decoded = jwt.verify(token, process.env.ACCESS_TOKEN_SECRET);
        const user = await User.findById(decoded._id);

        if (!user) {
            return res.status(401).json({ message: "Cannot find the user with such token" });
        }

        req.body.user = user;
        next();
    } catch (err) {
        console.error(err);
        return res.status(401).json({ message: "Invalid user" });
    }
}
