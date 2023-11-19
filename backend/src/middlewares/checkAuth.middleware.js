// authMiddleware
import { User } from "../models/user.model.js";
import jwt from "jsonwebtoken";

export async function checkAuth(req, res, next) {
    console.log(`(checkAuth middleware): Checking if user is authorized `)

    //Get the token from user cookies
    const token = req.cookies.token;

    // Check if user has token 
    if (!token) {
        return res
            .status(401)
            .json({ message: "No token in cookies, Please Login" });
    }

    try {
        //Verify the token 
        const decoded = jwt.verify(token, process.env.ACCESS_TOKEN_SECRET);

        //Get the user from decoded token
        const user = await User.findById(decoded._id);

        if (!user) {
            return res.status(401).json({ message: "Cannot find the user with such token" });
        }

        // Add the user to  req body 
        req.body.user = user;
        next();
    } catch (err) {
        console.error(err);
        return res.status(401).json({ message: "Invalid user" });
    }
}
