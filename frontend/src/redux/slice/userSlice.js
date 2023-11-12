import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";
const initialState = {
    isLoggedIn: false,
    user: null,
};
// yet to configure waiting for frontend to be available
export const userSlice = createSlice({
    name: "user",
    initialState,
    reducers: {
        setLoggedIn: (state) => (state.isLoggedIn = !state.isLoggedIn),
        loginIn: (state, action) => {
            //make post request to login and get user details
            axios
                .post("/api/signup", {
                    email: action.payload.email,
                    password: action.payload.password,
                })
                .then((respone) => {
                    console.log(respone.data);
                    state.user = respone.data;
                    state.isLoggedIn = true;
                });
        },
        // logout: (state, action) => {
        //     // Implement the logout method

        //     // remove token from cookies
        // }
    },
});
