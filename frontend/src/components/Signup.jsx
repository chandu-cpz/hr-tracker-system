export function Signup() {
    return (
        <div className="tw-flex tw-justify-center tw-items-center tw-h-screen tw-bg-gray">
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
        <div className="tw-max-w-md  tw-w-full tw-mx-auto tw-p-6 tw-bg-white tw-rounded-xl tw-shadow-lg tw-transform tw-translate-x-full tw-transition-transform tw-ease-in-out tw-duration-300 tw-ml-4">
          <h2 className="tw-text-2xl tw-font-bold tw-mb-4">Sign Up</h2>
          <form>
            <div className="tw-mb-4 md:tw-mb-0 md:tw-mr-4">
              <label className="tw-block tw-text-gray-700 tw-text-sm tw-font-bold tw-mb-2" htmlFor="name">
                Name:
              </label>
              <input
                type="text"
                id="name"
                name="name"
                className="tw-shadow tw-appearance-none tw-border tw-rounded-full tw-w-full tw-py-2 tw-px-3 tw-text-gray-700 tw-leading-tight tw-focus:outline-none tw-focus:shadow-outline"
                placeholder=""
              />
            </div>
            <div className="tw-mb-4">
              <label className="tw-block tw-text-gray-700 tw-text-sm tw-font-bold tw-mb-2" htmlFor="email">
                Email:
              </label>
              <input
                type="email"
                id="email"
                name="email"
                className="tw-shadow tw-appearance-none tw-border tw-rounded-full tw-w-full tw-py-2 tw-px-3 tw-text-gray-700 tw-leading-tight tw-focus:outline-none tw-focus:shadow-outline"
                placeholder=""
              />
            </div>
            <div className="tw-mb-4">
              <label className="tw-block text-gray-700 tw-text-sm tw-font-bold tw-mb-2" htmlFor="password">
                Password:
              </label>
              <input
                type="password"
                id="password"
                name="password"
                className="tw-shadow tw-appearance-none tw-border tw-rounded-full tw-w-full tw-py-2 t5w-px-3 tw-text-gray-700 tw-leading-tight tw-focus:outline-none tw-focus:shadow-outline"
                placeholder=""
              />
            </div>
            <div className="tw-mb-4">
              <span className="tw-block text-gray-700 tw-text-sm tw-font-bold tw-mb-2">Gender</span>
              <label className="tw-inline-flex tw-items-center tw-mr-6">
                <input type="radio" className="form-radio" name="gender" value="male" />
                <span className="ml-2">Male</span>
              </label>
              <label className="tw-inline-flex tw-items-center tw-mr-6">
                <input type="radio" className="form-radio" name="gender" value="female" />
                <span className="ml-2">Female</span>
              </label>
              <label className="tw-inline-flex tw-items-center">
                <input type="radio" className="form-radio" name="gender" value="other" />
                <span className="ml-2">Other</span>
              </label>
            </div>
            <div className="tw-mb-4">
              <span className="tw-block text-gray-700 tw-text-sm tw-font-bold tw-mb-2">HR</span>
              <label className="tw-inline-flex tw-items-center tw-mr-6">
                <input type="radio" className="form-radio" name="gender" value="HR" />
                <span className="ml-2">HR</span>
              </label>
              </div>
            <button
              type="submit"
              className="tw-bg-orange-500 tw-hover:bg-orange-700 tw-text-white tw-font-bold tw-py-2 tw-px-4 tw-rounded-full tw-focus:outline-none tw-focus:shadow-outline"
            >
              Sign Up
            </button>
          </form>
        </div>
        </div>
      );
    };