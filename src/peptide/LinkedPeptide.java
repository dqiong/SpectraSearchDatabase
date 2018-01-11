package peptide;

import utils.IonMass;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */
public class LinkedPeptide {
    private Peptide peptideOne;
    private Peptide peptideTwo;
    private List<IonMass> allPossibleIonMass;
    double parentMass;
    private boolean isCalculatedIonMass;

    public LinkedPeptide(){
        this.isCalculatedIonMass=false;
    }

    public boolean getIsCalculatedIonMass() {
        return isCalculatedIonMass;
    }

    public void setIsCalculatedIonMass(boolean calculatedIonMass) {
        isCalculatedIonMass = calculatedIonMass;
    }

    public Peptide getPeptideOne() {
        return peptideOne;
    }

    public void setPeptideOne(Peptide peptideOne) {
        this.peptideOne = peptideOne;
    }

    public Peptide getPeptideTwo() {
        return peptideTwo;
    }

    public void setPeptideTwo(Peptide peptideTwo) {
        this.peptideTwo = peptideTwo;
    }

    public List<IonMass> getAllPossibleIonMass() {
        return allPossibleIonMass;
    }

    public void setAllPossibleIonMass(List<IonMass> allPossibleIonMass) {
        this.allPossibleIonMass = allPossibleIonMass;
    }


    public double getParentMass() {
        return parentMass;
    }

    public void setParentMass(double parentMass) {
        this.parentMass = parentMass;
    }

    public String toString(){
        StringBuilder res=new StringBuilder();
        res.append(peptideOne.getName());
        res.append("\t");
        res.append(peptideTwo.getName());
        res.append("\n");
        return res.toString();
    }
}