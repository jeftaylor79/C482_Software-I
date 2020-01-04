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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class AddProductController implements Initializable {

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
    private TableView<Part> tempAssociatedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> tempAssociatedPartIdCol;
    @FXML
    private TableColumn<Part, Integer> tempAssociatedPartNameCol;
    @FXML
    private TableColumn<Part, Integer> tempAssociatedPartInvCountCol;
    @FXML
    private TableColumn<Part, Integer> tempAssociatedPartPriceCol;    
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
    private TextField addProductSearchField;
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
    
    // This Observable List is used to store the part association prior to the save action being executed.
    private static ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList(); 
    
    private int productId; // Used in the Auto-generated Product Id.
    
    @FXML
    private void returnToMainView(ActionEvent event) throws IOException 
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Any unsaved data wil be lost.");
        alert.setContentText("Click 'OK' to proceed. \n"
                            + "Click 'Cancel' to return to the Add Product Screen.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
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
    void onActionSaveAddProduct (ActionEvent event) throws IOException 
    {   
        try // This acts as a limited form validation.
        {   
            int id = Integer.parseInt(productIDField.getText());
            String name = productNameField.getText();
            int stock = Integer.parseInt(productInvField.getText());
            double price = Double.parseDouble(productPriceField.getText());
            int max = Integer.parseInt(productMaxField.getText());
            int min = Integer.parseInt(productMinField.getText());

            if (min > max)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Min Greater Than Max");
                alert.setHeaderText("A parts minimum quantity cannot exceed its maximum quantity");
                alert.setContentText("Click 'OK' to return to the Add Product Screen and correct the quantities.");
                alert.showAndWait();
            }
            else
            {
                // This will ensure that a product always has at least one part selected during creation.
                if (tempAssociatedParts.isEmpty())
                {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Associated Part Required");
                    alert.setHeaderText("A product must have at least one associated part.");
                    alert.setContentText("Click 'OK' to return to the Add Product Screen and add at least one part.");
                    alert.showAndWait();     
                }
                else
                {
                    Product newProduct = new Product(id, name, price, stock, min, max);
                    Inventory.addProduct(newProduct);

                    for (Part part : tempAssociatedParts)
                    {
                        newProduct.addAssociatedPart(part);
                    }

                    // Message for end user that product was successfully saved.
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("The product was successfully created.");
                    alert.showAndWait();

                    Parent returnToMainViewParent = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));  
                    Scene returnToMainViewScene = new Scene(returnToMainViewParent);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(returnToMainViewScene);
                    window.show();
                }
            }
        }
        catch (NumberFormatException e) // This acts as a limited form validation by catching any empty fields.
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Product");
            alert.setContentText("All fields are required.");
            alert.showAndWait();
        }  
    }
    
    @FXML
    void onActionAddtempPartAssociation (ActionEvent event) throws IOException
    {
        Part part = allPartsTableView.getSelectionModel().getSelectedItem();
        tempAssociatedParts.add(part);

        tempAssociatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        tempAssociatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        tempAssociatedPartInvCountCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tempAssociatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        tempAssociatedPartsTableView.setItems(tempAssociatedParts);
    }
    
    @FXML
    void onActionDeleteTempPartAssociation (ActionEvent event) throws IOException
    {
        Part associatedPart = tempAssociatedPartsTableView.getSelectionModel().getSelectedItem();
        
        if (associatedPart == null) 
        {
            System.out.println("selected a part to delete");
        }
        else
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("This action is will remove the part association immediately.");
            alert.setContentText("Click 'OK' to confirm.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                // user chose OK and parts is removed from the temporary observable list.
                tempAssociatedParts.remove(associatedPart);
            } 
            else 
            {
                // user chose CANCEL or closed the dialog. Refreshes the TableView.
                if (tempAssociatedParts.isEmpty())
                {
                System.out.println("This product has no associated parts."); // Added as a helpful console message.
                }
                else
                {
                    tempAssociatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                    tempAssociatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                    tempAssociatedPartInvCountCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                    tempAssociatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                    tempAssociatedPartsTableView.setItems(tempAssociatedParts);                
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
        // Intial population of the the All Parts TableView.
        partsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvCountCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        allPartsTableView.setItems(Inventory.getAllParts());

    /*
    * START the code blocks for the Parts Search functionality.
    */         
        // Wrap the ObservableList for all parts into a FilteredList.
        FilteredList<Part> filteredPartsDataAddProduct = new FilteredList<>(Inventory.getAllParts(), p -> true);
        
        // Set the filter Predicate whenever the filter changes.
        addProductSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPartsDataAddProduct.setPredicate(allParts -> 
            {
                
                // If filter text is empty, display all parts.
                if (newValue == null || newValue.isEmpty()) 
                {
                    return true;
                }
                
                // Compare part name for every part with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (allParts.getName().toLowerCase().contains(lowerCaseFilter)) 
                {
                    return true; // Filter matches a Part Name.                    
                }                
                else if (String.valueOf(allParts.getId()).indexOf(lowerCaseFilter) != -1)
                {
                    return true; // Filter matches a Part Id. 
                }                
                return false; // There is no match found.
                
            });
        });
        
            // Wrap the FilteredList in a SortedList. 
            SortedList<Part> sortedDataAddProduct = new SortedList<>(filteredPartsDataAddProduct);

            // Bind the SortedList comparator to the TableView comparator.
            sortedDataAddProduct.comparatorProperty().bind(allPartsTableView.comparatorProperty());

            // Add sorted (and filtered) data to the table.
            allPartsTableView.setItems(sortedDataAddProduct);
    /*
    * START the code blocks for the Parts Search functionality.
    */ 

        // Auto-generated Product Id.    
        productId = Inventory.getProductIdCount();
        productIDField.setText(Integer.toString(productId));
    }    
}
