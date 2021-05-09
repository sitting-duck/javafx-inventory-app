package inventoryApp;

/**
 * A Part that was made in house. It has on top of the normal Part attributes, a machineID attribute to specify
 * the ID number of the machine that made the Part.
 */
public class InHousePart extends Part {

    /**
     * Integer machine ID of the machine that made the Part
     */
    private int machineId;

    /**
     * Contructor for generating an InHousePart
     * @param id - id number
     * @param name - name
     * @param price - price
     * @param stock - amount of the Part in Inventory
     * @param min - minimum number of the Part to be held in Inventory
     * @param max - maximum number of the Part to be held in Inventory
     * @param machineId - Integer machine ID for the machine that made the InHousePart
     */
    public InHousePart(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * gets the MachineID
     * @return - integer machine ID
     */
    public int getMachineId() { return machineId; }

    /**
     * sets the machineID
     * @param machineId - the new integer machine ID
     */
    public void setMachineId(int machineId) { this.machineId = machineId; }
}
