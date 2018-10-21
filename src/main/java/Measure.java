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

    private static final String FILE_PATH = "CHANGEME";
    public static void main(String args[]){
        System.out.println("Calculating Password scores. This might take a while.... ");
        final double AVG_MEASUREMENT = 7;
        int aboveAvg = 0;

        Zxcvbn mes = new Zxcvbn();

        ArrayList<String> allPw = getPasswordList(FILE_PATH);


        ArrayList<Double> allPwStr = new ArrayList<>();

        System.out.println("[STATUS]: Finished adding all passwords into a list!");
        double weakest = Integer.MAX_VALUE;
        double strongest = Integer.MIN_VALUE;


        for (String pw: allPw) {
            double x = mes.measure(pw).getGuessesLog10();
            if(x > strongest){
                strongest = x;
            }

            if(weakest > x){
                weakest = x;
            }

            if(x > AVG_MEASUREMENT){
                aboveAvg = aboveAvg + 1;
            }

            if(x > 0.01) allPwStr.add(x);

        }

        System.out.println("[STATUS] Finished calculating all password scores!");

        double[] arr = allPwStr.stream().mapToDouble(Double::doubleValue).toArray();
        System.out.println(arr.length);


            System.out.println("MEAN: " + getMean(arr));
            System.out.println("STANDARD DEV: " + getStdDev(arr));
            System.out.println("Strongest Password: " + strongest);
            System.out.println("Weakest Password: " + weakest);
            System.out.println("Passwords above the average: " + aboveAvg);




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

    public static double getMean(double[] data) {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return (int) sum/data.length;
    }


    public static int getVariance(double[] data) {
        double mean = getMean(data);
        double temp = 0;
        for(double a :data)
            temp += (a-mean)*(a-mean);
        return (int) temp/(data.length-1);
    }

    public static double getStdDev(double[] data) {
        return Math.sqrt(getVariance(data));
    }
}
