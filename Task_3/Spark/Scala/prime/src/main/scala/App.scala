import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object App
{

  def main(args:Array[String]):Unit = {

    // Spark configuration
    val conf = new SparkConf().setAppName("prime")
    
    // Create context 
    val sc = new SparkContext("local[*]", "sparkPrimes")
    
    // Hard coded filepath
    val hadoopPrimeSource = "/home/ubuntu/input/primeNumbers.txt";
    val hadoopPrimeOutput = "/home/ubuntu/out/primeNumbersScalaSpark.out";
    
    // Get input
    val input =  sc.textFile(hadoopPrimeSource)
    
    // Get words from input
    val words = input.flatMap(line  => line.split("\n"))
    
    val isPrime: Double => Boolean = number => {	
      if (number == 1) {
        false
      }
      if (number % 2 == 0 && number != 2 || number % 3 == 0 && number != 3) {
        false
      }

      val limit = ((Math.pow(number, 0.5) + 1) / 6.0 + 1).toInt;

      for (i <- 0 until limit) {
        if(number % (6 * i - 1) == 0){
          false
        }
        if(number % (6 * i + 1) == 0){
          false
        }
      }
      //System.console().writer().println(number);
      true
    }
    
    // Create map of words and create unique values based on map key
    val counts = words.map(word => if (isPrime(word.toDouble)) word else "")

    //
    val counts2 = counts.filter(x => (x != null) && (x.length > 0))

    // Save file
    counts2.saveAsTextFile(hadoopPrimeOutput)
  }

  // Check if number is prime
	def isPrime(number: Double) : Boolean =
	{	
		if (number == 1) {
			return false;
		}
		if (number % 2 == 0 && number != 2 || number % 3 == 0 && number != 3) {
			return false;
		}

		val limit = ((Math.pow(number, 0.5) + 1) / 6.0 + 1).toInt;

		for (i <- 0 until limit) {
			if(number % (6 * i - 1) == 0){
				return false;
			}
			if(number % (6 * i + 1) == 0){
				return false;
			}
		}
		System.console().writer().println(number);
		return true;
	}
}