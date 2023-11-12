import { Link } from "react-router-dom";

export function Navbar() {
    return (
        <div className="tw-fixed tw-left-0 tw-right-0 tw-top-0 tw-z-10">
            <div className="tw-relative tw-flex tw-justify-between tw-bg-gray tw-p-2 tw-drop-shadow-2xl">
                <Link to="/">
                    <h1 className="tw-ms-4">HRJ</h1>
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
        </div>
    );
}
