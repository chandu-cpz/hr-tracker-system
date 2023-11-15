// Require role middleware
import { User } from "../models/user.model.js";
export async function requiredRole(req, res, next) {
    console.log("Checking if user got required role(requiredRole middleware)");
    const { _id } = req.body.user;
    console.log(_id);

    try {
        const user = await User.findOne({ _id: _id });

        if (!user || user.role !== "HR") {
            return res
                .status(403)
                .json({
                    error: "Unauthorized: Only HR can perform this action.",
                });
        }

        next();
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: "Internal Server Error" });
    }
}
