import mongoose from "mongoose";

const hrSchema = new mongoose.Schema({
    fullName: {
        type: String,
        required: true,
    }, gender: {
        type: String,
        enum: ['M', 'F', 'O'],
        required: true,
    },
    email: {
        type: String,
        required: true,
        unique: true
    },
    password: {
        type: String,
        required: true
    },
    phoneNumber: {
        type: String,
        required: true,
        unique: true
    },
    company: {
        true: String,
        required: true
    },
    profileImage: {
        type: String
    }
}, {timestamps: true})

export const Hr = new mongoose.model("Hr", hrSchema)