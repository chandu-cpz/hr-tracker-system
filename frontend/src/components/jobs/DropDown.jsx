import PropTypes from "prop-types";

export function Dropdown({ name, options, icon, onSelect }) {
    const handleSelection = (option) => {
        console.log(`The option is ${option} and name is ${name}`);
        onSelect(name, option);
    };

    return (
        <div className="dropdown">
            <a
                className="dropdown-toggle text-decoration-none tw-text-xl tw-text-white"
                data-bs-toggle="dropdown"
            >
                <span className="tw-pt-0.25 tw-mr-2 tw-h-11 tw-w-11 tw-shrink-0 tw-grow-0 tw-rounded-full tw-border tw-border-solid  tw-px-1 tw-pb-1">
                    {icon}
                </span>
                {name}
            </a>

            <ul className="dropdown-menu !tw-bg-blue-200">
                {options &&
                    options.map((option) => (
                        <li key={option}>
                            <a
                                className="dropdown-item tw-text-lg"
                                onClick={() => handleSelection(option)}
                                href="#"
                                value={option}
                            >
                                {option}
                            </a>
                        </li>
                    ))}
            </ul>
        </div>
    );
}

Dropdown.propTypes = {
    name: PropTypes.string.isRequired,
    options: PropTypes.arrayOf(PropTypes.string).isRequired,
    onSelect: PropTypes.func.isRequired,
    icon: PropTypes.element.isRequired,
};
