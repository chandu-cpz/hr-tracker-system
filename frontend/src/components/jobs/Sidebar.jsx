import PropTypes from "prop-types";
import { useEffect, useState } from "react";
export function Sidebar({ onSelect }) {
    const [checked, setChecked] = useState([
        "FULL_TIME",
        "PART_TIME",
        "INTERNSHIP",
    ]);

    const handleToggle = (option) => {
        if (checked.includes(option)) {
            setChecked(checked.filter((o) => o !== option));
        } else {
            setChecked([...checked, option]);
        }
    };
    useEffect(() => {
        onSelect(checked);
    }, [checked]);
    return (
        <div className="  tw-inline-block  tw-w-60 tw-shrink-0 tw-rounded-r-3xl tw-p-4">
            <div className="tw-mb-8 tw-rounded-2xl tw-bg-gray-200 tw-p-4 tw-shadow-lg">
                <div className="tw-mb-4 tw-rounded-lg tw-bg-white tw-p-4 tw-shadow-lg">
                    <p className="tw-text-xl tw-font-thin tw-text-gray-700">
                        Use filters for best results
                    </p>
                </div>

                <div>
                    <h3 className="tw-mb-2 tw-text-lg tw-font-bold dark:tw-text-black">
                        Working Schedule
                    </h3>

                    <div className="tw-flex tw-flex-col ">
                        <div className="tw-mb-2">
                            <input
                                type="checkbox"
                                id="fulltime"
                                className="tw-mr-2 tw-h-6 tw-w-6 "
                                checked={checked.includes("FULL_TIME")}
                                onChange={() => handleToggle("FULL_TIME")}
                            />
                            <label
                                htmlFor="fulltime"
                                className="tw-text-xl dark:tw-text-black"
                            >
                                Full Time
                            </label>
                        </div>

                        <div className="tw-mb-2">
                            <input
                                type="checkbox"
                                id="parttime"
                                className="tw-mr-2 tw-h-6 tw-w-6"
                                checked={checked.includes("PART_TIME")}
                                onChange={() => handleToggle("PART_TIME")}
                            />
                            <label
                                htmlFor="parttime"
                                className="tw-text-xl dark:tw-text-black"
                            >
                                Part Time
                            </label>
                        </div>

                        <div>
                            <input
                                type="checkbox"
                                id="internship"
                                className="tw-mr-2 tw-h-6 tw-w-6"
                                checked={checked.includes("INTERNSHIP")}
                                onChange={() => handleToggle("INTERNSHIP")}
                            />
                            <label
                                htmlFor="internship"
                                className="tw-text-xl dark:tw-text-black"
                            >
                                Internship
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

Sidebar.propTypes = {
    onSelect: PropTypes.func.isRequired,
};
