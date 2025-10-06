package Lab3;
import java.util.*;
import java.text.SimpleDateFormat;

class Pyrotechnics extends AbstractPyrotechnics {
    private String id;
    private String type;
    private String model;
    private double power;
    private int maxHeight;
    private Date productionDate;
    private double price;

    public Pyrotechnics(String id, String type, String model, double power,
                        int maxHeight, Date productionDate, double price) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.power = power;
        this.maxHeight = maxHeight;
        this.productionDate = productionDate;
        this.price = price;
    }

    @Override
    public String getId() {
        return id;
    }
    @Override
    public String getType() {
        return type;
    }
    @Override
    public String getModel() {
        return model;
    }
    @Override
    public double getPower() {
        return power;
    }
    @Override
    public int getMaxHeight() {
        return maxHeight;
    }
    @Override
    public Date getProductionDate() {
        return productionDate;
    }
    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
    @Override
    public void setType(String type) {
        this.type = type;
    }
    @Override
    public void setModel(String model) {
        this.model = model;
    }
    @Override
    public void setPower(double power) {
        this.power = power;
    }
    @Override
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }
    @Override
    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }
    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        String height = maxHeight + "м";
        return String.format("ID: %-5s  Тип: %-10s  Модель: %-15s  Мощность: %-10.2f  Высота: %-10s  Дата: %-15s  Цена: %-5.2f",
                id,
                type,
                model,
                power,
                height,
                sdf.format(productionDate),
                price);
    }
}