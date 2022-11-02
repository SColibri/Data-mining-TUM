# Introduction

The following list are examples of some frameworks used in hpc computing, machine learning and data manipulation.

# Hadoop (Apache)
link: https://hadoop.apache.org/

The Apache Hadoop software library is a framework that allows for the distributed processing of large data sets across clusters of computers using simple programming models.

# Hadoop HDFS

link: https://hadoop.apache.org/docs/r1.2.1/hdfs_design.html#Introduction

The Hadoop Distributed File System (HDFS) is a distributed file system designed to run on commodity hardware. It has many similarities with existing distributed file systems. However, the differences from other distributed file systems are significant. HDFS is highly fault-tolerant and is designed to be deployed on low-cost hardware. HDFS provides high throughput access to application data and is suitable for applications that have large data sets. HDFS relaxes a few POSIX requirements to enable streaming access to file system data. HDFS was originally built as infrastructure for the Apache Nutch web search engine project. HDFS is now an Apache Hadoop subproject. The project URL is https://hadoop.apache.org/hdfs/.

# Ansible and chef

link: https://www.upguard.com/blog/ansible-vs-chef#:~:text=Ansible%20and%20chef%20are%20configuration,of%20new%20servers%20from%20scratch.

Ansible and chef are configuration management (CM) tools that help sysadmins and DevOps professionals manage a large number of servers. They excel at repetitive task automation, simultaneous deployment of apps and packages to a group of servers or configuration and provisioning of new servers from scratch

# Scala

link: https://www.scala-lang.org/

Scala combines object-oriented and functional programming in one concise, high-level language. Scala's static types help avoid bugs in complex applications, and its JVM and JavaScript runtimes let you build high-performance systems with easy access to huge ecosystems of libraries.

# Apache spark

link: https://spark.apache.org/

pache Spark™ is a multi-language engine for executing data engineering, data science, and machine learning on single-node machines or clusters.

# Dask

link: https://www.dask.org/

Dask is a flexible library for parallel computing in Python.

Dask is composed of two parts:

Dynamic task scheduling optimized for computation. This is similar to Airflow, Luigi, Celery, or Make, but optimized for interactive computational workloads.

“Big Data” collections like parallel arrays, dataframes, and lists that extend common interfaces like NumPy, Pandas, or Python iterators to larger-than-memory or distributed environments. These parallel collections run on top of dynamic task schedulers.

# Ambari and Zookeeper

link: https://ambari.apache.org/ https://zookeeper.apache.org/

Developers describe Ambari as "A software for provisioning, managing, and monitoring Apache Hadoop clusters". This project is aimed at making Hadoop management simpler by developing software for provisioning, managing, and monitoring Apache Hadoop clusters. It provides an intuitive, easy-to-use Hadoop management web UI backed by its RESTful APIs. On the other hand, Zookeeper is detailed as "Because coordinating distributed systems is a Zoo". A centralized service for maintaining configuration information, naming, providing distributed synchronization, and providing group services. All of these kinds of services are used in some form or another by distributed applications.

Ambari belongs to "Monitoring Tools" category of the tech stack, while Zookeeper can be primarily classified under "Open Source Service Discovery".

# Yarn, Mesos and other schedulers

link: https://hadoop.apache.org/docs/stable/hadoop-yarn/hadoop-yarn-site/YARN.html
https://mesos.apache.org/

YARN: The fundamental idea of YARN is to split up the functionalities of resource management and job scheduling/monitoring into separate daemons. The idea is to have a global ResourceManager (RM) and per-application ApplicationMaster (AM). An application is either a single job or a DAG of jobs.

Mesos: Mesos is built using the same principles as the Linux kernel, only at a different level of abstraction. The Mesos
kernel runs on every machine and provides applications (e.g., Hadoop, Spark, Kafka, Elasticsearch) with API’s for
resource management and scheduling across entire datacenter and cloud environments.

# MLlib and Mahout

link: https://spark.apache.org/docs/latest/ml-guide.html
https://mahout.apache.org//

MLlib is Spark’s machine learning (ML) library. Its goal is to make practical machine learning scalable and easy. At a high level, it provides tools such as:

### MLib
ML Algorithms: common learning algorithms such as classification, regression, clustering, and collaborative filtering
Featurization: feature extraction, transformation, dimensionality reduction, and selection
Pipelines: tools for constructing, evaluating, and tuning ML Pipelines
Persistence: saving and load algorithms, models, and Pipelines
Utilities: linear algebra, statistics, data handling, etc.

### Mahout
Apache Mahout(TM) is a distributed linear algebra framework and mathematically expressive Scala DSL designed to let mathematicians, statisticians, and data scientists quickly implement their own algorithms. Apache Spark is the recommended out-of-the-box distributed back-end, or can be extended to other distributed backends.

Mathematically Expressive Scala DSL
Support for Multiple Distributed Backends (including Apache Spark)
Modular Native Solvers for CPU/GPU/CUDA Acceleration

# Jupyter

Link: https://jupyter.org/

JupyterLab is the latest web-based interactive development environment for notebooks, code, and data. Its flexible interface allows users to configure and arrange workflows in data science, scientific computing, computational journalism, and machine learning. A modular design invites extensions to expand and enrich functionality.

# Pig and Hive

Link: https://pig.apache.org/ https://hive.apache.org/

Text from [here](https://www.geeksforgeeks.org/difference-between-pig-and-hive/)
### 1. Pig : 

Pig is used for the analysis of a large amount of data. It is abstract over MapReduce. Pig is used to perform all kinds of data manipulation operations in Hadoop. It provides the Pig-Latin language to write the code that contains many inbuilt functions like join, filter, etc. The two parts of the Apache Pig are Pig-Latin and Pig-Engine. Pig Engine is used to convert all these scripts into a specific map and reduce tasks. Pig abstraction is at a higher level. It contains less line of code as compared to MapReduce. 

### 2. Hive : 

Hive is built on the top of Hadoop and is used to process structured data in Hadoop. Hive was developed by Facebook. It provides various types of querying language which is frequently known as Hive Query Language. Apache Hive is a data warehouse and which provides an SQL-like interface between the user and the Hadoop distributed file system (HDFS) which integrates Hadoop. 

# HBase

Link: https://hbase.apache.org/

Apache HBase™ is the Hadoop database, a distributed, scalable, big data store.

Use Apache HBase™ when you need random, realtime read/write access to your Big Data. This project's goal is the hosting of very large tables -- billions of rows X millions of columns -- atop clusters of commodity hardware. Apache HBase is an open-source, distributed, versioned, non-relational database modeled after Google's Bigtable: A Distributed Storage System for Structured Data by Chang et al. Just as Bigtable leverages the distributed data storage provided by the Google File System, Apache HBase provides Bigtable-like capabilities on top of Hadoop and HDFS.

# Cassandra

Link: https://cassandra.apache.org/_/index.html

Apache Cassandra is an open source NoSQL distributed database trusted by thousands of companies for scalability and high availability without compromising performance. Linear scalability and proven fault-tolerance on commodity hardware or cloud infrastructure make it the perfect platform for mission-critical data.

# Apache ozone

Link: https://ozone.apache.org/

Apache Ozone is a highly scalable, distributed storage for Analytics, Big data and Cloud Native applications. Ozone supports S3 compatible object APIs as well as a Hadoop Compatible File System implementation. It is optimized for both efficient object store and file system operations.

It is built on a highly available, replicated block storage layer called Hadoop Distributed Data Store (HDDS).

Applications using frameworks like Apache Spark, YARN and Hive work natively without any modifications.

Ozone is now Generally Available(GA) with 1.2.1 release.

# GraphX/Giraph/Pregel

Link: https://spark.apache.org/graphx/ https://giraph.apache.org/

### GraphX
GraphX unifies ETL, exploratory analysis, and iterative graph computation within a single system. You can view the same data as both graphs and collections, transform and join graphs with RDDs efficiently, and write custom iterative graph algorithms using the Pregel API.

### Giraph
Apache Giraph is an iterative graph processing system built for high scalability. For example, it is currently used at Facebook to analyze the social graph formed by users and their connections. Giraph originated as the open-source counterpart to Pregel, the graph processing architecture developed at Google and described in a 2010 paper. Both systems are inspired by the Bulk Synchronous Parallel model of distributed computation introduced by Leslie Valiant. Giraph adds several features beyond the basic Pregel model, including master computation, sharded aggregators, edge-oriented input, out-of-core computation, and more. With a steady development cycle and a growing community of users worldwide, Giraph is a natural choice for unleashing the potential of structured datasets at a massive scale. To learn more, consult the User Docs section above.

### Pregel

[Here are some pregel lecture notes](https://stanford.edu/~rezab/classes/cme323/S16/notes/Lecture16/Pregel_GraphX.pdf)


# Streaming and kafka

Link: https://kafka.apache.org/documentation/streams/

### kafka
Kafka Streams is a client library for building applications and microservices, where the input and output data are stored in Kafka clusters. It combines the simplicity of writing and deploying standard Java and Scala applications on the client side with the benefits of Kafka's server-side cluster technology.

# Elastic search

Text from [here](https://en.wikipedia.org/wiki/Elasticsearch)

Elasticsearch is a search engine based on the Lucene library. It provides a distributed, multitenant-capable full-text search engine with an HTTP web interface and schema-free JSON documents. Elasticsearch is developed in Java and is dual-licensed under the source-available Server Side Public License and the Elastic license,[2] while other parts[3] fall under the proprietary (source-available) Elastic License. Official clients are available in Java,[4] .NET[5] (C#), PHP,[6] Python,[7] Ruby[8] and many other languages.[9] According to the DB-Engines ranking, Elasticsearch is the most popular enterprise search engine.[10]
