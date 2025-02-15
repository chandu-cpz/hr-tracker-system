import cloudinary from '../utils/cloudinary.js';

export const getSignedUploadPreset = (req, res) => {
    console.log("================================================")
    console.log(`(getSignedUploadPreset Controller ): We got a signed upload preset request ${new Date().toLocaleString()}`)
    console.log("We got a signed upload preset request for folder" + req.params.folder)

    // Create timestamp for cloudinary upload preset
    const timestamp = Math.round((new Date).getTime() / 1000);

    // Create singed preset, 
    const signedPreset = cloudinary.utils.api_sign_request({
        folder: req.params.folder,
        timestamp
    }, cloudinary.config().api_secret);
    console.log(signedPreset);
    console.log(timestamp);
    //send sined preset and timestamp to client
    return res.json({
        signedPreset,
        timestamp
    });
    console.log("================================================");
}