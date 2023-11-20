import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { UserSidebar } from "../userDashboard/UserSidebar";
import {
    FaMapMarkerAlt,
    FaCalendarAlt,
    FaDollarSign,
    FaBuilding,
    FaPaperclip,
} from "react-icons/fa";
import uploadFile from "../../utils/uploadFile";
import { useDispatch, useSelector } from "react-redux";
import { setUser } from "../../redux/slice/userSlice";

export function ApplyJob() {
    const user = useSelector((state) => state.user);
    const { jobId } = useParams();
    const [jobDetails, setJobDetails] = useState({});
    const [resume, setResume] = useState();
    const dispatch = useDispatch();

    const fetchJobDetails = async (jobId) => {
        try {
            const { data } = await axios.get(`/api/jobs/details/${jobId}`);
            setJobDetails(data);
        } catch (error) {
            console.log(error);
        }
    };

    useEffect(() => {
        fetchJobDetails(jobId);
    }, []);

    const submitApplication = async () => {
        console.log(resume);
        try {
            await axios
                .post("/api/application", {
                    jobId: jobId,
                    resume: resume,
                })
                .then();
        } catch (err) {
            console.log(err);
        }
        dispatch(
            setUser({
                ...user,
                jobsApplied: user.jobsApplied.push(jobId),
            })
        );
    };

    const handleUploadFile = async (e) => {
        const response = await uploadFile(e.target.files[0], "resumes");
        setResume(response.secure_url);
        console.log("Finished Uploading resume");
    };

    function timeSincePosted(date) {
        const now = new Date();
        const posted = new Date(date);
        const diff = now - posted;

        // Calculate days
        const days = Math.floor(diff / (1000 * 60 * 60 * 24));

        // Get remainder hours
        const hours = Math.floor(diff / (1000 * 60 * 60)) % 24;

        // Get remainder minutes
        const mins = Math.floor(diff / (1000 * 60)) % 60;

        let timeSince = "";

        if (days > 0) {
            timeSince += `${days} day${days > 1 ? "s" : ""}`;
        }

        if (hours > 0) {
            timeSince += `${hours} hour${hours > 1 ? "s" : ""}`;
        }

        if (mins > 0) {
            timeSince += `${mins} minute${mins > 1 ? "s" : ""}`;
        }

        return timeSince;
    }

    return (
        <div className="tw-bg-gray-200 tw-flex tw-min-h-screen tw-p-4 tw-text-xl">
            {/* Sidebar */}
            <UserSidebar />

            {/* Main Content */}
            <div className=" tw-ml-15 tw-flex-1 tw-p-8">
                {/* Job Details Card */}
                <div className="max-w-4xl tw-mx-auto tw-rounded-lg tw-bg-white tw-p-6 tw-shadow-lg">
                    <div className="tw-mx-6 tw-flex tw-justify-between">
                        {jobDetails.jobTitle && (
                            <h1 className="tw-text-gray-900 tw-mb-4 tw-justify-self-center tw-text-3xl tw-font-bold">
                                {jobDetails.jobTitle.toUpperCase()}
                            </h1>
                        )}
                        {/* Save Button */}
                        <button className="tw-mx-4 tw-rounded-full tw-border-none tw-bg-gradient-to-b tw-from-orange-500 tw-to-orange-600 tw-px-4 tw-py-3 tw-text-white tw-shadow-2xl tw-transition-all  hover:tw-scale-105 hover:tw-shadow-xl">
                            Save Job for later
                        </button>
                    </div>
                    {/* Title */}
                    <div className="tw-m-4 tw-rounded-xl tw-bg-gray tw-p-6 tw-shadow-lg">
                        {/* Company */}
                        {jobDetails.companyName && (
                            <div className="tw-mb-4 tw-flex tw-items-center">
                                <FaBuilding className="tw-mr-3 tw-text-4xl tw-text-orange-500" />
                                <span className="">
                                    {jobDetails.companyName}
                                </span>
                            </div>
                        )}

                        {/* Location */}
                        {jobDetails.location && (
                            <div className="tw-mb-4 tw-flex tw-items-center">
                                <FaMapMarkerAlt className="tw-mr-3 tw-text-4xl tw-text-orange-500" />
                                <span className="">{jobDetails.location}</span>
                            </div>
                        )}

                        {/* Posted Date */}
                        {jobDetails.createdAt && (
                            <div className="tw-mb-4 tw-flex tw-items-center">
                                <FaCalendarAlt className="tw-mr-3 tw-text-4xl tw-text-orange-500" />
                                <span className="">
                                    Posted{" "}
                                    {timeSincePosted(jobDetails.createdAt)} ago
                                </span>
                            </div>
                        )}

                        {/* Salary */}
                        {jobDetails.salary && (
                            <div className="tw-mb-4 tw-flex tw-items-center">
                                <FaDollarSign className="tw-mr-3 tw-text-4xl tw-text-orange-500" />
                                <span className="">{jobDetails.salary}</span>
                            </div>
                        )}
                    </div>

                    {/* Description */}
                    {jobDetails.jobDescription && (
                        <>
                            <div className="tw-m-4 tw-rounded-xl tw-bg-gray tw-p-6 tw-shadow-lg">
                                <h2 className="tw-mb-2 tw-text-2xl tw-font-bold">
                                    Job Description
                                </h2>
                                <p>{jobDetails.jobDescription}</p>
                            </div>
                        </>
                    )}

                    {/* Responsibilities */}
                    {jobDetails.responsibilities && (
                        <div className=" tw-m-4 tw-rounded-xl tw-bg-gray tw-p-6 tw-shadow-lg">
                            <h2 className="tw-mb-2 tw-text-2xl tw-font-bold">
                                Responsibilities
                            </h2>
                            <p>{jobDetails.responsibilities}</p>
                        </div>
                    )}

                    {/* Qualifications */}
                    {jobDetails.qualifications && (
                        <div className=" tw-m-4 tw-rounded-xl tw-bg-gray tw-p-6 tw-shadow-lg">
                            <h2 className="tw-mb-2 tw-text-2xl tw-font-bold">
                                Qualifications
                            </h2>
                            <p>{jobDetails.qualifications}</p>
                        </div>
                    )}

                    {/* Skills */}
                    {jobDetails.skills && (
                        <div className=" tw-mb-4 tw-rounded-lg tw-p-4">
                            <h2 className="tw-mb-2 tw-text-2xl tw-font-medium">
                                Required Skills
                            </h2>

                            <div className="tw-flex tw-flex-wrap tw-gap-2">
                                {jobDetails.skills.map((skill) => (
                                    <span
                                        className="tw-rounded-full tw-bg-orange-500 tw-px-3 tw-py-1 tw-text-white"
                                        key={skill}
                                    >
                                        {skill}
                                    </span>
                                ))}
                            </div>
                        </div>
                    )}
                    <div className="tw-mt-4">
                        <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-bold">
                            Upload Your Resume
                        </label>

                        <div className="tw-bg-grey-lighter tw-flex tw-items-center tw-justify-center">
                            <label className="tw-flex tw-h-32 tw-w-full tw-flex-col tw-border-4 tw-border-dashed tw-p-10 tw-text-center">
                                <div className="tw-flex tw-flex-col tw-items-center ">
                                    <FaPaperclip
                                        className="tw-text-gray-400 tw-mb-3"
                                        size={60}
                                    />
                                </div>

                                <input
                                    type="file"
                                    className="tw-hidden"
                                    accept=".pdf"
                                    onChange={handleUploadFile}
                                />
                            </label>
                        </div>

                        {resume && (
                            <p className="tw-text-green-500">
                                Resume uploaded successfully!
                            </p>
                        )}
                    </div>

                    {/* Action Buttons */}
                    <div className="tw-m-6 tw-flex tw-justify-center">
                        {/* Apply Button */}
                        <button
                            className="tw-mx-4 tw-rounded-full tw-border-none tw-bg-gradient-to-b tw-from-orange-500 tw-to-orange-600 tw-px-4 tw-py-3 tw-text-3xl tw-text-white tw-shadow-2xl tw-transition-all  hover:tw-scale-105 hover:tw-shadow-xl"
                            onClick={submitApplication}
                        >
                            Apply for Job
                        </button>
                    </div>
                    {resume && <img src={resume.replace(/\.pdf$/, ".png")} />}
                </div>
            </div>
        </div>
    );
}
