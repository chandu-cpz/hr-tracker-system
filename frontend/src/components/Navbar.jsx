import { NavLink } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { setUser, setIsLoggedIn } from "../redux/slice/userSlice";
import { HiSun, HiMoon } from "react-icons/hi";

export function Navbar() {
    const [darkMode, setDarkMode] = useState(false);
    useEffect(() => {
        console.log("fist use effect is triggered");
        const theme = localStorage.getItem("theme");
        console.log(theme);
        if (theme === "dark") {
            setDarkMode(true);
            document.documentElement.classList.add("tw-dark");
        } else if (theme === "light") setDarkMode(false);
    }, []);

    function toggleDarkMode() {
        if (!darkMode) {
            localStorage.setItem("theme", "dark");
            document.documentElement.classList.add("tw-dark");
        } else {
            localStorage.setItem("theme", "light");
            document.documentElement.classList.remove("tw-dark");
        }
        setDarkMode((prev) => !prev);
        console.log("The value of dark mode" + darkMode);
    }

    const navigate = useNavigate();
    const dispatch = useDispatch();
    useEffect(() => {
        async function login() {
            const response = await axios.get("/api/login/auto");
            if (!response.data.error) {
                dispatch(setUser(response.data));
                dispatch(setIsLoggedIn(true));
                if (response.data.role === "HR") {
                    navigate("/dashboard");
                } else {
                    navigate("/userDashboard");
                }
            }
        }
        login();
    }, []);

    const isLoggedIn = useSelector((state) => state.isLoggedIn);
    const profileImg = useSelector((state) => state.user.profileImage);
    const role = useSelector((state) => state.user.role);

    const [isMenuOpen, setIsMenuOpen] = useState(false);

    const username = useSelector((state) => state.user.fullName);

    const logOut = async () => {
        await axios.get("/api/login/");
        dispatch(setIsLoggedIn(false));
        navigate("/");
    };

    return (
        <div className=" tw-h-15 tw-flex tw-justify-evenly tw-p-2 tw-shadow-lg dark:tw-bg-neutral-900">
            <NavLink to="/" className="tw-text-current tw-no-underline">
                <h1 className="tw-ms-4 dark:tw-text-gray-100">HRJ</h1>
            </NavLink>

            <NavLink
                to="/jobs"
                className={({ isActive }) =>
                    `tw-mt-2 tw-text-3xl tw-text-current tw-no-underline  ${
                        isActive ? "tw-text-orange-500" : "tw-text-gray-500"
                    }`
                }
            >
                <p className="dark:tw-text-gray-100">Find Jobs</p>
            </NavLink>
            <button
                className="tw-relative tw-inline-flex tw-h-14 tw-w-14 tw-items-center tw-justify-center tw-rounded-full tw-border-2 tw-border-gray-600 tw-bg-white"
                onClick={toggleDarkMode}
            >
                <HiSun
                    className={`tw-text-xl dark:tw-text-gray-100 ${
                        darkMode && "tw-hidden"
                    }`}
                />

                <HiMoon
                    className={`tw-text-xl dark:tw-text-black ${
                        !darkMode && "tw-hidden"
                    }`}
                />
            </button>
            {isLoggedIn ? (
                <div className="tw-flex tw-gap-2">
                    <div className="dropdown">
                        <a
                            className="dropdown-toggle"
                            data-bs-toggle="dropdown"
                        >
                            <img
                                src={profileImg}
                                className="tw-mr-4 tw-h-14 tw-w-14 tw-rounded-full"
                                alt="Profile"
                                onClick={() => setIsMenuOpen(!isMenuOpen)}
                            />
                        </a>
                        <ul className="dropdown-menu">
                            <li className="dropdown-item">
                                <img
                                    src={profileImg}
                                    className="tw-mr-4 tw-h-10 tw-w-10 tw-rounded-full"
                                    alt="Profile"
                                    onClick={() => setIsMenuOpen(!isMenuOpen)}
                                />{" "}
                                {username}
                                <div className="tw-mt-2  tw-flex tw-justify-center">
                                    <NavLink
                                        to="/profile"
                                        className="tw-current-text tw-no-underline"
                                    >
                                        <button className="tw-rounded-full tw-border-solid tw-border-orange-500 tw-bg-white tw-px-3 tw-text-orange-500 hover:tw-bg-orange-500  hover:tw-text-white">
                                            View Profile
                                        </button>
                                    </NavLink>
                                </div>
                            </li>
                            <li>
                                <hr className="dropdown-divider" />
                            </li>
                            <li className="dropdown-item">
                                <button
                                    className=" tw-mx-auto tw-rounded-full tw-border-none tw-bg-gradient-to-b tw-from-orange-500 tw-to-orange-600 tw-px-5 tw-py-2 tw-text-xl tw-text-white tw-shadow-2xl tw-transition-all  hover:tw-scale-105 hover:tw-shadow-xl"
                                    onClick={logOut}
                                >
                                    Log Out
                                </button>
                            </li>
                        </ul>
                    </div>
                    <NavLink
                        to={role === "HR" ? "/dashboard" : "/userDashboard"}
                        className={({ isActive }) =>
                            `tw-mt-2 tw-text-3xl tw-text-current tw-no-underline
                            ${
                                isActive
                                    ? "tw-text-orange-500"
                                    : "tw-text-black"
                            }`
                        }
                    >
                        <p className="dark:tw-text-gray-100">Dashboard</p>
                    </NavLink>
                </div>
            ) : (
                <div>
                    <NavLink to="/login">
                        <button className=" tw-mx-2 tw-rounded-full tw-border-none tw-bg-gradient-to-b tw-from-orange-500 tw-to-orange-600 tw-px-5 tw-py-3  tw-text-3xl tw-text-white tw-shadow-2xl tw-transition-all  hover:tw-scale-105 hover:tw-shadow-xl">
                            Login
                        </button>
                    </NavLink>

                    <NavLink to="/register">
                        <button className=" tw-px-23 tw-mx-2 tw-rounded-full tw-border-none tw-bg-gradient-to-b tw-from-orange-500  tw-to-orange-600 tw-py-3  tw-text-3xl tw-text-white tw-shadow-2xl tw-transition-all  hover:tw-scale-105 hover:tw-shadow-xl">
                            Sign Up
                        </button>
                    </NavLink>
                </div>
            )}
        </div>
    );
}
