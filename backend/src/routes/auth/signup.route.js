import express from 'express'
const router = express.Router()
import {User} from '../../models/user.model.js';

router.post("/", async (req, res) => {
    const {username, fullName , email, password, gender, skills } = req.body
    
    if(!(username && email && password)){
        res.status(400).send("Provide all necessary details")
    }
    // check if user exists using email

    const existingUser = await User.findOne({email});
    if(existingUser) {
        res.status(400).send("Already User exists");
    }

    const user = await User.create({
        username,
        fullName, 
        email,
        password,
        gender,
        skills
    });
    console.log(user);
    const token = user.generateAccessToken();
    console.log(token)
    res.status(200).send("Signup Succesfull")
    
})

export const signUpRouter = router;