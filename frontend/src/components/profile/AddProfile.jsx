import { useState } from "react";
import { useSelector } from "react-redux";
import { MdCameraAlt } from "react-icons/md";
import Multiselect from "multiselect-react-dropdown";

export function AddProfile() {
    const skills = ["Option 1", "Option 2", "Option 3", "Option 4", "Option 5"];
    const user = useSelector((state) => state.user);

    const [selectedSkills, setSelectedSkills] = useState([]);

    const [profile, setProfile] = useState({
        fullName: user.fullName,
        email: user.email,
        gender: user.gender,
        phoneNumber: user.phoneNumber,
        address: user.address,
        education: user.education,
        skills: user.skills,
        experience: user.experience,
        image: null,
    });

    const [preview, setPreview] = useState(user.profileImage);

    const handleChange = (e) => {
        setProfile({
            ...profile,
            [e.target.name]: e.target.value,
        });
    };

    const handleImageChange = (e) => {
        setPreview(URL.createObjectURL(e.target.files[0]));
        setProfile({
            ...profile,
            image: e.target.files[0],
        });
    };

    return (
        <div className="tw-bg-gray-100 tw-mx-auto tw-max-w-3xl tw-rounded-lg tw-p-8">
            <div className="tw-flex tw-flex-col tw-items-center  tw-rounded-lg tw-p-4 tw-shadow-md">
                <div className="tw-mb-5 tw-flex tw-justify-center">
                    <div className="">
                        <h1 className="tw-mb-10 tw-text-center tw-text-2xl tw-font-bold">
                            Edit Profile
                        </h1>
                        <img
                            src={preview}
                            alt="Profile"
                            className="tw-h-48 tw-w-48 tw-rounded-full tw-border-4 tw-border-orange-500 tw-object-cover"
                        />
                    </div>
                    <label className="tw-cursor-pointer">
                        <MdCameraAlt
                            size={32}
                            className="tw-text-gray-500 tw-ml-4 tw-mt-16"
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

                <div className="tw-rounded-lg tw-bg-white tw-p-8 tw-shadow">
                    <div className="tw-flex">
                        <div className="tw-m-5 ">
                            <div className="tw-mb-6">
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-medium">
                                    Full Name
                                </label>
                                <input
                                    type="text"
                                    name="fullName"
                                    value={profile.fullName}
                                    onChange={handleChange}
                                    className="tw-border-gray-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                />
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-medium">
                                    Email
                                </label>
                                <input
                                    type="email"
                                    name="email"
                                    value={profile.email}
                                    onChange={handleChange}
                                    className="tw-border-gray-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                />
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-medium">
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
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-medium">
                                    Phone Number
                                </label>
                                <input
                                    type="text"
                                    name="phoneNumber"
                                    value={profile.phoneNumber}
                                    onChange={handleChange}
                                    className="tw-border-gray-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                />
                            </div>
                        </div>
                        <div className="tw-m-5">
                            <div className="tw-mb-6">
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-medium">
                                    Address
                                </label>
                                <input
                                    type="text"
                                    name="address"
                                    value={profile.address}
                                    onChange={handleChange}
                                    className="tw-border-gray-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                />
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-medium">
                                    Education
                                </label>
                                <input
                                    type="text"
                                    name="education"
                                    value={profile.education}
                                    onChange={handleChange}
                                    className="tw-border-gray-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                />
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-medium">
                                    Experience (years)
                                </label>
                                <input
                                    type="number"
                                    name="experience"
                                    value={profile.experience}
                                    onChange={handleChange}
                                    className="tw-border-gray-300 tw-w-full tw-rounded-full tw-border tw-px-3 tw-py-2"
                                />
                            </div>

                            <div className="tw-mb-6">
                                <label className="tw-text-gray-700 tw-mb-2 tw-block tw-font-medium">
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
                                    onKeyPressFn={function noRefCheck() {}}
                                    onRemove={(e) => setSelectedSkills(e)}
                                    onSearch={function noRefCheck() {}}
                                    onSelect={(e) => setSelectedSkills(e)}
                                    options={skills}
                                />
                            </div>
                        </div>
                    </div>
                </div>

                <button className="tw-m-6 tw-rounded-full tw-border-none tw-bg-orange-500   tw-px-4 tw-py-3 tw-shadow-xl hover:tw-bg-orange-600">
                    Save Profile
                </button>
            </div>
        </div>
    );
}
