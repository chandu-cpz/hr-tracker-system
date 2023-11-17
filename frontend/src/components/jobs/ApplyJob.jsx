import PropTypes from "prop-types";

export function ApplyJob({ job }) {
    return (
        <>
            <h1>Apply Job</h1>
        </>
    );
}

ApplyJob.propTypes = {
    job: PropTypes.shape({
        createdAt: PropTypes.string.isRequired,
        companyName: PropTypes.string.isRequired,
        jobTitle: PropTypes.string.isRequired,
        skills: PropTypes.arrayOf(PropTypes.string).isRequired,
        salary: PropTypes.string.isRequired,
    }).isRequired,
};
