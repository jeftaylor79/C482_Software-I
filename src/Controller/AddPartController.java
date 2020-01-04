/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
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
import javafx.scene.control.Alert.AlertType;
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
public class AddPartController implements Initializable {

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

    private boolean isInHouse;
    
    private int partId; // Used in the AutoGen Process.
    
    // Allows the scene to change back to main when Cancel button is clicked.
    @FXML
    private void returnToMainView(ActionEvent event) throws IOException 
    {        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Action");
        alert.setHeaderText("Cancel Part Creation?");
        alert.setContentText("Click 'OK' to proceed. \n"
                            + "Click 'Cancel' to return to the Add Part Screen.");

        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK)
        {
            // user chose OK and is returned to the Main Screen.
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
        PartTypeLbl.setText("Machine ID"); 
        isInHouse = true;   
    }
    
    @FXML
    void onActionOutsourcedSelect (ActionEvent event) 
    {
        PartTypeLbl.setText("Company Name");
        isInHouse = false;
    }
    
    @FXML
    void onActionSaveAddPart (ActionEvent event) throws IOException 
    {
        int id = Integer.parseInt(PartIdTxtFld.getText());
        String name = PartNameTxtFld.getText();
        int stock = Integer.parseInt(PartInvTxtFld.getText());
        double price = Double.parseDouble(PartPriceTxtFld.getText());
        int max = Integer.parseInt(PartMaxTxtFld.getText());
        int min = Integer.parseInt(PartMinTxtFld.getText());
        
        if (isInHouse) 
        {   
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
                try // This try statement acts as a limited form validation if any of the fields are left empty.
                {

                    int machineID = Integer.parseInt(PartTypeTxtFld.getText());


                    InHouse newPart = new InHouse(id, name, price, stock, min, max, machineID);
                    Inventory.addPart(newPart);

                    // Message for end user that part was successfully saved.
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("The part was successfully created.");
                    alert.showAndWait();

                    Parent returnToMainViewParent = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));  
                    Scene returnToMainViewScene = new Scene(returnToMainViewParent);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(returnToMainViewScene);
                    window.show();
                }
                catch (NumberFormatException e) // This catchs the NFE and acts as a limited form validation for any empty fields.
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Adding Part");
                    alert.setContentText("All fields are required.");
                    alert.showAndWait();
                }
            }
        } 
        else 
        {
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
            
                try // This try statement acts as a limited form validation if any of the fields are left empty.
                {

                    String companyName = PartTypeTxtFld.getText();

                    Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(newPart);

                    // Message for end user that part was successfully saved.
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("The part was successfully created.");
                    alert.showAndWait();                

                    Parent returnToMainViewParent = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));  
                    Scene returnToMainViewScene = new Scene(returnToMainViewParent);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(returnToMainViewScene);
                    window.show();  
                }
                catch (NumberFormatException e) // This catchs the NFE and acts as a limited form validation for any empty fields.
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Adding Part");
                    alert.setContentText("All fields are required.");
                    alert.showAndWait();
                }
            }
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        PartTypeLbl.setText(""); // Clears the label to allow it to dynamically change based on the radio selection made.
        
        // Auto-generated Part ID counter.
        partId = Inventory.getPartIdCount();
        PartIdTxtFld.setText(Integer.toString(partId)); 
    }    
}
