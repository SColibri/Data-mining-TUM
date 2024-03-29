#!/bin/bash
# --------------------------------------------------
# This script will setup hadoop filesystem on your master node. Before running the script, please modify
# the files inside the directory ModifyThese such that it maps to your current network layout.
#
# SCRIPT MADE BY SEBASTIAN CARRION.
# --------------------------------------------------

# -----------------------------------------
# Variables
# -----------------------------------------

# Options
USER_INPUT=0;

# Database downloads
DOWNLOAD_WIKI=0;
DOWNLOAD_EMBEDDINGS=0;

# Installations
INSTALL_JAVA_1_8=1;
INSTALL_HADOOP_3_3_4=1;

# Path to files
PATH_HOME="/home/ubuntu"
PATH_SSHHOME=$PATH_HOME/.ssh

PATH_CURRENT=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
PATH_SSHKEY=$PATH_CURRENT:ModifyThese/privateKey

# -----------------------------------------
# Imports
# -----------------------------------------
for i in $PATH_CURRENT/imports/*;
  do source $i
done

udo apt-get update -y
sudo apt-get upgrade -y
sudo apt-get install ssh -y
sudo apt-get install openjdk-8-jdk openjdk-8-jre -y
sudo apt-get install python3 -y
sudo apt-get install python3-pip -y
sudo apt-get install yarn -y

pip install pyspark
pip install numpy
pip install matplotlib
pip install jupyter

sudo wget http://apache.cs.utah.edu/hadoop/common/current/hadoop-3.3.4.tar.gz
sudo tar -xzf hadoop-3.3.4.tar.gz
sudo mv hadoop-3.3.4 hadoop

wget https://dlcdn.apache.org/spark/spark-3.3.1/spark-3.3.1-bin-hadoop3.tgz
tar -xzf spark-3.3.1-bin-hadoop3.tgz
mv spark-3.3.1-bin-hadoop3 spark

# -----------------------------------------
# Install dependencies
#
# Implementation is found in the 
# installDependencies.sh file
# -----------------------------------------
#echo "Installing dependecnies"
#outDependencies=$(installDependencies)
#echo outDependencies
# echo -e $( installDependencies )

# -----------------------------------------
# Download all files
# -----------------------------------------
if [ $DOWNLOAD_WIKI -eq 1 ]
then
    echo "Downloading Wiki.."
fi

if [ $DOWNLOAD_EMBEDDINGS -eq 1 ]
then
    echo "Downloading embedding.."
    # wget https://rostlab.org/public/richter/sampled_embeddings.csv.gz
fi

# -----------------------------------------
# Configure the .ssh folder
# -----------------------------------------

# -----------------------------------------
# set ssh key
# -----------------------------------------

# Copy ssh key
sudo cp $PATH_CURRENT/ModifyThese/privateKey ~/.ssh

SETUP_SSHFILE=0;
if [ -f "$PATH_HOME/.ssh/config" ]
then
    
    if [ $USER_INPUT -eq 1 ]
    then
        echo "ssh config file exists, do you want to modify this file? 0/1"
        read BOOL_MODIFY
    else
        BOOL_MODIFY=1
    fi

    if [ $BOOL_MODIFY -eq 1 ]
    then
        SETUP_SSHFILE=1;
    fi
else
    SETUP_SSHFILE=1;
fi

if [ $SETUP_SSHFILE -eq 1 ]
then
    echo "setting up config file"
    echo -e $( createConfigFile $PATH_CURRENT ubuntu $PATH_SSHHOME )
    #Edit 
fi

# -----------------------------------------
# Get hadoop configuration files
# -----------------------------------------

# -----------------------------------------
# Get Spark configuration files
# -----------------------------------------

# -----------------------------------------
# Set .bashrc
# -----------------------------------------

# Setup hadoop dilesystem


