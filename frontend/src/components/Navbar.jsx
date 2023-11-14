import { Link } from "react-router-dom";

export function Navbar() {
    return (
        <div className=" tw-h-15 tw-flex tw-justify-between tw-bg-gray tw-p-2 tw-shadow-lg">
            <Link to="/">
                <h1 className="tw-ms-4">HRJ</h1>
            </Link>

            <Link to="/jobs" className="tw-text-current tw-no-underline">
                <p className="tw-mt-2 tw-text-3xl tw-text-orange-500">
                    Find jobs
                </p>
            </Link>
            <div>
                <Link to="/login">
                    <button className="tw-mx-1 tw-rounded-full tw-border-none tw-bg-orange-500 tw-px-5 tw-text-2xl lg:tw-mr-5 lg:tw-p-3 lg:tw-px-5">
                        Login
                    </button>
                </Link>

                <Link to="/register">
                    <button className="tw-rounded-full tw-border-none tw-bg-orange-500 tw-px-5 tw-text-2xl lg:tw-mr-5 lg:tw-p-3 lg:tw-px-5">
                        Sign Up
                    </button>
                </Link>
            </div>
        </div>
    );
}
