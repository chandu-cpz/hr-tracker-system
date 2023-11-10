import { User } from "../../models/user.model.js";

export async function createUser(req, res) {
    const {
        fullName,
        email,
        password,
        gender,
        skills,
        phoneNumber,
        address,
        education,
        experience,
        profileImage,
        jobsApplied,
        role,
        company
    } = req.body;

    let userData = {
        fullName,
        email,
        password,
        gender,
    };

    if (company)  userData.company = company;
    if(role) userData.role = role;
    if (skills) {
        userData.skills = skills;
    }
    if (phoneNumber) {
        userData.phoneNumber = phoneNumber;
    }
    if (address) {
        userData.address = address;
    }
    if (education) {
        userData.education = education;
    }
    if (experience) {
        userData.experience = experience;
    }
    if (profileImage) {
        userData.profileImage = profileImage;
    }
    if (jobsApplied) {
        userData.jobsApplied = jobsApplied;
    }

    // check if user exists using email
    const existingUser = await User.findOne({ email });
    if (existingUser) {
        res.status(400).send("Already User exists");
    }

    //Not a existing user so create a new one
    const user = await User.create(userData);

    //Generate a token for the user
    const token = await user.generateAccessToken();

    //setting cookie options
    const options = {
        expires: new Date(Date.now() + Number(process.env.ACCESS_TOKEN_EXPIRY_DAYS)*24* 60 * 60* 1000),
        httpOnly: true,
    };

    //set the token in cookie's
    res.status(200).cookie("token", token, options);

    //send the user details
    res.status(200).json(userData);
}
