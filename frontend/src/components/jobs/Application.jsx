import axios from "axios";
import { useEffect, useState } from "react";
import { Sidebar } from "../dashboard/Sidebar";
import { useParams } from "react-router-dom";
import { FaPhoneAlt, FaEnvelope } from "react-icons/fa";

export function Application() {
    const { applicationId } = useParams();
    console.log(applicationId);
    const [application, setApplication] = useState();
    const [accepted, setAccepted] = useState(false);
    const [rejected, setRejected] = useState(true);
    const fetchApplication = async (applicationId) => {
        try {
            const { data } = await axios.post("/api/application/details", {
                applicationId: applicationId,
            });
            console.log(data);
            setApplication(data);
        } catch (error) {
            console.log(error.message);
        }
    };
    useEffect(() => {
        fetchApplication(applicationId);
    }, []);

    const acceptApplication = async (applicationId) => {
        await axios.post("/api/application/accept", {
            applicationId: applicationId,
        });
        setAccepted(true);
    };

    const rejectApplication = async (applicationId) => {
        await axios.post("/api/application/reject", {
            applicationId: applicationId,
        });
        setRejected(false);
    };

    return (
        <div className="tw-flex tw-flex-wrap tw-gap-10">
            <Sidebar />
            <div className="tw-mt-5 tw-w-3/4">
                <div className="tw-flex tw-justify-around">
                    {" "}
                    <h1> Applicant Details: </h1>
                    <div className="tw-flex">
                        <div>
                            <button
                                className="tw-mx-4 tw-rounded-full tw-border-none tw-bg-gradient-to-b tw-from-blue-900 tw-to-blue-600 tw-px-4 tw-py-3 tw-text-3xl tw-text-white tw-shadow-2xl tw-transition-all  hover:tw-scale-105 hover:tw-shadow-xl"
                                onClick={() =>
                                    acceptApplication(application._id)
                                }
                            >
                                Recruit
                            </button>
                            {accepted && (
                                <span className="tw-text-green-900">
                                    {" "}
                                    The application is accepted
                                </span>
                            )}
                        </div>
                        <div>
                            <button
                                className="tw-mx-4 tw-rounded-full tw-border-none tw-bg-gradient-to-b tw-from-blue-900 tw-to-blue-600 tw-px-4 tw-py-3 tw-text-3xl tw-text-white tw-shadow-2xl tw-transition-all  hover:tw-scale-105 hover:tw-shadow-xl"
                                onClick={() =>
                                    rejectApplication(application._id)
                                }
                            >
                                Reject
                            </button>
                            {!rejected && (
                                <span className="tw-text-red-600">
                                    {" "}
                                    The application is rejected
                                </span>
                            )}
                        </div>
                    </div>
                </div>
                <div>
                    {/* Applicant Profile */}
                    {application && (
                        <div className="tw-bg-gray-100 tw-mx-auto tw-max-w-3xl tw-rounded-lg tw-p-8">
                            <div className="tw-flex tw-items-center tw-rounded-lg tw-bg-blue-200 tw-p-4 tw-shadow-md">
                                <img
                                    className="tw-mr-8 tw-h-40 tw-w-40 tw-rounded-full tw-border-4 tw-border-blue-900"
                                    src={application.appliedBy.profileImage}
                                    alt={application.appliedBy.fullName}
                                />

                                <div>
                                    <h1 className="tw-text-4xl tw-font-bold tw-text-blue-600">
                                        {application.appliedBy.fullName}
                                    </h1>

                                    <div className="tw-mt-5 tw-flex tw-items-center">
                                        <FaPhoneAlt className="tw-mr-2 tw-text-blue-900" />
                                        <span>
                                            {application.appliedBy.phoneNumber}
                                        </span>
                                    </div>

                                    <div className="tw-mb-5  tw-flex tw-items-center">
                                        <FaEnvelope className="tw-mr-2 tw-text-blue-900" />
                                        <span>
                                            {application.appliedBy.email}
                                        </span>
                                    </div>
                                </div>
                            </div>

                            <div className="tw-mt-8 tw-grid tw-grid-cols-1 tw-gap-4 tw-rounded-lg tw-bg-blue-200 tw-p-4 tw-shadow-md md:tw-grid-cols-2">
                                <div>
                                    <h3 className="tw-mb-4 tw-text-2xl tw-font-medium tw-text-blue-600">
                                        Info
                                    </h3>

                                    <div className="tw-mb-2 tw-flex tw-items-center">
                                        <p className="tw-mr-4 tw-font-medium">
                                            Gender:
                                        </p>
                                        <p className="tw-rounded-full tw-bg-blue-900 tw-px-4 tw-py-1 tw-text-white">
                                            {application.appliedBy.gender}
                                        </p>
                                    </div>

                                    <div className="tw-mb-2 tw-flex tw-items-center">
                                        <p className="tw-mr-4 tw-font-medium">
                                            Location:
                                        </p>
                                        <p className="tw-rounded-full tw-bg-blue-900 tw-px-4 tw-py-1 tw-text-white">
                                            {application.appliedBy.address}
                                        </p>
                                    </div>

                                    <div className="tw-flex tw-items-center">
                                        <p className="tw-mr-4 tw-font-medium">
                                            Education:
                                        </p>
                                        <p className="tw-rounded-full tw-bg-blue-900 tw-px-4 tw-py-1 tw-text-white">
                                            {application.appliedBy.education}
                                        </p>
                                    </div>
                                </div>

                                <div>
                                    <h3 className="tw-mb-4 tw-text-2xl tw-font-medium tw-text-blue-600">
                                        Experience
                                    </h3>

                                    <div className="tw-mb-2 tw-flex tw-items-center">
                                        <p className="tw-mr-4 tw-font-medium">
                                            Years:
                                        </p>
                                        <p className="tw-rounded-full tw-bg-blue-900 tw-px-4 tw-py-1 tw-text-white">
                                            {application.appliedBy.experience}
                                        </p>
                                    </div>
                                </div>

                                <div className="tw-col-span-2">
                                    <h3 className="tw-mb-4 tw-text-2xl tw-font-medium tw-text-blue-600">
                                        Skills
                                    </h3>

                                    <div className="tw-flex tw-flex-wrap tw-gap-2">
                                        {application.appliedBy.skills.map(
                                            (skill) => (
                                                <p
                                                    key={skill}
                                                    className="tw-rounded-full tw-bg-blue-900 tw-px-4 tw-py-1 tw-text-white"
                                                >
                                                    {skill}
                                                </p>
                                            )
                                        )}
                                    </div>
                                </div>
                            </div>
                        </div>
                    )}
                    {application && (
                        <div className="tw-flex tw-justify-around">
                            <h1> Applicant Resume: </h1>
                            <a
                                href={application.resume}
                                target="_blank"
                                rel="noreferrer"
                            >
                                <button className="tw-mx-4 tw-rounded-full tw-border-none tw-bg-gradient-to-b tw-from-blue-900 tw-to-blue-600 tw-px-4 tw-py-3 tw-text-3xl tw-text-white tw-shadow-2xl tw-transition-all  hover:tw-scale-105 hover:tw-shadow-xl">
                                    Download Resume
                                </button>
                            </a>
                        </div>
                    )}
                    <div>
                        {application && application.resume && (
                            <div className="tw-w-3/4">
                                {" "}
                                <img
                                    className=" tw-object-cover"
                                    src={application.resume.replace(
                                        /\.pdf$/,
                                        ".png"
                                    )}
                                />{" "}
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}
