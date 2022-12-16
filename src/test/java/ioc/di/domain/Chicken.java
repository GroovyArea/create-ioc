package ioc.di.domain;

public class Chicken {

    private final String name;
    private final int price;
    private final ChickenGender gender;

    public Chicken (final String name, final int price, final ChickenGender gender) {
        this.name = name;
        this.price = price;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public ChickenGender getGender() {
        return gender;
    }

}
