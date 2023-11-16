import { useDispatch } from "react-redux";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../redux/slice/userSlice";

export function Login() {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [userData, setUserData] = useState({
        email: "",
        password: "",
    });

    const handleChange = (e) => {
        setUserData({
            ...userData,
            [e.target.name]: e.target.value,
        });
    };

    const submitUser = (e) => {
        e.preventDefault();
        console.log("From frontend", userData);
        dispatch(loginUser(userData));
        navigate("/jobs");
    };
    return (
        <div className="tw-flex tw-h-screen tw-bg-gray">
            <div className="tw-mt-16 tw-flex tw-items-center">
                <div className="tw-ml-4 tw-w-1/6  tw-text-right">
                    <div className="tw-flex tw-flex-col">
                        <h1 className="tw-mb-2 tw-whitespace-nowrap tw-text-4xl tw-font-extrabold tw-text-black ">
                            Streamline Recruiting Workflows
                        </h1>
                        <h1 className="tw-mb-2 tw-whitespace-nowrap tw-text-4xl tw-font-extrabold tw-text-stone-700 tw-text-opacity-90">
                            Centralize Application Tracking
                        </h1>
                        <h1 className="tw-mb-2 tw-whitespace-nowrap tw-text-3xl tw-font-extrabold tw-text-black tw-text-opacity-60">
                            Optimize Hiring Outcomes
                        </h1>
                    </div>
                </div>
            </div>
            <div className="tw-flex tw-flex-1 tw-items-center tw-justify-center ">
                <div className="tw-mx-auto tw-ml-0 tw-w-full  tw-translate-x-full tw-transform  tw-rounded-xl tw-bg-white tw-p-12 tw-shadow-lg tw-transition-transform tw-duration-300 tw-ease-in-out md:tw-max-w-md">
                    <h2 className="tw-mb-4 tw-mt-4 tw-text-2xl tw-font-bold">
                        Login
                    </h2>
                    <form className="tw-text-left">
                        <div className="tw-flex tw-flex-col tw-items-start md:tw-flex-col">
                            <div className="tw-mb-4md:tw-mb-0 tw-flex tw-w-full tw-flex-col md:tw-mr-4">
                                <label
                                    className="text-gray-700 tw-mb-2 tw-block tw-text-sm tw-font-bold"
                                    htmlFor="email"
                                >
                                    Email:
                                </label>
                                <input
                                    type="email"
                                    id="email"
                                    name="email"
                                    className="tw-text-gray-700 tw-focus:outline-none tw-focus:shadow-outline tw-placeholder-gray-500 tw-focus:border-blue-500  tw-focus:ring tw-w-full tw-appearance-none tw-rounded-full tw-border tw-bg-white  tw-px-3 tw-py-2 tw-leading-tight tw-shadow"
                                    placeholder=""
                                    value={userData.email}
                                    onChange={handleChange}
                                />
                            </div>
                            <div className="tw-mb-4 tw-flex tw-w-full tw-flex-col md:tw-mb-0 md:tw-mr-4">
                                <label
                                    className="text-gray-700 tw-mb-2 tw-block tw-text-sm tw-font-bold"
                                    htmlFor="password"
                                >
                                    Password:
                                </label>
                                <input
                                    type="password"
                                    id="password"
                                    name="password"
                                    className="ma:tw-w-3/4 tw-text-gray-700 tw-focus:outline-none tw-focus:shadow-outline tw-placeholder-gray-500 tw-focus:border-blue-500 tw-focus:ring tw-w-full tw-appearance-none tw-rounded-full tw-border tw-bg-white tw-px-3 tw-py-2 tw-leading-tight tw-shadow"
                                    placeholder=""
                                    value={userData.password}
                                    onChange={handleChange}
                                />
                            </div>
                            <button
                                type="submit"
                                className="tw-hover:bg-orange-700 tw-focus:outline-none tw-focus:shadow-outline tw-mt-2 tw-rounded-full tw-bg-orange-500 tw-px-6 tw-py-3 tw-font-bold tw-text-white"
                                onClick={submitUser}
                            >
                                Login
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}
