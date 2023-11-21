import nodemailer from "nodemailer";

const transporter = nodemailer.createTransport({
    service: "gmail",
    auth: {
        user: process.env.NODEMAILER_EMAIL,
        pass: process.env.NODEMAILER_EMAIL_PASSWORD,
    },
});
console.log(`NODEMAILER HAS BEEN INITIALIZED SUCCESFULLY`);

const sendMail = async (to, subject, html) => {
    try {
        const mailOptions = {
            from: process.env.NODEMAILER_EMAIL,
            to,
            subject,
            html,
        };

        await transporter.sendMail(mailOptions);
        console.log("Email sent successfully");
    } catch (error) {
        console.log(error.message);
    }
};

export default sendMail;
