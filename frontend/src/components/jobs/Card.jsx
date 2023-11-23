import { BsBookmark, BsBookmarkFill } from "react-icons/bs";
import { useState } from "react";
import PropTypes from "prop-types";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useDispatch } from "react-redux";
import { addSavedJob, removeSavedJob } from "../../redux/slice/userSlice";
import { useSelector } from "react-redux";

export function Card({ job }) {
    console.log(job);
    const savedJobs = useSelector((state) => state.user.savedJobs);
    const { createdAt, companyName, jobTitle, skills, salary } = job;
    const [isBookmarked, setIsBookmarked] = useState(
        savedJobs ? savedJobs.includes(job._id) : false
    );
    const navigate = useNavigate();
    const dispatch = useDispatch();

    function formatDate(date) {
        const options = { day: "2-digit", month: "short", year: "numeric" };
        const formattedDate = new Date(date).toLocaleDateString(
            "en-US",
            options
        );

        // Split the formatted date into day, month, and year parts
        const [month, day, year] = formattedDate.split(" ");

        // Convert the month abbreviation to uppercase
        const capitalizedMonth = month.toUpperCase();

        // Return the formatted date with uppercase month abbreviation and desired format
        return `${day} ${capitalizedMonth} ${year}`;
    }

    const toggleBookmark = async () => {
        if (isBookmarked) {
            // Delete the saved job
            await axios.delete("/api/jobs/savejob", job._id);
            dispatch(removeSavedJob(job._id));
        } else {
            // Save the job
            await axios.post("/api/jobs/savejob", job._id);
            dispatch(addSavedJob(job._id));
        }

        setIsBookmarked((prev) => !prev);
    };

    const bookmarkIcon = isBookmarked ? <BsBookmarkFill /> : <BsBookmark />;

    return (
        <div className="tw-h-fit tw-w-1/5 tw-flex-shrink-0 tw-rounded-2xl tw-border tw-border-solid tw-p-2 tw-shadow-md">
            <div className="tw-rounded-2xl tw-bg-rose-300 tw-p-4">
                <div className="tw-mb-4 tw-flex tw-items-center tw-justify-between">
                    <div className="tw-inline-flex tw-items-center tw-rounded-full  tw-bg-white tw-px-2 tw-py-1 tw-font-medium">
                        {formatDate(createdAt)}
                    </div>

                    <div className="tw-rounded-full  tw-bg-white  tw-px-2 tw-py-1">
                        <a
                            href="#"
                            className="tw-text-current tw-no-underline"
                            onClick={toggleBookmark}
                        >
                            {bookmarkIcon}
                        </a>
                    </div>
                </div>

                <div className="tw-mb-2 tw-text-sm tw-text-gray-600">
                    {companyName}
                </div>

                <div className="tw-mb-2 tw-flex tw-justify-between">
                    <h3 className="tw-mb-2 tw-overflow-hidden tw-text-ellipsis tw-text-2xl tw-font-medium">
                        {jobTitle.toUpperCase()}
                    </h3>
                    <img
                        src="logo.png"
                        className="tw-mr-2 tw-h-6 tw-w-6 tw-rounded-full"
                    />
                </div>

                <div className="tw-flex tw-flex-wrap">
                    {skills &&
                        skills.map((skill, index) => (
                            <span
                                key={index}
                                className="tw-mb-2 tw-mr-2 tw-rounded-full tw-bg-white tw-px-3 tw-py-1 tw-font-medium "
                            >
                                {skill}
                            </span>
                        ))}
                </div>
            </div>

            <div className="tw-mt-4 tw-flex tw-items-center tw-justify-between">
                <div className=" tw-ml-2 tw-text-lg tw-font-medium">
                    ${salary}
                    <span className="tw-text-sm">/month</span>
                </div>

                <button
                    className="tw-rounded-full tw-border-none tw-bg-black tw-px-4 tw-py-2 tw-text-lg tw-font-medium tw-text-white"
                    onClick={() => navigate(`/job/${job._id}`)}
                >
                    Details
                </button>
            </div>
        </div>
    );
}

Card.propTypes = {
    job: PropTypes.shape({
        _id: PropTypes.string.isRequired,
        createdAt: PropTypes.string.isRequired,
        companyName: PropTypes.string.isRequired,
        jobTitle: PropTypes.string.isRequired,
        skills: PropTypes.arrayOf(PropTypes.string).isRequired,
        salary: PropTypes.string.isRequired,
    }).isRequired,
};
