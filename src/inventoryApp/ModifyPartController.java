package inventoryApp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the ModifyPart form
 */
public class ModifyPartController implements Initializable {

    /**
     * The Part currently being edited
     */
    public static Part part;

    /**
     * A copy made of the orginal Part passed into this form for editing. PartOriginal is used to delete the old part from
     * Inventory before we replace it with the new edited part in the case that the user decides to save.
     */
    public static Part partOriginal;

    /**
     * Holds the Part Id. Disabled and cannot be edited.
     */
    public TextField idTextField;

    /**
     * A field where the user can input a name for the Part being created
     */
    public TextField nameTextField;

    /**
     * A field where the user can input the amount of this part being created in inventory
     */
    public TextField invTextField;

    /**
     * A field where the user can input the price for the part being created
     */
    public TextField priceTextField;

    /**
     * The minimum number of the Part type being created that can be in Inventory
     */
    public TextField minTextField;

    /**
     * The maximum number of the Part type being created that can be Inventory
     */
    public TextField maxTextField;

    /**
     * If the part being created is an InHousePart this text field will take a machineId to represent the ID of the machine
     * that created the part. If it is an outSourcedPart this field will take a CompanyName to represent the company that made
     * the part being created.
     */
    public TextField bottomTextField;

    /**
     * Labels the bottomTextField. Says "Machine ID" when the part being created is an InHousePart, and "Company Name" if the part
     */
    public Label bottomTextFieldLabel;

    /**
     * A listener that changes based on whether the part being created is an InHousePart or an OutSourcedPart.
     * If it is an InHousePart then the textField will only accept numbers for the machineId,
     * If it is an outSourcedPart then the textField will accept all characters as acceptable input for the CompanyName
     * Updates based on whether the InHousePart radio button is selected, or the outsourcedPartRadioButton is selected
     */
    public ChangeListener<String> bottomTextFieldListener;

    /**
     * Toggle group that makes sure that only either inHouseRadioButton or outSourcedRadioButton can be selected
     */
    private ToggleGroup toggleGroup = new ToggleGroup();
    public RadioButton outsourcedRadioButton;
    public RadioButton inHouseRadioButton;

    /**
     * A static instance of the Inventory so that once the Part is finished being edited by the user, it can be added to the Inventory
     */
    private static Inventory inventory = new Inventory();

    /**
     * Sets listeners to filter all the text fields input and sets the product being modified
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(part instanceof InHousePart) {
            System.out.println("Part is InHousePart");
            partOriginal = new InHousePart(part.getId(), part.getName(), part.getPrice(), part.getStock(), part.getMin(), part.getMax(), ((InHousePart) part).getMachineId());
            inHouseRadioButton.setSelected(true);
        } else if(part instanceof OutSourcedPart) {
            System.out.println("Part is OutsourcedPart");
            partOriginal = new OutSourcedPart(part.getId(), part.getName(), part.getPrice(), part.getStock(), part.getMin(), part.getMax(), ((OutSourcedPart) part).getCompanyName());
            outsourcedRadioButton.setSelected(true);
        }

        minTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")) {
                    minTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        maxTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")) {
                    maxTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        if(part instanceof InHousePart) {
            bottomTextFieldLabel.setText("Machine ID");
        }


        if(part instanceof OutSourcedPart) {
            bottomTextFieldLabel.setText("Company Name");
        }

        inHouseRadioButton.setToggleGroup(toggleGroup);
        outsourcedRadioButton.setToggleGroup(toggleGroup);

        idTextField.setText(Integer.toString(part.getId()));
        idTextField.setEditable(false);
        idTextField.setDisable(true);
        nameTextField.setText(part.getName());
        priceTextField.setText(Double.toString(part.getPrice()));
        maxTextField.setText(Integer.toString(part.getMax()));
        minTextField.setText(Integer.toString(part.getMin()));
        invTextField.setText(Integer.toString(part.getStock()));
        if(part instanceof InHousePart) {
            bottomTextField.setText(Integer.toString(((InHousePart) part).getMachineId()));
        } else if(part instanceof OutSourcedPart) {
            bottomTextField.setText(((OutSourcedPart) part).getCompanyName());
        }
    }

    /**
     * Sets the Label of the bottomTextField to "Machine ID" when the inHouseRadioButton is selected
     * @param actionEvent - user clicks the InHouseRadioButton
     */
    public void setLabelToMachineID(ActionEvent actionEvent) {
        System.out.println("setLabelToMachineID");
        bottomTextFieldLabel.setText("Machine ID");
        bottomTextField.setText("");
    }

    /**
     * Sets the Label of the bottomTextField to "Company Name" when the outsourcedRadioButton is selected
     * @param actionEvent - InHouseRadioButton is clicked
     */
    public void setLabelToCompanyName(ActionEvent actionEvent) {
        System.out.println("setLabelToCompanyName");
        bottomTextFieldLabel.setText("Company Name");
        bottomTextField.setText("");
    }

    /**
     * Validates the data in all the text fields and creates a Part object which is then added to the Inventory, and then the program returns
     * to the MainForm
     * @param actionEvent - user clicks the save button
     * @throws IOException - thrown if the MainForm is not found to be loaded.
     *
     * RUNTIME ERROR - Before adding a try-catch block around each user input conversion, many runtime errors
     * were encountered. By adding a try-catch block I was able to catch these conversion errors and show the
     * user an appropriate warning
     *
     */
    public void savePart(ActionEvent actionEvent) throws IOException {
        String newName = nameTextField.getText();

        Double newPrice;
        try {
            newPrice = Double.parseDouble(priceTextField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing Price to Integer: " + priceTextField.getText());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Price");
            alert.setHeaderText("Invalid Price");
            alert.setContentText("Invalid Price");
            alert.showAndWait();
            return;
        }

        Integer newMin;
        try {
            newMin = Integer.parseInt(minTextField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing Min to Integer: " + minTextField.getText());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Min");
            alert.setHeaderText("Invalid Min");
            alert.setContentText("Invalid Min");
            alert.showAndWait();
            return;
        }

        Integer newMax;
        try {
            newMax = Integer.parseInt(maxTextField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing Max to Integer: " + maxTextField.getText());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Max");
            alert.setHeaderText("Invalid Max");
            alert.setContentText("Invalid Max");
            alert.showAndWait();
            return;
        }

        if(newMax < newMin) {
            System.out.println("Error: Max must be greater than min");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error: Max must be greater than min");
            alert.setHeaderText("Error: Max must be greater than min");
            alert.setContentText("Error: Max must be greater than min");
            alert.showAndWait();
            return;
        }

        Integer newStock;
        try {
            newStock = Integer.parseInt(invTextField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing Inventory/Stock to Integer: " + invTextField.getText());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Inv");
            alert.setHeaderText("Invalid Inv");
            alert.setContentText("Invalid Inv");
            alert.showAndWait();
            return;
        }

        if(newStock < newMin) {
            System.out.println("Error: Inventory must be greater than min");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error: Inventory must be greater than min");
            alert.setHeaderText("Error: Inventory must be greater than min");
            alert.setContentText("Error: Inventory must be greater than min");
            alert.showAndWait();
            return;
        }

        if(newStock > newMax) {
            System.out.println("Error: Inventory must be less than max");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error: Inventory must be less than max");
            alert.setHeaderText("Error: Inventory must be less than max");
            alert.setContentText("Error: Inventory must be less than max");
            alert.showAndWait();
            return;
        }

        int newMachineId = -1;
        String newCompanyName = "";
        if(inHouseRadioButton.isSelected()) {
            try {
                newMachineId = Integer.parseInt(bottomTextField.getText());
            } catch (NumberFormatException e) {
                System.out.println("Error parsing machineId to Integer: " + bottomTextField.getText());
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Machine ID");
                alert.setHeaderText("Invalid Machine ID");
                alert.setContentText("Invalid Machine ID");
                alert.showAndWait();
                return;
            }
        } else if(outsourcedRadioButton.isSelected()) {
            newCompanyName = bottomTextField.getText();
            if(newCompanyName.length() == 0) {
                System.out.println("Error: Empty company name");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error: Empty company name");
                alert.setHeaderText("Error: Empty company name");
                alert.setContentText("Error: Empty company name");
                alert.showAndWait();
                return;
            }
        }
        inventory.deletePart(partOriginal);

        if(inHouseRadioButton.isSelected()) {
            part = new InHousePart(part.getId(), newName, newPrice, newStock, newMin, newMax, newMachineId);
        } else if(outsourcedRadioButton.isSelected()) {
            part = new OutSourcedPart(part.getId(), newName, newPrice, newStock, newMin, newMax, newCompanyName);
        }
        inventory.addPart(part);
        toMainForm(actionEvent);
    }

    /**
     * Returns the program from the ModifyProductForm (the current form) the MainForm (the initial program screen that shows two tables)
     * @param actionEvent user clicks the save or cancel button
     * @throws IOException - thrown if the MainForm is not found to be loaded
     */
    public void toMainForm(ActionEvent actionEvent) throws IOException {
        if(outsourcedRadioButton.isSelected()) {
            System.out.println("Outsourced");
        }
        if(inHouseRadioButton.isSelected()) {
            System.out.println("In House");
        }

        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle("MainForm");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * A static function called from the MainForm before this form is loaded. It passes the Part object to be modified to this
     * form before it is loaded.
     * @param _part - new part to be set
     */
    public static void setPart(Part _part) {
        part = _part;
    }
}
