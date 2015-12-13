#!/bin/bash

# Configurations
#################################################################################
# Set Spark Master
spark_master=spark://akhtars-mbp:7077
# defines hdfs url
hdfs_root=hdfs://akhtars-mbp:9000
# define spark home
spark_home=/Users/akhtar/Downloads/hadoop/spark-1.5.0-bin-hadoop2.6/
#################################################################################


# saves current dir
CURRENT_DIR=`pwd`


# Builds project
mvn clean install

# Running Stage 1 Task
#=======================================================================================================================================
java -cp target/tasks-1.0-SNAPSHOT-jar-with-dependencies.jar com.test.Stage1Driver data/sample.csv  data/stage1.txt
cat data/stage1.txt


# Running Stage 2 Task
#=======================================================================================================================================
java -cp target/tasks-1.0-SNAPSHOT-jar-with-dependencies.jar com.test.Stage2Driver data/sample.csv  data/stage2.txt
cat data/stage2.txt


# Running Stage 3 Task
#=======================================================================================================================================
# Assuming HDFS can be accessible, change paths as per your environment

echo "Copying input data ..."
hdfs dfs -mkdir ${hdfs_root}/stage3TaskInput
hdfs dfs -put data/sample.csv ${hdfs_root}/stage3TaskInput/

echo "Cleaning up existing output dir ..."
hdfs dfs -rm -r ${hdfs_root}/stage3TaskOutput/


cd ${spark_home}
./bin/spark-submit --class com.test.Stage3Driver --master ${spark_master} ${CURRENT_DIR}/target/tasks-1.0-SNAPSHOT.jar ${hdfs_root}/stage3TaskInput/ ${hdfs_root}/stage3TaskOutput/

hdfs dfs -cat ${hdfs_root}/stage3TaskOutput/part-00000
hdfs dfs -cat ${hdfs_root}/stage3TaskOutput/part-00001
