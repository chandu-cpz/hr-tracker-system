export function JobFilter() {
    return (
        <div>
            <button className="tw-hover:tw-bg-blue-800 tw-focus:tw-ring-4 tw-focus:tw-outline-none tw-focus:tw-ring-blue-300 tw-flex tw-items-center tw-rounded-lg tw-bg-blue-700 tw-px-5 tw-py-2.5 tw-text-center tw-text-white">
                Dropdown
                <svg
                    className="tw-ms-3 tw-h-2.5 tw-w-2.5"
                    fill="none"
                    viewBox="0 0 10 6"
                >
                    <path
                        stroke="currentColor"
                        strokeWidth="2"
                        d="M1 1l4 4 4-4"
                    />
                </svg>
            </button>

            {
                <div className="tw-divide-gray-100 tw-z-10 tw-hidden tw-w-44 tw-divide-y tw-rounded-lg tw-bg-white tw-shadow">
                    <ul
                        className="tw-text-gray-700 tw-py-2 tw-text-sm"
                        aria-labelledby="dropdownHoverButton"
                    >
                        <li>
                            <a
                                href="#"
                                className="tw-hover:tw-bg-gray-100 tw-block tw-px-4 tw-py-2"
                            >
                                Dashboard
                            </a>
                        </li>
                        {/* other links */}
                    </ul>
                </div>
            }
        </div>
    );
}
