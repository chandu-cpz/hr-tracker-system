/** @type {import('tailwindcss').Config} */
export default {
    darkMode: 'class',
    prefix: "tw-",
    preflight: false,
    corePlugins: {
        preflight: false,
    },
    content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
    theme: {
        extend: {

            fontFamily: {
                sans: ["Poppins", "sans-serif"],
            },
            animation: {
                'slide-in': 'slide-in 0.5s ease-in-out',
            }
        },
    },
    plugins: [],

};
