package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liang on 2017/12/7.
 */
public class BaseMass {
    public final static double waterMass = 18.01524;
    public final static double hydrogen=1.00727638;
    public final static double tolerance=0.05;
    public final static double parent_tolerance=20; //单位ppm
    public final static double PROTON_MASS = 1.00727638;
    public final static double PHOTO=228.110996;
    public final static double HALF_PHOTO=114.055498;

    private Map<Character,Double> mapBaseMass ;

    public BaseMass(){
        mapBaseMass = new HashMap<>();
        mapBaseMass.put('G',57.021464);
        mapBaseMass.put('A',71.037114);
        mapBaseMass.put('V',99.068414);
        mapBaseMass.put('L',113.084064);
        mapBaseMass.put('I',113.084064);
        mapBaseMass.put('F',147.068414);
        mapBaseMass.put('W',186.079313);
        mapBaseMass.put('Y',163.06332);
        mapBaseMass.put('D',115.026943);
        mapBaseMass.put('N',114.042927);
        mapBaseMass.put('E',129.042593);
        mapBaseMass.put('K',128.094963);
        mapBaseMass.put('Q',128.058578);
        mapBaseMass.put('M',131.040485);
        mapBaseMass.put('S',87.032028);
        mapBaseMass.put('T',101.047679);
        mapBaseMass.put('C',103.009185);
        mapBaseMass.put('P',97.052764);
        mapBaseMass.put('H',137.058912);
        mapBaseMass.put('R',156.101111);
    }
    public Map<Character,Double> getMapBaseMass(){
        return this.mapBaseMass;
    }
}
