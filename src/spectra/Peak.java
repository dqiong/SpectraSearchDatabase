package spectra;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: qiong
 * \* Date: 2017/12/7
 * \* Time: 21:18
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class Peak implements Comparable{
    private double mass;
    private double intensity;

    public double getIntensity() {
        return intensity;
    }

    public double getMass() {

        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public String toString(){
        return (mass+"\t"+intensity+"\n");
    }

    @Override
    public int compareTo(Object o) {
        Peak tmp=(Peak) o;
        if(this.mass>tmp.getMass()){
            return 1;
        }
        else if(this.mass<tmp.getMass()){
            return -1;
        }
        else {
            return 0;
        }
    }
}