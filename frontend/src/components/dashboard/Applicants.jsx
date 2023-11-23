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
        <div className="tw-flex tw-flex-wrap  tw-gap-10">
            <Sidebar />
            <div className=" tw-w-13/15 tw-justify-center tw-mt-5">
                <h1>Applicants</h1>
                <div className=" tw-mx-auto tw-flex tw-flex-wrap tw-justify-center tw-gap-10">
                    {/* Table for Applicants*/}
                    {applicants.length > 0 && (
                        <table className="tw-mt-5  tw-border-collapse">
                            <thead>
                                <tr className="tw-bg-blue-900 tw-text-white">
                                    <th className="tw-border tw-border-blue-600 tw-p-2">
                                        Name
                                    </th>
                                    <th className="tw-border tw-border-blue-600 tw-p-2">
                                        Email
                                    </th>
                                    <th className="tw-border tw-border-blue-600 tw-p-2">
                                        Gender
                                    </th>
                                    <th className="tw-border tw-border-blue-600 tw-p-2">
                                        Education
                                    </th>
                                    <th className="tw-border tw-border-blue-600 tw-p-2">
                                        Skills
                                    </th>
                                    <th className="tw-border tw-border-blue-600 tw-p-2">
                                        View
                                    </th>
                                </tr>
                            </thead>

                            <tbody>
                                {applicants.map((applicant) => (
                                    <tr
                                        className="tw-bg-white hover:tw-bg-blue-200"
                                        key={applicant._id}
                                    >
                                        <td className="tw-border tw-border-blue-400 tw-p-2">
                                            {applicant.appliedBy.fullName}
                                        </td>
                                        <td className="tw-border tw-border-blue-400 tw-p-2">
                                            {applicant.appliedBy.email}
                                        </td>
                                        <td className="tw-border tw-border-blue-400 tw-p-2">
                                            {applicant.appliedBy.gender}
                                        </td>
                                        <td className="tw-border tw-border-blue-400 tw-p-2">
                                            {applicant.appliedBy.education}
                                        </td>
                                        <td className="tw-border tw-border-blue-400 tw-p-2">
                                            {applicant.appliedBy.skills.join(
                                                ", "
                                            )}
                                        </td>
                                        <td className="tw-border tw-border-blue-400 tw-p-2">
                                            <button
                                                className=" tw-rounded-full tw-border-none tw-bg-gradient-to-b tw-from-blue-400 tw-to-blue-700 tw-px-2 tw-py-2 tw-text-lg tw-text-white tw-shadow-2xl tw-transition-all  hover:tw-scale-105 hover:tw-shadow-xl "
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
