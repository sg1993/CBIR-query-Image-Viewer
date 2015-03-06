import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.LazyOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class CBIRQueryDriver {
	// private static Log logger = LogFactory
	// .getLog(BinaryFilesToHadoopSequenceFile.class);

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {

		if (args.length < 2) {
			System.out.println("Usage: <jar file> <Query filename>");
			System.exit(0);
		}

		Configuration conf = new Configuration();
		//conf.set("BASE_OUTPUT_FILE_NAME", baseOutputName);
		//conf.set("NUM_REDUCERS", Integer.toString(numOutputFiles));
		Job job = Job.getInstance(conf);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(CBIRQueryMapper.class);
		//job.setReducerClass(SequenceFileToImageReducer.class);

		job.setInputFormatClass(TextInputFormat.class);
		//job.setOutputFormatClass(TextOutputFormat.class);
		//job.setNumReduceTasks(numOutputFiles);
		//LazyOutputFormat.setOutputFormatClass(job,
		//		SequenceFileOutputFormat.class);
		//job.setPartitionerClass(SequenceFilePartitioner.class);
		
		for (int i = 0; i < args.length - 1; i++) {
			// FileInputFormat.setInputPaths(job, new Path(args[i]));
			MultipleInputs.addInputPath(job, new Path(args[i]),
					TextInputFormat.class);
		}
		job.setJarByClass(CBIRQueryDriver.class);
		FileOutputFormat.setOutputPath(job, new Path(args[args.length - 1]));
		job.submit();
	}
}