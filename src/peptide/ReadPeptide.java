package peptide;
import utils.FilePath;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

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
        File excelFile=new File(filePath);
        try{
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(excelFile));
            XSSFSheet sheet = wb.getSheetAt(1);
            Boolean is_head=false;
            for(Row row:sheet){
                if(!is_head){
                    is_head=true;
                    continue;
                }
                Cell cell=row.getCell(3);
                String peptide_name=cell.getStringCellValue();
                Peptide peptide = new Peptide();
                peptide.setName(peptide_name);
                allPeptide.add(peptide);
               // System.out.println(peptide.getName());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allPeptide;
    }
    /*
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
    */
    public static void main(String[] args)
    {
        ReadPeptide rp=new ReadPeptide();
        List<Peptide> allPeptide=rp.doRead(FilePath.PEPTIDE_PATH);
    }
}