import cloudinary from '../utils/cloudinary.js';

export const getSignedUploadPreset = (req, res) => {

    console.log(cloudinary.config().api_key)
    console.log("We got a signed upload preset request for folder" + req.params.folder)

    // Expire in 5 minutes
    const expireInSeconds = 60 * 5;
    const expiresAt = Date.now() / 1000 + expireInSeconds;
    const timestamp = Math.round((new Date).getTime() / 1000);

    const signedPreset = cloudinary.utils.api_sign_request({
        folder: req.params.folder,
        timestamp
    }, cloudinary.config().api_secret);

    console.log(signedPreset);

    return res.json({
        signedPreset,
        timestamp
    });

}