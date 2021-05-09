package inventoryApp;

/**
 * A Part made by another company. It has on top of the normal Part attributes, a machineID attribute to specify the ID
 * number of the machine that made the Part.
 */
public class OutSourcedPart extends Part {

    /**
     * String name of the Company that made the Part
     */
    private String companyName;

    /**
     * Constructor for generating the OutsourcedPart
     * @param id - id number
     * @param name - name
     * @param price - price
     * @param stock - amount of the Part in Inventory
     * @param min - minimum number of the Part to be held in Inventory
     * @param max - maximum number of the Part to be held in Inventory
     * @param companyName - String company name of the company that made the OutsourcedPart
     */
    public OutSourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Get the name of the company that made the part
     * @return - string - the company that made the part
     */
    public String getCompanyName() { return companyName; }

    /**
     * Set the name of the company that made the part
     * @param companyName - the String for Company Name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
