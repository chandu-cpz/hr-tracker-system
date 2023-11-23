import { useDispatch } from "react-redux";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../redux/slice/userSlice";
import validator from "validator";
import { setUser, setIsLoggedIn } from "../redux/slice/userSlice";
import { FaEye, FaEyeSlash } from "react-icons/fa";

export function Login() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [showPassword, setShowPassword] = useState(false);

    const [userData, setUserData] = useState({
        email: "",
        password: "",
    });

    const [errors, setErrors] = useState({
        email: "",
        password: "",
    });

    const handleChange = (e) => {
        setUserData({
            ...userData,
            [e.target.name]: e.target.value,
        });
    };

    const validateForm = () => {
        let isValid = true;
        const newErrors = { ...errors };

        // Validate email
        if (!validator.isEmail(userData.email)) {
            newErrors.email = "Please enter a valid email address";
            isValid = false;
        } else {
            newErrors.email = "";
        }
        setErrors(newErrors);
        return isValid;
    };

    const submitUser = async (e) => {
        e.preventDefault();

        if (validateForm()) {
            try {
                console.log("From frontend", userData);
                const response = await dispatch(loginUser(userData));
                if (response.status === 200) {
                    if (!response.data.error) {
                        // No error in response
                        dispatch(setUser(response.data));
                        dispatch(setIsLoggedIn(true));
                        console.log("Login successful!");
                        navigate("/jobs");
                    } else {
                        // Error exists in response
                        setErrors({
                            ...errors,
                            email: response.data.error,
                        });
                    }
                }
            } catch (error) {
                console.log(error.message);
            }
        }
    };

    return (
        <div className="tw-flex tw-h-screen ">
            <div className=" tw-mt-16 tw-flex tw-items-center">
                <div className="tw-ml-4 tw-w-1/6  tw-text-right">
                    <div className="tw-flex tw-flex-col">
                        <h1 className=" tw-m-12 tw-whitespace-nowrap tw-text-5xl tw-font-extrabold tw-text-black">
                            Streamline Recruiting Workflows
                        </h1>
                        <h1 className="tw-m-12 tw-whitespace-nowrap tw-text-5xl tw-font-extrabold tw-text-stone-700 tw-text-opacity-90">
                            Centralize Application Tracking
                        </h1>
                        <h1 className="tw-m-12 tw-whitespace-nowrap tw-text-5xl tw-font-extrabold tw-text-black tw-text-opacity-60">
                            Optimize Hiring Outcomes
                        </h1>
                    </div>
                </div>
            </div>
            <div className="tw-flex tw-flex-1 tw-items-center tw-justify-center ">
                <div className="tw-mx-auto tw-ml-0 tw-w-full  tw-translate-x-full tw-transform  tw-rounded-xl tw-bg-white tw-p-12 tw-shadow-lg tw-transition-transform tw-duration-300 tw-ease-in-out md:tw-max-w-md">
                    <h2 className="tw-mb-10 tw-mt-4 tw-text-center tw-text-4xl tw-font-bold">
                        Login
                    </h2>
                    <form className="tw-text-left">
                        <div className="tw-flex tw-flex-col tw-items-start tw-gap-10 md:tw-flex-col">
                            <div className="tw-mb-4md:tw-mb-0 tw-flex tw-w-full tw-flex-col md:tw-mr-4">
                                <label
                                    className="text-gray-700 tw-mb-3 tw-block tw-text-sm tw-font-bold"
                                    htmlFor="email"
                                >
                                    Email:
                                </label>
                                <input
                                    type="email"
                                    id="email"
                                    name="email"
                                    className="tw-focus:outline-none tw-focus:shadow-outline tw-focus:border-blue-500 tw-focus:ring tw-w-full  tw-appearance-none tw-rounded-full tw-border tw-bg-white tw-px-3 tw-py-2  tw-leading-tight tw-text-gray-700 tw-placeholder-gray-500 tw-shadow"
                                    placeholder=""
                                    value={userData.email}
                                    onChange={handleChange}
                                />
                                <span className="tw-text-red-500">
                                    {errors.email}
                                </span>
                            </div>
                            <div className="tw-relative tw-w-full">
                                <label
                                    className="tw-mb-3 tw-block tw-text-sm tw-font-bold tw-text-gray-700"
                                    htmlFor="password"
                                >
                                    Password:
                                </label>
                                <input
                                    className="tw-focus:tw-outline-none tw-focus:tw-shadow-outline tw-w-full tw-appearance-none tw-rounded-full tw-border tw-py-2 tw-pr-10 tw-leading-tight tw-text-gray-700 tw-shadow"
                                    type={showPassword ? "text" : "password"}
                                    id="password"
                                    name="password"
                                    value={userData.password}
                                    onChange={handleChange}
                                />

                                <button
                                    type="button"
                                    onClick={() =>
                                        setShowPassword(!showPassword)
                                    }
                                    className=" tw-absolute tw-bottom-0 tw-right-0 tw-m-2 tw-border-none tw-bg-white"
                                    style={{ zIndex: "10" }}
                                >
                                    {showPassword ? <FaEye /> : <FaEyeSlash />}
                                </button>
                                {errors.password && (
                                    <span className="tw-text-red-500">
                                        {errors.password}
                                    </span>
                                )}
                            </div>
                            <button
                                type="submit"
                                className="tw-hover:bg-orange-700 tw-focus:outline-none tw-focus:shadow-outline tw-mt-2 tw-rounded-full  tw-border-none tw-bg-orange-500  tw-bg-gradient-to-b tw-from-orange-500 tw-to-orange-600 tw-p-5  tw-px-6 tw-py-3 tw-text-3xl tw-font-medium tw-text-white tw-shadow-2xl tw-transition-all hover:tw-scale-105 hover:tw-shadow-xl"
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
