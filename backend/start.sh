#!/bin/bash

nohup npm run start > ../../node.log 2>&1 &

cd ../recSys/src/ 
python main.py
