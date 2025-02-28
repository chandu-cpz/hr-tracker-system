import PropTypes from "prop-types";

export function UserCard({ title, icon, value }) {
    return (
        <div className=" tw-rounded-4xl tw-rounded-3xl  tw-bg-gradient-to-b tw-from-orange-400 tw-to-orange-500 tw-p-16 tw-shadow-2xl tw-transition hover:tw-translate-y-2 dark:tw-bg-stone-700">
            {icon}

            <h3 className="tw-mb-4 tw-mt-8 tw-text-2xl tw-font-bold tw-text-gray-900">
                {title}
            </h3>

            <p className="tw-text-6xl tw-font-black tw-text-gray-900">
                {value}
            </p>
        </div>
    );
}

UserCard.propTypes = {
    title: PropTypes.string.isRequired,
    icon: PropTypes.element.isRequired,
    value: PropTypes.number.isRequired,
};
