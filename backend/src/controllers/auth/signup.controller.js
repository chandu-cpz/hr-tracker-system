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
            minNumbers: 1,
            minSymbols: 1,
        };

        if (!validator.isStrongPassword(userData.password, options)) {
            newErrors.password = "Password is not strong enough";
            isValid = false;
        }
    }

    console.log(userData);

    console.log("The user details are: ");
    console.log(userData);

    // check if user exists using email
    const existingUser = await User.findOne({ email });
    if (existingUser) {
        console.log("The user already exists aborting ... ");
        console.log("================================================")
        return res.send({ error: 'User already exists' });
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

