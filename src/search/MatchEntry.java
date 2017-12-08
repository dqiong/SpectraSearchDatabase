package search;

import peptide.LinkedPeptide;
import spectra.Peak;
import spectra.Spectra;

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
    private double score;

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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}