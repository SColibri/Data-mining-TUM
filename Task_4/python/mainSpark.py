import threading
import time
import logging

import findspark
findspark.init()

import pyspark
from pyspark.sql import SparkSession
from pyspark.sql.types import StructType,StructField, StringType, IntegerType 
from pyspark.sql.types import ArrayType, DoubleType, BooleanType
from pyspark.sql.functions import col,array_contains

filename = "/home/ubuntu/protein/sampled_embeddings.csv"
spark = SparkSession.builder.appName("dbExample").getOrCreate()

# Guide from
# https://sparkbyexamples.com/pyspark/pyspark-convert-string-type-to-double-type-float-type/

# ---------------------------------------------------------------------
#                       READ DATABASE DEFAULT WAY
# ---------------------------------------------------------------------

# df = spark.read.csv(filename)
# df.printSchema()

# ---------------------------------------------------------------------
#                      READ DATABASE USING HEADERS
# ---------------------------------------------------------------------

# df2 = spark.read.option("header",False).csv(filename)
# df2.printSchema()

# ---------------------------------------------------------------------
#         READ DATABASE USING HEADERS AND SPECIFYING DELIMITER
# ---------------------------------------------------------------------

# df3 = spark.read.options(header='False', delimiter=',').csv(filename)
# df3.printSchema()

# ---------------------------------------------------------------------
#                       READ DATABASE USING SCHEMA
# ---------------------------------------------------------------------

schema = StructType().add("embedding", DoubleType(), True)

df_with_schema = spark.read.format("csv").option("header", True).schema(schema).load(filename)
df_with_schema.printSchema()

# ---------------------------------------------------------------------
#                               WRITE DATA
# ---------------------------------------------------------------------

# df_with_schema.write.option("header",True).csv("/home/ubuntu/protein/zipcodes123")
df_with_schema.show()
