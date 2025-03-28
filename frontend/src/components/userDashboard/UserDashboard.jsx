import { UserSidebar } from "./UserSidebar";
import { BsBriefcase, BsPerson, BsPersonCheck } from "react-icons/bs";
import { UserCard } from "./UserCard";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";
import axios from "axios";
import { JobStats } from "../charts/JobStats";

export function UserDashboard() {
    const username = useSelector((state) => state.user.fullName);
    const [jobsOpen, setJobsOpen] = useState(0);
    const [applications, setApplications] = useState(0);
    const [accepted, setAccepted] = useState(0);
    const [rejected, setRejected] = useState(0);

    const fetchData = async () => {
        await axios
            .get("/api/jobs/open")
            .then((response) => setJobsOpen(response.data.openJobsCount));
        await axios.get("/api/application/count").then((response) => {
            console.log(response.data);
            setApplications(response.data.applications);
            setRejected(response.data.rejected);
            setAccepted(response.data.accepted);
        });
    };

    useEffect(() => {
        fetchData();
    }, []);
    return (
        <>
            <div className="tw-flex dark:tw-bg-gray-900">
                <UserSidebar />

                <div className=" tw-p-4">
                    <div>
                        <h1 className="tw-ml-5 dark:tw-text-white">DashBoard</h1>
                    </div>
                    <div className=" tw-flex tw-flex-wrap tw-items-center tw-justify-evenly tw-gap-x-16  tw-rounded-lg tw-p-8">
                        <div className="tw-self-start">
                            <h1 className="tw-my-5 tw-text-2xl tw-text-gray-800 dark:tw-text-white">
                                Welcome back,
                            </h1>
                            {username && (
                                <h2 className="tw-text-5xl tw-font-extrabold tw-text-gray-600 dark:tw-text-white">
                                    {username.toUpperCase()}
                                </h2>
                            )}
                        </div>
                        <UserCard
                            title="Jobs Open"
                            icon={
                                <BsBriefcase
                                    size={52}
                                    className="tw-text-orange-600"
                                />
                            }
                            value={jobsOpen}
                        />

                        <UserCard
                            title="Applications"
                            icon={
                                <BsPerson
                                    size={52}
                                    className="tw-text-orange-600"
                                />
                            }
                            value={applications}
                        />

                        <UserCard
                            title="Rejected"
                            icon={
                                <BsPersonCheck
                                    size={52}
                                    className="tw-text-orange-600"
                                />
                            }
                            value={rejected}
                        />
                    </div>
                    <div className="tw-flex tw-justify-center">
                        <JobStats
                            accepted={accepted}
                            rejected={rejected}
                            pending={applications}
                        />
                    </div>
                </div>
            </div>
        </>
    );
}
