import nodemailer from "nodemailer";

try {
    const transporter = nodemailer.createTransport({
        service: "gmail",
        auth: {
            user: process.env.NODEMAILER_EMAIL,
            pass: process.env.NODEMAILER_EMAIL_PASSWORD,
        }
    });
    console.log(`NODEMAILER HAS BEEN INITIALIZED SUCCESFULLY`);
} catch (error) {
    console.log(`ERROR INITIALISING NODEMAILER ERROR: ${error.message}`);
}
export const sendMail = async (to, subject, html) => {
    try {
        const mailOptions = {
            from: process.env.NODEMAILER_EMAIL,
            to,
            subject,
            html
        };

        await transporter.sendMail(mailOptions);
        console.log("Email sent successfully");
    } catch (error) {
        console.log(error.message);
    }
}

export default sendMail