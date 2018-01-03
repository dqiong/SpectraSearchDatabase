package utils;
/**
 * Created by Administrator on 2017/12/7.
 */
public class IonMass implements Comparable{
    public enum ion{B,Y,O}
    private ion ionProperty;
    private Double mass;

    public ion getIonProperty() {
        return ionProperty;
    }

    public void setIonProperty(ion ionProperty) {
        this.ionProperty = ionProperty;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    @Override
    public int compareTo(Object o) {
        IonMass tmp=(IonMass) o;
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
    //重写ToString

    @Override
    public String toString() {
        return this.getIonProperty() + " " + this.getMass();
    }


    // 重写equals方法和hashCode方法

    @Override
    public int hashCode() {
        int temp ;
        if(this.getIonProperty() == ion.B) temp = 1;
        else temp = 0;

        return mass.hashCode()+ temp;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(!(obj instanceof IonMass))
            return false;
        IonMass i = (IonMass)obj;
        return i.ionProperty.equals(ionProperty) && i.mass.equals(mass);
    }
}
