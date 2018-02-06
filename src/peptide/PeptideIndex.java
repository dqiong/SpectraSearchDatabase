package peptide;

import utils.BaseMass;
import utils.FilePath;
import utils.IonMass;

import java.util.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: qiong
 * \* Date: 2017/12/7
 * \* Time: 22:09
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class PeptideIndex {

    private List<LinkedPeptide> allLinkedPeptide=new ArrayList<>();
    private Map<Integer,ArrayList<Integer>> peptideIndex= new HashMap<>();

    public Map<Integer, ArrayList<Integer>> getPeptideIndex() {
        return peptideIndex;
    }

    public void setPeptideIndex(Map<Integer, ArrayList<Integer>> peptideIndex) {
        this.peptideIndex = peptideIndex;
    }

    public List<LinkedPeptide> getAllLinkedPeptide() {
        return allLinkedPeptide;
    }

    public void setAllLinkedPeptide(List<LinkedPeptide> allLinkedPeptide) {
        this.allLinkedPeptide = allLinkedPeptide;
    }

    public void linkPeptide(List<Peptide> readResult){
        Integer num=new Integer(0);
        for(int i = 0 ; i < readResult.size() ; i++)
        {
            for(int j = i+1 ; j < readResult.size();j++)
            {
                HandlePeptideFour handlePeptide=new HandlePeptideFour();
                Peptide composeOne = readResult.get(i);
                Peptide composeTwo = readResult.get(j);
              //  List<IonMass> result = handlePeptide.getAllPossibleIonMass(composeOne.getName(),composeTwo.getName());
                double mass1 = handlePeptide.calculate(composeOne.getName(),0,composeOne.getName().length());
                double mass2 = handlePeptide.calculate(composeTwo.getName(),0,composeTwo.getName().length());
                double parentMass = mass1 + mass2 + handlePeptide.getReagentMass()+ BaseMass.waterMass+BaseMass.waterMass;
                LinkedPeptide linkedPeptide = new LinkedPeptide();
                linkedPeptide.setParentMass(parentMass);
                linkedPeptide.setPeptideOne(composeOne);
                linkedPeptide.setPeptideTwo(composeTwo);
                allLinkedPeptide.add(linkedPeptide);

                Integer parentInt=new Double(linkedPeptide.getParentMass()).intValue();
                if(peptideIndex.containsKey(parentInt)){
                    peptideIndex.get(parentInt).add(num);
                }
                else {
                    ArrayList<Integer> index=new ArrayList<>();
                    index.add(num);
                    peptideIndex.put(parentInt,index);
                }
                num++;
            }
        }
        int cunt=0;
        for (ArrayList<Integer> value : peptideIndex.values()) {
            cunt+=value.size();
        }
        System.out.println("peptideIndex value size:"+cunt);
        System.out.println("peptideIndex size:"+peptideIndex.size());
    }

    public void init(String filePath){
        ReadPeptide readFile = new ReadPeptide();
        List<Peptide> readResult = readFile.doRead(filePath);
        linkPeptide(readResult);

        /*
        Iterator iiii = allLinkedPeptide.iterator();
        while(iiii.hasNext())
        {
            LinkedPeptide LinkedPeptide = (LinkedPeptide) iiii.next();
            System.out.println(LinkedPeptide.getPeptideOne().getName() + " " + LinkedPeptide.getPeptideTwo().getName()
                    + " " + LinkedPeptide.getParentMass());
        }
        */
    }

}