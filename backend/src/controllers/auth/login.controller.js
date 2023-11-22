import { User } from "../../models/user.model.js";

export async function loginUser(req, res) {
    console.log("================================================")
    console.log(`(loginUser controller) a user is logging in: ${new Date().toLocaleString()}`);

    //Get all user details from request
    const { email, password } = req.body;

    // validation of data
    if (!(email && password)) {
        return res.status(400).send("Either password or email are not provided");
    }

    // find user in db
    const user = await User.findOne({ email });

    //Not registered so send that back
    if (!user) {
        return res.status(401).send("User is not registered");
    }

    if (!userData?.email?.trim()) return res.send({ error: "email is required" });
    else{
        if (!validator.isEmail(userData.email)) return res.send({ error: "email is required" });
    }
    
    if(!userData?.password?.trim()) return res.send({ error: "password is required" });
    else{
        const options = {
            minLength: 8, 
            minLowercase: 1,
            minUppercase: 1,
            minNumbers: 1,
            minSymbols: 1,
        };
        if (!validator.isStrongPassword(userData.password, options)) return res.send({ error: "password is not strong enough" });
    }

    // Check if password is correct
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
        return res.status(401).send("Invalid credentials");
        console.log("================================================")
    }
}
    
