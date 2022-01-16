import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MergingFile {
    public static void main(String[] args) throws IOException {
        String path1 = "C:\\Users\\ASUS\\Desktop\\mnist\\test_label";
//        String path2 = "C:\\Users\\ASUS\\Desktop\\mnist\\test_pixel";
        String path2 = "C:\\Users\\ASUS\\Desktop\\mnist\\pixel_2";
        String path3 = "C:\\Users\\ASUS\\Desktop\\mnist\\finalData";
//        String path4 = "C:\\Users\\ASUS\\Desktop\\mnist\\final_training";
//        String path5 = "C:\\Users\\ASUS\\Desktop\\mnist\\final";
//        String path6 = "C:\\Users\\ASUS\\Desktop\\mnist\\final_shuffle";
//        String path7 = "C:\\Users\\ASUS\\Desktop\\mnist\\final_shuffle_5_Test";
//        String path8 = "C:\\Users\\ASUS\\Desktop\\mnist\\final_shuffle_5_Train";
        readFileintoHM(path1,path2,path3);
//        mergeFile(path3,path4,path5);
//        shuffleFile(path5,path6);
//        splitFile(path6,path7,path8,5,5);
    }

    public static void readFileintoHM(String path1, String path2, String path3) throws IOException {
        FileInputStream fileInputStream1 = new FileInputStream(path1); //label
        InputStreamReader inputStreamReader1 = new InputStreamReader(fileInputStream1);
        BufferedReader bufferedReader1 = new BufferedReader(inputStreamReader1);

        FileInputStream fileInputStream2 = new FileInputStream(path2); //pixel
        InputStreamReader inputStreamReader2 = new InputStreamReader(fileInputStream2);
        BufferedReader bufferedReader2 = new BufferedReader(inputStreamReader2);

        FileOutputStream fileOutputStream = new FileOutputStream(path3);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        String line1;
        String line2 = null;
        while ((line1 = bufferedReader1.readLine())!=null&&(line2 = bufferedReader2.readLine())!=null){
            String[] split = line1.split(":");
            String label = split[1];
            String[] st = line2.split(":");
            String data = st[1];
            bufferedWriter.write(label+"?"+data);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }

    public static void mergeFile(String path1, String path2, String path3) throws IOException {
        FileInputStream fileInputStream1 = new FileInputStream(path1);
        InputStreamReader inputStreamReader1 = new InputStreamReader(fileInputStream1);
        BufferedReader bufferedReader1 = new BufferedReader(inputStreamReader1);

        FileInputStream fileInputStream2 = new FileInputStream(path2);
        InputStreamReader inputStreamReader2 = new InputStreamReader(fileInputStream2);
        BufferedReader bufferedReader2 = new BufferedReader(inputStreamReader2);

        FileOutputStream fileOutputStream = new FileOutputStream(path3);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        String line1;
        String line2;

        while ((line1 = bufferedReader1.readLine()) != null){
            bufferedWriter.write(line1);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }

        while ((line2 = bufferedReader2.readLine()) != null){
            bufferedWriter.write(line2);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }

    public static void shuffleFile(String path1, String path2) throws IOException {
        FileInputStream fileInputStream1 = new FileInputStream(path1);
        InputStreamReader inputStreamReader1 = new InputStreamReader(fileInputStream1);
        BufferedReader bufferedReader1 = new BufferedReader(inputStreamReader1);

        FileOutputStream fileOutputStream = new FileOutputStream(path2);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        List<String> shuffle = new ArrayList<>();
        String line;
        while((line = bufferedReader1.readLine())!= null){
            shuffle.add(line);
        }
        Collections.shuffle(shuffle);
        for (int i = 0; i < shuffle.size() ; i++){
            bufferedWriter.write(shuffle.get(i));
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }

    public static void splitFile(String path1, String path2, String path3, int split, int whichpart) throws IOException {
        FileInputStream fileInputStream1 = new FileInputStream(path1);
        InputStreamReader inputStreamReader1 = new InputStreamReader(fileInputStream1);
        BufferedReader bufferedReader1 = new BufferedReader(inputStreamReader1);

        FileOutputStream fileOutputStream1 = new FileOutputStream(path2); //test
        OutputStreamWriter outputStreamWriter1 = new OutputStreamWriter(fileOutputStream1);
        BufferedWriter bufferedWriter1 = new BufferedWriter(outputStreamWriter1);

        FileOutputStream fileOutputStream2 = new FileOutputStream(path3); //train
        OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(fileOutputStream2);
        BufferedWriter bufferedWriter2 = new BufferedWriter(outputStreamWriter2);

        List<String> file = new ArrayList<>();
        String line;
        while ((line=bufferedReader1.readLine())!=null){
            file.add(line);
        }
        int size = file.size()/split;
        int startpoint = size*whichpart;
        int endpoint = size*(whichpart+1);
        for(int i =0; i<file.size(); i++){
            if((i>=startpoint)&&(i<endpoint)){
                bufferedWriter1.write(file.get(i));
                bufferedWriter1.newLine();
                bufferedWriter1.flush();
            } else {
                bufferedWriter2.write(file.get(i));
                bufferedWriter2.newLine();
                bufferedWriter2.flush();
            }
        }
    }

}
