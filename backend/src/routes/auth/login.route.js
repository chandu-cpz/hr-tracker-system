import express from "express";

const router = express.Router();
import { User } from "../../models/user.model.js";

router.post("/", async (req, res) => {
    // get data from server
    const { email, password } = req.body;

    // validation of data
    if (!(email && password)) {
        return res.status(400).send("Provide all necessary details");
    }

    try {
        // find user in db
        const user = await User.findOne({ email });

        // if user not found
        if (!user) {
            return res.status(401).send("Invalid user");
        }
    } catch (error) {
        console.error(error); // Log the actual error
        return res.status(500).send("something went wrong");
    }

    // match password
    if (user.isPasswordCorrect(password)) {
        // Generate and send token
        const token = user.generateAccessToken();
        return res.status(200).send(token);
    } else {
        return res.status(401).send("Invalid credentials");
    }
});

export const loginRouter = router;
