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
                    <div className="tw-mb-8 tw-flex tw-flex-wrap tw-items-center tw-justify-between tw-gap-5">
                        <div>
                            <h1 className="tw-text-gray-800 tw-mb-5 tw-text-4xl">
                                Welcome back,
                            </h1>
                            <h2 className="tw-text-gray-600 tw-text-5xl tw-font-extrabold">
                                {username.toUpperCase()}
                            </h2>
                        </div>
                        <UserCard
                            title="Jobs Open"
                            icon={BsBriefcase}
                            value={12}
                        />

                        <UserCard
                            title="Applications"
                            icon={BsPerson}
                            value={24}
                        />

                        <UserCard
                            title="Employees"
                            icon={BsPersonCheck}
                            value={5}
                        />
                    </div>
                </div>
            </div>
        </>
    );
}
