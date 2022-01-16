import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class createCentroids {

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\ASUS\\Desktop\\mnist\\finalData");
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line=null;
        int count = 0;
        String[] output = new String[60000];
        while((line = bufferedReader.readLine()) != null){
            output[count] = line;
            count++;
        }

        String[]centroids = new String[10];
        String[]correctID = new String[10];

        int cnt = 0;
        Set<Integer> test = new HashSet<>();

        while(cnt<10){
            int length = output.length;
            int index = (int) (Math.random()*length);
            if(!test.contains(index)){
                test.add(index);
                cnt++;
            }
        }

        int i =0;

        for(int key: test){
            String[] split = output[key].split("\\?");
            String[] split2 = split[1].split(" ");
            System.out.println(Arrays.toString(split2));
            centroids[i] = Arrays.toString(split2);
            correctID[i] = split[0];
            i++;
        }

        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\ASUS\\Desktop\\mnist\\centroid_3");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        for ( int j = 0; j < centroids.length ; j++){
            bufferedWriter.write("Centroid "+j + ": ");
            bufferedWriter.write(centroids[j] +", "+correctID[j] + ", "+0.0+", "+0.0+", "+0.0);//correctnumber,count,maxnumber,ratio
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }

    }

}
