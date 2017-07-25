package app.raulvalverde.com.coinmanager;

/**
 * Book
 * Created by pr_idi on 10/11/16.
 */
public class Coin {

    // Basic book data manipulation class
    // Contains basic information on the book

    private long id;
    private String currency;
    private double value;
    private int year;
    private String country;
    private String description;
    private String img1path;


    public Coin(String currency, double value, int year, String country, String description, String img1path) {
        this.currency = currency;
        this.value = value;
        this.year = year;
        this.country = country;
        this.description = description;
        this.img1path = img1path;
    }

    public Coin() {

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year= year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg1path() { return img1path;}

    public void setImg1path(String path) { this.img1path = path;}

    // Will be used by the ArrayAdapter in the ListView
    // Note that it only produces the value and the currency
    // Extra information should be created by modifying this
    // method or by adding the methods required
    @Override
    public String toString() {
        return String.format("%s - %s", value, currency, year, description, country);
    }
}