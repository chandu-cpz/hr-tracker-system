import React, { useState, useEffect } from 'react';
import axios from 'axios';

function JobList({ jobs }) {
  return (
    <ul>
      {jobs.map(job => (
        <li key={job.id}>
          <h2>{job.title}</h2>
          <p>{job.description}</p>
        </li>
      ))}
    </ul>
  );
}

export function SavedJobs({ savedJobs }) {
  const [savedJobDetails, setSavedJobDetails] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (savedJobs && savedJobs.length > 0) {
          const results = await Promise.all(savedJobs.map(jobId =>
            axios.get(`/api/jobs/savejob/${jobId}`).then(response => response.data)
          ));
          setSavedJobDetails(results);
        }
      } catch (error) {
        console.error('Error fetching saved job details:', error);
      }
    };

    fetchData();
  }, [savedJobs]);

  return (
    <div>
      <h2>Saved Job Details</h2>
      <JobList jobs={savedJobDetails} />
    </div>
  );
}