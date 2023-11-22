import { Sidebar } from "./Sidebar";
import { BsBriefcase, BsPerson, BsPersonCheck } from "react-icons/bs";
import { Card } from "./Card";
import { useEffect, useState } from "react";
import axios from "axios";
import { useSelector } from "react-redux";

export function Dashboard() {
    const username = useSelector((state) => state.user.fullName);

    const [jobsOpen, setJobsOpen] = useState(0);
    const [applications, setApplications] = useState(0);
    const [employees, setEmployees] = useState(0);

    const fetchData = async () => {
        await axios
            .get("/api/jobs/open")
            .then((response) => setJobsOpen(response.data.openJobsCount));
        await axios.get("/api/application/count").then((response) => {
            console.log(response.data);
            setApplications(response.data.applications);
            setEmployees(response.data.employees);
        });
    };

    useEffect(() => {
        fetchData();
    }, []);

    // const data = [
    //     { name: "John Doe", email: "john@email.com", phone: "123-456-7890" },
    //     { name: "Jane Doe", email: "jane@email.com", phone: "987-654-3210" },
    // ];
    return (
        <>
            <div className="tw-flex">
                <Sidebar />

                <div className="tw-w-full tw-p-4">
                    <div className="tw-mb-8 tw-flex tw-flex-wrap tw-items-center tw-justify-between tw-gap-5">
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
                    </div>
                </div>
            </div>
        </>
    );
}
