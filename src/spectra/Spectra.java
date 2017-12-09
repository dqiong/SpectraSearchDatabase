package spectra;

import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: qiong
 * \* Date: 2017/12/7
 * \* Time: 21:17
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class Spectra {
    private double parentMass;
    private List<Peak> peaks=new ArrayList<>();

    public void setParentMass(double parentMass) {
        this.parentMass = parentMass;
    }

    public void setPeaks(List<Peak> peaks) {
        this.peaks = peaks;
    }

    public double getParentMass() {

        return parentMass;
    }

    public List<Peak> getPeaks() {
        return peaks;
    }

    public String toString(Spectra spec){
        return ("Parent Mass:"+parentMass+"\n"+peaks);
    }
}