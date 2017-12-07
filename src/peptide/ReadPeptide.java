package peptide;

import peptide.Peptide;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: qiong
 * \* Date: 2017/12/7
 * \* Time: 20:34
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class ReadPeptide {
    public List<Peptide> doRead(String filePath){
        List<Peptide> allPeptide = new ArrayList<>();
        File file = new File(filePath);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String temp = bufferedReader.readLine();
            while(temp != null)
            {
                String [] stringArray = temp.split("\t");
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