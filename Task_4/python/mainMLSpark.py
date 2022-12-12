import pyspark
from pyspark.sql import SparkSession
from pyspark.sql.types import StructType,StructField, StringType, IntegerType 
from pyspark.sql.types import ArrayType, DoubleType, BooleanType
from pyspark.sql.functions import col,array_contains
from pyspark.ml.feature import VectorAssembler
from pyspark.ml.clustering import KMeans
from pyspark.ml.evaluation import ClusteringEvaluator
from pyspark.sql import functions as F
from pyspark.sql import types as T
from pyspark.ml.linalg import SparseVector, DenseVector

filename = "/home/ubuntu/protein/sampled_embeddings.csv"
spark = SparkSession.builder.appName("dbExample").getOrCreate()

temp_df = spark.createDataFrame([Row(V4366=0.0, V4460=0.232, V4916=-0.017, V1495=-0.104, V1639=0.005, V1967=-0.008, V3049=0.177, V3746=-0.675, V3869=-3.451, V524=0.004, V5409=0), Row(V4366=0.0, V4460=0.111, V4916=-0.003, V1495=-0.137, V1639=0.001, V1967=-0.01, V3049=0.01, V3746=-0.867, V3869=-2.759, V524=0.0, V5409=0), Row(V4366=0.0, V4460=-0.391, V4916=-0.003, V1495=-0.155, V1639=-0.006, V1967=-0.019, V3049=-0.706, V3746=0.166, V3869=0.189, V524=0.001, V5409=0), Row(V4366=0.0, V4460=0.098, V4916=-0.012, V1495=-0.108, V1639=0.005, V1967=-0.002, V3049=0.033, V3746=-0.787, V3869=-0.926, V524=0.002, V5409=0), Row(V4366=0.0, V4460=0.026, V4916=-0.004, V1495=-0.139, V1639=0.003, V1967=-0.006, V3049=-0.045, V3746=-0.208, V3869=-0.782, V524=0.001, V5409=0)])

assembler = VectorAssembler(
    inputCols=['V4366', 'V4460', 'V4916', 'V1495', 'V1639', 'V1967', 'V3049', 'V3746', 'V3869', 'V524'],
    outputCol='features')


df3 = spark.read.options(header='False', delimiter=',').csv(filename)
# df3.printSchema()

# assemble=VectorAssembler(inputCols = ['_c0'],outputCol = 'protein features')

assembled_data=assemble.transform(temp_df)