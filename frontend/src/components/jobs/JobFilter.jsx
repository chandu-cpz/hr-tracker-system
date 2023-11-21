import { Dropdown } from "./DropDown";
import { Sidebar } from "./Sidebar";
import { BsSearch, BsGeoAlt, BsBriefcase, BsClock } from "react-icons/bs";
import { Card } from "./Card";
import axios from "axios";
import { useEffect, useState } from "react";

export function JobFilter() {
    const [jobs, setJobs] = useState([]);
    const [filterData, setFilterData] = useState({
        jobTitle: ["loading", "loading"],
        location: ["loading", "loading"],
        experience: ["loading", "loading"],
        jobDuration: ["loading", "loading"],
    });
    const [filters, setFilters] = useState({
        jobTitle: "",
        location: "",
        experience: "",
        jobDuration: "",
    });
    const [selectedSalary, setSelectedSalary] = useState(0);

    useEffect(() => {
        fetchData();
    }, [filters]); // Refetch data when filters change

    const handleFilterChange = (field, value) => {
        setFilters((prevFilters) => ({
            ...prevFilters,
            [field]: value,
        }));
    };

    const fetchData = async () => {
        try {
            const response = await axios.get("/api/jobs", {
                params: { ...filters, sort: "salary" },
            });
            console.log(response.data);
            const jobs = response.data.jobs;
            setFilterData({
                jobTitle: response.data.jobTitle,
                location: response.data.location,
                experience: response.data.experience,
                jobDuration: response.data.jobDuration,
            });
            setJobs(jobs);
        } catch (error) {
            console.error("Error fetching jobs:", error.message);
            // You might want to set an error state or display a message to the user.
        }
    };

    const handleSalaryChange = async (event) => {
        const salary = event.target.value;
        try {
            const response = await axios.get(
                `/api/jobs?sort=salary&minSalary=0&maxSalary=${salary}`
            );
            const jobs = response.data;
            setJobs(jobs);
            setSelectedSalary(salary);
        } catch (error) {
            console.error("Error fetching jobs:", error);
            // You might want to set an error state or display a message to the user.
        }
    };

    return (
        <>
            <div className=" tw-flex tw-justify-around tw-gap-3 tw-bg-slate-500 tw-p-4 tw-shadow-lg">
                <Dropdown
                    name="Job Title"
                    options={filterData.jobTitle}
                    icon={<BsSearch />}
                    onSelect={(value) => handleFilterChange("jobTitle", value)}
                />
                <Dropdown
                    name="Location"
                    options={filterData.location}
                    icon={<BsGeoAlt />}
                    onSelect={(value) => handleFilterChange("location", value)}
                />
                <Dropdown
                    name="Experience"
                    options={filterData.experience}
                    icon={<BsBriefcase />}
                    onSelect={(value) =>
                        handleFilterChange("experience", value)
                    }
                />
                <Dropdown
                    name="Job Duration"
                    options={filterData.jobDuration}
                    icon={<BsClock />}
                    onSelect={(value) =>
                        handleFilterChange("jobDuration", value)
                    }
                />
                <div className="tw-flex">
                    <label
                        htmlFor="salary"
                        className="tw-mr-3 tw-text-xl tw-text-white"
                    >
                        Salary
                    </label>
                    <input
                        type="range"
                        min="0"
                        max="1000000"
                        value={selectedSalary}
                        onChange={handleSalaryChange}
                    />
                </div>
            </div>
            <div className="tw-flex tw-flex-wrap tw-gap-10">
                <Sidebar />
                <div className="tw-m-5 tw-w-10/12">
                    <h1>Recommended Jobs</h1>
                    <div className="tw-flex tw-w-full tw-flex-wrap tw-gap-10">
                        {jobs.map((job) => (
                            <Card key={job._id} job={job} />
                        ))}
                    </div>
                </div>
            </div>
        </>
    );
}
