import { Dropdown } from "./DropDown";
import { Sidebar } from "./Sidebar";
import { BsSearch, BsGeoAlt, BsBriefcase, BsClock } from "react-icons/bs";
import { Card } from "./Card";
import axios from "axios";
import { useEffect, useState } from "react";

export function JobFilter() {
    const options = ["Web Developer", "Data Scientist"];
    const [jobs, setJobs] = useState([]);
    const [filters, setFilters] = useState({
        jobTitle: "",
        location: "",
        experience: "",
        jobDuration: ""
    });
    const [selectedSalary, setSelectedSalary] = useState(0);

    useEffect(() => {
        fetchData();
    }, [filters]); // Refetch data when filters change

    const fetchData = async () => {
        try {
            const response = await axios.get("/api/jobs", { params: { ...filters, sort: 'salary' } });
            const jobs = response.data;
            setJobs(jobs);
        } catch (error) {
            console.error("Error fetching jobs:", error.message);
            // You might want to set an error state or display a message to the user.
        }
    };

    const handleSalaryChange = async (event) => {
        const salary = event.target.value;
        try {
            const response = await axios.get(`/api/jobs?sort=salary&minSalary=0&maxSalary=${salary}`);
            const jobs = response.data;
            setJobs(jobs);
            setSelectedSalary(salary);
        } catch (error) {
            console.error("Error fetching jobs:", error);
            // You might want to set an error state or display a message to the user.
        }
    };

    const handleFilterChange = (field, value) => {
        setFilters((prevFilters) => ({
            ...prevFilters,
            [field]: value
        }));
    };

    return (
        <>
            <div className=" tw-flex tw-justify-around tw-gap-3 tw-bg-slate-500 tw-p-4 tw-shadow-lg">
                <Dropdown
                    name="Job Title"
                    options={options}
                    icon={<BsSearch />}
                    onSelect={(value) => handleFilterChange("jobTitle", value)}
                />
                <Dropdown
                    name="Location"
                    options={options}
                    icon={<BsGeoAlt />}
                    onSelect={(value) => handleFilterChange("location", value)}
                />
                <Dropdown
                    name="Experience"
                    options={options}
                    icon={<BsBriefcase />}
                    onSelect={(value) => handleFilterChange("experience", value)}
                />
                <Dropdown
                    name="Job Duration"
                    options={options}
                    icon={<BsClock />}
                    onSelect={(value) => handleFilterChange("jobDuration", value)}
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
