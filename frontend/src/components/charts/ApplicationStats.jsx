import { BarChart, Bar, XAxis, YAxis } from "recharts";

export function ApplicationStats({ stats }) {
    return (
        <div>
            <h1 className="tw-mb-16 tw-text-green-400">
                Job Tilte vs No of Applications
            </h1>
            <BarChart width={600} height={300} data={stats}>
                <XAxis dataKey="_id" />
                <YAxis />
                <Bar dataKey="count" fill="#8884d8" />
            </BarChart>
        </div>
    );
}
