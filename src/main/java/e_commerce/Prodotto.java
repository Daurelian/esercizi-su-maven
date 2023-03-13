package e_commerce;

public class Prodotto {
    /*
    Ogni prodotto acquistabile possiede un id univoco, un nome, una breve
 descrizione, una quantità di disponibilità in stock e un prezzo.
     */
    private int id;
    private String name;
    private String description;
    private int stock;
    private double price;

    public Prodotto() {
    }

    public Prodotto(int id, String name, String description, int stock, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
