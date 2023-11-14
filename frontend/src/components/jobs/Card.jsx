import { BsBookmark } from "react-icons/bs";

export function Card() {
    return (
        <div className="tw-h-fit tw-w-2/12 tw-rounded-2xl tw-border tw-border-solid tw-p-4 tw-shadow-md">
            <div className="tw-rounded-2xl tw-bg-red-300 tw-p-4">
                <div className="tw-mb-4 tw-flex tw-items-center tw-justify-between">
                    <div className="tw-bg-gray-200 tw-inline-flex tw-items-center tw-rounded-full tw-bg-white tw-px-2 tw-py-1 tw-font-medium">
                        Nov 14, 2023
                    </div>

                    <div className="tw-bg-gray-200  tw-rounded-full tw-bg-white  tw-px-2 tw-py-1">
                        <a href="#" className="tw-text-current tw-no-underline">
                            <BsBookmark />
                        </a>
                    </div>
                </div>

                <div className="tw-text-gray-600 tw-mb-2 tw-text-sm">
                    SmartDesign Inc.
                </div>

                <div className="tw-mb-2 tw-flex tw-justify-between">
                    <h3 className="tw-mb-2 tw-text-2xl tw-font-medium">
                        UI/UX Designer
                    </h3>
                    <img
                        src="logo.png"
                        className="tw-mr-2 tw-h-6 tw-w-6 tw-rounded-full"
                    />
                </div>

                <div className="tw-flex tw-flex-wrap">
                    <span className="tw-mb-2 tw-mr-2 tw-rounded-full tw-bg-white tw-px-3 tw-py-1 tw-font-medium ">
                        HTML
                    </span>
                    <span className="tw-mb-2 tw-mr-2 tw-rounded-full tw-bg-white tw-px-3 tw-py-1 tw-font-medium ">
                        CSS
                    </span>
                    <span className="tw-mb-2 tw-mr-2 tw-rounded-full tw-bg-white tw-px-3 tw-py-1 tw-font-medium ">
                        JavaScript
                    </span>
                    <span className="tw-mb-2 tw-mr-2 tw-rounded-full tw-bg-white tw-px-3 tw-py-1 tw-font-medium ">
                        JavaScript
                    </span>
                </div>
            </div>

            <div className="tw-mt-4 tw-flex tw-items-center tw-justify-between">
                <div className="tw-text-lg tw-font-medium">$75,000/yr</div>

                <button className="tw-rounded-full tw-border-none tw-bg-black tw-px-4 tw-py-2 tw-text-lg tw-font-medium tw-text-white">
                    Details
                </button>
            </div>
        </div>
    );
}
