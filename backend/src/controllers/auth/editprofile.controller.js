import { User } from "../../models/user.model.js";
import validator from "validator";

export async function updateUser(req, res) {
    console.log("================================================");
    console.log(`updateUser(updateUser controller): updating user information ${new Date().toLocaleString()}`);

    // Get all user details from request
    const {
        fullName,
        gender,
        skills,
        phoneNumber,
        address,
        education,
        experience,
        profileImage,
        email, // Extract email from request body
    } = req.body;

    // Create an object containing the information
    let userData = {
        fullName,
        gender,
        skills,
        phoneNumber,
        address,
        education,
        experience,
        profileImage,
    };

    // Validate the fields
    const { errors, isValid } = validateForm(userData, email);
    if (!isValid) {
        return res.status(400).json({ errors });
    }

    // Delete all null and undefined values
    Object.keys(userData).forEach(key => {
        if (userData[key] === null || userData[key] === undefined) {
            delete userData[key];
        }
    });

    console.log("Updating user with email " + email + " with the following data");
    console.log(userData);

    const userId = req.body.user._id;

    // Update the user details
    try {
        const user = await User.findByIdAndUpdate(userId, userData, { new: true });
        console.log("Updated user details are:");
        console.log(user);
        res.status(200).json(user);
    } catch (error) {
        console.error("Error updating user:", error);
        res.status(500).json({ error: "Internal Server Error" });
    }
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
