import mongoose from "mongoose";

const applicationSchema = new mongoose.Schema({
    resume: {
        type: String,
        required: true
    },
    accepted: {
        type: Boolean,
        required: true
    },
    appliedBy: [
        {
            type: mongoose.Schema.Types.ObjectId,
            ref: "User"
        }
    ],
    postedBy: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Hr"
    }
}, {timestamps: true})


export const Application = new mongoose.model("Application", applicationSchema);