import mongoose from 'mongoose';

const jobSchema = new mongoose.Schema({
    appiledBy: [
        {
            type: mongoose.Schema.Types.ObjectId,
            ref: "User"
        }
    ],
    postedBy: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "User"
    },
    jobTitle: {
        type: String,
        required: true,
        lowercase: true
    },
    jobDescription: {
        type: String,
        required: true
    },
    companyName: {
        type: String,
        required: true
    },
    responsibilities: {
        type: String,
        required: true
    },
    qualifications: {
        type: String,
        required: true
    },
    location: {
        type: String,
        required: true
    },
    jobtype: {
        enum: ['FULL_TIME', 'PART_TIME', 'INTERNSHIP']
    },
    noOfPosts: {
        type: String,
        default: "1"
    },
    salary: {
        type: String,
        required: true
    },
    isOpen: {
        type: Boolean,
        default: true
    },
    skills: [{
        type: String,
        required: true
    }],
    
}, {timestamps: true});
export const Job = mongoose.model('Job', jobSchema);