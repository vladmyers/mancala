@echo off
set /p VERSION=<../../VERSION
echo The version is %VERSION%

cd ../../mancala-frontend
docker build -t mancala-frontend:%VERSION% .
