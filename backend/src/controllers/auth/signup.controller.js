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

    try {  // Add a try-catch block encompassing the entire function logic

        if (!userData?.fullName?.trim()) return res.status(400).send({ error: "fullName is required" });
        if (!userData?.email?.trim()) return res.status(400).send({ error: "email is required" });
        if (userData?.email?.trim()) {
            if (!validator.isEmail(userData.email)) return res.status(400).send({ error: "Invalid email format" });
        }
        if (!userData?.gender?.trim()) return res.status(400).send({ error: "gender is required" });
        if (userData?.role == "HR") {
            if (!userData?.company?.trim()) return res.status(400).send({ error: "company is required for HR role" });
        }
        if (!userData?.password?.trim()) return res.status(400).send({ error: "password is required" });
        else {
            const options = {
                minLength: 8,
                minLowercase: 1,
                minUppercase: 1,
            };

            if (!validator.isStrongPassword(userData.password, options)) {
                return res.status(401).send({ error: "Password is not strong enough" });
            }
        }

        // check if user exists using email
        const existingUser = await User.findOne({ email });
        if (existingUser) {
            console.log("The user already exists aborting ... ");
            console.log("================================================")
            return res.status(409).send({ error: 'User already exists' });  // 409 Conflict
        }

        //Not a existing user so create a new one
        try {
            const user = await User.create(userData);
            console.log("The created user details are: ");
            console.log(user);
            await sendMail(user.email, "Welcome", generateSignUpEmailTemplate(user.fullName));

            //send the user details
            res.status(201).send({ message: "User created successfully" }); // 201 Created
            console.log("================================================");
        } catch (dbError) {
            console.error("Database error:", dbError);
            return res.status(500).send({ error: "Failed to create user in database", details: dbError.message });  // 500 Internal Server Error
        }


    } catch (err) {
        console.error("General error:", err); // Log the error for debugging.
        return res.status(500).send({ error: "An unexpected error occurred", details: err.message }); // Respond with an error message.
    }
}