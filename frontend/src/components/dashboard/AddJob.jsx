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
import { IoDocumentTextOutline } from 'react-icons/io5'
import { IoMdConstruct } from 'react-icons/io';

export function AddJob() {
    const skills = ["Option 1", "Option 2", "Option 3", "Option 4", "Option 5"];
    const [selectedSkills, setSelectedSkills] = useState([]);

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

    return (
      <div className="tw-flex h-screen">
         <Sidebar />
        <div className="tw-bg-gray-100 tw-mx-auto tw-max-w-3xl tw-rounded-lg tw-p-8">
            <div className="tw-flex tw-flex-col tw-items-center  tw-rounded-lg tw-p-4 tw-shadow-md">
                <div className="tw-mb-5 tw-flex tw-justify-center">
                    <div className="">
                        <h1 className="tw-mb-6 tw-text-center tw-text-5xl tw-font-bold tw-font-mono">
                            Post Job
                        </h1>
                    </div>
                </div>

                <div className="tw-rounded-lg  tw-p-8 tw-shadow">
                    <div className="tw-flex">
                        <div className="tw-m-5 ">
                            <div className="tw-mb-6">
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-lg">
                                <FaBriefcase className="tw-mr-2" />
                                    Job Title
                                </label>
                                <input
                                    type="text"
                                    name="jobTitle"
                                    value={job.jobTitle}
                                    onChange={handleChange}
                                    className="tw-border-gray-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                />
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-lg">
                                <FiHardDrive className="tw-mr-2" />
                                    Job Description
                                </label>
                                <input
                                    type="text"
                                    name="JobDescription"
                                    value={job.jobDescription}
                                    onChange={handleChange}
                                    className="tw-border-gray-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                />
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-lg">
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
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-lg">
                                <FaDollarSign className="tw-mr-2" />
                                    Salary
                                </label>
                                <input
                                    type="number"
                                    name="salary"
                                    value={job.salary}
                                    onChange={handleChange}
                                    className="tw-border-gray-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                />
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-lg">
                                <FaListUl className="tw-mr-2" />
                                    Responsibilities
                                </label>
                                <input
                                    type="text"
                                    name="responsibilities"
                                    value={job.responsibilities}
                                    onChange={handleChange}
                                    className="tw-border-gray-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                />
                            </div>
                        </div>
                        <div className="tw-m-5">
                            <div className="tw-mb-6">
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-lg">
                                <FaGraduationCap className="tw-mr-2" />
                                    Qualifications
                                </label>
                                <input
                                    type="text"
                                    name="qualifications"
                                    value={job.qualifications}
                                    onChange={handleChange}
                                    className="tw-border-gray-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                />
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-lg">
                                <FaMapMarker className="tw-mr-2" />
                                    Location
                                </label>
                                <input
                                    type="text"
                                    name="location"
                                    value={job.location}
                                    onChange={handleChange}
                                    className="tw-border-gray-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                />
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-lg">
                                <MdWork className="tw-mr-2" />
                                    Job Type
                                </label>
                                <input
                                    type="text"
                                    name="jobType"
                                    value={job.jobType}
                                    onChange={handleChange}
                                    className="tw-border-gray-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                />
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-lg">
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
                                            background: "#f97316",
                                            borderRadius: "9999px",
                                        },
                                        option: {
                                            backgroundColor: "#f97316",
                                        },
                                    }}
                                    isObject={false}
                                    onKeyPressFn={function noRefCheck() {}}
                                    onRemove={(e) => setSelectedSkills(e)}
                                    onSearch={function noRefCheck() {}}
                                    onSelect={(e) => setSelectedSkills(e)}
                                    options={skills}
                                />
                            </div>
                            <div className="tw-mb-6">
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-lg">
                                  < IoDocumentTextOutline className="tw-mr-2"/>
                                    No Of Posts
                                </label>
                                <input
                                    type="number"
                                    name="noOfPosts"
                                    value={job.noOfPosts}
                                    onChange={handleChange}
                                    className="tw-border-gray-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                />
                            </div>
                            
                        </div>
                    </div>
                </div>

                <button className="tw-m-6 tw-rounded-full tw-border-none tw-bg-orange-500   tw-px-4 tw-py-3 tw-shadow-xl hover:tw-bg-orange-600">
                    Post Job
                </button>
            </div>
        </div>
        </div>
    );
}
