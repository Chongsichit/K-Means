import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class Mapper_1 extends Mapper<LongWritable, Text,IntWritable, Mapper_1.DoubleArrayWritable> {

    HashMap<Integer,Double[]> centroids = new HashMap<>();

    @Override
    protected void setup(Mapper<LongWritable, Text, IntWritable, DoubleArrayWritable>.Context context) throws IOException, InterruptedException {
        Path[] label = context.getLocalCacheFiles();
        FileReader fileReader = new FileReader(String.valueOf(label[0]));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String item;
        while((item = bufferedReader.readLine())!= null){

            String[] split = item.split(":");
            String id = split[0].substring(9,10);
            String[] without_number = split[1].split("]");
            String[] data = without_number[0].substring(2).split(",");
            String correctnumber = without_number[1].substring(2,5);
            Double[] temp = new Double[data.length+1];
            for (int i = 0; i < data.length ; i++){
                temp[i]=Double.parseDouble(data[i]);
            }
            temp[temp.length-1]=Double.parseDouble(correctnumber);
            centroids.put(Integer.parseInt(id),temp);

        }

        bufferedReader.close();
        fileReader.close();
    }

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, IntWritable, DoubleArrayWritable>.Context context) throws IOException, InterruptedException {
        String[] split = value.toString().split("\\?"); //value = 序號:數據 數據 數據
        String correct_numberforpixel = split[0];
        int closet_centroid = -1; //把最鄰近的centroid的id保存下來
        String[] split2 = split[1].split(" "); //獲取像素數據
        Double[] pixel = new Double[split2.length+2]; //把String數組轉成Double數組，方便計算
        double inf = Double.POSITIVE_INFINITY; //假設一開始的距離是無限大
        double sum = 0; //保存對象和的Centroid之間的差的平方
        double correctnumber = 0;

        for( int i = 0; i < split2.length ; i++){
            pixel[i] = Double.parseDouble(split2[i]); //把String數組轉成Double數組
        }

        Set<Integer> keySet = centroids.keySet(); //獲得預設的centroids的keySet

        for (int id: keySet){
            Double[] temp = centroids.get(id); //獲得預設的centroid的像素數據

            for(int i = 0; i<temp.length-1 ; i++){
                double square = Math.pow(pixel[i]-temp[i],2);
                sum += square;
            }
            double sum_root = Math.sqrt(sum);

            if (sum_root <= inf){ //判斷和這次循環的centroid的距離是否為最小的距離
                inf = sum_root;
                closet_centroid = id;
                correctnumber = temp[temp.length-1];
            }
            sum = 0;
        }

        pixel[pixel.length-2] = Double.parseDouble(correct_numberforpixel);
        pixel[pixel.length-1] = correctnumber;

        context.write(new IntWritable(closet_centroid), new DoubleArrayWritable(pixel));
    }

    static class DoubleArrayWritable extends ArrayWritable {

        public DoubleArrayWritable(){
            super(DoubleWritable.class);
        }

        public DoubleArrayWritable(Double[] doubles){
            super(DoubleWritable.class);
            DoubleWritable[] doubleWritables = new DoubleWritable[doubles.length];
            for ( int i = 0; i < doubleWritables.length ; i++){
                doubleWritables[i] = new DoubleWritable(doubles[i]);
            }
            set(doubleWritables);
        }
    }
}
