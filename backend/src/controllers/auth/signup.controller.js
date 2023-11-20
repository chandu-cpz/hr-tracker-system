import { User } from "../../models/user.model.js";

export async function createUser(req, res) {
    console.log("================================================")
    console.log(` (createUser controller): We are creating a user on ${new Date().toLocaleString()} `);

    //Get all user details from request
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
        company,
    } = req.body;

    // Create object with user data
    let userData = {
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
        company,
    };

    //Delete all null and undefined values
    Object.keys(userData).forEach(key => {
        if (userData[key] === null || userData[key] === undefined) {
            delete userData[key];
        }
    });

    console.log("The user details are: ");
    console.log(userData);


    // check if user exists using email
    const existingUser = await User.findOne({ email });
    if (existingUser) {
        console.log("The user already exists aborting ... ")
        return res.status(400).send("Already User exists");
    }



    //Not a existing user so create a new one
    const user = await User.create(userData);

    // //Generate a token for the user
    // const token = await user.generateAccessToken();

    // //setting cookie options
    // const options = {
    //     expires: new Date(
    //         Date.now() +
    //         Number(process.env.ACCESS_TOKEN_EXPIRY_DAYS) *
    //         24 *
    //         60 *
    //         60 *
    //         1000
    //     ),
    //     httpOnly: true,
    // };

    // //set the token in cookie's
    // res.cookie("token", token, options);

    //send the user details
    res.status(200).send("Completed Creating user")
    console.log("================================================")
}
