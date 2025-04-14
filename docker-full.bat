@echo off
REM This script builds the Docker image for the Mathlizer application and runs it in a container.
call build-scripts/docker-clean.bat || echo Clean up failed, continuing with build.
call build-scripts/docker-build.bat
call build-scripts/docker-run.bat