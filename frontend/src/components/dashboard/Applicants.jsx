import axios from "axios";
import { useEffect, useState } from "react";
import { Sidebar } from "./Sidebar";
import { useNavigate } from "react-router-dom";

export function Applicants() {
    const navigate = useNavigate();
    const [applicants, setApplicants] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const result = await axios.get("/api/application/applicants");

            setApplicants(result.data);
        };

        fetchData();
    }, []);
    return (
        <div className="tw-flex tw-flex-wrap tw-gap-10 dark:tw-bg-stone-900">
            <Sidebar />
            <div className=" tw-w-13/15 tw-mt-5">
                <h1 className="dark:tw-text-white">Applicants</h1>
                <div className=" tw-mx-auto tw-flex tw-flex-wrap tw-justify-center tw-gap-10">
                    {/* Table for Applicants*/}
                    {applicants.length > 0 && (
                        <table className="tw-mt-5  tw-border-collapse ">
                            <thead>
                                <tr className="tw-bg-orange-500 dark:tw-bg-orange-400 tw-text-white">
                                    <th className="tw-border tw-border-orange-600 tw-p-2 dark:tw-text-white">
                                        Name
                                    </th>
                                    <th className="tw-border tw-border-orange-600 tw-p-2 dark:tw-text-white">
                                        Email
                                    </th>
                                    <th className="tw-border tw-border-orange-600 tw-p-2 dark:tw-text-white">
                                        Gender
                                    </th>
                                    <th className="tw-border tw-border-orange-600 tw-p-2 dark:tw-text-white">
                                        Education
                                    </th>
                                    <th className="tw-border tw-border-orange-600 tw-p-2 dark:tw-text-white">
                                        Skills
                                    </th>
                                    <th className="tw-border tw-border-orange-600 tw-p-2 dark:tw-text-white">
                                        View
                                    </th>
                                </tr>
                            </thead>

                            <tbody>
                                {applicants.map((applicant) => (
                                    <tr
                                        className="tw-bg-white hover:tw-bg-gray dark:tw-bg-stone-900 dark:tw-text-white"
                                        key={applicant._id}
                                    >
                                        <td className="tw-border tw-border-orange-400 tw-p-2 dark:tw-bg-stone-900 dark:tw-text-white">
                                            {applicant.appliedBy.fullName}
                                        </td>
                                        <td className="tw-border tw-border-orange-400 tw-p-2 dark:tw-bg-stone-900 dark:tw-text-white">
                                            {applicant.appliedBy.email}
                                        </td>
                                        <td className="tw-border tw-border-orange-400 tw-p-2 dark:tw-bg-stone-900 dark:tw-text-white">
                                            {applicant.appliedBy.gender}
                                        </td>
                                        <td className="tw-border tw-border-orange-400 tw-p-2 dark:tw-bg-stone-900 dark:tw-text-white">
                                            {applicant.appliedBy.education}
                                        </td>
                                        <td className="tw-border tw-border-orange-400 tw-p-2 dark:tw-bg-stone-900 dark:tw-text-white">
                                            {applicant.appliedBy.skills.join(
                                                ", "
                                            )}
                                        </td>
                                        <td className="tw-border tw-border-orange-400 tw-p-2">
                                            <button
                                                className=" tw-rounded-full tw-border-none tw-bg-gradient-to-b tw-from-orange-500 tw-to-orange-600 tw-px-2 tw-py-2 tw-text-lg tw-text-white tw-shadow-2xl tw-transition-all  hover:tw-scale-105 hover:tw-shadow-xl dark:tw-orange-400"
                                                onClick={() =>
                                                    navigate(
                                                        `/application/${applicant._id}`
                                                    )
                                                }
                                            >
                                                View Application
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    )}
                    {applicants.length == 0 && <h1> No applicants found .</h1>}
                </div>
            </div>
        </div>
    );
}
