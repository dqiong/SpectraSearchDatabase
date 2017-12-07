import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by liang on 2017/12/7.
 */
public class main {

    public static void main(String[] args)
    {
        ReadFile readFile = new ReadFile();
        List<Peptide> readResult = readFile.doRead("./input.txt");
        List<Peptide> readResultTemp = readResult;
        HandleMethod handleMethod = new HandleMethod();
        List<ProcessedSet> finalResult = new ArrayList<>();
        for(int i = 0 ; i < readResult.size() ; i++)
        {
            for(int j = i+1 ; j < readResultTemp.size();j++)
            {
                Peptide composeOne = readResult.get(i);
                Peptide composeTwo = readResult.get(j);
                List<IonMass> result = handleMethod.getAllPossibleIonMass(composeOne.getName(),composeOne.getCutPoint(),composeTwo.getName(),composeTwo.getCutPoint());
                double mass1 = handleMethod.calculate(composeOne.getName(),0,composeOne.getName().length());
                double mass2 = handleMethod.calculate(composeTwo.getName(),0,composeTwo.getName().length());
                double parentMass = mass1 + mass2 + handleMethod.getReagentMass();
                ProcessedSet processedSet = new ProcessedSet();
                processedSet.setParentMass(parentMass);
                processedSet.setPeptideOne(composeOne);
                processedSet.setPeptideTwo(composeTwo);
                processedSet.setAllPossibleIonMass(result);
                finalResult.add(processedSet);
            }
        }

        Iterator iiii = finalResult.iterator();
        while(iiii.hasNext())
        {
            ProcessedSet processedSet = (ProcessedSet) iiii.next();
            System.out.println(processedSet.getPeptideOne().getName() + " " + processedSet.getPeptideTwo().getName()
             + " " + processedSet.getParentMass());
        }


//        HandleMethod handleMethod = new HandleMethod();
//        handleMethod.setReagentMass(0);
//        //System.out.println(handleMethod.calculate("GAVGNST",));
//        List<IonMass> result = handleMethod.getAllPossibleIonMass("GAVGNST",4,"FWYDC",3);
//        Iterator iterator = result.iterator();
//        while(iterator.hasNext())
//        {
//            IonMass i = (IonMass) iterator.next();
//            System.out.println(i.getIonProperty() + "  " + i.getMass());
//        }
    }
}
