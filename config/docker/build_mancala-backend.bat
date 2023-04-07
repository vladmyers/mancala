@echo off
set /p VERSION=<../../VERSION
echo The version is %VERSION%

cd ../../mancala-backend
docker build -t mancala-backend:%VERSION% .
