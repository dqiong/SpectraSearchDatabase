import peptide.*;

import java.util.*;
import utils.IonMass;

/**
 * Created by liang on 2017/12/7.
 */
public class main {

    public static void main(String[] args)
    {

        PeptideIndex pein=new PeptideIndex();


        Iterator iiii = pein.getAllLinkedPeptide().iterator();
        while(iiii.hasNext())
        {
            LinkedPeptide LinkedPeptide = (LinkedPeptide) iiii.next();
            System.out.println(LinkedPeptide.getPeptideOne().getName() + " " + LinkedPeptide.getPeptideTwo().getName()
             + " " + LinkedPeptide.getParentMass());
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
