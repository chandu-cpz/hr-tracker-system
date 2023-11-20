import axios from "axios";

const uploadFile = async (file, folder) => {

    console.log(`${file.name} is being uploaded ${folder}`)

    // Get signed upload preset
    const uploadPreset = await axios.get('/api/signed-upload/' + folder).then((response) => response.data);
    console.log("the uploader preset is: ")
    console.log(uploadPreset);
    // Create form data 
    const formData = new FormData();
    formData.append('file', file);
    formData.append('signature', uploadPreset.signedPreset);
    formData.append('folder', folder);
    formData.append("timestamp", uploadPreset.timestamp);
    formData.append("api_key", import.meta.env.VITE_CLOUDINARY_API_KEY);


    // Make upload request
    const uploadRes = await axios.post(
        `https://api.cloudinary.com/v1_1/${import.meta.env.VITE_CLOUDINARY_CLOUD_NAME}/auto/upload`,
        formData
    );

    console.log(uploadRes.data)

    console.log("Finished uploading")

    return uploadRes.data; // Return uploaded file data

}

export default uploadFile;