@echo off
REM This scripts runs the application in a container.
docker run --name mathlizer-container -p 8080:8080 -d wassimtahraoui88/mathlizer:latest