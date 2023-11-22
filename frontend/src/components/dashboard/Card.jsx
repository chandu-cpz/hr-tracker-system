import PropTypes from "prop-types";

export function Card({ title, icon: Icon, value }) {
    return (
        <div className="tw-rounded-4xl  tw-rounded-3xl tw-bg-gradient-to-b tw-from-blue-900 tw-to-blue-400 tw-p-16 tw-shadow-2xl tw-transition hover:tw-translate-y-5">
            <Icon size={52} className="tw-text-blue-300" />

            <h3 className="tw-text-white-900 tw-mb-4 tw-mt-8 tw-text-2xl tw-font-bold">
                {title}
            </h3>

            <p className="tw-text-white-900 tw-text-6xl tw-font-white">
                {value}
            </p>
        </div>
    );
}

Card.propTypes = {
    title: PropTypes.string.isRequired,
    icon: PropTypes.element.isRequired,
    value: PropTypes.number.isRequired,
};