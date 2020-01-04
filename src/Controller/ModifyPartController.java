/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jeffery
 */
public class ModifyPartController implements Initializable {

    @FXML
    private Label PartLbl;
    @FXML
    private RadioButton PartInhouseRadioBtn;
    @FXML
    private RadioButton PartOutsourcedRadioBtn;
    @FXML
    private TextField PartIdTxtFld;
    @FXML
    private TextField PartNameTxtFld;
    @FXML
    private TextField PartInvTxtFld;
    @FXML
    private TextField PartPriceTxtFld;
    @FXML
    private TextField PartMaxTxtFld;
    @FXML
    private TextField PartMinTxtFld;
    @FXML
    private Label PartIdLbl;
    @FXML
    private Label PartNameLbl;
    @FXML
    private Label PartInvLbl;
    @FXML
    private Label PartMaxLbl;
    @FXML
    private Label PartMinLbl;
    @FXML
    private TextField PartTypeTxtFld;
    @FXML
    private Label PartTypeLbl;
    @FXML
    private Button addPartSaveBtn;
    @FXML
    private Button addPartCancelBtn;

    private Part selectedPart;
    private static int index;
    private boolean isInHouse;
    
    // Allows the scene to change back to main when Cancel button is clicked.
    @FXML
    private void returnToMainView(ActionEvent event) throws IOException 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Action");
        alert.setHeaderText("Cancel Part Modification?");
        alert.setContentText("Click 'OK' to proceed. \n"
                            + "Click 'Cancel' to return to the Modify Part Screen.");

        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK)
        {
            // user chose OK and app closes.
            selectedPart = null;

            Parent returnToMainViewParent = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));  
            Scene returnToMainViewScene = new Scene(returnToMainViewParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(returnToMainViewScene);
            window.show();
        } 
        else 
        {
            // user chose CANCEL or closed the dialog.
        } 
    }     
    
    @FXML
    void onActionInhouseSelect (ActionEvent event) 
    {   
        // Populates the label for the machine id when InHouse radio is selected.
        PartTypeLbl.setText("Machine ID");
        PartTypeTxtFld.clear(); 
        isInHouse = true;
    }
    
    @FXML
    void onActionOutsourcedSelect (ActionEvent event) 
    {
        PartTypeLbl.setText("Company Name");
        PartTypeTxtFld.clear();
        isInHouse = false;
    }
       
    @FXML
    void onActionSaveModifyPart (ActionEvent event) throws IOException 
    {
        int id = Integer.parseInt(PartIdTxtFld.getText());
        String name = PartNameTxtFld.getText();
        int stock = Integer.parseInt(PartInvTxtFld.getText());
        double price = Double.parseDouble(PartPriceTxtFld.getText());
        int min = Integer.parseInt(PartMinTxtFld.getText());
        int max = Integer.parseInt(PartMaxTxtFld.getText());
        
        //  Checks for exception in which min is greater than max.
        if (min > max)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Min Greater Than Max");
            alert.setHeaderText("A parts minimum quantity cannot exceed its maximum quantity");
            alert.setContentText("Click 'OK' to return to the Modify Part Screen and correct the quantities.");
            alert.showAndWait();
        }
        else
        {
            try
            {   
                if (isInHouse) 
                {   
                    System.out.println("Modified the part successfully and saved an InHouse part."); // Console helper for debugging Modify part.             
                    int machineID = Integer.parseInt(PartTypeTxtFld.getText());

                    InHouse selectedPart = new InHouse(id, name, price, stock, min, max, machineID);

                    Inventory.updatePart(index, selectedPart);

                    // Message for end user that part was successfully modified.
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("The part was successfully modified.");
                    alert.showAndWait();

                    Parent returnToMainViewParent = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));  
                    Scene returnToMainViewScene = new Scene(returnToMainViewParent);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(returnToMainViewScene);
                    window.show();    
                } 
                else 
                {   
                    System.out.println("Modified the part successfully and saved an Outsourced part."); // Console helper for debugging Modify part.
                    String companyName = PartTypeTxtFld.getText();

                    Outsourced selectedPart = new Outsourced(id, name, price, stock, min, max, companyName);

                    Inventory.updatePart(index, selectedPart);       

                    // Message for end user that part was successfully modified.
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("The part was successfully modified.");
                    alert.showAndWait();

                    Parent returnToMainViewParent = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));  
                    Scene returnToMainViewScene = new Scene(returnToMainViewParent);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(returnToMainViewScene);
                    window.show();
                }
            }
            catch (NumberFormatException e)
            {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Modifying Part");
                    alert.setContentText("All fields are required.");
                    alert.showAndWait();            
            }
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        PartTypeLbl.setText("");
        
        selectedPart = MainScreenController.selectedPartLineItem;
        index = MainScreenController.selectedPartLineItemIndex;
        
        PartIdTxtFld.setText(Integer.toString(selectedPart.getId()));
        PartNameTxtFld.setText(selectedPart.getName());
        PartInvTxtFld.setText(Integer.toString(selectedPart.getStock()));
        PartPriceTxtFld.setText(Double.toString(selectedPart.getPrice()));
        PartMinTxtFld.setText(Integer.toString(selectedPart.getMin()));
        PartMaxTxtFld.setText(Integer.toString(selectedPart.getMax()));
        
        if (selectedPart instanceof Outsourced)
        {   
            PartTypeLbl.setText("Company Name");
            PartTypeTxtFld.setText((((Outsourced) Inventory.getAllParts().get(index)).getCompanyName()));
            PartOutsourcedRadioBtn.setSelected(true);
            isInHouse = false;
        }      
        else
        {
            PartTypeLbl.setText("Machine ID");
            PartTypeTxtFld.setText(Integer.toString(((InHouse) Inventory.getAllParts().get(index)).getMachineID()));
            PartInhouseRadioBtn.setSelected(true);
            isInHouse = true;
        }       
        
        // Console helper to show the conversion from InHouse to Outsourced, vice versa.
        System.out.println("Currently displayed part is an instance of the " + selectedPart.getClass()); 
    }    
}
