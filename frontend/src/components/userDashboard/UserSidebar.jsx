import { NavLink } from "react-router-dom";

import { MdDashboard } from "react-icons/md";
import { MdGroupAdd } from "react-icons/md";
import { FaRegCalendarAlt } from "react-icons/fa";
import { MdOutlinePersonPin } from "react-icons/md";
import { FaGear } from "react-icons/fa6";



export function UserSidebar() {
    return (
        <div className={"tw-bg-blue-900 tw-flex tw-h-screen tw-p-4"}>
            <div className="tw-mb-10 tw-bg-blue tw-p-9 tw-shadow-lg">
                <div>
                    <h3 className="tw-mb-4 tw-pb-3 tw-text-2xl tw-text-white">MAIN MENU</h3>

                    <NavLink
                        to="/UserDashboard"
                        className={({ isActive }) =>
                            isActive
                                ? "tw-text-blue-500 tw-no-underline"
                                : " tw-p-5 tw-text-white tw-no-underline"
                        }
                    >
                        <div className="tw-flex tw-gap-4">
                            <MdDashboard size={35} />
                            <p className="tw-text-3xl ">Dashboard</p>
                        </div>
                    </NavLink>

                    <NavLink
                        to="/jobs"
                        className={({ isActive }) =>
                            isActive
                                ? " tw-text-blue-500 tw-no-underline"
                                : " tw-p-5 tw-text-white tw-no-underline"
                        }
                    >
                        <div className="tw-flex tw-gap-4">
                            <MdGroupAdd size={35} />
                            <p className="tw-text-3xl">Find Jobs</p>
                        </div>
                    </NavLink>

                    <NavLink
                        to="/appliedjobs"
                        className={({ isActive }) =>
                            isActive
                                ? " tw-text-blue-500 tw-no-underline"
                                : " tw-p-5 tw-text-white tw-no-underline"
                        }
                    >
                        <div className="tw-flex tw-gap-4">
                            <FaRegCalendarAlt size={35} />
                            <p className="tw-text-3xl ">Applied Jobs</p>
                        </div>
                    </NavLink>

                    <NavLink
                        to="/savedJobs"
                        className={({ isActive }) =>
                            isActive
                                ? " tw-text-blue-500 tw-no-underline"
                                : " tw-p-5 tw-text-white tw-no-underline"
                        }
                    >
                        <div className="tw-flex tw-gap-4">
                            <MdOutlinePersonPin size={35} />
                            <p className="tw-text-3xl ">Saved Jobs</p>
                        </div>
                    </NavLink>
                </div>

                <div>
                    <h3 className="tw-mb-4 tw-pb-3 tw-text-2xl tw-text-white">OTHERS</h3>

                    <NavLink
                        to="/profile"
                        className={({ isActive }) =>
                            isActive
                                ? " tw-text-blue-500 tw-no-underline"
                                : " tw-p-5 tw-text-white tw-no-underline"
                        }
                    >
                        <div className="tw-flex tw-gap-4">
                            <FaGear size={35} />
                            <p className="tw-text-3xl "> Profile</p>
                        </div>
                    </NavLink>
                </div>
            </div>
        </div>
    );
}