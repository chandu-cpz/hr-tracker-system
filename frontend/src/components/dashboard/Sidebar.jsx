import { NavLink } from "react-router-dom";

import { MdDashboard } from "react-icons/md";
import { MdGroupAdd } from "react-icons/md";
import { FaRegCalendarAlt } from "react-icons/fa";
import { MdOutlinePersonPin } from "react-icons/md";
import { FaGear } from "react-icons/fa6";

export function Sidebar() {
    return (
        <div className=" tw-flex tw-h-screen tw-rounded-r-3xl tw-p-4 dark:tw-bg-neutral-900 tw-mt-1">
            <div className="tw-mb-10 tw-rounded-3xl  tw-p-9 tw-shadow-lg">
                <div>
                    <h3 className="tw-mb-4 tw-pb-3 tw-text-2xl dark:tw-text-white ">
                        MAIN MENU
                    </h3>

                    <NavLink
                        to="/dashboard"
                        className={({ isActive }) =>
                            isActive
                                ? "tw-text-orange-500 tw-no-underline"
                                : " tw-p-5 tw-text-current tw-no-underline"
                        }
                    >
                        <div className="tw-flex tw-gap-4">
                            <MdDashboard
                                size={35}
                                className="dark:tw-text-white"
                            />
                            <p className="tw-text-3xl dark:tw-text-white">
                                Dashboard
                            </p>
                        </div>
                    </NavLink>

                    <NavLink
                        to="/addjob"
                        className={({ isActive }) =>
                            isActive
                                ? " tw-text-orange-500 tw-no-underline"
                                : " tw-p-5 tw-text-current tw-no-underline"
                        }
                    >
                        <div className="tw-flex tw-gap-4">
                            <MdGroupAdd
                                size={35}
                                className="dark:tw-text-white"
                            />
                            <p className="tw-text-3xl dark:tw-text-white">
                                Post Job
                            </p>
                        </div>
                    </NavLink>

                    <NavLink
                        to="/applicants"
                        className={({ isActive }) =>
                            isActive
                                ? " tw-text-orange-500 tw-no-underline"
                                : " tw-p-5 tw-text-current tw-no-underline"
                        }
                    >
                        <div className="tw-flex tw-gap-4">
                            <FaRegCalendarAlt
                                size={35}
                                className="dark:tw-text-white"
                            />
                            <p className="tw-text-3xl dark:tw-text-white">
                                Applicants
                            </p>
                        </div>
                    </NavLink>

                    <NavLink
                        to="/employees"
                        className={({ isActive }) =>
                            isActive
                                ? " tw-text-orange-500 tw-no-underline"
                                : " tw-p-5 tw-text-current tw-no-underline"
                        }
                    >
                        <div className="tw-flex tw-gap-4">
                            <MdOutlinePersonPin
                                size={35}
                                className="dark:tw-text-white"
                            />
                            <p className="tw-text-3xl dark:tw-text-white">
                                Employees
                            </p>
                        </div>
                    </NavLink>
                </div>

                <div>
                    <h3 className="tw-mb-4 tw-pb-3 tw-text-2xl dark:tw-text-white">
                        OTHERS
                    </h3>

                    <NavLink
                        to="/profile"
                        className={({ isActive }) =>
                            isActive
                                ? " tw-text-orange-500 tw-no-underline"
                                : " tw-p-5 tw-text-current tw-no-underline"
                        }
                    >
                        <div className="tw-flex tw-gap-4">
                            <FaGear size={35} className="dark:tw-text-white" />
                            <p className="tw-text-3xl dark:tw-text-white">
                                {" "}
                                Profile
                            </p>
                        </div>
                    </NavLink>
                </div>
            </div>
        </div>
    );
}
