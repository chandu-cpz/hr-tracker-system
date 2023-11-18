import { User } from "../../models/user.model.js";

export async function loginUser(req, res) {
    console.log("================================================")
    console.log(`(loginUser controller) a user is logging in: ${new Date().toLocaleString()}`);
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
        console.log(`User: ${user.fullName} is logged in.`);
        //send the user details
        res.json(user);
        console.log("================================================")
    } else {
        return res.status(401).send("Invalid credentials");
        console.log("================================================")
    }
}
