import React, { useState } from "react";
import { FaPhoneAlt, FaEnvelope } from "react-icons/fa";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";
import validator from "validator";

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

    // Validate password (you can add more complex validation rules)
    // For demonstration purposes, let's assume there's a user.password field
    // Change it according to your actual user structure
    if (user.password.length < 6) {
      newErrors.password = "Password must be at least 6 characters long";
      isValid = false;
    } else {
      newErrors.password = "";
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
    <div className="tw-bg-gray-100 tw-mx-auto tw-max-w-3xl tw-rounded-lg tw-p-8">
      <div className="tw-flex tw-items-center tw-rounded-lg tw-bg-blue-200 tw-p-4 tw-shadow-md">
        <img
          className="tw-mr-8 tw-h-40 tw-w-40 tw-rounded-full tw-border-4 tw-border-blue-900"
          src={user.profileImage}
          alt={user.fullName}
        />

        <div>
          <h1 className="tw-text-4xl tw-font-bold tw-text-blue-600">
            {user.fullName}
          </h1>

          <div className="tw-mt-5 tw-flex tw-items-center">
            <FaPhoneAlt className="tw-mr-2 tw-text-blue-900" />
            <span>{user.phoneNumber}</span>
          </div>

          <div className="tw-mb-5  tw-flex tw-items-center">
            <FaEnvelope className="tw-mr-2 tw-text-blue-900" />
            <span>{user.email}</span>
          </div>
          <Link to="/editprofile">
            <button className="tw-my-2 tw-rounded-full tw-border-none tw-bg-blue-900 tw-px-4 tw-py-2 tw-text-white">
              Edit Profile
            </button>
          </Link>
        </div>
      </div>

      <div className="tw-mt-8 tw-grid tw-grid-cols-1 tw-gap-4 tw-rounded-lg tw-bg-blue-200 tw-p-4 tw-shadow-md md:tw-grid-cols-2">
        {/* ... existing code ... */}
        <div>
          <h3 className="tw-mb-4 tw-text-2xl tw-font-medium tw-text-blue-600">
            Info
          </h3>

          <div className="tw-mb-2 tw-flex tw-items-center">
            <p className="tw-mr-4 tw-font-medium">Gender:</p>
            <p className="tw-rounded-full tw-bg-blue-900 tw-px-4 tw-py-1 tw-text-white">
              {user.gender}
            </p>
          </div>

          <div className="tw-mb-2 tw-flex tw-items-center">
            <p className="tw-mr-4 tw-font-medium">Location:</p>
            <p className="tw-rounded-full tw-bg-blue-900 tw-px-4 tw-py-1 tw-text-white">
              {user.address}
            </p>
          </div>

          <div className="tw-flex tw-items-center">
            <p className="tw-mr-4 tw-font-medium">Education:</p>
            <p className="tw-rounded-full tw-bg-blue-900 tw-px-4 tw-py-1 tw-text-white">
              {user.education}
            </p>
          </div>
        </div>

        <div>
          <h3 className="tw-mb-4 tw-text-2xl tw-font-medium tw-text-blue-600">
            Experience
          </h3>

          <div className="tw-mb-2 tw-flex tw-items-center">
            <p className="tw-mr-4 tw-font-medium">Years:</p>
            <p className="tw-rounded-full tw-bg-blue-900 tw-px-4 tw-py-1 tw-text-white">
              {user.experience}
            </p>
          </div>
        </div>

        <div className="tw-col-span-2">
          <h3 className="tw-mb-4 tw-text-2xl tw-font-medium tw-text-blue-600">
            Skills
          </h3>

          <div className="tw-flex tw-flex-wrap tw-gap-2">
            {user && user.skills && user.skills.map((skill) => (
              <p
                key={skill}
                className="tw-rounded-full tw-bg-blue-900 tw-px-4 tw-py-1 tw-text-white"
              >
                {skill}
              </p>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
