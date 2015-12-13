How To Run
------------------
1. Clone the project and goto project root directory
2. Open run.sh in an editor and make changes in the configuration section as per your environment:
   These 3 settings are required to run spark task:
   - spark_master
   - hdfs_root
   - spark_home

   See the examples in run.sh for each of above configurations
   You can change input/output paths for all 3 tasks

3. Run script
   ./run.sh

   Tested on Ubuntu 12.04 and Mac OS



Project Dependencies
--------------------------
- Maven
- Git
- Hadoop 2.6 (Tested on Hadoop 2.6), for other version may require changes in pom.xml
- Spark 1.5
- Akka: Used akka for actors based task implementation




