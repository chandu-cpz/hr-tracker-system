export function Login() {
    return (
        <div className="tw-flex tw-h-screen tw-bg-gray">
        <div className="tw-flex tw-items-center tw-mt-16">
          <div className="tw-w-1/6 tw-ml-4  tw-text-right">
           <div className="tw-flex tw-flex-col">
             <h1 className="tw-text-4xl tw-font-extrabold tw-text-black tw-mb-2 tw-whitespace-nowrap ">
                 Streamline Recruiting Workflows
                </h1>
              <h1 className="tw-text-4xl tw-font-extrabold tw-text-stone-700 tw-text-opacity-90 tw-mb-2 tw-whitespace-nowrap">
                  Centralize Application Tracking
               </h1>
              <h1 className="tw-text-3xl tw-font-extrabold tw-text-black tw-text-opacity-60 tw-mb-2 tw-whitespace-nowrap">
                  Optimize Hiring Outcomes
               </h1>
              </div>
              </div>
           </div>
        <div className="tw-flex tw-flex-1 tw-items-center tw-justify-center ">
          <div className="tw-bg-white md:tw-max-w-md tw-w-full  tw-mx-auto tw-p-12  tw-rounded-xl tw-shadow-lg tw-transform tw-translate-x-full tw-transition-transform tw-ease-in-out tw-duration-300 tw-ml-0">
            <h2 className="tw-text-2xl tw-font-bold tw-mb-4 tw-mt-4">Login</h2>
            <form className="tw-text-left">
            <div className="tw-flex tw-flex-col md:tw-flex-col tw-items-start">
              <div className="tw-mb-4md:tw-mb-0 md:tw-mr-4 tw-flex tw-flex-col tw-w-full">
                <label className="tw-block text-gray-700 tw-text-sm tw-font-bold tw-mb-2" htmlFor="email">
                  Email:
                </label>
                <input
                  type="email"
                  id="email"
                  name="email"
                  className="tw-shadow tw-appearance-none tw-border tw-rounded-full tw-w-full  tw-py-2 tw-px-3 tw-text-gray-700 tw-leading-tight tw-focus:outline-none tw-focus:shadow-outline  tw-placeholder-gray-500 tw-focus:border-blue-500 tw-focus:ring tw-bg-white"
                  placeholder=""
                />
              </div>
              <div className="tw-mb-4 md:tw-mb-0 md:tw-mr-4 tw-flex tw-flex-col tw-w-full">
                <label className="tw-block text-gray-700 tw-text-sm tw-font-bold tw-mb-2" htmlFor="password">
                  Password:
                </label>
                <input
                  type="password"
                  id="password"
                  name="password"
                  className="tw-shadow tw-appearance-none tw-border tw-rounded-full tw-w-full ma:tw-w-3/4 tw-py-2 tw-px-3 tw-text-gray-700 tw-leading-tight tw-focus:outline-none tw-focus:shadow-outline tw-placeholder-gray-500 tw-focus:border-blue-500 tw-focus:ring tw-bg-white"
                  placeholder=""
                />
              </div>
              <button
                type="submit"
                className="tw-bg-orange-500 tw-hover:bg-orange-700 tw-text-white tw-font-bold tw-py-3 tw-px-6 tw-rounded-full tw-focus:outline-none tw-focus:shadow-outline tw-mt-2"
              >
                Login
              </button>
              </div>
            </form>
          </div>
          </div>
          </div>
      );
    };