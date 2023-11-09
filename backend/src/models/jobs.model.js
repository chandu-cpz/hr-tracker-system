import mongoose from 'mongoose';

const jobSchema =new mongoose.Schema({
    id: {
        type: String,
        required: true,
        unique: true,
        lowercase: true
    },
    AppiledBy:[
        {
            type: mongoose.Schema.Types.ObjectId,
            ref: 'User'
        }
    ],
    jobName:{
        type:String,
        required:true,
        lowercase: true
    },
    jobDescription:{
        type:String,
        required:true
    },
    companyName:{
        type:String,
        required:true
    },
    responsibiliut:{
        type:String,
        required:true
    },
    qualifiactions:{
        type:String,
        required:true
    },
    loction:{
        type:String,
        required:true
    },
    jobtype:{
        enum:['Full Time','Part Time','Internship']
    },
    NoofPosts:{
        type:String,
        required:true
    },
    Salary:{
        type:String,
        required:true
    },
    isOpen:{
        type:Boolean,
        required:true
    },
    skills:[{
        type:String,
        required:true
    }],
    timestamps: true 
});
export const Jobs = mongoose.model('Job', jobSchema);