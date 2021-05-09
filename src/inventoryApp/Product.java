package inventoryApp;

import inventoryApp.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product is an item that can be sold by a company. It is composed of Parts.
 */
public class Product {

    /**
     * The list of parts associated with a Product. For example, a Bike Product may have two Wheel Parts, a HandleBars Part, a Seat
     * Part, etc.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Product ID number. Unique. Generated on Creation. All Product IDs are sequential.
     */
    private int id;

    /**
     * Product Name
     */
    private String name;

    /**
     * Product Price in Dollars
     */
    private double price;

    /**
     * Number of Product held in Inventory
     */
    private int stock;

    /**
     * Minimum number of Product held in Inventory
     */
    private int min;

    /**
     * Maximum number of Product held in Inventory
     */
    private int max;

    /**
     * Constuctor for creating a Product
     * @param id - Integer ID number.
     * @param name - String name for Product
     * @param price - Double value for price of Product in Dollars
     * @param stock - Integer amount of Product currently in Inventory
     * @param min - Integer minimum amount of Product in Inventory
     * @param max - Integer maximum amount of Product in Inventory
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Set the Product ID
     * @param id - Integer ID number
     */
    public void setId(int id) { this.id = id; }

    /**
     * Set the Product name
     * @param name - String Product name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Set the Product price in Dollars
     * @param price - Double dollar amount
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * Set the amount of Product currently in Inventory
     * @param stock - Integer, amount of product currently in Inventory
     */
    public void setStock(int stock) { this.stock = stock; }

    /**
     * Set the minumum amount of Product currently in Inventory
     * @param min - Integer, amount of Product currently in Inventory
     */
    public void setMin(int min) { this.min = min; }

    /**
     * Set the maximum amount of Product currently in Inventory
     * @param max - Integer, the maximum amount of Product currently in Inventory
     */
    public void setMax(int max) { this.max = max; }

    /**
     * Get the ID number for Product
     * @return Integer ID number
     */
    public int getId() { return this.id; }

    /**
     * Get Product name
     * @return - String product name
     */
    public String getName() { return this.name; }

    /**
     * Get Product Price
     * @return - Double Product price in Dollars
     */
    public double getPrice() { return this.price; }

    /**
     * Amount of Product currently in Inventory
     * @return - integer amount of Product currently in Inventory
     */
    public int getStock() { return this.stock; }

    /**
     * Minimum amount of Product in Inventory
     * @return - integer, minimum amount of Product in Inventory
     */
    public int getMin() { return this.min; }

    /**
     * Maximum amount of Product in Inventory
     * @return - integer, maximum amount of Product in Inventory
     */
    public int getMax() { return this.max; }

    /**
     * Add a Part to the Product
     * @param part - the part to be added to the Product
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * delete a matching part from the Product
     * @param selectedPart - a Part with matching attributes to the Part to be deleted
     * @return - boolean, true if the Part was deleted sucessfully, false otherwise
     */
    public boolean deleteAssociatedPart(Part selectedPart) {
        for(Part part : associatedParts) {
            if(part.getId() == selectedPart.getId() &&
            part.getName() == selectedPart.getName() &&
            part.getMax() == selectedPart.getMax() &&
            part.getMin() == selectedPart.getMin() &&
            part.getPrice() == selectedPart.getPrice() &&
            part.getStock() == selectedPart.getStock()) {
                if(selectedPart instanceof InHousePart &&
                        ((InHousePart) part).getMachineId() == ((InHousePart) selectedPart).getMachineId()) {
                    return associatedParts.remove(part);
                } else if(selectedPart instanceof OutSourcedPart &&
                        ((OutSourcedPart)part).getCompanyName().equals(((OutSourcedPart) selectedPart).getCompanyName())) {
                    return associatedParts.remove(part);
                }
            }
        }
        System.out.println("No matching part was found");
        return false;
    }

    /**
     * return the Parts associated with the Product
     * @return - returns the ObservableList of Parts associated with the Product
     */
    public ObservableList<Part> getAllAssociatedParts() { return associatedParts; }






}
