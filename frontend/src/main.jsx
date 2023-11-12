import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "./index.css";
import "bootstrap/dist/css/bootstrap.css";
import { store } from "./redux/store.js";
import { Provider } from "react-redux";
import {
    RouterProvider,
    createRoutesFromElements,
    createBrowserRouter,
    Route,
} from "react-router-dom";
import { LandingPage, Signup, Login } from "./components/";

const router = createBrowserRouter(
    createRoutesFromElements(
        <Route path="/" element={<App />}>
            <Route path="" element={<LandingPage />} />
            <Route path="register" element={<Signup />} />
            <Route path="login" element={<Login />} />
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
