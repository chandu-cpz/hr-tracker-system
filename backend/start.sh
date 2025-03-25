#!/bin/bash
# Change to the recSys directory and run the Python service in the background,
# redirecting its output to a log file.
(cd ../recSys/src && nohup python3 main.py > recSys.log 2>&1 &)

# Start the Node.js backend service
npm run start
