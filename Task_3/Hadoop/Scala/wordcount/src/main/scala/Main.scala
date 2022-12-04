
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.Mapper
import org.apache.hadoop.mapreduce.Reducer
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.util.GenericOptionsParser
import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.ArrayOps



// This class configures and runs the job with the map and reduce classes we've
// specified above.
@throws[Exception]
object WordCount {

  def main(args:Array[String]):Unit = {
    val conf = new Configuration()
	  val hadoopWikiSource = "/home/ubuntu/wiki/wiki.txt";
    val hadoopWikiOutput = "/home/ubuntu/out/wikiScalaHadoop.out";

    val job = new Job(conf, "word count")
    job.setJarByClass(classOf[TokenizerMapper])
    job.setMapperClass(classOf[TokenizerMapper])
    job.setCombinerClass(classOf[IntSumReducer])
    job.setReducerClass(classOf[IntSumReducer])
    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[IntWritable])
    FileInputFormat.addInputPath(job, new Path(hadoopWikiSource))
    FileOutputFormat.setOutputPath(job, new Path((hadoopWikiOutput)))
    job.waitForCompletion(true)
  }

  // This class performs the map operation, translating raw input into the key-value
// pairs we will feed into our reduce operation.
class TokenizerMapper extends Mapper[Object,Text,Text,IntWritable] {
    import WordCount._
    val one = new IntWritable(1)
    val word = new Text
    
    override
    def map(key:Object, value:Text, context:Mapper[Object,Text,Text,IntWritable]#Context) = {
      
      if (value != null && context != null)
      {
        var line = value.toString
        var lList = line.split(" ")

        if(line != null)
        {
          lList.foreach
          {
            token =>
              word.set(token)
              context.write(word, one)
          }
          System.console().writer().println(line);
        }
        
      }
      
    }
  }
    
// This class performs the reduce operation, iterating over the key-value pairs
// produced by our map operation to produce a result. In this case we just
// calculate a simple total for each word seen.
class IntSumReducer extends Reducer[Text,IntWritable,Text,IntWritable] {
    import WordCount._

    override
    def reduce(key:Text, values:java.lang.Iterable[IntWritable], context:Reducer[Text,IntWritable,Text,IntWritable]#Context) = {
      var iList = new ListBuffer[IntWritable]()

      for(itemy <- values)
      {
        iList += itemy
      }

      //val sum = iList.toList.foldLeft(0) { (t,i) => t + i.get }
      
      context.write(key, new IntWritable(100))
    }
  }
  // class Map extends MapReduceBase with Mapper[LongWritable, Text, Text, IntWritable] 
	// {
	// 	val one = new IntWritable(1)
  // 	val word = new Text
  
	// 	@throws[IOException]
	// 	def map(
	// 		key: LongWritable,
	// 		value: Text,
	// 		output: OutputCollector[Text, IntWritable],
	// 		reporter: Reporter): Unit = 
	// 		{
	// 			val line: String = value.toString
  //         line.split(" ").foreach { token =>
  //           word.set(token)
  //           output.collect(word, one)
  //         }
	// 		}
	// }

	// class Reduce extends MapReduceBase with Reducer[Text, IntWritable, Text, IntWritable]
	// {
	// 	override
	// 	def reduce(
	// 		key: Text,
	// 		values: Iterator[IntWritable],
	// 		output: OutputCollector[Text, IntWritable],
	// 		reporter: Reporter): Unit = 
	// 		{
  //       var lList = new ListBuffer[IntWritable]()  
  //       for (vl <- values) {
  //           lList += vl
  //       }

	// 			val sum = lList.toList.reduce((valueOne, valueTwo) =>
  //         new IntWritable(valueOne.get() + valueTwo.get()))

  //       output.collect(key, sum)
	// 		}
	// }

	// @throws[Exception]
	// def main(args: Array[String]): Unit = {

	// 	val hadoopWikiSource = "/home/ubuntu/wiki/wiki.txt";
  //   val hadoopWikiOutput = "/home/ubuntu/out/wikiScalaHadoop.out";

	// 	val conf: JobConf = new JobConf(this.getClass)
	// 	conf.setJobName("wordcountScalaHadoop")
	// 	conf.setOutputKeyClass(classOf[Text])
	// 	conf.setOutputValueClass(classOf[IntWritable])
	// 	conf.setMapperClass(classOf[Map])
	// 	//conf.setCombinerClass(classOf[Reduce])
	// 	conf.setReducerClass(classOf[Reduce])
	// 	conf.setInputFormat(classOf[TextInputFormat])
	// 	conf.setOutputFormat(classOf[TextOutputFormat[Text, IntWritable]])
	// 	FileInputFormat.setInputPaths(conf, new Path(hadoopWikiSource))
	// 	FileOutputFormat.setOutputPath(conf, new Path(hadoopWikiOutput))
	// 	JobClient.runJob(conf)
	// }

}