import { MdDashboard } from "react-icons/md";
import { MdGroupAdd } from "react-icons/md";
import { FaRegCalendarAlt } from "react-icons/fa";
import { MdOutlinePersonPin } from "react-icons/md";
import { FaUsersGear } from "react-icons/fa6";
import { FaHeadset } from "react-icons/fa6";
import { FaGear } from "react-icons/fa6";

export function Dashboard() {
    return (
        <>
            <div className="tw-flex">
                <div className="tw-bg-gray-100  tw-inline-block tw-flex tw-h-screen tw-rounded-r-3xl tw-p-4">
                    <div className="tw-mb-10 tw-rounded-3xl tw-bg-gray tw-p-9 tw-shadow-lg ">
                        <div>
                            <h3 className="tw-mb-4 tw-pb-3 tw-text-2xl tw-font-bold">
                                MAIN MENU
                            </h3>

                            <div className="tw-flex tw-flex-col tw-pb-10">
                                <div className="tw-flex  tw-gap-4 ">
                                    <MdDashboard size={35} />
                                    <p className="tw-text-3xl">Dashboard</p>
                                </div>
                            </div>

                            <div className="tw-flex tw-flex-col tw-pb-10">
                                <div className="tw-flex  tw-gap-4 ">
                                    <MdGroupAdd size={35} />
                                    <p className="tw-text-3xl">Recruitment</p>
                                </div>
                            </div>

                            <div className="tw-flex tw-flex-col tw-pb-10">
                                <div className="tw-flex  tw-gap-4 ">
                                    <FaRegCalendarAlt size={35} />
                                    <p className="tw-text-3xl">Schedule</p>
                                </div>
                            </div>

                            <div className="tw-flex tw-flex-col tw-pb-10">
                                <div className="tw-flex  tw-gap-4 ">
                                    <MdOutlinePersonPin size={35} />
                                    <p className="tw-text-3xl">Employee</p>
                                </div>
                            </div>

                            <div className="tw-flex tw-flex-col tw-pb-10">
                                <div className="tw-flex  tw-gap-4 ">
                                    <FaUsersGear size={35} />
                                    <p className="tw-text-3xl">Department</p>
                                </div>
                            </div>
                            <div>
                                <h3 className="tw-mb-4 tw-pb-3 tw-text-2xl tw-font-bold">
                                    OTHERS
                                </h3>
                            </div>

                            <div className="tw-flex tw-flex-col tw-pb-10">
                                <div className="tw-flex  tw-gap-4 ">
                                    <FaHeadset size={35} />
                                    <p className="tw-text-3xl">Support</p>
                                </div>
                            </div>

                            <div className="tw-flex tw-flex-col tw-pb-10">
                                <div className="tw-flex  tw-gap-4 ">
                                    <FaGear size={35} />
                                    <p className="tw-text-3xl">Settings</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="tw-bg-slate-950  tw-rounded-r-3xl">
                    <div>
                        <h1>DashBoard</h1>
                        <div className="tw-flex tw-flex-row  tw-gap-10 tw-text-white ">
                            <div className="tw-basis-1/2 tw-text-3xl tw-grow">
                                <h1>Hello,Shindha</h1>
                            </div>

                            <div className="tw-basis-1/4 tw-bg-slate-900 tw-h-max tw-p-4 tw-grow">
                                <div className="tw-flex-col">
                                <span><MdGroupAdd size={35} /></span> 
                                <h2>TotalJobs</h2>
                                </div> 
                            </div>

                            <div className="tw-basis-1/4 tw-bg-slate-900 tw-h-max tw-p-4 tw-grow">
                            <div className="tw-flex-col tw-text-white">
                                <span><MdGroupAdd size={35}/></span> 
                                <h2 clas>TotalJobs</h2>
                                </div> 
                            </div>

                            <div className="tw-basis-1/4 tw-bg-slate-900  tw-h-max tw-p-4 tw-grow">
                            <div className="tw-flex-col">
                                <span><MdGroupAdd size={35} /></span> 
                                <h2>TotalJobs</h2>
                                </div> 
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}
