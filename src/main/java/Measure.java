import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Measure {

    public static void main(String args[]){
        System.out.println("test");

        Zxcvbn mes = new Zxcvbn();

        ArrayList<String> allPw = getPasswordList("D:\\rockyou\\pw.txt");

        ArrayList<Double> allPwStr = new ArrayList<>();

        for (String pw: allPw) {
            allPwStr.add(mes.measure(pw).getGuessesLog10());
        }

        double[] arr = allPwStr.stream().mapToDouble(Double::doubleValue).toArray();

        Statistics stats = new Statistics(arr);

        System.out.println("MEAN: " + stats.getMean());
        System.out.println("STANDARD DEV: " + stats.getStdDev());




    }

    public static ArrayList<String> getPasswordList(String filePath){
        ArrayList<String> allPw = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                allPw.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allPw;
    }
}
