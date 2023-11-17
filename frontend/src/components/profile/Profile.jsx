import { FaPhoneAlt, FaEnvelope } from "react-icons/fa";
import { Link } from "react-router-dom";

export function Profile() {
    const user = {
        fullName: "John Doe",
        gender: "M",
        email: "john@email.com",
        phoneNumber: "123-456-7890",
        address: "123 Main St",
        education: "BS Computer Science",
        skills: ["JavaScript", "React"],
        experience: "5 years",
        profileImage:
            "https://qph.cf2.quoracdn.net/main-qimg-deffe0bd0e8c5cc04b4ba763afd2a686-lq",
    };

    return (
        <div className="tw-bg-gray-100 tw-mx-auto tw-max-w-3xl tw-rounded-lg tw-p-8">
            <div className="tw-flex tw-items-center tw-rounded-lg tw-bg-orange-200 tw-p-4 tw-shadow-md">
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
                        <button className="tw-my-2 tw-rounded-full tw-border-none tw-bg-orange-500 tw-px-4 tw-py-2 tw-text-white">
                            Edit Profile
                        </button>
                    </Link>
                </div>
            </div>

            <div className="tw-mt-8 tw-grid tw-grid-cols-1 tw-gap-4 tw-rounded-lg tw-bg-orange-100 tw-p-4 tw-shadow-md md:tw-grid-cols-2">
                <div>
                    <h3 className="tw-mb-4 tw-text-2xl tw-font-medium tw-text-orange-600">
                        Info
                    </h3>

                    <div className="tw-mb-2 tw-flex tw-items-center">
                        <p className="tw-mr-4 tw-font-medium">Gender:</p>
                        <p className="tw-rounded-full tw-bg-orange-500 tw-px-2 tw-py-1 tw-text-white">
                            {user.gender}
                        </p>
                    </div>

                    <div className="tw-mb-2 tw-flex tw-items-center">
                        <p className="tw-mr-4 tw-font-medium">Location:</p>
                        <p className="tw-rounded-full tw-bg-orange-500 tw-px-2 tw-py-1 tw-text-white">
                            {user.address}
                        </p>
                    </div>

                    <div className="tw-flex tw-items-center">
                        <p className="tw-mr-4 tw-font-medium">Education:</p>
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
                        <p className="tw-mr-4 tw-font-medium">Years:</p>
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
                        {user.skills.map((skill) => (
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
    );
}
