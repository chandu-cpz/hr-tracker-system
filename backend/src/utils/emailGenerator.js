export function generateSignUpEmailTemplate(username) {

  return `
      <!DOCTYPE html>
      <html>
        <head>
          <jobTitle>HR Tracker System</jobTitle>
          <style>
            body {
              font-family: Arial, sans-serif;
              font-size: 16px;
              color: #333333;  
            }
              
            h1 {
              background-color: #0072C6;
              color: #ffffff;
              padding: 10px; 
            }
            
            p {
              line-height: 1.5;
              margin-bottom: 20px; 
            }
          </style>
        </head>
  
        <body>
          <h1>Hello!</h1>
              
          <p><b> Hi ${username},</b></p>
              
          <p>Welcome to the HR Tracker System. We're excited to have you on board.</p>
              
          <p>Let us know if you have any questions getting started with the system.</p>
              
          <p>Thanks,</p> 
         <b> <p>The HR Tracker Team</p></b>
        </body>
      </html>
    `;
}

export function generateApplicationEmail(application, username) {

  return `
      <!DOCTYPE html>
      <html>
        <head>
          <jobTitle>Application Update</jobTitle>
          
          <style>
            body {
              font-family: Arial, sans-serif;
              font-size: 16px;
            }
            
            h3 {
              color: #0072c6;  
            }
            
            p {
              line-height: 1.5;
              margin-bottom: 10px;
            }
            
            ul {
              list-style: none;
              padding-left: 0;
            }
            
            li {
              margin-bottom: 5px;
            }
            
            a {
              color: #0072c6; 
              text-decoration: none;
            }
            
            .accepted {
              color: green;
            }
            
            .pending {
              color: orange;
            }
          </style>
          
        </head>
  
        <body>
          <h3>Hi <b>${username}</b>,</h3>
  
          <p>Your application for the position of <b>${application.jobId.jobTitle}</b> has been received by our HR department.</p>
  
          <p>A member of our HR team will review your resume and be in touch if we would like to move forward with next steps. We will notify you if the status of your application changes.</p>
  
          <p>Application Details:</p>
          
          <ul>
            <li>Position Title: ${application.jobId.jobTitle}</li>
            <li><a href="${application.resumeSrc}" download>Download Resume</a></li>
            <li>Application Status: ${application.accepted}</span></li> 
          </ul>
          
          <p>Thanks,</p>
          
          <p><b>The HR Team</b></p>
        </body>
      </html>
    `;
}

export function generateRejectedEmail(application) {

  return `
      <!DOCTYPE html>
      <html>
        <head>
          <jobTitle>Application Update</jobTitle>
          
          <style>
            body {
              font-family: Arial, sans-serif;
              font-size: 16px;  
            }
            
            h3 {
              color: #0072c6;
            }
            
            p {
              line-height: 1.5;
              margin-bottom: 10px;  
            }
            
            a {
              color: #0072c6;
              text-decoration: none; 
            }
          </style>
          
        </head>
  
        <body>  
          <h3>Hi <b>${application.appliedBy.fullName}</b>,</h3>
  
          <p>Thank you for applying for the <b>${application.jobId.jobTitle}</b> position.</p>
          
          <p>Unfortunately our HR team has decided not to move forward with your application at this time.</p>
          
          <p>We appreciate you taking the time to apply and wish you the best in your job search.</p>
  
          <p>Thanks,</p>
  
          <p><b>The HR Team</b></p>
        </body>
      </html>
    `;
}


export function generateAcceptedEmail(application) {

  return `
      <!DOCTYPE html>
      <html>
        <head>
          <jobTitle>Application Accepted</jobTitle>
          
          <style>
           body {
             font-family: Arial, sans-serif;
             font-size: 16px;  
           }
           
           h3 {
             color: #0072c6;
           }
           
           p {
             line-height: 1.5;
             margin-bottom: 10px;
           }
           
           ul {
             list-style: none;
             padding-left: 0;
           }
           
           li {
             margin-bottom: 5px;
           }
          </style>
          
        </head>
  
        <body>
          <h3>Congratulations <b>${application.appliedBy.fullName}</b>!</h3>
  
          <p>We are excited to inform you that your application for the position of <b>${application.jobId.jobTitle}</b> has been accepted by our HR department.</p>
  
          <p>Next steps:</p>
          
          We will be contacting you shortly to arrange next steps. Please let us know if you have any other questions!
  
          <p>Thanks,</p>
  
          <p><b>The HR Team</b></p>
        </body>
      </html>
    `;
}