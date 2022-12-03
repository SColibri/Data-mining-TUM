package com.prime;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;

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
/**
 * Original code can be found online, sadly I forgot to paste the reference. The code
 * was adapted to our setup.
 */
public class App 
{
	// we ignore input in this class, but you can add the input manually if you want
    public static void main(String[] args) throws Exception {
		// -----------------------------------------------
		// 					FILE PATHS
		// -----------------------------------------------
		String primeNumbersSource = "/home/ubuntu/resources/primeNumbers.txt";
		String hadoopPrimeSource = "/home/ubuntu/input/primeNumbers.txt";
		String hadoopPrimeOutput = "/home/ubuntu/out/primeNumbersScala.out";
		String hadoopAddress = "hdfs://192.168.4.83:9000";

		// -----------------------------------------------
		// 				CREATE NUMBER LIST
		// -----------------------------------------------
		
		// if file does not exist, we create one, this takes 
		// some time, so eat something or watch a movie
		create_primeNumberList(primeNumbersSource);

		// -----------------------------------------------
		// 					JOB SETUP
		// -----------------------------------------------

		// create new hadoop configuration object
		Configuration conf = new Configuration();

		// create new hadoop mapreduce job
		Job job = new Job(conf, "Primes");
		job.setJarByClass(App.class);

		// 
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(IntWritable.class);

		job.setMapperClass(PrimesMap.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(hadoopPrimeSource));
		FileOutputFormat.setOutputPath(job, new Path(hadoopPrimeOutput));

		// copy input file into hadoop filesystem
		check_ifInputExists(hadoopAddress, 
							primeNumbersSource, 
							hadoopPrimeOutput, 
							hadoopPrimeSource, 
							conf);

		job.waitForCompletion(true);
	}

	// Extend the Mapper class
	public static final class PrimesMap extends Mapper<LongWritable, Text, NullWritable, IntWritable> {
		final NullWritable nw = NullWritable.get();

		public final void map(final LongWritable key, final Text value, final Context context)
				throws IOException, InterruptedException {
			if (value == null) return;
			if (value.getLength() == 0) return;
			
			try {
				final int number = Integer.parseInt(value.toString());
				if(isPrime(number)) {
					context.write(nw, new IntWritable(number));
				}
			} catch (Exception e) {
				// just ignore this entry
				return;
			}
			
		}
	}

	// Check if number is prime
	private static final boolean isPrime(final int number) {
		if (number == 1) {
			return false;
		}
		if (number % 2 == 0 && number != 2 || number % 3 == 0 && number != 3) {
			return false;
		}
		int limit = (int) ((Math.pow(number, 0.5) + 1) / 6.0 + 1);
		for (int i = 1; i < limit; i++) {
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

	/* 
	 * Creates a file that contains 1E9 numbers
	*/
	private static void create_primeNumberList(String filename)
	{
		try {
			
			File fileObject = new File(filename);
			if(fileObject.exists()) return;

			BufferedWriter bufwriter = new BufferedWriter(new FileWriter(filename));
			String toWriter;
			

			int bufferSize = 500;
			for (int i = 0; i < 1E9; i += bufferSize) 
			{
				toWriter = "";
				
				// We write to hadoop filesystem after each
				// interval, since memory access is costly
				// but we can do better using char[] instead
				// of string.
				for (int j = 0; j < bufferSize; j++) 
				{
					toWriter += ( i + j ) + "\n";
				}
				
				bufwriter.write(toWriter);
			}

			bufwriter.close();//closes the file
		} catch (Exception e) {
			// do nothing
			return;
		}
	}

	private static void check_ifInputExists(String hadoopAddress, String primeNumbersSource, String hadoopPrimeOutput, String hadoopPrimeSource, Configuration conf)
	{
		try {
			// local filename
			InputStream inputStream = new BufferedInputStream(new FileInputStream(primeNumbersSource));
			
			//hdfs instance
			FileSystem hdfs = FileSystem.get(new URI(hadoopAddress), conf);
			
			// Delete previous folder bc hadoop does not like to overwrite stuff?
			Path output = new Path(hadoopAddress + hadoopPrimeOutput);
			if (hdfs.exists(output)) {
				hdfs.delete(output, true);
			}

			//copy the input file into hadoop ~9.3GB
			OutputStream outputStream = hdfs.create(new Path(hadoopAddress + hadoopPrimeSource),
			new Progressable() {  
					@Override
					public void progress() {
				System.out.println("....");
					}
			});
			
			try
			{
				IOUtils.copyBytes(inputStream, outputStream, 4096, false); 
			}
			finally
			{
				IOUtils.closeStream(inputStream);
				IOUtils.closeStream(outputStream);
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

