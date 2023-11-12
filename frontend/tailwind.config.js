/** @type {import('tailwindcss').Config} */
export default {
    prefix: "tw-",
    preflight: false,
    corePlugins: {
        preflight: false,
    },
    content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
    theme: {
        extend: {
            colors: {
                gray: "#E2E2E2",
            },
            fontFamily: {
                sans: ["Poppins", "sans-serif"],
            },
        },
    },
    plugins: [],
};
