import { PieChart, Pie, Legend, Cell, ResponsiveContainer } from "recharts";
import PropTypes from "prop-types";

export function JobStats({ accepted, pending, rejected }) {
    const data = [
        { name: "Accepted", value: accepted },
        { name: "Pending", value: pending },
        { name: "Rejected", value: rejected },
    ];

    const COLORS = ["#4470e1", "#1e3a8a", "#867ca3"];

    const RADIAN = Math.PI / 180;
    const renderCustomizedLabel = ({
        cx,
        cy,
        midAngle,
        innerRadius,
        outerRadius,
        percent,
    }) => {
        const radius = innerRadius + (outerRadius - innerRadius) * 0.5;
        const x = cx + radius * Math.cos(-midAngle * RADIAN);
        const y = cy + radius * Math.sin(-midAngle * RADIAN);

        return (
            <text
                x={x}
                y={y}
                fill="white"
                textAnchor={x > cx ? "start" : "end"}
                dominantBaseline="central"
            >
                {`${(percent * 100).toFixed(0)}%`}
            </text>
        );
    };

    return (
        <>
            <ResponsiveContainer
                width={320}
                height={320}
                className="text-center"
            >
                <PieChart width={320} height={320}>
                    <Legend layout="vertical" verticalAlign="top" align="top" />
                    <Pie
                        data={data}
                        cx="50%"
                        cy="50%"
                        labelLine={false}
                        label={renderCustomizedLabel}
                        outerRadius={110}
                        fill="#8884d8"
                        dataKey="value"
                    >
                        {data.map((entry, index) => (
                            <Cell
                                key={`cell-${index}`}
                                fill={COLORS[index % COLORS.length]}
                            />
                        ))}
                    </Pie>
                </PieChart>
            </ResponsiveContainer>
        </>
    );
}

JobStats.propTypes = {
    accepted: PropTypes.number.isRequired,
    pending: PropTypes.number.isRequired,
    rejected: PropTypes.number.isRequired,
};
