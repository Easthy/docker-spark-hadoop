#!/bin/bash

cd master && docker build -t spark-master .
docker run --name spark-master --net=spark-net -p 8080:8080 -p 7077:7077 -p 6066:6066 spark-master

sleep 20

cd ../worker && docker build -t spark-worker-1 .
docker run --name spark-worker-1 --net=spark-net -p 8081:8081 spark-worker-1
