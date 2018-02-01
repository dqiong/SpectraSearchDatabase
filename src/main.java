import peptide.*;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import spectra.ReadSpectra;
import spectra.Spectra;
import utils.FilePath;
import utils.IonMass;
import search.SearchMethod;

/**
 * Created by liang on 2017/12/7.
 */
public class main {

    public static String GetNowDate(){
        String temp_str="";
        Date dt = new Date();
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");
        temp_str=sdf.format(dt);
        return temp_str;
    }

    public static void main(String[] args)
    {

        System.out.println(GetNowDate());
        PeptideIndex peindex=new PeptideIndex();
        peindex.init(FilePath.PEPTIDE_PATH);
        System.out.println("read peptide!"+peindex.getAllLinkedPeptide().size());
        System.out.println(GetNowDate());

        ReadSpectra rs=new ReadSpectra();
        rs.doRead(FilePath.SPECTRA_PATH);
        System.out.println("read spectra!"+rs.getAllSpectra().size());
        System.out.println(GetNowDate());

//        Iterator iiii = peindex.getAllLinkedPeptide().iterator();
//        while(iiii.hasNext())
//        {
//            LinkedPeptide LinkedPeptide = (LinkedPeptide) iiii.next();
//            System.out.println(LinkedPeptide.getPeptideOne().getName() + " " + LinkedPeptide.getPeptideTwo().getName()
//             + " " + LinkedPeptide.getParentMass());
//        }


        SearchMethod sm=new SearchMethod();
       // sm.spectraSearchDatabase(peindex.getAllLinkedPeptide(),peindex.getPeptideIndex(),rs.getAllSpectra());
        sm.spectraSearchDatabase(peindex.getAllLinkedPeptide(),rs.getAllSpectra());

        try{
            FileWriter fw=new FileWriter(new File(FilePath.RESULT_PATH));
            for(int i=0;i<sm.getResultMatch().size();i++){
                String str= sm.getResultMatch().get(i).toString();
                fw.write(str);
            }
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("result size:"+sm.getResultMatch().size());
        System.out.println(GetNowDate());
    }
}
