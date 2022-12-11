import threading
import time
import logging

import findspark
findspark.init()

from pyspark.sql import SparkSession
from pyspark.ml.linalg import Vectors
from embeddingStructure import embeddingStructure
from batch import batch

if __name__ == "__main__":
    
    filename = "/home/ubuntu/protein/sampled_embeddings.csv"

    spark = SparkSession\
        .builder\
        .appName("PythonWordCount")\
        .getOrCreate()

    csvData = spark.read.text(filename).rdd.map(lambda r: r[0])
    flatData = csvData.flatMap(lambda x: x.split(' '))\
                      .map(lambda dataLine: test)
    # output = flatData.collect()


def test(dataLine):
    print(dataLine)