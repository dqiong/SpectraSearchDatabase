/**
 * Created by Administrator on 2017/12/7.
 */
public class IonMass {
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


    // 重写equals方法和hashCode方法

//    @Override
//    public int hashCode() {
//        return mass.hashCode();
//    }
//    @Override
//    public boolean equals(Object obj) {
//        if(obj == this)
//            return true;
//        if(!(obj instanceof IonMass))
//            return false;
//        IonMass i = (IonMass)obj;
//        return i.ionProperty.equals(ionProperty) && i.mass.equals(mass);
//    }
}
