import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "./index.css";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap/dist/js/bootstrap.bundle.js";
import { store } from "./redux/store.js";
import { Provider } from "react-redux";
import {
    RouterProvider,
    createRoutesFromElements,
    createBrowserRouter,
    Route,
} from "react-router-dom";
import {
    LandingPage,
    Signup,
    Login,
    JobFilter,
    Dashboard,
    AddJob,
} from "./components/";

const router = createBrowserRouter(
    createRoutesFromElements(
        <Route path="/" element={<App />}>
            <Route index element={<LandingPage />} />
            <Route path="register" element={<Signup />} />
            <Route path="login" element={<Login />} />
            <Route path="jobs" element={<JobFilter />} />
            <Route path="dashboard" element={<Dashboard />} />
            <Route path="addjob" element={<AddJob />} />
        </Route>
    )
);

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <Provider store={store}>
            <RouterProvider router={router} />
        </Provider>
    </React.StrictMode>
);
