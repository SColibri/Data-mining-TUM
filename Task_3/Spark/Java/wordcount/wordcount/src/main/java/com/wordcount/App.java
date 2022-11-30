package com.wordcount;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String hadoopPrimeSource = "/home/ubuntu/wiki/wiki.txt";

        wordCount(hadoopPrimeSource);
    }

    private static void wordCount(String fileName) {

        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("Scala wordcount spark");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<String> inputFile = sparkContext.textFile(fileName);
        
        JavaRDD<String> wordsFromFile = inputFile.flatMap(content -> Arrays.asList(content.split(" ")).iterator());

        JavaPairRDD<String, Integer> countData = wordsFromFile.mapToPair(t -> new Tuple2(t, 1)).reduceByKey((x, y) -> (int) x + (int) y);

        //countData.collect().forEach(t -> System.out.println(t._1+" : "+t._2));
        countData.saveAsTextFile("ScalaWordcountSpark");
    }
}
