package com.prime;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;
/**
 * Hello world!
 *
 */
public class App 
{
    private static boolean isPrime(long n) {
        for (long i = 2; 2 * i < n; i++) {
          if (n % i == 0) {
            return false;
          }
        }
        return true;
      }
     
      public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().appName("PrimeApp").getOrCreate();
        Dataset<Tuple2<Long, Boolean>> rnd = spark.range(0L, 1000000L).map(
          (MapFunction<Long, Tuple2<Long, Boolean>>) x -> new Tuple2<Long, Boolean>(x, isPrime(x)), Encoders.tuple(Encoders.LONG(), Encoders.BOOLEAN()));
        rnd.show(false);
        spark.stop();
      }
}

