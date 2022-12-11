#!/bin/bash

# Installs all dependencies needed for hadoop, and also installs hadoop/spark
installDependencies()
{

    # apt
    if [ $( isInstalled "apt" ) -eq 1 ]
    then
        echo "apt is installed, updating..\n" 
        # sudo apt-get update -y
    else
        echo "apt is not installed, please install or add the installation to this script\n"
        exit 0
    fi

    # java
    if [ $( isInstalled "openjdk-8-jre-headless" ) -eq 1 ]
    then
        echo "java is installed\n"
    else
        echo "java is not installed, installing..\n"
        sudo apt install openjdk-8-jdk openjdk-8-jre
    fi

    # scala
    # Working scala version is suggested to be 2.12.5
    if [ $( isInstalled "scala" ) -eq 1 ]
    then
        echo "scala is installed\n"
    else
        echo "scala is not installed, installing..\n"
        sudo apt-get remove scala-library scala -y
        sudo wget http://scala-lang.org/files/archive/scala-2.12.5.deb
        sudo dpkg -i scala-2.12.5.deb
        sudo apt-get update
        sudo apt-get install scala -y
    fi

    # sbt
    if [ $( isInstalled "sbt" ) -eq 1 ]
    then
        echo "sbt is installed\n"
    else
        echo "sbt is not installed, installing..\n"
        sudo apt-get install sbt
    fi

    # python
    if [ $( isInstalled "python" ) -eq 1 ]
    then
        echo "python is installed\n"
    else
        echo "python is not installed, installing..\n"
        # sudo apt-get install python3
        
    fi

    # hadoop
    if [ $"HADOOP_HOME" == "" ]
    then
        echo "hadoop is not installed, installing hadoop 3.3.4\n"
    
    else
        echo "hadoop is installed\n"

    fi

    # Spark
    if [ $"SPARK_HOME" == "" ]
    then
        echo "spark is not installed, installing spark 3.3.1\n"
    
    else
        echo "spark is installed\n"

    fi
}