import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import com.sun.prism.paint.Stop;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import static java.lang.Double.NaN;

public class Measure {

    public static void main(String args[]){
        System.out.println("Calculating Password scores. This might take a while.... ");

        Zxcvbn mes = new Zxcvbn();

        ArrayList<String> allPw = getPasswordList("C:\\Users\\Leuma\\IdeaProjects\\zxcvbnMeasure1\\passwords\\rockyou.txt");


        ArrayList<Integer> allPwStr = new ArrayList<>();

        System.out.println("[STATUS]: Finished adding all passwords into a list!");

        for (String pw: allPw) {
            int x = (int) mes.measure(pw).getGuessesLog10();
            allPwStr.add(x);
        }

        System.out.println("[STATUS] Finished calculating all password scores!");

        int[] arr = allPwStr.stream().mapToInt(Integer::intValue).toArray();
        System.out.println(arr.length);


            System.out.println("MEAN: " + getMean(arr));
            System.out.println("STANDARD DEV: " + getStdDev(arr));



    }

    public static ArrayList<String> getPasswordList(String filePath){
        ArrayList<String> allPw = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                allPw.add(line.trim());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allPw;
    }

    public static int getMean(int[] data) {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return (int) sum/data.length;
    }


    public static int getVariance(int[] data) {
        double mean = getMean(data);
        double temp = 0;
        for(int a :data)
            temp += (a-mean)*(a-mean);
        return (int) temp/(data.length-1);
    }

    public static double getStdDev(int[] data) {
        return (int) Math.sqrt(getVariance(data));
    }
}
