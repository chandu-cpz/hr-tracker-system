import { User } from "../../models/user.model.js";
import { generateSignUpEmailTemplate } from "../../utils/emailGenerator.js";
import sendMail from "../../utils/nodeMailer.js";
import validator from "validator";

export async function createUser(req, res) {
    console.log("================================================");
    console.log(
        ` (createUser controller): We are creating a user on ${new Date().toLocaleString()} `
    );

    //Get all user details from request
    const {
        fullName,
        email,
        password,
        gender,
        role,
        company,
        companyImage,
    } = req.body;

    // Create object with user data
    let userData = {
        fullName,
        email,
        password,
        gender,
        role,
        company,
        companyImage,
    };
    Object.keys(userData).forEach((key) => {
        if (userData[key] === null || userData[key] === undefined) {
            delete userData[key];
        }
    });

    if (!userData?.fullName?.trim()) return res.send({ error: "fullName is required" });
    if (!userData?.email?.trim()) return res.send({ error: "email is required" });
    if (userData?.email?.trim()) {
        if (!validator.isEmail(userData.email)) return res.send({ error: "email is required" });
    }
    if (!userData?.gender?.trim()) return res.send({ error: "gender is required" });
    if (userData?.role == "HR") {
        if (!userData?.company?.trim()) return res.send({ error: "company is required" });
    }
    if (!userData?.password?.trim()) return res.send({ error: "password is required" });
    else {
        const options = {
            minLength: 8,
            minLowercase: 1,
            minUppercase: 1,
        };

        if (!validator.isStrongPassword(userData.password, options)) {
            newErrors.password = "Password is not strong enough";
            isValid = false;
        }
    }

    // check if user exists using email
    const existingUser = await User.findOne({ email });
    if (existingUser) {
        console.log("The user already exists aborting ... ");
        console.log("================================================")
        return res.send({ error: 'User already exists' });
    }

    //Not a existing user so create a new one
    try {
        const user = await User.create(userData);
        console.log("The created user details are: ");
        console.log(user);
        await sendMail(user.email, "Welcome", generateSignUpEmailTemplate(user));

    }
    catch (err) {
        console.log(err.message);
    }
    //send the user details
    res.status(200).send("Completed Creating user");
    console.log("================================================");
}

