import peptide.*;

import java.util.*;

import spectra.ReadSpectra;
import spectra.Spectra;
import utils.FilePath;
import utils.IonMass;

/**
 * Created by liang on 2017/12/7.
 */
public class main {

    public static void main(String[] args)
    {

        PeptideIndex peindex=new PeptideIndex();

        ReadSpectra rs=new ReadSpectra();
        rs.doRead(FilePath.SPECTRA_PATH);
        List<Spectra> allSpectra=rs.getAllSpectra();

        Iterator iiii = peindex.getAllLinkedPeptide().iterator();
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
