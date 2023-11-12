// authMiddleware
import { User } from "../models/user.model.js";

export async function checkAuth(req, res, next) {
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
            return res.status(401).json({ message: "Invalid user" });
        }

        req.user = user;
        next();
    } catch (err) {
        console.error(err);
        return res.status(401).json({ message: "Invalid user" });
    }
}
