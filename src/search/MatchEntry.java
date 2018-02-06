package search;

import peptide.LinkedPeptide;
import spectra.Peak;
import spectra.Spectra;

import java.util.Iterator;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: qiong
 * \* Date: 2017/12/8
 * \* Time: 20:12
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class MatchEntry {
    private Spectra spectra;
    private LinkedPeptide linkedPeptide;
    private List<Peak> matchedPeaks;
    private Double score;

    public Spectra getSpectra() {
        return spectra;
    }

    public void setSpectra(Spectra spectra) {
        this.spectra = spectra;
    }

    public LinkedPeptide getLinkedPeptide() {
        return linkedPeptide;
    }

    public void setLinkedPeptide(LinkedPeptide linkedPeptide) {
        this.linkedPeptide = linkedPeptide;
    }

    public List<Peak> getMatchedPeaks() {
        return matchedPeaks;
    }

    public void setMatchedPeaks(List<Peak> matchedPeaks) {
        this.matchedPeaks = matchedPeaks;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String toString(){
        StringBuilder res=new StringBuilder();
        res.append(spectra.toString());
        res.append("score: ");
        res.append(score);
        res.append("\n");
        res.append("matched peptides：");
        res.append("mass: ");
        res.append(linkedPeptide.getParentMass());
        res.append("\t");
        res.append(linkedPeptide.getPeptideOne().getName());
        res.append("\t");
        res.append(linkedPeptide.getPeptideTwo().getName());
        res.append("\n\n");
        return res.toString();
    }
}