import express from 'express'
const router = express.Router()
import { User } from '../../models/user.model.js';

router.post("/", async (req, res) => {
    const { fullName, email, password, gender, skills } = req.body

    if (!(email && password)) {
        res.status(400).send("Provide all necessary details")
    }

    // check if user exists using email
    const existingUser = await User.findOne({ email });
    if (existingUser) {
        res.status(400).send("Already User exists");
    }

    //Not a existing user so create a new one
    const user = await User.create({
        fullName,
        email,
        password,
        gender,
        skills
    });

    //Generate a token for the user
    const token = user.generateAccessToken();

    //setting cookie options
    const options = {
        expires: new Date(Date.now() + 30 *1000),
        httpOnly: true,
    }

    //set the token in cookie's
    res.status(200).cookie("token", token, options)

    //send the user details 
    res.status(200).json({
        fullName: fullName,
        email: email,
        skills: skills,
        gender: gender,
    })

})

export const signUpRouter = router;