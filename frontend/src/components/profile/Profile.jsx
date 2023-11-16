import {
    FaUserCircle,
    FaPhoneAlt,
    FaEnvelope,
    FaVenusMars,
} from "react-icons/fa";

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
        <div className="tw-bg-gray-100 tw-rounded-lg tw-p-8">
            {/* Profile header */}
            <div className="tw-flex tw-items-center tw-rounded-lg tw-bg-white tw-p-4 tw-shadow-md">
                <img
                    className="tw-border-primary tw-mr-8 tw-h-32 tw-w-32 tw-rounded-full tw-border-4"
                    src={user.profileImage}
                    alt={user.fullName}
                />

                <div>
                    <h1 className="tw-text-primary tw-text-3xl tw-font-bold">
                        {user.fullName}
                    </h1>

                    <p className="tw-mt-2 tw-flex tw-items-center">
                        <FaPhoneAlt className="tw-text-gray-500 tw-mr-2" />
                        <span>{user.phoneNumber}</span>
                    </p>

                    <p className="tw-flex tw-items-center">
                        <FaEnvelope className="tw-text-gray-500 tw-mr-2" />
                        <span>{user.email}</span>
                    </p>
                </div>
            </div>

            {/* Info cards */}
            <div className="tw-mt-8 tw-grid tw-grid-cols-1 tw-gap-8 lg:tw-grid-cols-2">
                <div className="tw-rounded-lg tw-bg-white tw-p-4 tw-shadow-md">
                    <h3 className="tw-mb-4 tw-text-lg tw-font-medium">Info</h3>

                    <div className="tw-mb-2 tw-flex tw-items-center">
                        <p className="tw-mr-4 tw-font-medium">Gender:</p>
                        <p className="tw-bg-primary tw-rounded-full tw-px-2 tw-py-1 tw-text-white">
                            {user.gender}
                        </p>
                    </div>

                    <div className="tw-mb-2 tw-flex tw-items-center">
                        <p className="tw-mr-4 tw-font-medium">Location:</p>
                        <p className="tw-bg-primary tw-rounded-full tw-px-2 tw-py-1 tw-text-white">
                            {user.address}
                        </p>
                    </div>

                    <div className="tw-flex tw-items-center">
                        <p className="tw-mr-4 tw-font-medium">Education:</p>
                        <p className="tw-bg-primary tw-rounded-full tw-px-2 tw-py-1 tw-text-white">
                            {user.education}
                        </p>
                    </div>
                </div>

                <div className="tw-rounded-lg tw-bg-white tw-p-4 tw-shadow-md">
                    <h3 className="tw-mb-4 tw-text-lg tw-font-medium">
                        Experience
                    </h3>

                    <div className="tw-mb-2 tw-flex tw-items-center">
                        <p className="tw-mr-4 tw-font-medium">Years:</p>
                        <p className="tw-bg-primary tw-rounded-full tw-px-2 tw-py-1 tw-text-white">
                            {user.experience}
                        </p>
                    </div>
                </div>

                <div className="tw-col-span-2 tw-rounded-lg tw-bg-white tw-p-4 tw-shadow-md">
                    <h3 className="tw-mb-4 tw-text-lg tw-font-medium">
                        Skills
                    </h3>

                    <div className="tw-flex tw-flex-wrap tw-gap-2">
                        {user.skills.map((skill) => (
                            <p
                                key={skill}
                                className="tw-bg-primary tw-rounded-full tw-px-3 tw-py-1 tw-text-white"
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
