import { BarChart, Bar, XAxis, YAxis } from "recharts";

export function ApplicationStats({ stats }) {
    return (
        <div>
            <h1 className="tw-mb-16 tw-text-blue-900">
                Job Tilte vs No of Applications
            </h1>
            <BarChart width={400} height={300} data={stats}>
                <XAxis dataKey="_id" />
                <YAxis />
                <Bar dataKey="count" fill="#4470e1" />
            </BarChart>
        </div>
    );
}
