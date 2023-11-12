import mongoose from "mongoose";
import { DB_NAME } from "../constants.js";

const connectDB = async function () {
    try {
        console.log(`${process.env.DB_URI}/${DB_NAME}`);
        const connectionInstance = await mongoose.connect(
            `${process.env.DB_URI}/${DB_NAME}`
        );
        console.log(
            `MONGODB CONNECTION SUCCESSFUL DB HOST: ${connectionInstance.connection.host}`
        );
    } catch (err) {
        console.log("MONGODB CONNECTION ERROR", err);
        process.exit(1);
    }
};

export default connectDB;
