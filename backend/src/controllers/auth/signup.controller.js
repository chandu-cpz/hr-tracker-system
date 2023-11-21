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
        skills,
        phoneNumber,
        address,
        education,
        experience,
        profileImage,
        jobsApplied,
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
        skills,
        phoneNumber,
        address,
        education,
        experience,
        profileImage,
        jobsApplied,
        role,
        company,
        companyImage,
    };

    //validate fileds
    const { errors, isValid } = validateForm(userData, email);
    if (!isValid) {
        return res.status(400).json({ errors });
    }

    //Delete all null and undefined values
    Object.keys(userData).forEach((key) => {
        if (userData[key] === null || userData[key] === undefined) {
            delete userData[key];
        }
    });

    console.log("The user details are: ");
    console.log(userData);

    // check if user exists using email
    const existingUser = await User.findOne({ email });
    if (existingUser) {
        console.log("The user already exists aborting ... ");
        return res.status(400).send("Already User exists");
    }

    //Not a existing user so create a new one
    const user = await User.create(userData);

    sendMail(user.email, "Welcome", generateSignUpEmailTemplate(user));

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
    res.status(200).send("Completed Creating user");
    console.log("================================================");
}

function validateForm(userData, email) {
    let isValid = true;
    const errors = {};

    if (!validator.isEmail(email)) {
        errors.email = "Invalid email";
        isValid = false;
    } else {
        errors.email = "";
    }

    if (!validator.isMobilePhone(userData.phoneNumber)) {
        errors.phoneNumber = "Invalid phone number";
        isValid = false;
    } else {
        errors.phoneNumber = "";
    }

    const options = {
        minLength: 8, 
        minLowercase: 1,
        minUppercase: 1,
        minNumbers: 1,
        minSymbols: 1,
    };

    if (!validator.isStrongPassword(userData.password, options)) {
        errors.password = "Password is not strong enough";
        isValid = false;
    } else {
        errors.password = "";
    }

    if (!validator.isString(userData.fullName)) {
        errors.fullName = "Full name must be a string";
        isValid = false;
    } else {
        errors.fullName = "";
    }

    if (!validator.isString(userData.companyName)) {
        errors.fullName = "company name must be a string";
        isValid = false;
    } else {
        errors.fullName = "";
    }

    function isAddress(input) {
        const addressRegex = /^[\w\s\d,-]+$/; 
         return addressRegex.test(input);
    }

    if (isAddress(userData.address)) {
        console.log('Valid address!');
    } else {
        console.log('Not a valid address!');
    }

    return { errors, isValid };
}
