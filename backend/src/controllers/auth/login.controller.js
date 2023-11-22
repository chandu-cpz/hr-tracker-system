import { User } from "../../models/user.model.js";
import validator from "validator";

export async function loginUser(req, res) {
    console.log("================================================")
    console.log(`(loginUser controller) a user is logging in: ${new Date().toLocaleString()}`);

    //Get all user details from request
    const { email, password } = req.body;

    const userData = {
        email: email,
        password: password
    }

    if (!userData?.email?.trim()) return res.send({ error: "email is required" });
    else {
        if (!validator.isEmail(userData.email)) return res.send({ error: "email is required" });
    }

    // find user in db
    const user = await User.findOne({ email });

    //Not registered so send that back
    if (!user) {
        return res.send({ error: "User is not registered" });
    }

    console.l
    // Check if password is correct
    const isPasswordCorrect = await user.isPasswordCorrect(userData.password)
    if (isPasswordCorrect) {

        // Generate and send token
        const token = user.generateAccessToken();

        //setting cookie options
        const options = {
            expires: new Date(
                Date.now() +
                Number(process.env.ACCESS_TOKEN_EXPIRY_DAYS) *
                24 *
                60 *
                60 *
                1000
            ),
            httpOnly: true, //httpOnly so that user can't play around with token (security)
        };

        //set the token in cookie
        res.cookie("token", token, options);

        //delete password from user object
        user.password = undefined;
        console.log(`User: ${user.fullName} is logged in.`);

        //send the user details to client
        res.json(user);
        console.log("================================================")
    } else {
        return res.send({ error: "Invalid credentials" });
        console.log("================================================")
    }
}

