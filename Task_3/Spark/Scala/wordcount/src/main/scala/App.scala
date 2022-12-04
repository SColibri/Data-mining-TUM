
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object App
{

  def main(args:Array[String]):Unit = {
    val conf = new SparkConf().setAppName("wordcount")
    val sc = new SparkContext("local[*]", "SparkHelloWorld")
    val hadoopPrimeSource = "/home/ubuntu/wiki/wiki.txt";
    val hadoopPrimeOutput = "/home/ubuntu/out/wikiScala.out";
    

    val input =  sc.textFile(hadoopPrimeSource)
    val words = input.flatMap(line  => line.split(" "))
    val counts = words.map(word => (word, 1)).reduceByKey{case (x, y) => x + y}
    val counts2 = counts.sortBy(- _._2)
    counts2.saveAsTextFile(hadoopPrimeOutput)
  }

}