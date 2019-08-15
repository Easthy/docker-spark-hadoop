# Apache Spark 2.4.3 with hadoop 2.8. You can change apache spark and hadoop version in DockerFiles

## To run cluster keep the order of launch
### Run spark-master
```
cd master && docker build -t spark-master .
docker run --name spark-master --net=spark-net -p 8080:8080 -p 7077:7077 -p 6066:6066 spark-master
```

### Run spark-worker
```
cd ../worker && docker build -t spark-worker-1 .
docker run --name spark-worker-1 --net=spark-net -p 8081:8081 spark-worker-1
```

### Run zeppelin
```
cd ../zeppelin && docker build -t zeppelin .
docker run --name zeppelin --net=spark-net -p 80:8080 zeppelin
```

#### Run examples (with delta.io) from app directory
```
docker exec -it spark-master /bin/bash
apt-get update && apt-get install wget
cd /spark/jars && wget https://repo1.maven.org/maven2/io/delta/delta-core_2.11/0.3.0/delta-core_2.11-0.3.0.jar
cd /spark/bin
spark-shell
write some code
```

##### Notes
You can not start cluster with docker compose file as cluster requires the certain order of application launching, but you can use run-cluster.sh containing 20 seconds delaying of spark worker launching.
Apache Spark >= 2.4.0 requires Zeppelin >= 0.8.2 [issue link](https://issues.apache.org/jira/browse/ZEPPELIN-3939). Zeppelin in this repository could not be compiled as of [missing dependencies error](http://apache-zeppelin-dev-mailing-list.75694.x6.nabble.com/jira-Created-ZEPPELIN-3899-Cannot-build-with-Scala-2-11-due-to-Missing-Dependency-zeppelin-scio-2-11-td34509.html)