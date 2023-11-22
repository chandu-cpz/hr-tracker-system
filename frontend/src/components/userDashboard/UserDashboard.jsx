import { UserSidebar } from "./UserSidebar";
import { BsBriefcase, BsPerson, BsPersonCheck } from "react-icons/bs";
import { UserCard } from "./UserCard";

export function UserDashboard() {
    const username = "chaitu";
    return (
        <>
            <div className="tw-flex">
                <UserSidebar />

                <div className="tw-w-full tw-p-4">
                    <div>
                        <h1>DashBoard</h1>
                    </div>
                    <div className="tw-my-20 tw-flex tw-flex-wrap tw-items-center tw-justify-between tw-gap-5 tw-rounded-lg tw-bg-blue-300 tw-p-8">
                        <div className="tw-self-start">
                            <h1 className="tw-text-gray-800 tw-mb-5 tw-text-4xl">
                                Welcome back,
                            </h1>
                            <h2 className="tw-text-gray-600 tw-text-5xl tw-font-extrabold">
                                {username.toUpperCase()}
                            </h2>
                        </div>
                        <UserCard
                            title="Jobs Open"
                            icon={
                                <BsBriefcase
                                    size={52}
                                    className="tw-text-blue-600"
                                />
                            }
                            value={12}
                        />

                        <UserCard
                            title="Applications"
                            icon={
                                <BsPerson
                                    size={52}
                                    className="tw-text-blue-600"
                                />
                            }
                            value={24}
                        />

                        <UserCard
                            title="Feedbacks"
                            icon={
                                <BsPersonCheck
                                    size={52}
                                    className="tw-text-blue-600"
                                />
                            }
                            value={5}
                        />
                    </div>
                </div>
            </div>
        </>
    );
}
