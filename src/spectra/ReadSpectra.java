package spectra;

import peptide.Peptide;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: qiong
 * \* Date: 2017/12/7
 * \* Time: 21:30
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class ReadSpectra {

    private List<Spectra> allSpectra=new ArrayList<>();

    public List<Spectra> getAllSpectra() {
        return allSpectra;
    }

    public void setAllSpectra(List<Spectra> allSpectra) {
        this.allSpectra = allSpectra;
    }

    public void doRead(String filePath){
        File file = new File(filePath);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String temp = bufferedReader.readLine();
            List<Peak> peaks= new ArrayList<>();
            double parentMass=0.0;
            while(temp != null)
            {

                if (temp.split("=")[0].equals("PARENT_MASS")){
                    String [] stringArray = temp.split("=");
                    parentMass=Double.parseDouble(stringArray[1]);
                }
                else if(temp.split("\t").length>1){
                    String [] tempPeak= temp.split("\t");
                    Peak peak=new Peak();
                    peak.setMass(Double.parseDouble(tempPeak[0]));
                    peak.setIntensity(Double.parseDouble(tempPeak[1]));
                    peaks.add(peak);
                }
                else if(temp.equals("END IONS")){
                    Spectra spec= new Spectra();
                    spec.setParentMass(parentMass);
                    spec.setPeaks(peaks);
                    allSpectra.add(spec);
                    parentMass=0.0;
                    peaks.clear();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}