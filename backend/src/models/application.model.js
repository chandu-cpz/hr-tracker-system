import mongoose from "mongoose";
import { User } from "./user.model.js"

const applicationSchema = new mongoose.Schema(
    {
        resume: {
            type: String,
            required: true,
        },
        accepted: {
            type: String,
            enum: ["PENDING", "ACCEPTED", "REJECTED"],
            default: "PENDING",
        },
        jobId: {
            type: mongoose.Schema.Types.ObjectId,
            ref: "Job",
        },
        appliedBy: {
            type: mongoose.Schema.Types.ObjectId,
            ref: "User",
        },
        postedBy: {
            type: mongoose.Schema.Types.ObjectId,
            ref: "User",
        },
    },
    { timestamps: true }
);

applicationSchema.pre('save', function (next) {


    // Add job to user logic...

    User.findById(this.appliedBy)
        .then(user => {
            user.jobsApplied.push(this.jobId);
            user.save();
        })
        .then(() => next());

});

export const Application = new mongoose.model("Application", applicationSchema);
