import axios from 'axios'
import './App.css'
import './index.css'

function App() {
  axios.get("/api/jobs").
  then((response) => {
    console.log(response.data);
  }).catch((error) => {
    console.log(error.message);
  });

  return (
    <>
        {/* Place holder */}
        <h1 className='tw-text-4xl tw-bg-orange-600 tw-text-white tw-rounded-lg'>HR Recruitment and Applicant Tracking System</h1>
		<h1 className='text-success-emphasis'>HR Recruitment and Applicant Tracking System</h1>
    </>
  )
}

export default App
