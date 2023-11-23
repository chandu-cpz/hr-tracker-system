import { Dropdown } from "./DropDown";
import { Sidebar } from "./Sidebar";
import { BsSearch, BsGeoAlt, BsBriefcase } from "react-icons/bs";
import { Card } from "./Card";
import axios from "axios";
import { useEffect, useState } from "react";

export function JobFilter() {
    const [jobs, setJobs] = useState([]);
    const [totalPages, setTotalPages] = useState(1);
    const [filterData, setFilterData] = useState({
        jobTitle: ["loading"],
        location: ["loading"],
        experience: ["loading"],
        jobDuration: ["loading"],
    });
    const [filters, setFilters] = useState({
        jobTitle: "",
        location: "",
        experience: "",
        jobType: [],
    });
    const [selectedSalary, setSelectedSalary] = useState(0);

    useEffect(() => {
        console.log("refeteching data");
        console.log(filters);
        fetchData();
    }, [filters]); // Refetch data when filters change

    const handleFilterChange = (field, value) => {
        console.log("a filter has been changesd");
        console.log(`We got ${field} with ${value}`);
        setFilters((prevFilters) => ({
            ...prevFilters,
            [field]: value,
        }));
        console.log("The new filters are : ");
        console.log(filters);
    };

    // Usage
    const handlePageChange = (page) => {
        fetchData(page);
    };

    const fetchData = async (page) => {
        try {
            const response = await axios.get("/api/jobs", {
                params: { ...filters, sort: "salary", page },
            });
            const jobs = response.data.jobs;
            setFilterData({
                jobTitle: response.data.jobTitle,
                location: response.data.location,
                experience: response.data.experience,
            });
            setTotalPages(response.data.totalPages);
            console.log("The filter data is ");
            console.log(filterData);
            setJobs(jobs);
            console.log(jobs);
        } catch (error) {
            console.error("Error fetching jobs:", error.message);
        }
    };

    const handleSalaryChange = async (event) => {
        const salary = event.target.value;
        try {
            const response = await axios.get(
                `/api/jobs?sort=salary&minSalary=${salary}&maxSalary=9999999999999`
            );
            const jobs = response.data.jobs;
            setJobs(jobs);
            setSelectedSalary(salary);
        } catch (error) {
            console.error("Error fetching jobs:", error);
        }
    };

    return (
        <>
            <div className=" tw-m-2 tw-flex tw-justify-around tw-gap-3 tw-p-4 tw-shadow-xl">
                <Dropdown
                    name="Job Title"
                    options={filterData.jobTitle}
                    icon={<BsSearch />}
                    onSelect={(name, value) =>
                        handleFilterChange("jobTitle", value)
                    }
                />
                <Dropdown
                    name="Location"
                    options={filterData.location}
                    icon={<BsGeoAlt />}
                    onSelect={(name, value) =>
                        handleFilterChange("location", value)
                    }
                />
                <Dropdown
                    name="Experience"
                    options={filterData.experience}
                    icon={<BsBriefcase />}
                    onSelect={(name, value) =>
                        handleFilterChange("experience", value)
                    }
                />

                <div className="tw-flex">
                    <label htmlFor="salary" className="tw-mr-3 tw-text-xl">
                        Salary
                    </label>
                    <input
                        type="range"
                        min="0"
                        max="99999"
                        value={selectedSalary}
                        onChange={handleSalaryChange}
                    />
                    <input
                        type="number"
                        value={selectedSalary}
                        onChange={(e) => setSelectedSalary(e.target.value)}
                        className="tw-ml-5 tw-rounded-full tw-border-none"
                    />
                    <div>
                        <button
                            className=" tw-rounded-full tw-border-none tw-bg-gradient-to-b tw-from-orange-500 tw-to-orange-600 tw-px-2 tw-py-1  tw-text-white tw-shadow-2xl tw-transition-all  hover:tw-scale-105 hover:tw-shadow-xl"
                            onClick={() => setFilters({})}
                        >
                            Clear
                        </button>
                    </div>
                </div>
            </div>
            <div className="tw-flex tw-flex-wrap tw-gap-10">
                <Sidebar
                    onSelect={(checked) =>
                        setFilters((prev) => ({ ...prev, jobType: checked }))
                    }
                />
                <div className="tw-m-5 tw-w-10/12">
                    <h1>Recommended Jobs</h1>
                    <div className="tw-flex tw-w-full tw-flex-wrap tw-gap-10">
                        {jobs &&
                            jobs.map((job) => <Card key={job._id} job={job} />)}
                    </div>
                    <div>
                        {Array.from({ length: totalPages }, (_, i) => (
                            <button
                                key={i}
                                onClick={() => handlePageChange(i + 1)}
                                className="btn btn-primary m-2"
                            >
                                {i + 1}
                            </button>
                        ))}
                    </div>
                </div>
            </div>
        </>
    );
}
