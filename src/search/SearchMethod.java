package search;

import com.sun.javafx.scene.EnteredExitedHandler;
import peptide.LinkedPeptide;
import spectra.Spectra;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: qiong
 * \* Date: 2017/12/7
 * \* Time: 21:17
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class SearchMethod {
    private List<MatchEntry> resultMatch=new ArrayList<>();

    public List<MatchEntry> getResultMatch() {
        return resultMatch;
    }

    public void setResultMatch(List<MatchEntry> resultMatch) {
        this.resultMatch = resultMatch;
    }

    private double getMatchedScore(Spectra spectra,LinkedPeptide linkedPeptide){
        double score=0.0;

        return score;
    }
    private MatchEntry spectraAlignmentLinkedPeptideIndex(List<LinkedPeptide> allLinkedPeptide,
                                                     ArrayList<Integer> index, Spectra spectra){
        MatchEntry me=new MatchEntry();
        for(Integer i: index){
            LinkedPeptide linkedPeptide=allLinkedPeptide.get(i);

        }
        return me;
    }

    public void spectraSearchDatabase(List<LinkedPeptide> allLinkedPeptide,
                                                  Map<Integer,ArrayList<Integer>> peptideIndex, List<Spectra> allSpectra){
        for(Spectra spectra:allSpectra){
            Integer parentMass=new Double(spectra.getParentMass()).intValue();
            ArrayList<Integer> indexOfParentMass=new ArrayList<>();
            if(peptideIndex.containsKey(parentMass-1)){
                indexOfParentMass.addAll(peptideIndex.get(parentMass-1));
            }
            if(peptideIndex.containsKey(parentMass)){
                indexOfParentMass.addAll(peptideIndex.get(parentMass));
            }
            if(peptideIndex.containsKey(parentMass+1)){
                indexOfParentMass.addAll(peptideIndex.get(parentMass+1));
            }
            if(indexOfParentMass.size()<1){
                System.out.println("spectra: "+spectra+"parent mass 数据库中无对应的交联肽段！");
            }
            else {

            }
        }
    }
}