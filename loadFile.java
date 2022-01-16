import java.io.*;

public class loadFile {

    public static final String TRAIN_IMAGES_FILE = "C:\\Users\\ASUS\\Desktop\\mnist\\train-images.idx3-ubyte";
    public static final String TRAIN_LABELS_FILE = "C:\\Users\\ASUS\\Desktop\\mnist\\train-labels.idx1-ubyte";
    public static final String TEST_IMAGES_FILE = "C:\\Users\\ASUS\\Desktop\\mnist\\t10k-images.idx3-ubyte";
    public static final String TEST_LABELS_FILE = "C:\\Users\\ASUS\\Desktop\\mnist\\t10k-labels.idx1-ubyte";

    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static double[][] getImages(String fileName) {
        double[][] x = null;
        try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName))) {
            byte[] bytes = new byte[4];
            bin.read(bytes, 0, 4);
            if (!"00000803".equals(bytesToHex(bytes))) {                        // 读取魔数
                throw new RuntimeException("Please select the correct file!");
            } else {
                bin.read(bytes, 0, 4);
                int number = Integer.parseInt(bytesToHex(bytes), 16);           // 读取样本总数
                bin.read(bytes, 0, 4);
                int xPixel = Integer.parseInt(bytesToHex(bytes), 16);           // 读取每行所含像素点数
                bin.read(bytes, 0, 4);
                int yPixel = Integer.parseInt(bytesToHex(bytes), 16);           // 读取每列所含像素点数
                x = new double[number][xPixel * yPixel];
                for (int i = 0; i < number; i++) {
                    double[] element = new double[xPixel * yPixel];
                    for (int j = 0; j < xPixel * yPixel; j++) {// 逐一读取像素值
                        element[j] = bin.read() / 255.0;
                    }
                    x[i] = element;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return x;
    }

    public static double[] getLabels(String fileName) {
        double[] y = null;
        try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName))) {
            byte[] bytes = new byte[4];
            bin.read(bytes, 0, 4);
            if (!"00000801".equals(bytesToHex(bytes))) {
                throw new RuntimeException("Please select the correct file!");
            } else {
                bin.read(bytes, 0, 4);
                int number = Integer.parseInt(bytesToHex(bytes), 16);
                y = new double[number];
                for (int i = 0; i < number; i++) {
                    y[i] = bin.read();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return y;
    }

    public static void outputBuffer(double[][] images) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\ASUS\\Desktop\\mnist\\test_pixel");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        double pixel = 0;
        for (int i = 0; i < images.length ; i++){
            bufferedWriter.write(i + ":");
            for (int j = 0; j < images[i].length ; j++){
                pixel = images[i][j];
                bufferedWriter.write(  Double.toString(pixel) + " ");
            }
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }

    public static void outputLabel(double[] labels) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\ASUS\\Desktop\\mnist\\test_label");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        for (int i = 0; i< labels.length; i++){
            bufferedWriter.write(i+":"+Double.toString(labels[i]));
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }

    public static void main(String[] args) throws IOException {
        double[][] images = getImages(TEST_IMAGES_FILE);
        double[] labels = getLabels(TRAIN_LABELS_FILE);
        outputBuffer(images);
        outputLabel(labels);
        System.out.println();
    }
}


