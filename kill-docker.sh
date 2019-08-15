#!/bin/bash

docker kill $(docker ps -q)
docker rm $(docker ps -a)
docker rmi $(docker images -f "dangling=true" -q)
docker rm spark-master
docker rm spark-worker-1
docker rm zeppelin