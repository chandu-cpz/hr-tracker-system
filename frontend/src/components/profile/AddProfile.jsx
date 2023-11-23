import { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { MdCameraAlt } from "react-icons/md";
import Multiselect from "multiselect-react-dropdown";
import { skills } from "../../constants";
import uploadFile from "../../utils/uploadFile";
import axios from "axios";
import { setUser } from "../../redux/slice/userSlice";
import { useNavigate } from "react-router-dom";

export function AddProfile() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const user = useSelector((state) => state.user);

    const [profileUploaded, setProfileUploaded] = useState(false);

    const [profile, setProfile] = useState({
        fullName: user.fullName,
        email: user.email,
        gender: user.gender,
        phoneNumber: user.phoneNumber,
        address: user.address,
        education: user.education,
        skills: user.skills,
        experience: user.experience,
        profileImage: user.profileImage,
    });

    const [preview, setPreview] = useState(user.profileImage);
    const [errors, setErrors] = useState({});

    const handleChange = (e) => {
        setProfile({
            ...profile,
            [e.target.name]: e.target.value,
        });
        setErrors({ ...errors, [e.target.name]: "" });
    };

    const handleImageChange = async (e) => {
        setPreview(URL.createObjectURL(e.target.files[0]));
        setProfile({
            ...profile,
            image: e.target.files[0],
        });

        try {
            const image = await uploadFile(e.target.files[0], "profile");
            setProfile({
                ...profile,
                profileImage: image.secure_url,
            });
            setProfileUploaded(true);
        } catch (err) {
            console.log(err);
        }
    };

    const validateFields = () => {
        let isValid = true;
        const newErrors = {};

        if (!profile.fullName?.trim()) {
            newErrors.fullName = "Please provide a full name";
            isValid = false;
        }

        // Validate each field

        if (profile.phoneNumber) {
            if (profile.phoneNumber.length !== 10) {
                newErrors.phoneNumber =
                    "Phone number must be 10 characters long";
                isValid = false;
            } else {
                newErrors.phoneNumber = "";
            }
        }
        // Add more validation for other fields if needed

        setErrors(newErrors);
        return isValid;
    };

    const editProfile = async (e) => {
        e.preventDefault();

        if (validateFields()) {
            const updatedUser = await axios.patch("/api/updateuser", profile);
            dispatch(setUser(updatedUser.data));
            console.log("Updated user");
            console.log(updatedUser.data);
            navigate("/profile");
        }
    };

    return (
        <div className="tw-mx-auto tw-max-w-3xl tw-rounded-lg  tw-p-8">
            <div className="tw-flex tw-flex-col tw-items-center  tw-rounded-lg tw-p-4 tw-shadow-2xl">
                <div className="tw-mb-5 tw-flex tw-justify-center">
                    <div className="">
                        <h1 className="tw-mb-10 tw-text-center tw-text-2xl tw-font-bold">
                            Edit Profile
                        </h1>
                        <img
                            src={preview}
                            alt="Profile Image"
                            className="tw-h-48 tw-w-48 tw-rounded-full tw-border-4 tw-border-orange-500 tw-object-cover"
                        />
                    </div>
                    <label className="tw-cursor-pointer">
                        <MdCameraAlt
                            size={32}
                            className="tw-ml-4 tw-mt-16 tw-text-gray-500"
                        />
                        <input
                            type="file"
                            name="image"
                            accept="image/*"
                            hidden
                            onChange={handleImageChange}
                        />
                    </label>
                </div>
                {profileUploaded && (
                    <span className="tw-text-green-500">
                        The profile is been uploaded
                    </span>
                )}

                <div className="tw-rounded-lg tw-bg-white tw-p-8 tw-shadow-xl">
                    <div className="tw-flex">
                        <div className="tw-m-5 ">
                            <div className="tw-mb-6">
                                <label className="tw-mb-2 tw-block tw-font-medium tw-text-gray-700">
                                    Full Name
                                </label>
                                <input
                                    type="text"
                                    name="fullName"
                                    value={profile.fullName}
                                    onChange={handleChange}
                                    className="tw-w-full tw-rounded-full tw-border tw-border-gray-300 tw-px-3 tw-py-2"
                                />
                                {errors.fullName && (
                                    <span className="tw-text-red-500">
                                        {errors.fullName}
                                    </span>
                                )}
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-mb-2 tw-block tw-font-medium tw-text-gray-700">
                                    Email
                                </label>
                                <input
                                    type="email"
                                    name="email"
                                    value={profile.email}
                                    onChange={handleChange}
                                    className="tw-w-full tw-rounded-full tw-border tw-border-gray-300 tw-px-3 tw-py-2"
                                />
                                {errors.email && (
                                    <span className="tw-text-red-500">
                                        {errors.email}
                                    </span>
                                )}
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-mb-2 tw-block tw-font-medium tw-text-gray-700">
                                    Gender
                                </label>
                                <select
                                    name="gender"
                                    value={profile.gender}
                                    onChange={handleChange}
                                    className="tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                >
                                    <option value="male">Male</option>
                                    <option value="female">Female</option>
                                    <option value="other">Other</option>
                                </select>
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-mb-2 tw-block tw-font-medium tw-text-gray-700">
                                    Phone Number
                                </label>
                                <input
                                    type="text"
                                    name="phoneNumber"
                                    value={profile.phoneNumber}
                                    onChange={handleChange}
                                    className="tw-w-full tw-rounded-full tw-border tw-border-gray-300 tw-px-3 tw-py-2"
                                />
                                {errors.phoneNumber && (
                                    <span className="tw-text-red-500">
                                        {errors.phoneNumber}
                                    </span>
                                )}
                            </div>
                        </div>
                        <div className="tw-m-5">
                            <div className="tw-mb-6">
                                <label className="tw-mb-2 tw-block tw-font-medium tw-text-gray-700">
                                    Address
                                </label>
                                <input
                                    type="text"
                                    name="address"
                                    value={profile.address}
                                    onChange={handleChange}
                                    className="tw-w-full tw-rounded-full tw-border tw-border-gray-300 tw-px-3 tw-py-2"
                                />
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-mb-2 tw-block tw-font-medium tw-text-gray-700">
                                    Education
                                </label>
                                <input
                                    type="text"
                                    name="education"
                                    value={profile.education}
                                    onChange={handleChange}
                                    className="tw-w-full tw-rounded-full tw-border tw-border-gray-300 tw-px-3 tw-py-2"
                                />
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-mb-2 tw-block tw-font-medium tw-text-gray-700">
                                    Experience (years)
                                </label>
                                <input
                                    type="number"
                                    name="experience"
                                    value={profile.experience}
                                    onChange={handleChange}
                                    className="tw-w-full tw-rounded-full tw-border tw-border-gray-300 tw-px-3 tw-py-2"
                                />
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-mb-2 tw-block tw-font-medium tw-text-gray-700">
                                    Skills
                                </label>
                                <Multiselect
                                    placeholder="Select Skills"
                                    style={{
                                        searchBox: {
                                            borderRadius: "9999px",
                                        },
                                        chips: {
                                            background: "#f97316",
                                            borderRadius: "9999px",
                                        },
                                        option: {
                                            backgroundColor: "#f97316",
                                        },
                                    }}
                                    isObject={false}
                                    selectedValues={profile.skills}
                                    onKeyPressFn={function noRefCheck() {}}
                                    onRemove={(e) => {
                                        setProfile({
                                            ...profile,
                                            skills: e,
                                        });
                                    }}
                                    onSearch={function noRefCheck() {}}
                                    onSelect={(e) => {
                                        setProfile({
                                            ...profile,
                                            skills: e,
                                        });
                                    }}
                                    options={skills}
                                />
                            </div>
                        </div>
                    </div>
                </div>

                <button
                    className="tw-m-4 tw-rounded-full tw-border-none tw-bg-gradient-to-b tw-from-orange-500 tw-to-orange-600 tw-px-4 tw-py-2  tw-text-3xl tw-text-white tw-shadow-2xl tw-transition-all  hover:tw-scale-105 hover:tw-shadow-xl"
                    onClick={editProfile}
                >
                    Save Profile
                </button>
            </div>
        </div>
    );
}
