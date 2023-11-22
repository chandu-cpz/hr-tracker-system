import { Sidebar } from "./Sidebar";
import { BsBriefcase, BsPerson, BsPersonCheck } from "react-icons/bs";
import { Card } from "./Card";
import { useEffect, useState } from "react";
import axios from "axios";

export function Dashboard() {
    const username = "chandu";

    const [jobsOpen,setJobsOpen]=useState(0);

    useEffect(() => {
        axios
            .get("/api/jobs/open")
            .then((response) => setJobsOpen(response.data.openJobsCount));
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
                    <div className="tw-my-20 tw-flex tw-flex-wrap tw-items-center tw-justify-between tw-gap-5 tw-p-8 tw-rounded-lg tw-bg-blue-200">
                        <div>
                            <h1 className="tw-text-white-800 tw-mb-5 tw-text-4xl">
                                Welcome back,
                            </h1>
                            <h2 className="tw-text-white-600 tw-text-5xl tw-font-extrabold">
                                {username.toUpperCase()}
                            </h2>
                        </div>
                        <Card
                            title="Jobs Open"
                            icon={BsBriefcase}
                            value={jobsOpen}
                        />

                        <Card title="Applications" icon={BsPerson} value={24} />

                        <Card
                            title="Employees"
                            icon={BsPersonCheck}
                            value={5}
                        />
                    </div>
                </div>
            </div>
        </>
    );
}