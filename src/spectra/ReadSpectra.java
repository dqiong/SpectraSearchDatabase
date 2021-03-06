package spectra;

import utils.BaseMass;
import utils.FilePath;

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
            double parentMass=0.0;
            double charge=1.0;
            StringBuffer title=new StringBuffer("");
            int count=0;
            while(temp != null)
            {
                count++;
                if(temp.split("=")[0].equals("TITLE")){
                    title.append(temp.split("=")[1]);
                    temp = bufferedReader.readLine();

                }
                if(temp.split("=")[0].equals("CHARGE")){
                    String stringTemp = temp.split("=")[1].split("\\+")[0];
                    charge=Double.parseDouble(stringTemp);
                    temp = bufferedReader.readLine();

                }
                if (temp.split("=")[0].equals("PEPMASS")){
                    String [] stringArray = temp.split("=");
                    double pepMass=Double.parseDouble(stringArray[1]);
                    parentMass=(pepMass*charge)-((charge-1)* BaseMass.PROTON_MASS);

                    List<Peak> peaks= new ArrayList<>();
                    temp = bufferedReader.readLine();
                    while(temp!=null && temp.split("\t").length>1){
                        String [] tempPeak= temp.split("\t");
                        Peak peak=new Peak();
                        double peakMass=Double.parseDouble(tempPeak[0]);
                        double peakCharge=Double.parseDouble(tempPeak[2]);

                        peak.setMass((peakMass*peakCharge)-((peakCharge-1)*BaseMass.PROTON_MASS));
                        peak.setIntensity(Double.parseDouble(tempPeak[1]));
                        peak.setIsMatched(false);
                        peaks.add(peak);
                        temp = bufferedReader.readLine();
                    }
                    Spectra spec= new Spectra();
                    spec.setParentMass(parentMass);
                    spec.setPeaks(peaks);
                    spec.setNumber(count);
                    spec.setTitle(title.toString());
                    allSpectra.add(spec);

                    title.delete(0,title.length());
                    charge=1.0;
                }
                temp = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        ReadSpectra rs=new ReadSpectra();
        rs.doRead(FilePath.SPECTRA_PATH);
        System.out.println(rs.getAllSpectra().size());
    }
}