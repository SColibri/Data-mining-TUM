# source: Coursera course on Spark and AWS, Introduction to Apache Spark and AWS, University of London, www.coursera.org.
# based on tutorial on https://districtdatalabs.silvrback.com/getting-started-with-spark-in-python
# for more info, see Spark docs: https://spark.apache.org/docs/2.0.0-preview/programming-guide.html

from pyspark.sql import SparkSession
from pyspark.context import SparkContext
from math import sqrt
import os
import sys
import time

os.environ['PYSPARK_PYTHON'] = sys.executable
os.environ['PYSPARK_DRIVER_PYTHON'] = sys.executable

def isprime(n):
    """ check if integer n is a prime """

	# make sure n is a positive integer
    n = abs(int(n))

	# 0 and 1 are not primes
    if n < 2:
        return False
    # 2 is the only even prime number
    elif n == 2:
        return True
    # all other even numbers are not primes
    elif n % 2 == 0:
        return False

	# range starts with 3 and only needs to go up to the integer
    # square root by odd numbers
    for x in range( 3, int( sqrt(n) )+1, 2 ):
        if n % x == 0:
            return False
    return True

if __name__ == "__main__":
    """ Generate prime numbers in range x - y

	NOTES:
	- SparkSession is new entry point to Spark as of Spark 2.0
	- For backwards compatibility, SparkContext class is retained by pyspark API

	"""

	# build a SparkSession object that contains information about your application
	# the appName parameter is a name for your application to show on the cluster UI
	# most options should be set at runtime when launching with spark-submit
spark = SparkSession.builder.appName("primes").enableHiveSupport().getOrCreate()

	# create a new Spark Context object, which tells Spark how to access a cluster
sc = SparkContext.getOrCreate()
start = time.time()
	# create an RDD of numbers from 0 to num
x = 0
y = 10**4
nums = sc.parallelize( range (x,y) )
nums = nums.filter(isprime)
# nums = sc.parallelize( lambda x: for x in range(x,y): x if isprime(x) )
par_time = time.time()
# # Hard coded filepath
# hadoopPrimeSource = "/home/ubuntu/input/primeNumbers.txt";
# hadoopPrimeOutput = "/home/ubuntu/out/primeNumbersScalaSpark.out";
    
# # Get input
# input =  sc.textFile(hadoopPrimeSource)
# prime = nums.map(lambda x: (x,1) if (isprime(x)))
print(type(nums))
# rdd2=rdd.map(lambda x: (x,1))
# for element in rdd2.collect():
#     print(element)

# prime = prime.collect()
# compute and display the number of primes found
print('What is this')
# print(nums.collect())
clt_time = time.time()
# print('primes', prime.count())
# print ( "NUMBER OF PRIMES BELOW %d IS: %d" % (y, nums.filter( isprime ).count()) )
print ( "NUMBER OF PRIMES BELOW %d IS: %d" % (y, nums.count()) )
final_time = time.time()
print(nums.filter( isprime ).collect())
print('Times:',par_time-start,clt_time-par_time,final_time-clt_time)
