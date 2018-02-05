package spectra;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

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
    private String title="";
    private int number=0;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String toString(){
        StringBuilder res=new StringBuilder();
        res.append("Parent Mass:");
        res.append(parentMass);
        res.append("\t TITLE:");
        res.append(title);
        res.append("\t Number:");
        res.append(number);
        res.append("\n");
        res.append("matched peaks:");
        res.append("\n");
        Iterator it1 = this.peaks.iterator();
        while (it1.hasNext()) {
            res.append(it1.next());
        }
        return res.toString();
    }
}