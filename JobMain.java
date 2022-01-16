import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.util.Arrays;

public class JobMain extends Configured implements Tool {

    @Override
    public int run(String[] strings) throws Exception {
        Job job = Job.getInstance(super.getConf(), "HW4_Q2_1");
        job.setJarByClass(Mapper_1.class);
        FileInputFormat.addInputPath(job, new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155164831/finalData"));
        //job.addCacheFile(new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155164831/centroid_Q2C3").toUri());
        job.addCacheFile(new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155164831/centroid_1").toUri());
        job.setInputFormatClass(TextInputFormat.class);
        job.setMapperClass(Mapper_1.class);
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(Mapper_1.DoubleArrayWritable.class);
        job.setReducerClass(Reducer_1.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job, new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155164831/HW4_Q2_1"));

        job.waitForCompletion(true);

        Job job1 = Job.getInstance(super.getConf(), "HW3_Q2A_1");
        job1.setJarByClass(Mapper_1.class);
        FileInputFormat.addInputPath(job1, new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155164831/finalData"));
        //job.addCacheFile(new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155164831/centroid").toUri());
        job1.addCacheFile(new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155164831/HW4_Q2_1/part-r-00000").toUri());
        job1.setInputFormatClass(TextInputFormat.class);
        job1.setMapperClass(Mapper_1.class);
        job1.setMapOutputKeyClass(IntWritable.class);
        job1.setMapOutputValueClass(Mapper_1.DoubleArrayWritable.class);
        job1.setReducerClass(Reducer_1.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(Text.class);
        job1.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job1, new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155164831/HW4_Q2_2"));

        job1.waitForCompletion(true);

        Job job2 = Job.getInstance(super.getConf(), "HW3_Q2A_1");
        job2.setJarByClass(Mapper_1.class);
        FileInputFormat.addInputPath(job2, new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155164831/finalData"));
        //job.addCacheFile(new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155164831/centroid").toUri());
        job2.addCacheFile(new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155164831/HW4_Q2_2/part-r-00000").toUri());
        job2.setInputFormatClass(TextInputFormat.class);
        job2.setMapperClass(Mapper_1.class);
        job2.setMapOutputKeyClass(IntWritable.class);
        job2.setMapOutputValueClass(Mapper_1.DoubleArrayWritable.class);
        job2.setReducerClass(Reducer_1.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(Text.class);
        job2.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job2, new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155164831/HW4_Q2_3"));

        return (job2.waitForCompletion(true)? 0:1);
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        int run = ToolRunner.run(conf,new JobMain(),args);
        System.exit(run);
    }
}
