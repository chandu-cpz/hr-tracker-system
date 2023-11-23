import { useState } from "react";
import { FaPhoneAlt, FaEnvelope } from "react-icons/fa";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";
import validator from "validator";
import { Sidebar } from "../dashboard";
import { UserSidebar } from "../userDashboard";

export function Profile() {
    const user = useSelector((state) => state.user);

    const [errors, setErrors] = useState({
        email: "",
        password: "",
        phoneNumber: "",
    });

    const validateForm = () => {
        let isValid = true;
        const newErrors = { ...errors };

        // Validate email
        if (!validator.isEmail(user.email)) {
            newErrors.email = "Please enter a valid email address";
            isValid = false;
        } else {
            newErrors.email = "";
        }

        // Validate phone number
        if (user.phoneNumber.length !== 10) {
            newErrors.phoneNumber = "Phone number must be 10 characters long";
            isValid = false;
        } else {
            newErrors.phoneNumber = "";
        }

        setErrors(newErrors);
        return isValid;
    };

    return (
        <>
            <div className="tw-flex">
                <div className="">
                    {user.role == "HR" ? <Sidebar /> : <UserSidebar />}
                </div>
                <div className="tw-flex tw-w-10/12  dark:tw-bg-stone-800">
                    <div className="tw-mx-auto tw-max-w-3xl  tw-rounded-lg  tw-p-8 tw-pt-12">
                        <div className="tw-flex tw-items-center tw-rounded-lg tw-bg-orange-100 tw-p-4 tw-shadow-md">
                            <img
                                className="tw-mr-8 tw-h-40 tw-w-40 tw-rounded-full tw-border-4 tw-border-orange-500"
                                src={user.profileImage}
                                alt={user.fullName}
                            />

                            <div>
                                <h1 className="tw-text-4xl tw-font-bold tw-text-orange-600">
                                    {user.fullName}
                                </h1>

                                <div className="tw-mt-5 tw-flex tw-items-center">
                                    <FaPhoneAlt className="tw-mr-2 tw-text-orange-500" />
                                    <span>{user.phoneNumber}</span>
                                </div>

                                <div className="tw-mb-5  tw-flex tw-items-center">
                                    <FaEnvelope className="tw-mr-2 tw-text-orange-500" />
                                    <span>{user.email}</span>
                                </div>
                                <Link to="/editprofile">
                                    <button className=" tw-rounded-full tw-border-none tw-bg-gradient-to-b tw-from-orange-500 tw-to-orange-600 tw-px-2 tw-py-2  tw-text-white tw-shadow-2xl tw-transition-all  hover:tw-scale-105 hover:tw-shadow-xl">
                                        Edit Profile
                                    </button>
                                </Link>
                            </div>
                        </div>

                        <div className="tw-mt-8 tw-grid tw-grid-cols-1 tw-gap-4 tw-rounded-lg tw-bg-orange-100 tw-p-4 tw-shadow-md md:tw-grid-cols-2">
                            {/* ... existing code ... */}
                            <div>
                                <h3 className="tw-mb-4 tw-text-2xl tw-font-medium tw-text-orange-600">
                                    Info
                                </h3>

                                <div className="tw-mb-2 tw-flex tw-items-center">
                                    <p className="tw-mr-4 tw-font-medium">
                                        Gender:
                                    </p>
                                    <p className="tw-rounded-full tw-bg-orange-500 tw-px-2 tw-py-1 tw-text-white">
                                        {user.gender}
                                    </p>
                                </div>

                                <div className="tw-mb-2 tw-flex tw-items-center">
                                    <p className="tw-mr-4 tw-font-medium">
                                        Location:
                                    </p>
                                    <p className="tw-rounded-full tw-bg-orange-500 tw-px-2 tw-py-1 tw-text-white">
                                        {user.address}
                                    </p>
                                </div>

                                <div className="tw-flex tw-items-center">
                                    <p className="tw-mr-4 tw-font-medium">
                                        Education:
                                    </p>
                                    <p className="tw-rounded-full tw-bg-orange-500 tw-px-2 tw-py-1 tw-text-white">
                                        {user.education}
                                    </p>
                                </div>
                            </div>

                            <div>
                                <h3 className="tw-mb-4 tw-text-2xl tw-font-medium tw-text-orange-600">
                                    Experience
                                </h3>

                                <div className="tw-mb-2 tw-flex tw-items-center">
                                    <p className="tw-mr-4 tw-font-medium">
                                        Years:
                                    </p>
                                    <p className="tw-rounded-full tw-bg-orange-500 tw-px-2 tw-py-1 tw-text-white">
                                        {user.experience}
                                    </p>
                                </div>
                            </div>

                            <div className="tw-col-span-2">
                                <h3 className="tw-mb-4 tw-text-2xl tw-font-medium tw-text-orange-600">
                                    Skills
                                </h3>

                                <div className="tw-flex tw-flex-wrap tw-gap-2">
                                    {user &&
                                        user.skills &&
                                        user.skills.map((skill) => (
                                            <p
                                                key={skill}
                                                className="tw-rounded-full tw-bg-orange-500 tw-px-3 tw-py-1 tw-text-white"
                                            >
                                                {skill}
                                            </p>
                                        ))}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}
