package peptide;

import utils.BaseMass;
import utils.IonMass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */
public class HandlePeptide {
    private BaseMass baseMass;
    public HandlePeptide(){
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
        double result = b.setScale(5 ,BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
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

    // list合并，并去重（不同位点得到的b,y离子有重复的情况）
    public List<IonMass> mergeList(List<IonMass> listOne, List<IonMass> listTwo)
    {
        List<IonMass> resultList = new ArrayList<>();
        resultList.addAll(listOne);
        resultList.addAll(listTwo);
        resultList = new ArrayList<>(new LinkedHashSet<>(resultList));
        return resultList;
    }

    // 这个是没有胶合剂相连的那种情况下的B Y离子质量，（有些情况下的BY离子质量在之前的Case中就算过了，这里没有算）
    public List<IonMass> getIonMassWithoutConnect(String p)
    {
        int lengthP = p.length();
        List<IonMass> listAdd = new ArrayList<>();
        for(int i = 0 ; i < lengthP ; i++) // 遍历第一个字符串交联剂位置
        {

            for(int j = 0 ; j < i ; j++)  // 遍历位点 这种情况不用算B的质量，之前都有算过。只需要算Y离子的质量就行
            {
                IonMass ionMassY = new IonMass();
                ionMassY.setIonProperty(IonMass.ion.Y);
                ionMassY.setMass(calculate(p,j+1,lengthP) + this.reagentMass + BaseMass.waterMass + 1);
                IonMass ionMassYHalf = new IonMass();
                ionMassYHalf.setIonProperty(IonMass.ion.Y);
                ionMassYHalf.setMass(calculate(p,j+1,lengthP) + this.reagentMass/2 + BaseMass.waterMass + 1);
                listAdd.add(ionMassY);
                listAdd.add(ionMassYHalf);
            }

            for(int j = lengthP-1 ; j>i ; j--)// 遍历位点 这种情况不用算Y的质量，之前都有算过。只需要算B离子的质量就行
            {
                IonMass ionMassB = new IonMass();
                ionMassB.setIonProperty(IonMass.ion.B);
                ionMassB.setMass(calculate(p,0,j) + this.reagentMass + 1);
                IonMass ionMassBHalf = new IonMass();
                ionMassBHalf.setIonProperty(IonMass.ion.B);
                ionMassBHalf.setMass(calculate(p,0,j) + this.reagentMass/2 + 1);
                listAdd.add(ionMassB);
                listAdd.add(ionMassBHalf);
            }
        }

        return listAdd;
    }



    // 给定两个字符串，没有连接位置，计算可能出现的所有质量
    public List<IonMass> getAllPossibleIonMassWithoutPosition(String pOne, String pTwo)
    {
        int lengthOne = pOne.length();
        int lengthTwo = pTwo.length();
        List<IonMass> allPossibleIonMass = new ArrayList<>();
        //case1 - case8所有情况的集合
        for(int i = 0 ; i < lengthOne ; i++) // 遍历第一个字符串位点
        {
            for(int j = 0 ; j < lengthTwo ; j++)  // 遍历第二个字符串位点
            {
                List<IonMass> list = this.getAllPossibleIonMass(pOne,i,pTwo,j);
                allPossibleIonMass = this.mergeList(allPossibleIonMass,list);
            }
        }
        // 带胶合剂没合在一起的情况
        /*
              -----------
                   |         // 这里可以是整个交联剂也可以是一半的交联剂
         */
        List<IonMass> listOne = getIonMassWithoutConnect(pOne);
        List<IonMass> listTwo = getIonMassWithoutConnect(pTwo);
        allPossibleIonMass = this.mergeList(allPossibleIonMass,listOne);
        allPossibleIonMass = this.mergeList(allPossibleIonMass,listTwo);


        // 还有就是整个蛋白质的情况(不带胶合剂) 这个不知道整个算B还是Y。（这里按B算的）
        IonMass ionMassPOne = new IonMass();
        IonMass ionMassPTwo = new IonMass();
        ionMassPOne.setIonProperty(IonMass.ion.B);
        ionMassPTwo.setIonProperty(IonMass.ion.B);
        ionMassPOne.setMass(calculate(pOne,0,lengthOne) + 1);
        ionMassPTwo.setMass(calculate(pTwo,0,lengthTwo) + 1);
        allPossibleIonMass.add(ionMassPOne);
        allPossibleIonMass.add(ionMassPTwo);

        return  allPossibleIonMass;
    }




    //给定两个字符串 和 连接位置 计算可能出现的所有质量
    public List<IonMass> getAllPossibleIonMass(String pOne, Integer cutOne, String pTwo, Integer cutTwo) {
        int lengthOne = pOne.length();
        int lengthTwo = pTwo.length();
        List<IonMass> allPossibleIonMass = new ArrayList<>();
        double temp = 0;


        //case One
        temp = 1;
        for (int i = 0; i < cutOne; i++)
        {
            //可能出现B离子的质量
            IonMass ionMass = new IonMass();
            ionMass.setIonProperty(IonMass.ion.B);
            temp = temp + baseMass.getMapBaseMass().get(pOne.charAt(i));
            ionMass.setMass(retainDecimal(temp));
            allPossibleIonMass.add(ionMass);
            //可能出现Y离子的质量
            IonMass ionMass1 = new IonMass();
            ionMass1.setIonProperty(IonMass.ion.Y);
            double mass1 = calculate(pOne,i+1,lengthOne);
            double mass2 = calculate(pTwo,0,lengthTwo);
            ionMass1.setMass(retainDecimal(mass1+mass2+this.reagentMass + baseMass.waterMass + 1));
            allPossibleIonMass.add(ionMass1);
        }

        //case Two
        temp = 1;
        for(int i = 0 ; i < cutTwo ; i++)
        {
            //可能出现的B离子的质量
            IonMass ionMass = new IonMass();
            ionMass.setIonProperty(IonMass.ion.B);
            temp = temp + baseMass.getMapBaseMass().get(pTwo.charAt(i));
            ionMass.setMass(retainDecimal(temp));
            allPossibleIonMass.add(ionMass);
            //可能出现Y离子的质量
            IonMass ionMass1 = new IonMass();
            ionMass1.setIonProperty(IonMass.ion.Y);
            double mass1 = calculate(pOne,0,lengthOne);
            double mass2 = calculate(pTwo,i+1,lengthTwo);
            ionMass1.setMass(retainDecimal(mass1+mass2+this.reagentMass + baseMass.waterMass + 1));
            allPossibleIonMass.add(ionMass1);
        }

        //case Three
        temp = baseMass.waterMass + 1;
        for(int i = lengthOne-1; i > cutOne ; i--)
        {
            //可能出现的Y离子的质量
            IonMass ionMass = new IonMass();
            ionMass.setIonProperty(IonMass.ion.Y);
            temp = temp + baseMass.getMapBaseMass().get(pOne.charAt(i));
            ionMass.setMass(retainDecimal(temp));
            allPossibleIonMass.add(ionMass);
            //可能出现的B离子的质量
            IonMass ionMass1 = new IonMass();
            ionMass1.setIonProperty(IonMass.ion.B);
            double mass1 = calculate(pOne,0,i);
            double mass2 = calculate(pTwo,0,lengthTwo);
            ionMass1.setMass(retainDecimal(mass1+mass2+this.reagentMass + 1));
            allPossibleIonMass.add(ionMass1);
        }

        //case Four
        temp = baseMass.waterMass + 1;
        for(int i = lengthTwo-1;i > cutTwo ;i--)
        {
            //可能出现的Y离子的质量
            IonMass ionMass = new IonMass();
            ionMass.setIonProperty(IonMass.ion.Y);
            temp = temp + baseMass.getMapBaseMass().get(pTwo.charAt(i));
            ionMass.setMass(retainDecimal(temp));
            allPossibleIonMass.add(ionMass);
            //可能出现的B离子的质量
            IonMass ionMass1 = new IonMass();
            ionMass1.setIonProperty(IonMass.ion.B);
            double mass1 = calculate(pOne,0,lengthOne);
            double mass2 = calculate(pTwo,0,i);
            ionMass1.setMass(retainDecimal(mass1+mass2+this.reagentMass + 1 ));
            allPossibleIonMass.add(ionMass1);
        }


        //case Five
        for(int i = 0;i<cutOne;i++)
        {
            for(int j = 0;j<cutTwo;j++)
            {
                // 只考虑可能出现的Y离子的情况
                IonMass ionMass = new IonMass();
                ionMass.setIonProperty(IonMass.ion.Y);
                double mass1 = calculate(pOne,i+1,lengthOne);
                double mass2 = calculate(pTwo,j+1,lengthTwo);
                ionMass.setMass(retainDecimal(mass1+mass2+this.reagentMass + baseMass.waterMass + 1));
                allPossibleIonMass.add(ionMass);
            }
        }

        //case Six
        for(int i = lengthOne-1; i > cutOne;i--)
        {
            for(int j = lengthTwo-1; j > cutTwo ;j--)
            {
                //只考虑可能出现的B离子的情况
                IonMass ionMass = new IonMass();
                ionMass.setIonProperty(IonMass.ion.B);
                double mass1 = calculate(pOne,0,i);
                double mass2 = calculate(pTwo,0,j);
                ionMass.setMass(retainDecimal(mass1+mass2+this.reagentMass + 1));
                allPossibleIonMass.add(ionMass);

            }
        }

        //case Seven
        for(int i = 0 ; i < cutOne ; i++)
        {
            for(int j = lengthTwo-1 ; j > cutTwo ; j--)
            {
                //只考虑中间的情况
                IonMass ionMass = new IonMass();
                ionMass.setIonProperty(IonMass.ion.O);
                double mass1 = calculate(pOne,i+1,lengthOne);
                double mass2 = calculate(pTwo,0,j);
                ionMass.setMass(retainDecimal(mass1+mass2+this.reagentMass));
                allPossibleIonMass.add(ionMass);
            }
        }

        //case Eight
        for(int i = lengthOne-1 ; i > cutOne;i--)
        {
            for(int j = 0 ; j < cutTwo ;j++)
            {
                IonMass ionMass = new IonMass();
                ionMass.setIonProperty(IonMass.ion.O);
                double mass1 = calculate(pOne,0,i);
                double mass2 = calculate(pTwo,j+1,lengthTwo);
                ionMass.setMass(retainDecimal(mass1+mass2+this.reagentMass));
                allPossibleIonMass.add(ionMass);
            }
        }



        return allPossibleIonMass;
    }
}