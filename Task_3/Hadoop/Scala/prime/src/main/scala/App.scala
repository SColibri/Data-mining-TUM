
import java.io.IOException
import java.util._
import org.apache.hadoop.fs.Path
import org.apache.hadoop.conf._
import org.apache.hadoop.io._
import org.apache.hadoop.mapred._
import org.apache.hadoop.util._

object App
{
	class Map extends MapReduceBase with Mapper[LongWritable, Text, Text, IntWritable] 
	{
		val one = new IntWritable(1)
  		val word = new Text
  
		@throws[IOException]
		def map(
			key: LongWritable,
			value: Text,
			output: OutputCollector[Text, IntWritable],
			reporter: Reporter): Unit = 
			{
				val line: String = value.toString
				
				line.split(" ").foreach 
				{ 
					token =>
					word.set(token)
					output.collect(word, one)
				}
			}
	}

	class Reduce extends MapReduceBase with Reducer[Text, IntWritable, Text, IntWritable] 
	{
		@throws[IOException]
		def reduce(
			key: Text,
			values: Iterator[IntWritable],
			output: OutputCollector[Text, IntWritable],
			reporter: Reporter): Unit = 
			{
				//values.toList.foreach {value => if (value.get()>0) output.collect(key, value)}
			}
	}

	@throws[Exception]
	def main(args: Array[String]): Unit = {

		val hadoopPrimeSource = "/home/ubuntu/input/primeNumbers.txt";
    	val hadoopPrimeOutput = "/home/ubuntu/out/primeScalaHadoop.out";

		val conf: JobConf = new JobConf(this.getClass)
		conf.setJobName("primesScalaHadoop")
		conf.setOutputKeyClass(classOf[Text])
		conf.setOutputValueClass(classOf[IntWritable])
		conf.setMapperClass(classOf[Map])
		conf.setCombinerClass(classOf[Reduce])
		conf.setReducerClass(classOf[Reduce])
		conf.setInputFormat(classOf[TextInputFormat])
		conf.setOutputFormat(classOf[TextOutputFormat[Text, IntWritable]])
		FileInputFormat.setInputPaths(conf, new Path(hadoopPrimeSource))
		FileOutputFormat.setOutputPath(conf, new Path(hadoopPrimeOutput))
		JobClient.runJob(conf)
	}
}



