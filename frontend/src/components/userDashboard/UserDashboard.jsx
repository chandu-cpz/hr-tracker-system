import { UserSidebar } from "./UserSidebar";
import { BsBriefcase, BsPerson, BsPersonCheck } from "react-icons/bs";
import { UserCard } from "./UserCard";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";
import axios from "axios";

export function UserDashboard() {
    const username = useSelector((state) => state.user.fullName);
    const [jobsOpen, setJobsOpen] = useState(0);
    const [applications, setApplications] = useState(0);
    const [rejected, setRejected] = useState(0);

    const fetchData = async () => {
        await axios
            .get("/api/jobs/open")
            .then((response) => setJobsOpen(response.data.openJobsCount));
        await axios.get("/api/application/count").then((response) => {
            console.log(response.data);
            setApplications(response.data.applications);
            setRejected(response.data.rejected);
        });
    };

    useEffect(() => {
        fetchData();
    }, []);
    return (
        <>
            <div className="tw-flex">
                <UserSidebar />

                <div className="tw-w-full tw-p-4">
                    <div>
                        <h1>DashBoard</h1>
                    </div>
                    <div className="tw-my-20 tw-flex tw-flex-wrap tw-items-center tw-justify-between tw-gap-5 tw-rounded-lg tw-bg-orange-200 tw-p-8">
                        <div className="tw-self-start">
                            <h1 className="tw-text-gray-800 tw-mb-5 tw-text-4xl">
                                Welcome back,
                            </h1>
                            {username && (
                                <h2 className="tw-text-gray-600 tw-text-5xl tw-font-extrabold">
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
                </div>
            </div>
        </>
    );
}
