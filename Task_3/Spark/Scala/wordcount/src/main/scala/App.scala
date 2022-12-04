
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object App
{

  def main(args:Array[String]):Unit = {

    // Spark configuration
    val conf = new SparkConf().setAppName("wordcount")
    
    // Create context 
    val sc = new SparkContext("local[*]", "SparkHelloWorld")
    
    // Hard coded filepath
    val hadoopPrimeSource = "/home/ubuntu/wiki/wiki.txt";
    val hadoopPrimeOutput = "/home/ubuntu/out/wikiScala.out";
    
    // Get input
    val input =  sc.textFile(hadoopPrimeSource)
    
    // Get words from input
    val words = input.flatMap(line  => line.split(" "))
    
    // Create map of words and create unique values based on map key
    val counts = words.map(word => (word, 1)).reduceByKey{case (x, y) => x + y}
    input = sc.t

    // sort locally
    val counts2 = counts.sortBy(- _._2)

    // Save file
    counts2.saveAsTextFile(hadoopPrimeOutput)
  }

}