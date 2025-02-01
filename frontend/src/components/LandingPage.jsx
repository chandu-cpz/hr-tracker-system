import { Link } from "react-router-dom";

export function LandingPage() {
    return (
        <div className="tw-flex tw-h-screen tw-flex-col tw-items-center tw-justify-center dark:tw-bg-stone-900">
            <div>
                <h1 className="tw-m-12 tw-mt-12 tw-text-center tw-text-6xl tw-font-extrabold tw-text-black dark:tw-text-gray-400 dark:tw-text-opacity-90">
                    Streamline Recruiting Workflows
                </h1>
                <h1 className="tw-m-12 tw-text-center tw-text-6xl tw-font-extrabold tw-text-stone-700 tw-text-opacity-90 dark:tw-text-gray-300">
                    Centralize Application Tracking{" "}
                </h1>
                <h1 className="tw-m-12 tw-text-center tw-text-6xl tw-font-extrabold tw-text-black tw-text-opacity-60  dark:tw-text-gray-200">
                    {" "}
                    Optimize Hiring Outcomes
                </h1>
            </div>
            <div className="tw-mb-40 tw-w-5/6">
                <p className="tw-text-center tw-text-3xl tw-font-medium tw-text-black tw-text-opacity-50 dark:tw-text-white">
                    Your all-in-one talent acquisition solution for a seamless
                    recruiting experience.
                    <br />
                    Our intuitive, integrated and intelligent platform
                    revolutionizes your hiring.
                </p>
            </div>
            <Link to="/register">
                <button className=" tw-rounded-full tw-border-none tw-bg-gradient-to-b tw-from-orange-500 tw-to-orange-600 tw-p-5  tw-text-3xl tw-text-white tw-shadow-2xl tw-transition-all  hover:tw-scale-105 hover:tw-shadow-xl">
                    Sign Up
                </button>
            </Link>
        </div>
    );
}
