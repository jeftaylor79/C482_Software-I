/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jeffery
 */
public class ModifyProductController implements Initializable {

    @FXML
    private Label productIDLbl;
    @FXML
    private Label productNameLbl;
    @FXML
    private Label productInvLbl;
    @FXML
    private Label productPriceLbl;
    @FXML
    private Label productMaxLbl;
    @FXML
    private Label productMinLbl;
    @FXML
    private Label productLbl;
    @FXML
    private TableView<Part> allPartsTableView;
    @FXML
    private TableColumn<Part, Integer> partsIdCol;
    @FXML
    private TableColumn<Part, String> partsNameCol;
    @FXML
    private TableColumn<Part, Integer> partsInvCountCol;
    @FXML
    private TableColumn<Part, Integer> partsPriceCol;
    @FXML
    private TableView<Part> associatedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;
    @FXML
    private TableColumn<Part, Integer> associatedPartNameCol;
    @FXML
    private TableColumn<Part, Integer> associatedPartInvCountCol;   
    @FXML
    private TableColumn<Part, Integer> associatedPartPriceCol;
    @FXML
    private Button productSearchBtn;
    @FXML
    private Button productAddBtn;
    @FXML
    private Button productDeleteBtn;
    @FXML
    private Button productCancelBtn;
    @FXML
    private Button productSaveBtn;
    @FXML
    private TextField modifyProductSearchField;
    @FXML
    private TextField productIDField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField productInvField;
    @FXML
    private TextField productPriceField;
    @FXML
    private TextField productMaxField;
    @FXML
    private TextField productMinField;

    private static Product selectedProduct;
    private static int index;
    
    @FXML // Cancel button action.
    private void returnToMainView(ActionEvent event) throws IOException 
    {
        if (selectedProduct.getAllAssociatedParts().isEmpty())
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Associated Part Required");
            alert.setHeaderText("A product must have at least one associated part.");
            alert.setContentText("Click 'OK' to return to the Add Product Screen and add at least one part.");
            alert.showAndWait();        
        }
        else
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Any unsaved product data will be reverted.");
            alert.setContentText("Click 'OK' to return to the Main Screen.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // user chose OK and is returned to Main Screen.
                Parent returnToMainViewParent = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));  
                Scene returnToMainViewScene = new Scene(returnToMainViewParent);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(returnToMainViewScene);
                window.show();
            } 
            else 
            {
                // user chose CANCEL or closed the dialog
            }
        }    
    }    
       
    @FXML
    void onActionSaveModifyProduct (ActionEvent event) throws IOException 
    {   
        try //  This try statement will attempt to pass the above values and if interrupted by NFE, it will present a message to the end user. 
        {
            String productName = productNameField.getText();
            int productStock = Integer.parseInt(productInvField.getText());
            double productPrice = Double.parseDouble(productPriceField.getText());
            int productMax = Integer.parseInt(productMaxField.getText());
            int productMin = Integer.parseInt(productMinField.getText());

            if (productMin > productMax)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Min Greater Than Max");
                alert.setHeaderText("A parts minimum quantity cannot exceed its maximum quantity");
                alert.setContentText("Click 'OK' to return to the Modify Product Screen and correct the quantities.");
                alert.showAndWait();
            } 
            else
            {
                selectedProduct.setName(productName);
                selectedProduct.setStock(productStock);
                selectedProduct.setPrice(productPrice);
                selectedProduct.setMax(productMax);
                selectedProduct.setMin(productMin);

                // Update the product.
                Inventory.updateProduct(index, selectedProduct);

                // Message for end user that product was successfully modified.
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("The product was successfully modified.");
                alert.showAndWait();                 

                // Returns the user to the Main Screen.
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
            alert.setHeaderText("Error Modifying Product");
            alert.setContentText("All fields are required.");
            alert.showAndWait(); 
        }
    }
    
    @FXML
    void onActionAddPartAssociation (ActionEvent event) throws IOException
    { 
        Part part = allPartsTableView.getSelectionModel().getSelectedItem();
        
        if (part == null)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Select A Part");
            alert.setHeaderText("Select a part to associated with this product.");
            alert.setContentText("Highlight a part line item before trying that action again");
            alert.showAndWait();
        }    
        else
        {
            selectedProduct.addAssociatedPart(part);

            associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            associatedPartInvCountCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            associatedPartsTableView.setItems(selectedProduct.getAllAssociatedParts());
        }
    }
    
    @FXML
    void onActionDeletePartAssociation (ActionEvent event) throws IOException
    {
        Part associatedPart = associatedPartsTableView.getSelectionModel().getSelectedItem();
        
        if (selectedProduct.getAllAssociatedParts().size() <= 1)
        {
            Alert alert1 = new Alert(AlertType.ERROR);
            alert1.setTitle("Associated Part Required");
            alert1.setHeaderText("This product must have at least one associated part.");
            alert1.setContentText("Click 'OK' to return to the Modify Product Screen.");
            alert1.showAndWait(); 
        } 
        else if (associatedPart == null) 
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Select A Part");
            alert.setHeaderText("Select a part to delete.");
            alert.setContentText("Highlight a part line item before trying that action again");
            alert.showAndWait();
        }
        else
        {          
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("This action is will remove the part association immediately.");
            alert.setContentText("Click 'OK' to confirm.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // user chose OK
                selectedProduct.deleteAssociatedPart(associatedPart);
                
                associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                associatedPartInvCountCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                associatedPartsTableView.setItems(selectedProduct.getAllAssociatedParts()); 
            } 
            else 
            {
                // user chose CANCEL or closed the dialog
            }
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        selectedProduct = MainScreenController.selectedProductLineItem;
        index = MainScreenController.selectedProductLineItemIndex;        
        
        partsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvCountCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        allPartsTableView.setItems(Inventory.getAllParts());
        
        if (selectedProduct.getAllAssociatedParts().isEmpty()) 
        {
            System.out.println("There are no associated parts to this product."); // Console message.
        }
        else
        {
            associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            associatedPartInvCountCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            associatedPartsTableView.setItems(selectedProduct.getAllAssociatedParts());

    /*
    * START the code blocks for the Parts Search functionality.
    */  
            // Wrap the ObservableList for all parts into a FilteredList.
            FilteredList<Part> filteredAssociatedPartsDataModifyProduct = new FilteredList<>(selectedProduct.getAllAssociatedParts(), p -> true);

            // Set the filter Predicate whenever the filter changes.
            modifyProductSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredAssociatedPartsDataModifyProduct.setPredicate(selectedPart -> 
                {

                    // If filter text is empty, display all parts.
                    if (newValue == null || newValue.isEmpty()) 
                    {
                        return true;
                    }

                    // Compare part name for every part with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (selectedPart.getName().toLowerCase().contains(lowerCaseFilter)) 
                    {
                        return true; // Filter matches part name.

                    }

                    else if (String.valueOf(selectedPart.getId()).indexOf(lowerCaseFilter) != -1)
                    {
                        return true;
                    }

                    return false; // Does not match.

                });
            });

            // Wrap the FilteredList in a SortedList. 
            SortedList<Part> sortedAssociatedPartsData = new SortedList<>(filteredAssociatedPartsDataModifyProduct);

            // Bind the SortedList comparator to the TableView comparator.
            sortedAssociatedPartsData.comparatorProperty().bind(associatedPartsTableView.comparatorProperty());

            // Add sorted (and filtered) data to the table.
            associatedPartsTableView.setItems(sortedAssociatedPartsData);
    /*
    * END the code blocks for the Parts Search functionality.
    */      
        }
          
        
        productIDField.setText(Integer.toString(selectedProduct.getId()));
        productNameField.setText(selectedProduct.getName());
        productInvField.setText(Integer.toString(selectedProduct.getStock()));
        productPriceField.setText(Double.toString(selectedProduct.getPrice()));
        productMinField.setText(Integer.toString(selectedProduct.getMin()));
        productMaxField.setText(Integer.toString(selectedProduct.getMax()));
    
        
    /*
    * START the code blocks for the Parts Search functionality.
    */          
        // Wrap the ObservableList for all parts into a FilteredList.
        FilteredList<Part> filteredPartsDataModifyProduct = new FilteredList<>(Inventory.getAllParts(), p -> true);
        
        // Set the filter Predicate whenever the filter changes.
        modifyProductSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPartsDataModifyProduct.setPredicate(part -> 
            {
                
                // If filter text is empty, display all parts.
                if (newValue == null || newValue.isEmpty()) 
                {
                    return true;
                }
                
                // Compare part name for every part with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (part.getName().toLowerCase().contains(lowerCaseFilter)) 
                {
                    return true; // Filter matches first name.
                    
                }
                
                else if (part.getName().toLowerCase().contains(lowerCaseFilter)) 
                {  
                    return true; // Filter matches last name.
                    
                } 
                
                else if (String.valueOf(part.getId()).indexOf(lowerCaseFilter) != -1)
                {
                    return true;
                }
                
                return false; // Does not match.
                
            });
        });
        
            // Wrap the FilteredList in a SortedList. 
            SortedList<Part> sortedData = new SortedList<>(filteredPartsDataModifyProduct);

            // Bind the SortedList comparator to the TableView comparator.
            sortedData.comparatorProperty().bind(allPartsTableView.comparatorProperty());

            // Add sorted (and filtered) data to the table.
            allPartsTableView.setItems(sortedData);
    /*
    * END the code blocks for the Parts Search functionality.
    */              
    }    
}
