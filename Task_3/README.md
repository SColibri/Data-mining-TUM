# Before you start
## starting hdfs
To start/stop hdfs use the following commands

```bash
# start hdfs filesystem
start-dfs.sh

# stop hdfs filesystem
stop-dfs.sh
```

## Starting Yarn
Start/stop yarn using the command:
```bash
# start yarn
start-yarn.sh

# stop yarn
stop-yarn.sh
```

Before starting to code and running code, make sure you have a correct yarn configuration. To check if your yarn configuration is correct use the commands ```jps``` & ```yarn node -list```, an example of a working cluster might have the following output:

```bash
# example output

>> jps
1077808 NameNode
1078116 SecondaryNameNode
1276537 Jps
1085516 ResourceManager
1039858 Master

>> yarn node -list

```

If you can't find the ResourceManager entry please refer to the Yarn guide.

# Hadoop 3.3.4
## Navigating the filesystem
We have two command options for navigating the file system on the cluster, namely ```hdfs``` & ```hadoop```.

```bash
# ---------------
# Using hdfs
# ---------------

# show directory/file list
hdfs dfs -ls <path>

# remove directory recursive example
hdfs dfs -rm -r <path/filename>

# make directory
hdfs dfs -mkdir <path>

# put file into hdfs
hdfs dfs -put <filename> <path in hdfs>

# get file from hdfs
hdfs dfs -get <path to file in hdfs>

# read file content, you can also pipe methods
hdfs dfs -cat <filename in hdf>

# ---------------
# Using hadoop
# ---------------

# SInce hadoop uses hdfs, the commands have the same structure
# but instead of using hdfs dfs, we now call hadoop fs, e.g.:

hadoop fs -ls <filepath>

```

## Java 1.8

### Hadoop API for java
Any programming languague can communicate with hadoop's API's by using streaming, and before we dive into some coding examples we will first reference all dependencies and configurations that we will need. 

#### Maven dependecy

Common dependencies needed for running a hadoop mapreduce job
```xml
<!-- Hadoop core -->
<dependency>
    <groupId>org.apache.hadoop</groupId>
    <artifactId>hadoop-core</artifactId>
    <version>1.2.0</version>
</dependency>

<!-- Hadoop client-->
<dependency>
    <groupId>org.apache.hadoop</groupId>
    <artifactId>hadoop-client</artifactId>
    <version>3.3.4</version>
    <scope>provided</scope>
</dependency>

<!-- Java SQL Database Engine -->
<dependency> 
    <groupId>org.hsqldb</groupId> 
    <artifactId>hsqldb</artifactId> 
    <version>2.0.0</version> 
</dependency>
```

#### Java imports

These are the common hadoop imports used for running an application using hadoop.

```Java
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Progressable;
```

#### Hadoop library classes

```Configuration conf = new Configuration();``` </br>
Hadoop configuration and provides access to configuration parameters. [refclass](https://hadoop.apache.org/docs/r2.7.1/api/org/apache/hadoop/conf/Configuration.html)

```Job job = new Job(conf, "<job name>");``` </br>
It allows the user to configure the job, submit it, control its execution, and query the state. The set methods only work until the job is submitted, afterwards they will throw an IllegalStateException. Normally the user creates the application, describes various facets of the job via Job and then submits the job and monitor its progress. [refclass](https://hadoop.apache.org/docs/stable/api/org/apache/hadoop/mapreduce/Job.html)

|Job methods| Description |
|--|--|
|setJarByClass(object.class)|Set the Jar by finding where a given class came from.|
|setOutputKeyClass(object.class)|Set the key class for the job output data. Parameters: theClass - the key class for the job output data. Throws: IllegalStateException - if the job is submitted|
|setOutputValueClass(object.class)|Set the value class for job outputs.|
|setMapperClass(object.class)|Set the Mapper for the job. Parameters: cls - the Mapper to use Throws: IllegalStateException - if the job is submitted|
|setInputFormatClass(object.class)|Set the InputFormat for the job.|
|setOutputFormatClass(object.class)|Set the OutputFormat for the job.|
|waitForCompletion|Submit the job to the cluster and wait for it to finish. Parameters: verbose - print the progress to the user Returns: true if the job succeeded Throws:IOException - thrown if the communication with the JobTracker is lostInterruptedException ClassNotFoundException|

```FileInputFormat```</br>
FileInputFormat is the base class for all file-based InputFormats. This provides a generic implementation of getSplits(JobContext). Subclasses of FileInputFormat can also override the isSplitable(JobContext, Path) method to ensure input-files are not split-up and are processed as a whole by Mappers.

|FileInputFormat Methods| Description |
|--|--|
|addInputPath| Add a Path to the list of inputs for the map-reduce job. Parameters:job - The Job to modify path - Path to be added to the list of inputs for the map-reduce job. Throws: IOException|

```FileOutputFormat```</br>

|FileOutputFormat Methods| Description |
|--|--|
|setOutputPath| Set the Path of the output directory for the map-reduce job. Parameters: job - The job to modify outputDir - the Path of the output directory for the map-reduce job.|

### compile using maven
To compile the jar file run the following command:

```bash
mvn clean install
```

### Maven project source codes
- [Prime numbers maven project](https://github.com/SColibri/Data-mining-TUM/tree/master/Task_3/Java/prime)
- [Word count maven project](https://github.com/SColibri/Data-mining-TUM/tree/master/Task_3/Java/wordcount)

### Before you run
#### Create the numberList.txt
The prime number project creates this file for you, and the you can modify this in the ```create_primeNumberList``` method. The method also checks if the file was already created just to avoid creating the file after each run, so don't forget to delete the file manually if you plan to edit the number list.

#### Upload the wordbank
For running the wordcount project we need to add the wordbank into our hdfs, and since the path is hardcoded keep in mind that you might also want to update this on the java file. 

#### Optional
If you want to use the wikipedia xml database, you can reduce the size by using the ```wiki parser project```. This will reduce the project size from 80GB to around 25GB.

### running a job on hadoop

Running a java file compiled into a jar object can be run on top of hadoop is done using the following command:
```bash
hadoop jar <jar file> <class name>

# example
hadoop jar javafilename.jar com.object.App
```

### Output
The output when running a hadoop job can be pretty long and it depends on how you implemented the code, here we will only show how a correctly executed job should report back.

#### Prime numbers using hadoop
```bash
>> hadoop jar prime.jar com.prime.App


```

Now we read the output file from hdfs.
```bash
>> hadoop fs -cat /home/ubuntu/out/primeNumbers.out/part-m-00001 | wc -l

# Output
>> 50847534
```


#### Wordcount using hadoop
```bash
>> hadoop jar wordcount.jar com.wordcount.App
```

Now we sort the file using ```sort``` and see the word ranking.

```bash
>> hadoop fs -cat /home/ubuntu/out/primeNumbers.out/part-m-00001 | sort -k2
```

### Known issues

|Issue|Solution|
|--|--|
|Yarn has no nodes available| This happens when the resource manager host in yarn-site.xml is not configured correctly. Check if the hostname is correct and that it can connect to all workers|
|Yarn has nodes available but does not have resources| This happens because of an invalid memory setting in the yarn-site.xml, refer to the yarn setup page for more details on how to do configure yarn correctly|
|hadoop does not connect to the datanodes|etc/hosts d|
## Scala 2.12

### SBT scala version

```java
scalaVersion := "2.12.3"
```

### SBT imports

```java
libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "3.3.4"
libraryDependencies += "org.apache.hadoop" % "hadoop-core" % "1.2.0"
```

### Source Codes
- [Prime numbers](https://github.com/SColibri/Data-mining-TUM/tree/master/Task_3/Hadoop/Scala/prime)
- [Wordcount](https://github.com/SColibri/Data-mining-TUM/tree/master/Task_3/Hadoop/Scala/wordcount)

### Errors


## Python


# Spark 3.3.1
## Java

### Maven imports

Include the following in the dependecies section:
```xml
<dependencies>
	<dependency>
		<groupId>org.apache.spark</groupId>
		<artifactId>spark-core_2.10</artifactId>
		<version>1.2.0</version>
	</dependency>

	<!-- https://mvnrepository.com/artifact/org.apache.spark/spark-sql -->
	<dependency>
		<groupId>org.apache.spark</groupId>
		<artifactId>spark-sql_2.13</artifactId>
		<version>3.3.1</version>
		<scope>provided</scope>
	</dependency>
</dependencies>
```

### Maven compile
``` bash
>> mvn clean install
```

### Spark run command

```bash
>> spark-submit --deploy-mode client <Filename>.jar
```

If the jar file has more than one main method, you have to specify the main class
```bash
>> spark-submit --deploy-mode client <Filename>.jar <class name>
```

### Source Codes
- [Prime Numbers](https://github.com/SColibri/Data-mining-TUM/tree/master/Task_3/Spark/Java/prime)
- [WordCount](https://github.com/SColibri/Data-mining-TUM/tree/master/Task_3/Spark/Java/wordcount/wordcount)

## Scala 2.12
### SBT scala version

```java
scalaVersion := "2.12.3"
```

### SBT imports

```java
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.3.1"
```

### Spark run command
This is similar to running a java packaged jar file.

```bash
>> spark-submit --deploy-mode client <Filename>.jar
```

If the jar file has more than one main method, you have to specify the main class
```bash
>> spark-submit --deploy-mode client <Filename>.jar <class name>
```

### Source codes
- [Prime numbers](https://github.com/SColibri/Data-mining-TUM/tree/master/Task_3/Spark/Scala/prime)
- [WordCount](https://github.com/SColibri/Data-mining-TUM/tree/master/Task_3/Spark/Scala/wordcount)

### Maven dependency

```xml
<!-- https://mvnrepository.com/artifact/org.apache.spark/spark-core -->
<dependency>
    <groupId>org.apache.spark</groupId>
    <artifactId>spark-core_2.12</artifactId>
    <version>3.3.1</version>
</dependency>
```

## Python

# Troubleshooting

## Dask


## Maven

- Find maven extensions and dependencies [here](https://mvnrepository.com/).

## Scala Maven

Boilerplate for scala maven can be found [here](https://github.com/meniluca/spark-scala-maven-boilerplate-project)

### Maven Plugin

For compiling scala code using maven, you can use the following plugin:

```xml
<plugin>
	<!-- see http://davidb.github.com/scala-maven-plugin -->
	<groupId>net.alchim31.maven</groupId>
	<artifactId>scala-maven-plugin</artifactId>
	<version>3.1.3</version>
	<executions>
		<execution>
			<goals>
				<goal>compile</goal>
				<goal>testCompile</goal>
			</goals>
			<configuration>
				<args>
					<arg>-make:transitive</arg>
					<arg>-dependencyfile</arg>
					<arg>${project.build.directory}/.scala_dependencies</arg>
				</args>
			</configuration>
		</execution>
	</executions>
</plugin>

<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-surefire-plugin</artifactId>
	<version>2.13</version>
	<configuration>
		<useFile>false</useFile>
		<disableXmlReport>true</disableXmlReport>

		<!-- If you have classpath issue like NoDefClassError,... -->
		<!-- useManifestOnlyJar>false</useManifestOnlyJar -->
		<includes>
			<include>**/*Test.*</include>
			<include>**/*Suite.*</include>
		</includes>
	</configuration>
</plugin>

<!-- "package" command plugin -->
<plugin>
	<artifactId>maven-assembly-plugin</artifactId>
	<version>2.4.1</version>
	<configuration>
		<descriptorRefs>
			<descriptorRef>jar-with-dependencies</descriptorRef>
		</descriptorRefs>
	</configuration>
	<executions>
		<execution>
			<id>make-assembly</id>
			<phase>package</phase>
			<goals>
				<goal>single</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```

Modify the properties section such that it includes thescala version that you are using.

```xml
<properties>
	<maven.compiler.source>1.8</maven.compiler.source>
	<maven.compiler.target>1.8</maven.compiler.target>
	<encoding>UTF-8</encoding>
	<scala.tools.version>2.12</scala.tools.version>
	<!-- Put the Scala version of the cluster -->
	<scala.version>2.12</scala.version>
</properties>
```

And include the scala dependency:

```xml
<dependency>
	<groupId>org.scala-lang</groupId>
	<artifactId>scala-library</artifactId>
	<version>${scala.version}</version>
</dependency>
```

By adding these three objects you will be able to manage your scala project using maven!

## Scala SBT
sbt is a build tool for Scala, Java, and more. It requires Java 1.8 or later.

### Installation
For installing sbt you can follow the follwing [guide](https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Linux.html). The guide also offers a solution for installing scala using cs powered by Coursier! 

### SBT compile

Compile and create jar files by calling the package command.

```bash
>> sbt compile package
```

