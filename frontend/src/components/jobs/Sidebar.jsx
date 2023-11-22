export function Sidebar() {
    return (
        <div className="tw-bg-blue-300 tw-inline-block  tw-w-60 tw-rounded-r-3xl tw-p-2">
            <div className="tw-mb-8 tw-rounded-2xl tw-bg-blue-300 tw-p-4 ">
                <div className="tw-mb-4 tw-rounded-lg tw-bg-white tw-p-4 tw-shadow-lg">
                    <p className="tw-text-gray-700 tw-text-xl tw-font-thin">
                        Use filters for best results
                    </p>
                </div>

                <div>
                    <h3 className="tw-mb-2 tw-text-lg tw-font-bold">
                        Working Schedule
                    </h3>

                    <div className="tw-flex tw-flex-col ">
                        <div className="tw-mb-2">
                            <input
                                type="checkbox"
                                id="fulltime"
                                className="tw-mr-2 tw-h-6 tw-w-6 "
                            />
                            <label htmlFor="fulltime" className="tw-text-xl">
                                Full Time
                            </label>
                        </div>

                        <div className="tw-mb-2">
                            <input
                                type="checkbox"
                                id="parttime"
                                className="tw-mr-2 tw-h-6 tw-w-6"
                            />
                            <label htmlFor="parttime" className="tw-text-xl">
                                Part Time
                            </label>
                        </div>

                        <div>
                            <input
                                type="checkbox"
                                id="internship"
                                className="tw-mr-2 tw-h-6 tw-w-6"
                            />
                            <label htmlFor="internship" className="tw-text-xl">
                                Internship
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
