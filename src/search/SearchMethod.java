package search;

import com.sun.javafx.scene.EnteredExitedHandler;
import peptide.LinkedPeptide;
import spectra.Spectra;
import spectra.Peak;
import utils.BaseMass;
import utils.IonMass;

import java.util.ArrayList;
import java.util.Collections;
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

    private boolean equalMass(double a,double b){
        if( ((a- BaseMass.tolerance)<b && (a+ BaseMass.tolerance)>b) || (((b- BaseMass.tolerance)<a && (b+ BaseMass.tolerance)>a)) ){
            return true;
        }
        else
            return  false;
    }

    //一个谱图与数据库中一个理论交联肽段离子比对得分
    private double getMatchedScore(Spectra spectra,LinkedPeptide linkedPeptide){
        double score=0.0;
        List<Peak> peaks=spectra.getPeaks();
        Collections.sort(peaks);
        List<IonMass> ionMasses=linkedPeptide.getAllPossibleIonMass();
        Collections.sort(ionMasses);
        int i=0;
        for(Peak peak: peaks){
            double peakMass=peak.getMass();
            for(;i<ionMasses.size();i++){
                double ionTmpMass=ionMasses.get(i).getMass();
                if(this.equalMass(peakMass,ionTmpMass)){
                    score+=1.0;
                    break;
                }
            }
            if(i==(ionMasses.size()-1)){
                break;
            }
        }
        return score;
    }

    private MatchEntry spectraAlignmentLinkedPeptideIndex(List<LinkedPeptide> allLinkedPeptide,
                                                     ArrayList<Integer> index, Spectra spectra){
        MatchEntry me=new MatchEntry();
        me.setSpectra(spectra);
        double maxScore=0.0;
        LinkedPeptide resultLinkedPeptide=new LinkedPeptide();
        for(Integer i: index){
            LinkedPeptide linkedPeptide=allLinkedPeptide.get(i);
            double tempScore=this.getMatchedScore(spectra,linkedPeptide);
            if(tempScore>maxScore){
                resultLinkedPeptide=linkedPeptide;
                maxScore=tempScore;
            }
        }
        me.setScore(maxScore);
        me.setLinkedPeptide(resultLinkedPeptide);
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
                this.resultMatch.add(this.spectraAlignmentLinkedPeptideIndex(allLinkedPeptide,indexOfParentMass,spectra));
            }
        }
    }
}