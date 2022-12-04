package com.prime
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Progressable;
import javax.naming.Context

class PrimesMap extends Mapper[LongWritable, Text, Text, IntWritable]
{
	final var nw = new Text

	@throws[IOException]
	override def map(key: LongWritable, value: Text, context:Mapper[LongWritable,Text,Text,IntWritable]#Context) = {
		var number = Integer.parseInt(value.toString());
		if(isPrime(number)) {
			context.write(nw, new IntWritable(number));
		}
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


object App
{
	def main(args: Array[String]) : Unit =
	{
		// -----------------------------------------------
		// 					FILE PATHS
		// -----------------------------------------------
		val primeNumbersSource = "/home/ubuntu/resources/primeNumbers.txt";
		val hadoopPrimeSource = "/home/ubuntu/input/primeNumbers.txt";
		val hadoopPrimeOutput = "/home/ubuntu/out/primeNumbersScala.out";
		val hadoopAddress = "hdfs://192.168.4.83:9000";

		// -----------------------------------------------
		// 					JOB SETUP
		// -----------------------------------------------

		// create new hadoop configuration object
		var conf = new Configuration();

		// create new hadoop mapreduce job
		val job = new Job(conf, "Primes");
		job.setJarByClass(classOf[PrimesMap]);

		job.setOutputKeyClass(classOf[Text]);
		job.setOutputValueClass(classOf[IntWritable]);

    	job.setMapperClass(classOf[PrimesMap]);

		//job.setInputFormatClass(classOf[TextInputFormat]);
		//job.setOutputFormatClass(classOf[TextOutputFormat[_, _]]);

		FileInputFormat.addInputPath(job, new Path(hadoopPrimeSource));
		FileOutputFormat.setOutputPath(job, new Path(hadoopPrimeOutput));

		job.waitForCompletion(true)
  	}
}



