import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";
const initialState = {
    isLoggedIn: false,
    user: {},
};
// yet to configure waiting for frontend to be available
export const userSlice = createSlice({
    name: "user",
    initialState,
    reducers: {
        setUser: (state, action) => {
            console.log("RTK: Setting user to", action.payload);
            return {
                ...state,
                user: action.payload,
            }
        },
        setIsLoggedIn: (state, action) => {
            console.log("RTK: Setting isLoggedIn to", action.payload);
            return {
                ...state,
                isLoggedIn: action.payload
            }
        },
        addSavedJob(state, action) {
            console.log(`RTK: Adding a saved Job ${action.payload}`);
            return {
                ...state,
                user: {
                    ...state.user,
                    savedJobs: [...state.user.savedJobs, action.payload]
                }
            }
        },
        removeSavedJob(state, action) {
            console.log(`RTK: Removing a saved Job ${action.payload}`);
            return {
                ...state,
                user: {
                    ...state.user,
                    savedJobs: state.user.savedJobs.filter(id => id !== action.payload)
                }
            }
        },
        addAppliedJob(state, action) {
            console.log(`RTK: Adding a saved Job ${action.payload}`);
            return {
                ...state,
                user: {
                    ...state.user,
                    jobsApplied: [...state.user.jobsApplied, action.payload]
                }
            }
        },
        removeAppliedJob(state, action) {
            console.log(`RTK: Removing a saved Job ${action.payload}`);
            return {
                ...state,
                user: {
                    ...state.user,
                    jobsApplied: state.user.jobsApplied.filter(id => id !== action.payload)
                }
            }
        }
    },
});

export const { setUser, setIsLoggedIn, addSavedJob, removeSavedJob, addAppliedJob, removeAppliedJob } = userSlice.actions;

export const signUpUser = (userData) => {
    console.log("RTK: We started sign up ")
    return async (dispatch) => {
        try {
            console.log("RTK: Making sign up request to server")
            const response = await axios.post("/api/signup", userData);
            console.log(response);
            dispatch(setIsLoggedIn(false));
            return response;
        } catch (error) {
            console.error(error);
        }
        console.log("RTK: We finished signup");
    };
};

export const loginUser = (userData) => {
    console.log("RTK: We started log in ")
    return async (dispatch) => {
        try {
            console.log("RTK: Making log in request to server")
            const response = await axios.post("/api/login", userData);
            dispatch(setIsLoggedIn(false));
            return response
        } catch (error) {
            console.error(error);
        }
        console.log("RTK: We finished log in");
    };
}

export default userSlice.reducer;
