import { NavLink } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { setUser, setIsLoggedIn } from "../redux/slice/userSlice";
import logo from '../assets/logo.svg';

export function Navbar() {
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
        <div className="tw-bg-blue-900">

            <div className="tw-flex tw-w-[100vw] ">
                
                <div className="  tw-w-[25%] ">
                <NavLink to="/" className="tw-text-current tw-no-underline">
                    <h1 className="tw-p-3"><img src={logo} className="tw-h-[4rem]"/></h1>
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
                    <div className="tw-flex tw-gap-3  ">
                        <div className="dropdown">
                            <a
                                className="dropdown-toggle"
                                data-bs-toggle="dropdown"
                            >
                                <img
                                    src={profileImg}
                                    className="tw-mr-3 tw-h-14 tw-w-14 tw-mt-5 tw-rounded-full"
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
                                            <button className="tw-rounded-full tw-border-solid tw-border-blue-500 tw-bg-white tw-px-3 tw-text-blue-500 hover:tw-text-blue-300  hover:tw-text-black">
                                                View Profile
                                            </button>
                                        </NavLink>
                                    </div>
                                </li>
                                <li>
                                    <hr className="dropdown-divider" />
                                </li>
                                <li className="dropdown-item">
                                    <button className="tw-rounded-full tw-border-none tw-text-blue-900 tw-px-10 tw-text-2xl tw-bg-blue-200 tw-py-2">
                                        Log Out
                                    </button>
                                </li>
                            </ul>
                        </div>
                        <NavLink
                            to={role === "HR" ? "/dashboard" : "/userDashboard"}
                            className={({ isActive }) =>
                                `tw-mt-7 tw-text-3xl tw-text-current tw-no-underline
                                ${
                                    isActive
                                        ? "tw-text-white"
                                        : "tw-text-white"
                                }`
                            }
                        >
                            <p className="">Dashboard</p>
                        </NavLink>
                    </div>
                ) : (
                    <div className="tw-px-6 tw-space-x-1 tw-align-center tw-my-5">
                        <NavLink to="/login">
                        <button className="tw-rounded-full tw-border-none  tw-bg-gray tw-text-gray-900  tw-px-5 tw-text-xl   lg:tw-py-2 lg:tw-px-[1rem]">
                                Login
                            </button>
                        </NavLink>

                        <NavLink to="/register">
                            <button className="tw-rounded-full tw-border-none  tw-bg-gray tw-text-gray-900  tw-px-5 tw-text-xl  lg:tw-py-2 lg:tw-px-[1rem]">
                                Sign Up
                            </button>
                        </NavLink>
                    </div>
                )}
                </div>
            </div>
        </div>
    );
    
}
