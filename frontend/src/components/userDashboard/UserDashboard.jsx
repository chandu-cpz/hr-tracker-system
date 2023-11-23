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
            <div className="tw-flex">
                <UserSidebar />

                <div className="tw-w-full tw-p-9">
                    <div>
                        <h1>DASHBOARD</h1>
                    </div>
                    <div className="tw-self-start">
                        <h1 className="tw-text-gray-800 tw-mb-5 tw-text-4xl">
                            Welcome back,
                        </h1>
                        {username && (
                            <h2 className="tw-text-gray-600  tw-text-2xl tw-font-extrabold">
                                {username.toUpperCase()}
                            </h2>
                        )}
                    </div>
                    <div className=" tw-flex tw-flex-wrap tw-items-center tw-gap-2 tw-rounded-lg tw-bg-blue-200 tw-p-4">
                        
                        <UserCard
                            title="Jobs Open"
                            icon={
                                <BsBriefcase
                                    size={45}
                                    className="tw-text-blue-600"
                                />
                            }
                            value={jobsOpen}
                        />

                        <UserCard
                            title="Applications"
                            icon={
                                <BsPerson
                                    size={45}
                                    className="tw-text-blue-600"
                                />
                            }
                            value={applications}
                        />

                        <UserCard
                            title="Rejected"
                            icon={
                                <BsPersonCheck
                                    size={45}
                                    className="tw-text-blue-600"
                                />
                            }
                            value={rejected}
                        />

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
