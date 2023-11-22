import { Dropdown } from "./DropDown";
import { Sidebar } from "./Sidebar";
import { BsSearch, BsGeoAlt, BsBriefcase, BsClock } from "react-icons/bs";
import { Card } from "./Card";
import axios from "axios";
import { useEffect, useState } from "react";

export function JobFilter() {
    const options = ["Web Developer", "Data Scientist"];
    const [jobs, setJobs] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get("/api/jobs");
                const jobs = response.data;
                setJobs(jobs);
            } catch (error) {
                console.log("ERROR HITTING /api/jobs", error.message);
            }
        };

        fetchData();
    }, []);

    return (
        <>
            <div className=" tw-flex tw-justify-around tw-gap-3 tw-bg-blue-900 tw-p-4 tw-shadow-lg">
                <Dropdown
                    name="Job Title"
                    options={options}
                    icon={<BsSearch />}
                />
                <Dropdown
                    name="Location"
                    options={options}
                    icon={<BsGeoAlt />}
                />
                <Dropdown
                    name="Experience"
                    options={options}
                    icon={<BsBriefcase />}
                />
                <Dropdown
                    name="Job Duration"
                    options={options}
                    icon={<BsClock />}
                />
                <div className="tw-flex">
                    <label
                        htmlFor="salary"
                        className="tw-mr-3 tw-text-xl tw-text-white"
                    >
                        Salary
                    </label>
                    <input type="range" min="0" max="1000000" />
                </div>
            </div>
            <div className="tw-flex tw-flex-wrap tw-gap-10">
                <Sidebar />
                <div className="tw-m-5 tw-w-10/12">
                    <h1>Recomended Jobs</h1>
                    <div className="tw-flex tw-w-full tw-flex-wrap tw-gap-10">
                        {jobs.map((job) => {
                            return <Card key={job._id} job={job} />;
                        })}
                    </div>
                </div>
            </div>
        </>
    );
}
