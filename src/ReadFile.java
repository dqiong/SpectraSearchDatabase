import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liang on 2017/12/7.
 */

public class ReadFile {
    public List<Peptide> doRead(String filePath){
        List<Peptide> allPeptide = new ArrayList<>();
        File file = new File(filePath);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String temp = bufferedReader.readLine();
            while(temp != null)
            {
                String [] stringArray = temp.split("\\s+");
                String peptideName = stringArray[0];
                Integer peptideCutPoint = Integer.parseInt(stringArray[1]);
                Peptide peptide = new Peptide();
                peptide.setName(peptideName);peptide.setCutPoint(peptideCutPoint);
                allPeptide.add(peptide);
                //System.out.println(peptideName + " " + peptideCutPoint);
                temp = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allPeptide;
    }

}
