export function LandingPage() {
    return (
        <div className="tw-flex tw-h-screen tw-flex-col tw-items-center tw-justify-center tw-bg-gray">
            <div>
                <h1 className="tw-mt-12 tw-text-center tw-text-6xl tw-font-extrabold tw-text-black">
                    Streamline Recruiting Workflows
                </h1>
                <br />
                <br />
                <h1 className="tw-text-center tw-text-6xl tw-font-extrabold tw-text-stone-700 tw-text-opacity-90">
                    Centralize Application Tracking{" "}
                </h1>
                <br />
                <br />
                <h1 className="tw-text-center tw-text-6xl tw-font-extrabold tw-text-black tw-text-opacity-60">
                    {" "}
                    Optimize Hiring Outcomes
                </h1>
                <br />
                <br />
            </div>
            <div className="tw-w-5/6">
                <p className="tw-text-center tw-text-3xl tw-font-medium tw-text-black tw-text-opacity-50">
                    Your all-in-one talent acquisition solution for a seamless
                    recruiting experience.
                    <br />
                    Our intuitive, integrated and intelligent platform
                    revolutionizes your hiring.
                </p>
                <br />
                <br />
                <br />
                <br />
            </div>
            <button
                className=" lg: tw-scale-125 tw-transform tw-rounded-full tw-border-none  tw-bg-orange-500
tw-py-5 tw-text-3xl tw-drop-shadow-2xl lg:tw-mr-5 lg:tw-px-10"
            >
                Sign Up
            </button>
        </div>
    );
}
