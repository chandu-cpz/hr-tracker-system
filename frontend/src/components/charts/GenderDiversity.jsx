import { PieChart, Pie, Legend, Cell, ResponsiveContainer } from "recharts";
import PropTypes from "prop-types";

export function GenderDiversity({ male, female, others }) {
    const data = [
        { name: "Male", value: male },
        { name: "Female", value: female },
        { name: "Others", value: others },
    ];

    const COLORS = ["#0088FE", "#00C49F", "#FF8042"];

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
                width={400}
                height={400}
                className="text-center"
            >
                <PieChart width={400} height={400}>
                    <Legend layout="vertical" verticalAlign="top" align="top" />
                    <Pie
                        data={data}
                        cx="50%"
                        cy="50%"
                        labelLine={false}
                        label={renderCustomizedLabel}
                        outerRadius={150}
                        innerRadius={70}
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

GenderDiversity.propTypes = {
    male: PropTypes.number.isRequired,
    female: PropTypes.number.isRequired,
    others: PropTypes.number.isRequired,
};
