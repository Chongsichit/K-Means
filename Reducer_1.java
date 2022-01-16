import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class Reducer_1 extends Reducer<IntWritable, Mapper_1.DoubleArrayWritable, Text,Text> {

    private HashMap<String,Integer> hashMap = new HashMap<>();

    @Override
    protected void reduce(IntWritable key, Iterable<Mapper_1.DoubleArrayWritable> values, Reducer<IntWritable, Mapper_1.DoubleArrayWritable, Text, Text>.Context context) throws IOException, InterruptedException {
        double[] sum = new double[20];
        String[] output = new String[20];
        double count = 0;
        String clustercorrectnum = null;

        for(double i = 0; i < 10; i++){
            hashMap.put(Double.toString(i),0);
        }

        for (Mapper_1.DoubleArrayWritable value:values){
            Writable[] writables = value.get();
            for ( int i =0; i<writables.length-2; i++){
                sum[i] += Double.parseDouble(writables[i].toString());
            }
            if(hashMap.containsKey(writables[writables.length-2].toString())){
                hashMap.put(writables[writables.length-2].toString(),hashMap.get(writables[writables.length-2].toString()) + 1);
            }
            clustercorrectnum = writables[writables.length-1].toString();
            count++;
        }

        for (int i = 0; i < sum.length ; i++){
            output[i]=Double.toString(sum[i]/count);
        }

        Set<String> key_set = hashMap.keySet();
        int max = 0;
        String themax = null;
        for (String label: key_set) {
            int test = hashMap.get(label);
            if(max < test){
                max = test;
                themax = label;
            }
        }
        int total = hashMap.get(clustercorrectnum);
        double ratio = total/count;

        context.write(new Text("Centroid " + key.toString() +":"), new Text( Arrays.toString(output)
                + ", "+clustercorrectnum+", " + count +", "+ total+ ", "+themax+", "+ratio));
    }
}
