%spark.dep

z.reset()

z.load("org.apache.hadoop:hadoop-common:jar:2.8.0")
z.load("org.apache.hadoop:hadoop-aws:jar:2.8.0")

z.load("com.fasterxml.jackson.core:jackson-databind:jar:2.6.6")
z.load("com.fasterxml.jackson.core:jackson-annotations:jar:2.6.0")
z.load("com.fasterxml.jackson.core:jackson-core:jar:2.6.6")
z.load("com.fasterxml.jackson.dataformat:jackson-dataformat-cbor:jar:2.6.6")

z.load("com.amazonaws:aws-java-sdk-core:jar:1.11.608")
z.load("com.amazonaws:aws-java-sdk:jar:1.11.608")
z.load("com.amazonaws:aws-java-sdk-s3:jar:1.11.608")
z.load("org.apache.spark:spark-streaming-kinesis-asl_2.11:2.4.1")


System.setProperty("com.amazonaws.services.s3.enableV4", "true")

sc.hadoopConfiguration.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
sc.hadoopConfiguration.set("fs.s3a.access.key", "")
sc.hadoopConfiguration.set("fs.s3a.secret.key", "")
sc.hadoopConfiguration.set("fs.s3a.endpoint", "s3.eu-central-1.amazonaws.com")

spark.range(5).write.format("delta").save("s3a://easthy/delta-table")