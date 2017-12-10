package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liang on 2017/12/7.
 */
public class BaseMass {
    public final static double waterMass = 0;
    public final static double hydrogen=1;
    public final static double tolerance=0.015;
    private Map<Character,Double> mapBaseMass ;

    public BaseMass(){
        mapBaseMass = new HashMap<>();
        mapBaseMass.put('G',75.07);
        mapBaseMass.put('A',89.09);
        mapBaseMass.put('V',117.15);
        mapBaseMass.put('L',131.17);
        mapBaseMass.put('I',131.17);
        mapBaseMass.put('F',165.19);
        mapBaseMass.put('W',204.23);
        mapBaseMass.put('Y',181.19);
        mapBaseMass.put('D',133.10);
        mapBaseMass.put('N',132.12);
        mapBaseMass.put('E',147.13);
        mapBaseMass.put('K',146.19);
        mapBaseMass.put('Q',146.15);
        mapBaseMass.put('M',149.21);
        mapBaseMass.put('S',105.09);
        mapBaseMass.put('T',119.12);
        mapBaseMass.put('C',121.16);
        mapBaseMass.put('P',115.13);
        mapBaseMass.put('H',155.16);
        mapBaseMass.put('R',174.20);
    }
    public Map<Character,Double> getMapBaseMass(){
        return this.mapBaseMass;
    }
}
