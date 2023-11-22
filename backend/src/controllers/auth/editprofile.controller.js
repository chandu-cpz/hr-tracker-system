import { User } from "../../models/user.model.js";

export async function updateUser(req, res) {
    console.log("================================================");
    console.log(`updateUser(updateUser controller): updating user information ${new Date().toLocaleString()}`);

    //Get all user details from request
    const {
        fullName,
        gender,
        skills,
        phoneNumber,
        address,
        education,
        experience,
        profileImage,
    } = req.body;

    //Create a object containing the information
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

    //Delete all null and undefined values
    Object.keys(userData).forEach(key => {
        if (userData[key] === null || userData[key] === undefined) {
            delete userData[key];
        }
    });

    console.log("updating user with email " + req.body.user.email + " with the following data");
    console.log(userData);
    const userId = req.body.user._id;

    //Update the user details
    const user = await User.findByIdAndUpdate(userId, userData, { new: true });
    console.log("Updated user details are: ")
    console.log(user);
    res.status(200).json(user);
}