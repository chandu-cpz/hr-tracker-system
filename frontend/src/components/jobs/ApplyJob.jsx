import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { Sidebar } from './Sidebar';

export function ApplyJob() {
  const {jobId} =useParams();
  console.log("hello from applyJob"+jobId);
  const [jobDetails, setJobDetails] = useState(null);

  useEffect(() => {
    if (jobId) {
      axios.get(`/api/jobs/${jobId}`)
        .then(response => {
          console.log(response.data);
          setJobDetails(response.data);
        })
        .catch(error => {
          console.error('Error fetching job details:', error);
        });
    }
  }, []);

  return (
    <div className="tw-flex tw-items-center tw-justify-center tw-h-screen">
      <div className="tw-w-full tw-p-8 tw-shadow-lg">
        {jobId ? (
          <>
            {jobDetails ? (
              <>
                <h1 className="tw-text-2x1 tw-font-bold tw-mb-4">{jobDetails.jobTitle}</h1>
                <p className="tw-text-gray-700 tw-text-base">
                  {jobDetails.companyName}. {jobDetails.location}
                </p>
                <p className="tw-text-gray-700 tw-text-lg">{jobDetails.jobDescription}</p>
                <p className="tw-text-gray-700 tw-text-lg">{jobDetails.responsibilities}</p>
                <p className="tw-text-gray-700 tw-text-lg">{jobDetails.qualifications}</p>
                <p className="tw-text-gray-700 tw-text-lg">{jobDetails.skills.join(',')}</p>
                <p className="tw-text-gray-700 tw-text-lg">{jobDetails.salary}</p>
                <p className="tw-text-gray-700 tw-text-lg">{jobDetails.location}</p>
                <p className="tw-text-gray-700 tw-text-lg">{jobDetails.noOfPosts}</p>
                <p className="tw-text-gray-700 tw-text-lg">{jobDetails.isOpen.toString()}</p>
              </>
            ) : (
              <p>Loading job details...</p>
            )}
          </>
        ) : (
          <p>No job data available.</p>
        )}

        <div className="tw-mb-4 tw-flex tw-justify-start tw-items-start">
          <button
            type="submit"
            className="tw-bg-blue-500 tw-hover:bg-blue-700 tw-text-white tw-font-bold tw-py-2 tw-px-4 tw-rounded tw-focus:outline-none tw-focus:shadow-outline"
          >
            Apply
          </button>

          <button
            type="button"
            className="tw-bg-gray-300 tw-hover:bg-gray-400 tw-text-gray-700 tw-font-bold tw-py-2 tw-px-4 tw-rounded tw-focus:outline-none tw-focus:shadow-outline"
          >
            Save
          </button>
        </div>
      </div>
    </div>
  );
}
