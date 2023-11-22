import { NavLink } from "react-router-dom";
import { useSelector } from "react-redux";
import { useState } from "react";
import logo from '../assets/logo.svg';
import logo1 from '../assets/logo1.svg';
import { FaSun, FaMoon } from 'react-icons/fa';
export function Navbar() {
    const isLoggedIn = useSelector((state) => state.isLoggedIn);
    const profileImg = useSelector((state) => state.user.profileImage);
    const role = useSelector((state) => state.user.role);

    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const [darkMode, setDarkMode] = useState(false);

    const toggleDarkMode = () => {
        setDarkMode(!darkMode);
    };

    return (
        <div className={`tw-bg-${darkMode ? "black" : "blue-900"} tw-transition-colors`}>

            <div className="tw-flex tw-w-[100vw] ">
                <div className="  tw-w-[25%] ">
                    <NavLink to="/" className="tw-text-current tw-no-underline">
                        <h1 className="tw-p-3"><img
                            src={darkMode ? logo1 : logo}
                            alt="Logo"
                            className="tw-h-[4rem]"
                            />
                        </h1>
                    </NavLink>
                </div>
                <div className="tw-w-[60%] tw-text-center ">
                    <NavLink
                        to="/jobs"
                        className={({ isActive }) =>
                            `tw-mt-2 tw-text-3xl tw-text-current tw-no-underline ${
                                isActive ? "tw-text-blue-500" : "tw-text-gray"
                            }`
                        }
                    >
                        <p className="tw-pt-7 tw-font-bold">Find jobs</p>
                    </NavLink>
                </div>
                <div className="tw-w-[35%] tw-text-end ">
                    {isLoggedIn ? (
                        <div className="tw-flex tw-gap-2">
                            {/* ... (existing code) */}
                            <NavLink
                                to={role === "HR" ? "/dashboard" : "/userDashboard"}
                                className={({ isActive }) =>
                                    `tw-mt-2 tw-text-3xl tw-text-current tw-no-underline
                                    ${
                                        isActive
                                            ? "tw-text-blue-500"
                                            : "tw-text-black"
                                    }`
                                }
                            >
                                <p className="">Dashboard</p>
                            </NavLink>
                        </div>
                    ) : (
                        
                        <div className="tw-px-6 tw-space-x-2 tw-align-center tw-my-5">
                            <button
                                onClick={toggleDarkMode}
                                className={`tw-rounded-full tw-border-none tw-bg-${darkMode ? "white" : "gray-700"} tw-text-${darkMode ? "black" : "blue-900"} tw-px-1`}
                            >
                                {darkMode ? <FaSun /> : <FaMoon />}
                            </button>
                            <NavLink to="/login">
                                <button className={`tw-rounded-full tw-border-none  tw-bg-${darkMode ? "blue-200" : "gray-900"} tw-text-${darkMode ? "gray-900" : "gray-900"} tw-px-5 tw-text-xl   lg:tw-py-2 lg:tw-px-[1rem]`}>
                                    Login
                                </button>
                            </NavLink>

                            <NavLink to="/register">
                                <button className={`tw-rounded-full tw-border-none  tw-bg-${darkMode ? "blue-200" : "gray-900"} tw-text-${darkMode ? "gray-900" : "gray-900"} tw-px-5 tw-text-xl  lg:tw-py-2 lg:tw-px-[1rem]`}>
                                    Sign Up
                                </button>
                            </NavLink>
                        </div>
                    )}
                    
                    {/* Dark Mode Toggle Button */}
                    
                </div>
            </div>
        </div>
    );
}
