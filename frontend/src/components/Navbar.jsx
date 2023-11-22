import { NavLink } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { setUser, setIsLoggedIn } from "../redux/slice/userSlice";

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
        <div className=" tw-h-15 tw-flex tw-justify-evenly tw-bg-gray tw-p-2 tw-shadow-lg">
            <NavLink to="/" className="tw-text-current tw-no-underline">
                <h1 className="tw-ms-4">HRJ</h1>
            </NavLink>

            <NavLink
                to="/jobs"
                className={({ isActive }) =>
                    `tw-mt-2 tw-text-3xl tw-text-current tw-no-underline ${
                        isActive ? "tw-text-orange-500" : "tw-text-gray-500"
                    }`
                }
            >
                <p className="">Find jobs</p>
            </NavLink>
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
                                    className="tw-rounded-full tw-border-none tw-bg-orange-500 tw-px-4 tw-text-2xl"
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
                        <p className="">Dashboard</p>
                    </NavLink>
                </div>
            ) : (
                <div>
                    <NavLink to="/login">
                        <button className="tw-mx-1 tw-rounded-full tw-border-none tw-bg-orange-500 tw-px-5 tw-text-2xl lg:tw-mr-5 lg:tw-p-3 lg:tw-px-5">
                            Login
                        </button>
                    </NavLink>

                    <NavLink to="/register">
                        <button className="tw-rounded-full tw-border-none tw-bg-orange-500 tw-px-5 tw-text-2xl lg:tw-mr-5 lg:tw-p-3 lg:tw-px-5">
                            Sign Up
                        </button>
                    </NavLink>
                </div>
            )}
        </div>
    );
}
