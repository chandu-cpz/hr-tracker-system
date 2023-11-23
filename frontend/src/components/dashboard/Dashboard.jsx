import { Sidebar } from "./Sidebar";
import { BsBriefcase, BsPerson, BsPersonCheck } from "react-icons/bs";
import { Card } from "./Card";
import { useEffect, useState } from "react";
import axios from "axios";
import { useSelector } from "react-redux";
import { JobStats } from "../charts/JobStats";
import { GenderDiversity } from "../charts/GenderDiversity";
import { ApplicationStats } from "../charts/ApplicationStats";

export function Dashboard() {
    const [stats, setStats] = useState([]);
    const username = useSelector((state) => state.user.fullName);
    const [jobsOpen, setJobsOpen] = useState(0);
    const [applications, setApplications] = useState(0);
    const [employees, setEmployees] = useState(0);
    const [rejected, setRejected] = useState(0);
    const [male, setMale] = useState(0);
    const [female, setFemale] = useState(0);
    const [others, setOthers] = useState(0);

    const fetchData = async () => {
        await axios
            .get("/api/jobs/open")
            .then((response) => setJobsOpen(response.data.openJobsCount));
        await axios.get("/api/application/count").then((response) => {
            console.log(response.data);
            setApplications(response.data.applications);
            setEmployees(response.data.employees);
            setRejected(response.data.rejected);
            setMale(response.data.male);
            setFemale(response.data.female);
            setOthers(response.data.others);
            setStats(response.data.stats);
        });
    };

    useEffect(() => {
        fetchData();
    }, []);

    return (
        <>
            <div className="tw-flex">
                <Sidebar />

                <div className="tw-w-full tw-p-4">
                <div>
                    <h1 className="tw-text-gray-800 tw-mb-5 tw-text-4xl">
                        Welcome back,
                    </h1>
                    {username && (
                        <h2 className="tw-text-gray-600 tw-text-5xl tw-font-extrabold">
                            {username.toUpperCase()}
                        </h2>
                    )}
                </div>
                    <div className="">
                        <div className="tw-flex tw-flex-wrap tw-items-center tw-gap-2 tw-rounded-lg tw-bg-blue-200 tw-p-4">
                         
                            <Card
                                title="Jobs Open"
                                icon={BsBriefcase}
                                value={jobsOpen}
                            />

                            <Card
                                title="Applications"
                                icon={BsPerson}
                                value={applications}
                            />

                            <Card
                                title="Employees"
                                icon={BsPersonCheck}
                                value={employees}
                            />
                             <JobStats
                                accepted={employees}
                                pending={applications}
                                rejected={rejected}
                            />
                            
                        </div>
                    </div>
                    <div className="tw-flex tw-flex-wrap tw-items-center tw-gap-12 tw-rounded-lg tw-bg-blue-200 tw-p-4">
                        <ApplicationStats stats={stats} />
                        
                        <GenderDiversity
                            male={male}
                            female={female}
                            others={others}
                        />
                    </div>
                </div>
            </div>
        </>
    );
}
