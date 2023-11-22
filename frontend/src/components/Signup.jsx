import { useState } from "react";
import { signUpUser } from "../redux/slice/userSlice";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";

export function Signup() {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [userData, setUserData] = useState({
        fullName: "",
        email: "",
        password: "",
        gender: "",
    });

    const handleChange = (e) => {
        setUserData({
            ...userData,
            [e.target.name]: e.target.value,
        });
    };

    const submitUser = async (e) => {
        e.preventDefault();
        if (userData.gender == "male") userData.gender = "M";
        else if (userData.gender == "female") userData.gender = "F";
        else userData.gender = "O";

        console.log("From frontend", userData);
        await dispatch(signUpUser(userData));
        console.log("Finished Signing Up");
        navigate("/login");
    };

    return (
        <div className="tw-flex tw-h-screen tw-items-center tw-justify-center tw-bg-gray">
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
            <div className="tw-mx-auto  tw-ml-4 tw-w-full tw-max-w-md tw-translate-x-full tw-transform tw-rounded-xl tw-bg-white tw-p-6 tw-shadow-lg tw-transition-transform tw-duration-300 tw-ease-in-out">
                <h2 className="tw-mb-4 tw-text-2xl tw-font-bold">Sign Up</h2>
                <form>
                    <div className="tw-mb-4 md:tw-mb-0 md:tw-mr-4">
                        <label
                            className="tw-text-gray-700 tw-mb-2 tw-block tw-text-sm tw-font-bold"
                            htmlFor="name"
                        >
                            Name:
                        </label>
                        <input
                            type="text"
                            id="name"
                            name="fullName"
                            className="tw-text-gray-700 tw-focus:outline-none tw-focus:shadow-outline tw-w-full tw-appearance-none tw-rounded-full tw-border tw-px-3 tw-py-2 tw-leading-tight tw-shadow"
                            placeholder=""
                            value={userData.fullName}
                            onChange={handleChange}
                        />
                    </div>
                    <div className="tw-mb-4">
                        <label
                            className="tw-text-gray-700 tw-mb-2 tw-block tw-text-sm tw-font-bold"
                            htmlFor="email"
                        >
                            Email:
                        </label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            className="tw-text-gray-700 tw-focus:outline-none tw-focus:shadow-outline tw-w-full tw-appearance-none tw-rounded-full tw-border tw-px-3 tw-py-2 tw-leading-tight tw-shadow"
                            placeholder=""
                            value={userData.email}
                            onChange={handleChange}
                        />
                    </div>
                    <div className="tw-mb-4">
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
                            className="t5w-px-3 tw-text-gray-700 tw-focus:outline-none tw-focus:shadow-outline tw-w-full tw-appearance-none tw-rounded-full tw-border tw-py-2 tw-leading-tight tw-shadow"
                            placeholder=""
                            value={userData.password}
                            onChange={handleChange}
                        />
                    </div>
                    <div className="tw-mb-4">
                        <span className="text-gray-700 tw-mb-2 tw-block tw-text-sm tw-font-bold">
                            Gender
                        </span>
                        <label className="tw-mr-6 tw-inline-flex tw-items-center">
                            <input
                                type="radio"
                                className="form-radio"
                                name="gender"
                                value="male"
                                onChange={handleChange}
                            />
                            <span className="ml-2">Male</span>
                        </label>
                        <label className="tw-mr-6 tw-inline-flex tw-items-center">
                            <input
                                type="radio"
                                className="form-radio"
                                name="gender"
                                value="female"
                                onChange={handleChange}
                            />
                            <span className="ml-2">Female</span>
                        </label>
                        <label className="tw-inline-flex tw-items-center">
                            <input
                                type="radio"
                                className="form-radio"
                                name="gender"
                                value="other"
                                onChange={handleChange}
                            />
                            <span className="ml-2">Other</span>
                        </label>
                    </div>
                    <div className="tw-mb-4">
                        <span className="text-gray-700 tw-mb-2 tw-block tw-text-sm tw-font-bold">
                            HR
                        </span>
                        <label className="tw-mr-6 tw-inline-flex tw-items-center">
                            <input
                                type="radio"
                                className="form-radio"
                                name="role"
                                value="HR"
                                onChange={handleChange}
                            />
                            <span className="ml-2">HR</span>
                        </label>
                    </div>
                    <button
                        type="submit"
                        className="tw-hover:bg-blue-700 tw-focus:outline-none tw-focus:shadow-outline tw-rounded-full tw-bg-blue-500 tw-px-4 tw-py-2 tw-font-bold tw-text-white"
                        onClick={submitUser}
                    >
                        Sign Up
                    </button>
                </form>
            </div>
        </div>
    );
}
