import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */
public class ProcessedSet {

    private Peptide peptideOne;
    private Peptide peptideTwo;
    private List<IonMass> allPossibleIonMass;
    double parentMass;

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
}
