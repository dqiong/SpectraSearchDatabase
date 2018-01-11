package peptide;
import utils.BaseMass;
import utils.IonMass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by Liang on 2018/1/11.
 */
public class HandlePeptideFour {

    private BaseMass baseMass;
    public HandlePeptideFour(){
        baseMass = new BaseMass();
    }

    public double getReagentMass() {
        return reagentMass;
    }

    public void setReagentMass(double reagentMass) {
        this.reagentMass = reagentMass;
    }

    //胶合剂质量
    private double reagentMass=BaseMass.PHOTO;

    //保留小数
    public double retainDecimal(double temp)
    {
        BigDecimal b = new BigDecimal(temp);
        double result = b.setScale(4 ,BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }

    // list合并，并去重（不同位点得到的b,y离子有重复的情况）
    public List<IonMass> mergeList(List<IonMass> listOne, List<IonMass> listTwo)
    {
        List<IonMass> resultList = new ArrayList<>();
        resultList.addAll(listOne);
        resultList.addAll(listTwo);
        resultList = new ArrayList<>(new LinkedHashSet<>(resultList));
        return resultList;
    }


    //根据字符串的起始终点位置计算质量(不算胶合剂)
    public double calculate(String temp , int start , int end)
    {
        double result = 0.00;
        for(int i = start; i < end;i++){
            result+= baseMass.getMapBaseMass().get(temp.charAt(i));
        }
        return  result;
    }

    public List<IonMass> getSinglePeptideAllPossibleIonMass(String p,String add){
        double massAdd = calculate(add,0,add.length());
        int length = p.length();
        List<IonMass> singlePeptideAllPossibleIonMass = new ArrayList<>();

        // 一个字符串所有可能的B和Y离子（带交联剂,不带交联剂，带一半交联剂）
        for(int i = 1 ; i < length ; i++)
        {
            // 所有的B
            double massB = calculate(p,0,i);
            IonMass ionMassBWithOutJ = new IonMass();  // 不带交联剂
            ionMassBWithOutJ.setIonProperty(IonMass.ion.B);
            ionMassBWithOutJ.setMass(massB + 1);
            IonMass ionMassBWithJ = new IonMass();    // 带交联剂
            ionMassBWithJ.setIonProperty(IonMass.ion.B);
            ionMassBWithJ.setMass(massB + this.reagentMass + 1);
            IonMass ionMassBWithHalfJ = new IonMass();  // 带一半交联剂
            ionMassBWithHalfJ.setIonProperty(IonMass.ion.B);
            ionMassBWithHalfJ.setMass(massB + this.reagentMass/2 + 1);
            IonMass ionMassBWithAnotherP = new IonMass(); // 相连情况下 加上一个Peptide的质量
            ionMassBWithAnotherP.setIonProperty(IonMass.ion.B);
            ionMassBWithAnotherP.setMass(massB + this.reagentMass + 1 + massAdd);
            singlePeptideAllPossibleIonMass.add(ionMassBWithOutJ);
            singlePeptideAllPossibleIonMass.add(ionMassBWithJ);
            singlePeptideAllPossibleIonMass.add(ionMassBWithHalfJ);
            singlePeptideAllPossibleIonMass.add(ionMassBWithAnotherP);

            //所有的Y
            double massY = calculate(p,i,length);
            IonMass ionMassYWithOutJ = new IonMass(); // Y离子不带交联剂
            ionMassYWithOutJ.setIonProperty(IonMass.ion.Y);
            ionMassYWithOutJ.setMass(massY + baseMass.waterMass + 1);
            IonMass ionMassYWithJ = new IonMass();  // Y离子带交联剂
            ionMassYWithJ.setIonProperty(IonMass.ion.Y);
            ionMassYWithJ.setMass(massY + this.reagentMass + baseMass.waterMass + 1);
            IonMass ionMassYWithHalfJ = new IonMass(); // Y离子带一半交联剂
            ionMassYWithHalfJ.setIonProperty(IonMass.ion.Y);
            ionMassYWithHalfJ.setMass(massY + this.reagentMass/2 + baseMass.waterMass + 1);
            IonMass ionMassYWithAnotherP = new IonMass();
            ionMassYWithAnotherP.setIonProperty(IonMass.ion.Y);
            ionMassYWithAnotherP.setMass(massY + this.reagentMass + baseMass.waterMass + 1 + massAdd);
            singlePeptideAllPossibleIonMass.add(ionMassYWithJ);
            singlePeptideAllPossibleIonMass.add(ionMassYWithHalfJ);
            singlePeptideAllPossibleIonMass.add(ionMassYWithOutJ);
            singlePeptideAllPossibleIonMass.add(ionMassYWithAnotherP);
        }
        return singlePeptideAllPossibleIonMass;
    }


    public List<IonMass> getAllPossibleOnlyFourCases(String pOne,String pTwo)
    {
        List<IonMass> allPossibleOnlyFourCases = new ArrayList<>();
        List<IonMass> singleOneAllIonMass = getSinglePeptideAllPossibleIonMass(pOne,pTwo);
        List<IonMass> singleTwoAllIonMass = getSinglePeptideAllPossibleIonMass(pTwo,pOne);
        allPossibleOnlyFourCases = mergeList(singleOneAllIonMass,singleTwoAllIonMass);


        return  allPossibleOnlyFourCases;

    }

}
