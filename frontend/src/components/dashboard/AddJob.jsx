import { useState } from "react";
import Multiselect from "multiselect-react-dropdown";
import { Sidebar } from "./Sidebar";
import {
    FaBriefcase,
    FaMapMarker,
    FaDollarSign,
    FaListUl,
    FaGraduationCap,
} from "react-icons/fa";
import { FiHardDrive } from "react-icons/fi";
import { GiOfficeChair } from "react-icons/gi";
import { MdWork } from "react-icons/md";
import { IoDocumentTextOutline } from "react-icons/io5";
import { IoMdConstruct } from "react-icons/io";
import { skills } from "../../constants";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export function AddJob() {
    const navigate = useNavigate();
    const [job, setJob] = useState({
        jobTitle: "",
        jobDescription: "",
        companyName: "",
        responsibilities: "",
        qualifications: "",
        location: "",
        jobType: "",
        noOfPosts: "",
        salary: "",
        skills: [],
        isOpen: true,
    });

    const handleChange = (e) => {
        setJob({
            ...job,
            [e.target.name]: e.target.value,
        });
    };

    const submitJob = async () => {
        const newJob = {
            jobTitle: job.jobTitle,
            jobDescription: job.jobDescription,
            companyName: job.companyName,
            responsibilities: job.responsibilities,
            qualifications: job.qualifications,
            location: job.location,
            jobType: job.jobType,
            noOfPosts: job.noOfPosts,
            salary: job.salary,
            skills: job.skills,
            isOpen: job.isOpen,
        };
        console.log("we are creating a new job");
        const addedJob = await axios
            .post("/api/jobs", newJob)
            .then((response) => response.data);
        console.log(addedJob);
        navigate(`/job/${addedJob._id}`);
    };

    return (
        <div className="h-screen tw-flex">
            <Sidebar />
            <div className="tw-rounded-4xl tw-mt-7  tw-rounded-3xl tw-bg-gradient-to-b tw-from-blue-900 tw-to-blue-200 tw-p-16 tw-shadow-2xl tw-mx-auto tw-max-w-3xl tw-rounded-lg tw-p-5">
                <div className="tw-flex tw-flex-col tw-items-center  tw-rounded-lg tw-p-2 ">
                    <div className="tw-mb-5 tw-flex tw-justify-center">
                        <div className="">
                            <h1 className="tw-mb-6 tw-text-center tw-font-mono tw-text-5xl tw-font-bold tw-text-white">
                                Post Job
                            </h1>
                        </div>
                    </div>

                    <div className="tw-rounded-lg  tw-p-2">
                        <div className="tw-flex ">
                            <div className="tw-min-w-25 tw-m-5 tw-flex-shrink-0 tw-flex-grow">
                                <div className="tw-mb-6">
                                    <label className="tw-text-white tw-font-lg tw-mb-2 tw-block">
                                        <FaBriefcase className="tw-mr-2" />
                                        Job Title
                                    </label>
                                    <input
                                        type="text"
                                        name="jobTitle"
                                        value={job.jobTitle}
                                        onChange={handleChange}
                                        className="tw-border-black-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                    />
                                </div>

                                <div className="tw-mb-6">
                                    <label className="tw-text-white tw-font-lg tw-mb-2 tw-block">
                                        <FiHardDrive className="tw-mr-2" />
                                        Job Description
                                    </label>
                                    <input
                                        type="text"
                                        name="jobDescription"
                                        value={job.jobDescription}
                                        onChange={handleChange}
                                        className="tw-border-black-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                    />
                                </div>

                                <div className="tw-mb-6">
                                    <label className="tw-text-white tw-font-lg tw-mb-2 tw-block">
                                        <GiOfficeChair className="tw-mr-2" />
                                        CompanyName
                                    </label>
                                    <input
                                        type="text"
                                        name="companyName"
                                        value={job.companyName}
                                        onChange={handleChange}
                                        className="tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                    />
                                </div>

                                <div className="tw-mb-6">
                                    <label className="tw-text-white tw-font-lg tw-mb-2 tw-block">
                                        <FaDollarSign className="tw-mr-2" />
                                        Salary
                                    </label>
                                    <input
                                        type="number"
                                        name="salary"
                                        value={job.salary}
                                        onChange={handleChange}
                                        className="tw-border-black-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                    />
                                </div>

                                <div className="tw-mb-6">
                                    <label className="tw-text-white tw-font-lg tw-mb-2 tw-block">
                                        <FaListUl className="tw-mr-2" />
                                        Responsibilities
                                    </label>
                                    <textarea
                                        type="text"
                                        name="responsibilities"
                                        value={job.responsibilities}
                                        onChange={handleChange}
                                        className="tw-border-black-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                    />
                                </div>
                            </div>
                            <div className="tw-min-w-25 tw-m-5 ">
                                <div className="tw-mb-6">
                                    <label className="tw-text-white tw-font-lg tw-mb-2 tw-block">
                                        <FaGraduationCap className="tw-mr-2" />
                                        Qualifications
                                    </label>
                                    <input
                                        type="text"
                                        name="qualifications"
                                        value={job.qualifications}
                                        onChange={handleChange}
                                        className="tw-border-black-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                    />
                                </div>

                                <div className="tw-mb-6">
                                    <label className="tw-text-white tw-font-lg tw-mb-2 tw-block">
                                        <FaMapMarker className="tw-mr-2" />
                                        Location
                                    </label>
                                    <input
                                        type="text"
                                        name="location"
                                        value={job.location}
                                        onChange={handleChange}
                                        className="tw-border-black-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                    />
                                </div>

                                <div className="tw-mb-6">
                                    <label className="tw-text-white tw-font-lg tw-mb-2 tw-block">
                                        <MdWork className="tw-mr-2" />
                                        Job Type
                                    </label>
                                    <input
                                        type="text"
                                        name="jobType"
                                        value={job.jobType}
                                        onChange={handleChange}
                                        className="tw-border-black-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                    />
                                </div>

                                <div className="tw-mb-6">
                                    <label className="tw-text-white tw-font-lg tw-mb-2 tw-block">
                                        <IoMdConstruct className="tw-mr-2" />
                                        Skills
                                    </label>
                                    <Multiselect
                                        placeholder="Select Skills"
                                        style={{
                                            searchBox: {
                                                borderRadius: "9999px",
                                            },
                                            chips: {
                                                background: "#213d9c",
                                                borderRadius: "9999px",
                                            },
                                            option: {
                                                backgroundColor: "#213d9c",
                                            },
                                        }}
                                        isObject={false}
                                        onKeyPressFn={function noRefCheck() {}}
                                        onRemove={(e) =>
                                            setJob({
                                                ...job,
                                                skills: e,
                                            })
                                        }
                                        onSearch={function noRefCheck() {}}
                                        onSelect={(e) =>
                                            setJob({
                                                ...job,
                                                skills: e,
                                            })
                                        }
                                        options={skills}
                                    />
                                </div>
                                <div className="tw-mb-6">
                                    <label className="tw-text-white tw-font-lg tw-mb-2 tw-block">
                                        <IoDocumentTextOutline className="tw-mr-2" />
                                        No Of Posts
                                    </label>
                                    <input
                                        type="number"
                                        name="noOfPosts"
                                        value={job.noOfPosts}
                                        onChange={handleChange}
                                        className="tw-border-black-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                    />
                                </div>
                            </div>
                        </div>
                    </div>

                    <button
                        className="tw-m-6 tw-rounded-full tw-text-white tw-border-none tw-bg-blue-400 tw-px-14 tw-py-4 hover:tw-bg-blue-900"
                        onClick={submitJob}
                    >
                        Post Job
                    </button>
                </div>
            </div>
        </div>
    );
}