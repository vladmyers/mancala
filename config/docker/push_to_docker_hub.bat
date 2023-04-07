@echo off
set /p VERSION=<../../VERSION
echo The version is %VERSION%

docker tag mancala-backend:%VERSION% vladcares/mancala-backend:%VERSION%
docker push vladcares/mancala-backend:%VERSION%

docker tag mancala-frontend:%VERSION% vladcares/mancala-frontend:%VERSION%
docker push vladcares/mancala-frontend:%VERSION%
