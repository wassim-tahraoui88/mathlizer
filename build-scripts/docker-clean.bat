@echo off
REM This script cleans up the Docker environment by stopping and removing the container and image.
docker stop mathlizer-container 2>nul || echo Container not found, skipping stop.
docker rm mathlizer-container 2>nul || echo Container not found, skipping remove.
docker rmi wassimtahraoui88/mathlizer:latest 2>nul || echo Image not found, skipping remove.