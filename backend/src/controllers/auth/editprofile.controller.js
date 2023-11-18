import { User } from "../../models/user.model.js";

export async function updateUser(req, res) {
    console.log("updateUser(updateUser controller): updating user information ");
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
    Object.keys(userData).forEach(key => {
        if (userData[key] === null || userData[key] === undefined) {
            delete userData[key];
        }
    });
    console.log("updating user with email " + req.body.user.email + " with the following data");
    console.log(userData);
    const userId = req.body.user._id;

    const user = await User.findByIdAndUpdate(userId, userData, { new: true });
    console.log("Updated user details are: ")
    console.log(user);
    res.status(200).json(user);
}