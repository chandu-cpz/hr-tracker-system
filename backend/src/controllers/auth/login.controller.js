import { User } from "../../models/user.model.js";

export async function loginController(req, res) {
    const { email, password } = req.body;

    // validation of data
    if (!(email && password)) {
        return res.status(400).send("Provide all necessary details");
    }

    // find user in db
    const user = await User.findOne({ email });

    // if user not found
    if (!user) {
        return res.status(401).send("Invalid user");
    }

    // match password
    if (user.isPasswordCorrect(password)) {
        // Generate and send token
        const token = user.generateAccessToken();

        //setting cookie options
        const options = {
            expires: new Date(Date.now() + Number(process.env.ACCESS_TOKEN_EXPIRY_DAYS)*24* 60 * 60* 1000),
            httpOnly: true,
        };

        //set the token in cookie's
        res.status(200).cookie("token", token, options);

        user.password = undefined;

        //send the user details
        res.status(200).json(user);
    } else {
        return res.status(401).send("Invalid credentials");
    }
}
