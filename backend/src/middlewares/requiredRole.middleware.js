// Require role middleware
import { User } from "../models/user.model.js";
export const requiredRole = (role) => {

    return async (req, res, next) => {

        console.log(`(requiredRole middleware): Checking if user got required role`);

        if (role == req.body.user.role) next();
        else return res.status(403).json({ error: "Unauthorized: You do not have enough permissions to do the actin" });
    }
}
