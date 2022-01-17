#!/bin/bash

echo ">>>>> Hello :) Starting locally k-events application..."
echo ">>>>> Estimated start time: 4 min"

echo ">>>>> Building API app..."
docker build -t k-events-api:1.0.0 -f Dockerfile_API .

echo ">>>>> Building Front-end app..."
docker build -t k-events-app:1.0.0 -f Dockerfile_APP .

docker compose up