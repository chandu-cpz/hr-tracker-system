import { Sidebar } from "./Sidebar";
import { BsBriefcase, BsPerson, BsPersonCheck } from "react-icons/bs";
import { Card } from "./Card";

export function Dashboard() {
    const username = "chandu";
    // const data = [
    //     { name: "John Doe", email: "john@email.com", phone: "123-456-7890" },
    //     { name: "Jane Doe", email: "jane@email.com", phone: "987-654-3210" },
    // ];
    return (
        <>
            <div className="tw-flex">
                <Sidebar />

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
                        <Card title="Jobs Open" icon={BsBriefcase} value={12} />

                        <Card title="Applications" icon={BsPerson} value={24} />

                        <Card
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
