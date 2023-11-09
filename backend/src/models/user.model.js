import mongoose from 'mongoose';

const userSchema = new mongoose.Schema({
    userName: {
        type: String,
        required: true,
        unique: true,
        lowercase: true
    },
    fullName: {
        type: String,
        required: true
    },
    gender: {
        type: String,
        enum: ['M', 'F', 'O'],
        required: true,
    },
    password: {
        type: String,
        required: true
    },
    phoneNumber: {
        type: String,
        unique: true
    },
    email: {
        type: String,
        required: true,
        unique: true
    },
    address: {
        type: String,
    },
    education: {
        type: String,
    },
    skills: [
        {
            type: String,
            required: true
        }
    ],
    experience: {
        type: String,
        default: null
    },
    profileImage: {
        type: String
    },
    jobsApplied: [
        {
            type: mongoose.Schema.Types.ObjectId,
            ref: 'Job'
        }
    ]

}, { timestamps: true });

export const user = mongoose.model('User', userSchema);