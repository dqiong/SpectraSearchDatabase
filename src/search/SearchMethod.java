package search;

import peptide.HandlePeptideFour;
import peptide.LinkedPeptide;
import spectra.Spectra;
import spectra.Peak;
import utils.BaseMass;
import utils.IonMass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

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

    private boolean equalParentMass(double a,double b){
        double diff=abs(a-b);
        double real_ppm=(diff/b)*1000000;
        if(real_ppm<=BaseMass.parent_tolerance)
            return true;
        else
            return false;
        /*
        if( ((a- BaseMass.parent_tolerance)<b && (a+ BaseMass.parent_tolerance)>b) || (((b- BaseMass.parent_tolerance)<a && (b+ BaseMass.parent_tolerance)>a)) ){
            return true;
        }
        else
            return  false;
         */
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
            for(i=0;i<ionMasses.size();i++){
                double ionTmpMass=ionMasses.get(i).getMass();
                if(this.equalMass(peakMass,ionTmpMass)){
                    score+=1.0;
                }
            }

          //  if(i>=(ionMasses.size()-1) || i>peaks.size()){
          //      break;
           // }
        }
        return score;
    }

    private MatchEntry spectraAlignmentLinkedPeptideIndex(List<LinkedPeptide> allLinkedPeptide,
                                                     ArrayList<Integer> index, Spectra spectra){
        MatchEntry me=new MatchEntry();
        me.setSpectra(spectra);
        double maxScore=-1.0;
        int resultIndex=-1;
        for(Integer i: index){
            LinkedPeptide linkedPeptide=allLinkedPeptide.get(i);
            double tempScore=this.getMatchedScore(spectra,linkedPeptide);
            if(tempScore>maxScore){
                resultIndex=i;
                maxScore=tempScore;
            }
        }
        me.setScore(maxScore);
        me.setLinkedPeptide(allLinkedPeptide.get(resultIndex));
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
                continue;
                //System.out.println("spectra: "+spectra+"parent mass 数据库中无对应的交联肽段！");
            }
            else {
                ArrayList<Integer> indexOfCandidateLinkedPeptide=new ArrayList<>();
                for(Integer i: indexOfParentMass){
                    LinkedPeptide linkedPeptide=allLinkedPeptide.get(i);
                    if(this.equalParentMass(linkedPeptide.getParentMass(),spectra.getParentMass())){
                        indexOfCandidateLinkedPeptide.add(i);
                        if(!allLinkedPeptide.get(i).getIsCalculatedIonMass()){
                            allLinkedPeptide.get(i).setIsCalculatedIonMass(true);
                            HandlePeptideFour handlePeptide=new HandlePeptideFour();
                            String p1=allLinkedPeptide.get(i).getPeptideOne().getName();
                            String p2=allLinkedPeptide.get(i).getPeptideTwo().getName();
                            allLinkedPeptide.get(i).setAllPossibleIonMass(handlePeptide.getAllPossibleOnlyFourCases(p1,p2));
                        }

                    }
                }
                if(indexOfCandidateLinkedPeptide.size()>0){
                    this.resultMatch.add(this.spectraAlignmentLinkedPeptideIndex(allLinkedPeptide,indexOfCandidateLinkedPeptide,spectra));
                }

            }
        }
    }

    public void spectraSearchDatabase(List<LinkedPeptide> allLinkedPeptide,
                                       List<Spectra> allSpectra){
        int num=0;
        for(Spectra spectra:allSpectra){
            Double parentMass=new Double(spectra.getParentMass());
            ArrayList<Integer> indexOfCandidateLinkedPeptide=new ArrayList<>();
            for(int k=0; k<allLinkedPeptide.size();k++){
                if(this.equalParentMass(allLinkedPeptide.get(k).getParentMass(),parentMass)) {
                    indexOfCandidateLinkedPeptide.add(k);
                    if(!allLinkedPeptide.get(k).getIsCalculatedIonMass()){
                        allLinkedPeptide.get(k).setIsCalculatedIonMass(true);
                        HandlePeptideFour handlePeptide=new HandlePeptideFour();
                        String p1=allLinkedPeptide.get(k).getPeptideOne().getName();
                        String p2=allLinkedPeptide.get(k).getPeptideTwo().getName();
                        allLinkedPeptide.get(k).setAllPossibleIonMass(handlePeptide.getAllPossibleOnlyFourCases(p1,p2));
                    }
                }
            }
            if(indexOfCandidateLinkedPeptide.size()<1){
                num++;
                continue;
                //System.out.println("spectra: "+spectra+"parent mass 数据库中无对应的交联肽段！");
            }
            else {
                MatchEntry me=this.spectraAlignmentLinkedPeptideIndex(allLinkedPeptide,indexOfCandidateLinkedPeptide,spectra);
                if(me.getScore()>0.0){
                    this.resultMatch.add(me);
                }
            }
        }
        System.out.println("the number of spectra that has no the corresponding linkedpeptide in "+ BaseMass.parent_tolerance +"ppm : "+num);
    }
}