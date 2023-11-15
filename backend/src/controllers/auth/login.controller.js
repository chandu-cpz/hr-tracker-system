import { User } from "../../models/user.model.js";

export async function loginUser(req, res) {
    console.log("Login user (loginUser controller)");
    const { email, password } = req.body;

    // validation of data
    if (!(email && password)) {
        return res.status(400).send("Either password or email are not provided");
    }

    // find user in db
    const user = await User.findOne({ email });

    // if user not found
    if (!user) {
        return res.status(401).send("User is not registered");
    }

    // match password
    if (user.isPasswordCorrect(password)) {
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
            httpOnly: true,
        };

        //set the token in cookie's
        res.cookie("token", token, options);

        user.password = undefined;

        //send the user details
        res.json(user);
    } else {
        return res.status(401).send("Invalid credentials");
    }
}
