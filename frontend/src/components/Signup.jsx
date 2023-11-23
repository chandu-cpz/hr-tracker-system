import { useState } from "react";
import { signUpUser } from "../redux/slice/userSlice";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { MdCameraAlt } from "react-icons/md";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import uploadFile from "../utils/uploadFile";
import validator from "validator";

export function Signup() {
    const [showPassword, setShowPassword] = useState(false);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [userData, setUserData] = useState({
        fullName: "",
        email: "",
        password: "",
        gender: "",
        role: "USER",
        company: "",
        companyImage: null, // Updated to handle file input
    });

    const handleChange = (e) => {
        setUserData({
            ...userData,
            [e.target.name]: e.target.value,
        });
    };

    const [preview, setPreview] = useState(userData.companyImage);

    const handleImageChange = async (e) => {
        setPreview(URL.createObjectURL(e.target.files[0]));
        setUserData({
            ...userData,
            companyImage: e.target.files[0],
        });

        console.log("We are uploading image");

        try {
            const image = await uploadFile(e.target.files[0], "company");
            setUserData({
                ...userData,
                companyImage: image.secure_url,
            });
        } catch (err) {
            console.log(err);
        }
    };
    const [errors, setErrors] = useState({});

    const validateInput = () => {
        let isValid = true;
        const newErrors = {};

        // Name
        if (userData.fullName == "") {
            isValid = false;
            newErrors.fullName = "Please enter your name";
        }

        // Email
        if (!validator.isEmail(userData.email)) {
            isValid = false;
            newErrors.email = "Please enter a valid email";
        }

        const options = {
            minLength: 8,
            minLowercase: 1,
            minUppercase: 1,
        };

        if (!validator.isStrongPassword(userData.password, options)) {
            newErrors.password =
                "Password should contain lowercase, uppercase, and 8 chars long ";
            isValid = false;
        }

        if (userData.role === "HR") {
            if (!userData.company) {
                newErrors.company = "Company name is required ";
                isValid = false;
            }
        }

        setErrors(newErrors);
        return isValid;
    };

    const submitUser = async (e) => {
        e.preventDefault();
        if (userData.gender === "male") userData.gender = "M";
        else if (userData.gender === "female") userData.gender = "F";
        else userData.gender = "O";

        if (validateInput()) {
            console.log("From frontend", userData);
            const response = await dispatch(signUpUser(userData));
            console.log(response);
            if (response.status === 200) {
                if (!response.data.error) {
                    // No error in response
                    console.log("Signup successful!");
                    navigate("/login");
                } else {
                    // Error exists in response
                    setErrors({
                        ...errors,
                        email: response.data.error,
                    });
                }
            } else {
                // Handle other response codes
                console.log("Error signing up");
            }
        }
    };

    return (
        <div className="tw-flex tw-h-screen tw-items-center tw-justify-center ">
            <div className="tw-mt-16 tw-flex tw-items-center">
                <div className="tw-ml-4 tw-w-1/6  tw-text-right">
                    <div className="tw-flex tw-flex-col">
                        <h1 className="tw-m-12 tw-whitespace-nowrap tw-text-4xl tw-font-extrabold tw-text-black ">
                            Streamline Recruiting Workflows
                        </h1>
                        <h1 className="tw-m-12 tw-whitespace-nowrap tw-text-4xl tw-font-extrabold tw-text-stone-700 tw-text-opacity-90">
                            Centralize Application Tracking
                        </h1>
                        <h1 className="tw-m-12 tw-whitespace-nowrap tw-text-3xl tw-font-extrabold tw-text-black tw-text-opacity-60">
                            Optimize Hiring Outcomes
                        </h1>
                    </div>
                </div>
            </div>
            <div className="tw-mx-auto tw-ml-4 tw-w-full tw-max-w-md tw-translate-x-full tw-transform tw-rounded-xl tw-bg-white tw-p-6 tw-shadow-lg tw-backdrop-blur-3xl tw-transition-transform tw-duration-300 tw-ease-in-out">
                <h2 className="tw-my-5 tw-text-center tw-text-4xl tw-font-bold">
                    Sign Up
                </h2>
                <form>
                    <div className="tw-mb-4 md:tw-mb-0 md:tw-mr-4">
                        <label
                            className="tw-mb-2 tw-block tw-text-sm tw-font-bold tw-text-gray-700"
                            htmlFor="name"
                        >
                            Name:
                        </label>
                        <input
                            type="text"
                            id="name"
                            name="fullName"
                            className="tw-focus:outline-none tw-focus:shadow-outline tw-w-full tw-appearance-none tw-rounded-full tw-border tw-px-3 tw-py-2 tw-leading-tight tw-text-gray-700 tw-shadow"
                            placeholder=""
                            value={userData.fullName}
                            onChange={handleChange}
                        />
                        {errors.fullName && (
                            <span className="tw-text-red-500">
                                {errors.fullName}
                            </span>
                        )}
                    </div>
                    <div className="tw-mb-4">
                        <label
                            className="tw-mb-2 tw-block tw-text-sm tw-font-bold tw-text-gray-700"
                            htmlFor="email"
                        >
                            Email:
                        </label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            className="tw-focus:outline-none tw-focus:shadow-outline tw-w-full tw-appearance-none tw-rounded-full tw-border tw-px-3 tw-py-2 tw-leading-tight tw-text-gray-700 tw-shadow"
                            placeholder=""
                            value={userData.email}
                            onChange={handleChange}
                        />
                        {errors.email && (
                            <span className="tw-text-red-500">
                                {errors.email}
                            </span>
                        )}
                    </div>
                    <div className="tw-relative">
                        <label
                            className="tw-mb-2 tw-block tw-text-sm tw-font-bold tw-text-gray-700"
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
                            onClick={() => setShowPassword(!showPassword)}
                            className=" tw-absolute tw-bottom-0 tw-right-0 tw-m-2 tw-border-none tw-bg-white"
                            style={{ zIndex: "10" }}
                        >
                            {showPassword ? <FaEye /> : <FaEyeSlash />}
                        </button>
                    </div>

                    {errors.password && (
                        <span className="tw-text-red-500">
                            {errors.password}
                        </span>
                    )}
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
                            <span className="tw-ml-2">Male</span>
                        </label>
                        <label className="tw-mr-6 tw-inline-flex tw-items-center">
                            <input
                                type="radio"
                                className="form-radio"
                                name="gender"
                                value="female"
                                onChange={handleChange}
                            />
                            <span className="tw-ml-2">Female</span>
                        </label>
                        <label className="tw-inline-flex tw-items-center">
                            <input
                                type="radio"
                                className="form-radio"
                                name="gender"
                                value="other"
                                onChange={handleChange}
                            />
                            <span className="tw-ml-2">Other</span>
                        </label>
                    </div>
                    <div className="tw-mb-4">
                        <label className="tw-mr-6 tw-inline-flex tw-items-center">
                            <input
                                type="checkbox"
                                className="form-checkbox"
                                name="role"
                                value="HR"
                                onChange={handleChange}
                            />
                            <span className="text-gray-700 tw-mb-2 tw-ml-2 tw-block tw-text-sm tw-font-bold">
                                HR
                            </span>
                        </label>
                    </div>

                    {userData.role === "HR" && (
                        <>
                            <div className="tw-mb-4">
                                <label
                                    className="tw-mb-2 tw-block tw-text-sm tw-font-bold tw-text-gray-700"
                                    htmlFor="company"
                                >
                                    Company Name:
                                </label>
                                <input
                                    type="text"
                                    id="company"
                                    name="company"
                                    className="tw-focus:outline-none tw-focus:shadow-outline tw-w-full tw-appearance-none tw-rounded-full tw-border tw-px-3 tw-py-2 tw-leading-tight tw-text-gray-700 tw-shadow"
                                    placeholder=""
                                    value={userData.company}
                                    onChange={handleChange}
                                />
                                <span className="tw-text-red-500">
                                    {errors.company}
                                </span>
                            </div>

                            <div className="tw-mb-4">
                                <label className="tw-mb-2 tw-block tw-text-sm tw-font-bold tw-text-gray-700">
                                    Company Image:
                                    <MdCameraAlt
                                        size={32}
                                        className="tw-ml-4 tw-mt-16 tw-text-gray-500"
                                    />
                                </label>
                                {userData.companyImage && (
                                    <img
                                        src={preview}
                                        alt="Profile"
                                        className="tw-m-5 tw-h-48 tw-w-48 tw-rounded-full tw-border-4 tw-border-orange-500 tw-object-cover"
                                    />
                                )}
                                <input
                                    type="file"
                                    id="companyImage"
                                    name="companyImage"
                                    accept="image/*" // Accept only image files
                                    onChange={handleImageChange}
                                />
                            </div>
                        </>
                    )}

                    <button
                        type="submit"
                        className="tw-hover:bg-orange-700  tw-focus:outline-none tw-focus:shadow-outline tw-rounded-full tw-border-none tw-bg-orange-500  tw-bg-gradient-to-b tw-from-orange-500 tw-to-orange-600 tw-p-5  tw-px-4 tw-py-3 tw-text-3xl tw-font-medium tw-text-white  tw-shadow-2xl tw-transition-all hover:tw-scale-105 hover:tw-shadow-xl"
                        onClick={submitUser}
                    >
                        Sign Up
                    </button>
                </form>
            </div>
        </div>
    );
}
