package Lab3;
import java.util.Date;
abstract class AbstractPyrotechnics {
    public abstract String getId();
    public abstract String getType();
    public abstract String getModel();
    public abstract double getPower();
    public abstract int getMaxHeight();
    public abstract Date getProductionDate();
    public abstract double getPrice();

    public abstract void setId(String id);
    public abstract void setType(String type);
    public abstract void setModel(String model);
    public abstract void setPower(double power);
    public abstract void setMaxHeight(int maxHeight);
    public abstract void setProductionDate(Date productionDate);
    public abstract void setPrice(double price);
}