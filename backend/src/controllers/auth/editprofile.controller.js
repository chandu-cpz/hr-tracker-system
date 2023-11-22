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
        email,
    };

    // Delete all null and undefined values
    Object.keys(userData).forEach(key => {
        if (userData[key] === null || userData[key] === undefined) {
            delete userData[key];
        }
    });

    console.log(userData);

    // Validate the fields
    if (!userData?.fullName?.trim())
        return res.send({ error: "fullName is required" });
    else {
        if (!validator.isLength(userData.fullName, { min: 3, max: 50 }))
            return res.send({ error: "Invalid fullName" });
    }

    if (!userData?.email?.trim())
        return res.send({ error: "email is required" });

    if (userData?.email?.trim()) {
        if (!validator.isEmail(userData.email))
            return res.send({ error: "Invalid email" });
    }

    if (!validator.isMobilePhone(userData.phoneNumber))
        return res.send({ error: "Invalid phone number" });

    console.log("Updating user with email " + email + " with the following data");
    console.log(userData);

    const userId = req.body.user._id;

    // Update the user details
    try {
        const user = await User.findByIdAndUpdate(userId, userData, { new: true });
        console.log("Updated user details are:");
        console.log(user);
        res.status(200).json(user);
        console.log("================================================");
    } catch (error) {
        console.error("Error updating user:", error);
        res.status(200).json({ error: "Internal Server Error" });
        console.log("================================================");
    }
}

