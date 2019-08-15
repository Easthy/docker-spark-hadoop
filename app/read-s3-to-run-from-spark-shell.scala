System.setProperty("com.amazonaws.services.s3.enableV4", "true")

sc.hadoopConfiguration.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
sc.hadoopConfiguration.set("fs.s3a.access.key", "")
sc.hadoopConfiguration.set("fs.s3a.secret.key", "")
sc.hadoopConfiguration.set("fs.s3a.endpoint", "s3.eu-central-1.amazonaws.com")

spark.range(5).write.format("delta").save("s3a://easthy/delta-table-3")
val df = spark.read.format("delta").load("s3a://easthy/delta-table-3")
df.show()
/*
+---+                                                                           
| id|
+---+
|  3|
|  4|
|  1|
|  0|
|  2|
+---+
*/

val data = spark.range(4, 10)
data.write.format("delta").mode("overwrite").save("s3a://easthy/delta-table-3")
val df = spark.read.format("delta").load("s3a://easthy/delta-table-3")
df.show()

/*
+---+                                                                           
| id|
+---+
|  5|
|  6|
|  8|
|  9|
|  7|
|  4|
+---+
*/

import io.delta.tables._
import org.apache.spark.sql.functions._

val deltaTable = DeltaTable.forPath("s3a://easthy/delta-table-3")

// Update every even value by adding 100 to it
deltaTable.update(
  condition = expr("id % 2 == 0"),
  set = Map("id" -> expr("id + 100")))

deltaTable.toDF.show()

/*
+---+                                                                           
| id|
+---+
|  5|
|106|
|108|
|  9|
|104|
|  7|
+---+
*/

// Delete every even value
deltaTable.delete(condition = expr("id % 2 == 0"))
deltaTable.toDF.show()
/*
+---+                                                                           
| id|
+---+
|  5|
|  9|
|  7|
+---+
*/

// Upsert (merge) new data
val newData = spark.range(0, 20).as("newData").toDF

deltaTable.as("oldData").merge(
    newData,
    "oldData.id = newData.id").whenMatched.update(
        Map("id" -> col("newData.id"))).whenNotMatched.insert(
            Map("id" -> col("newData.id"))).execute()

deltaTable.toDF.show()

/*
+---+                                                                           
| id|
+---+
| 19|
|  1|
|  6|
|  9|
| 17|
| 14|
|  2|
| 15|
| 13|
|  3|
|  5|
| 18|
|  8|
|  7|
| 10|
| 12|
| 16|
| 11|
|  0|
|  4|
+---+
*/

// Read older versions of data using Time Travel
val df = spark.read.format("delta").option("versionAsOf", 0).load("s3a://easthy/delta-table-3")
df.show()
/*
+---+                                                                           
| id|
+---+
|  3|
|  4|
|  1|
|  0|
|  2|
+---+
*/