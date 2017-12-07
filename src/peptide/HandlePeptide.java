package peptide;

import utils.BaseMass;
import utils.IonMass;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private double reagentMass;

    //保留小数
    public double retainDecimal(double temp)
    {
        BigDecimal b = new BigDecimal(temp);
        double result = b.setScale(4 ,BigDecimal.ROUND_HALF_UP).doubleValue();
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
            double mass1 = calculate(pOne,i,lengthOne);
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
            double mass2 = calculate(pTwo,i,lengthTwo);
            ionMass1.setMass(retainDecimal(mass1+mass2+this.reagentMass + baseMass.waterMass + 1));
            allPossibleIonMass.add(ionMass1);
        }

        //case Three
        temp = baseMass.waterMass + 1;
        for(int i = lengthOne-1; i >= cutOne ; i--)
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
        for(int i = lengthTwo-1;i >= cutTwo ;i--)
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
                double mass1 = calculate(pOne,i,lengthOne);
                double mass2 = calculate(pTwo,j,lengthTwo);
                ionMass.setMass(retainDecimal(mass1+mass2+this.reagentMass + baseMass.waterMass + 1));
                allPossibleIonMass.add(ionMass);
            }
        }

        //case Six
        for(int i = lengthOne; i >= cutOne;i--)
        {
            for(int j = lengthTwo; j >= cutTwo ;j--)
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
            for(int j = lengthTwo ; j >= cutTwo ; j--)
            {
                //只考虑中间的情况
                IonMass ionMass = new IonMass();
                ionMass.setIonProperty(IonMass.ion.O);
                double mass1 = calculate(pOne,i,lengthOne);
                double mass2 = calculate(pTwo,0,j);
                ionMass.setMass(retainDecimal(mass1+mass2+this.reagentMass));
                allPossibleIonMass.add(ionMass);
            }
        }

        //case Eight
        for(int i = lengthOne ; i >= cutOne;i--)
        {
            for(int j = 0 ; j < cutTwo ;j++)
            {
                IonMass ionMass = new IonMass();
                ionMass.setIonProperty(IonMass.ion.O);
                double mass1 = calculate(pOne,0,i);
                double mass2 = calculate(pTwo,j,lengthTwo);
                ionMass.setMass(retainDecimal(mass1+mass2+this.reagentMass));
                allPossibleIonMass.add(ionMass);
            }
        }



        return allPossibleIonMass;
    }
}