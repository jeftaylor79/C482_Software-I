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
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jeffery
 */
public class MainScreenController implements Initializable {

    @FXML
    private MenuBar mainMenuBar;
    @FXML
    private Button mainExitBtn;
    @FXML
    private AnchorPane partsAnchorPane;
    @FXML
    private Label partsLbl;
    @FXML
    private TextField partsSearchField;
    @FXML
    private Button partsSearchBtn;
    @FXML
    private Button partsAddBtn;
    @FXML
    private Button partsModifyBtn;
    @FXML
    private Button partsDeleteBtn;
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partsIdCol;
    @FXML
    private TableColumn<Part, String> partsNameCol;
    @FXML
    private TableColumn<Part, Integer> partsInvCountCol;
    @FXML
    private TableColumn<Part, Double> partsPriceCol;
    @FXML
    private AnchorPane productsAnchorPane;
    @FXML
    private Label productsLbl;
    @FXML
    private TextField productsSearchField;
    @FXML
    private Button productsSearchBtn;
    @FXML
    private Button productsAddBtn;
    @FXML
    private Button productsModifyBtn;
    @FXML
    private Button productsDeleteBtn;
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, Integer> productsIdCol;
    @FXML
    private TableColumn<Product, String> productsNameCol;
    @FXML
    private TableColumn<Product, Integer> productsInvCountCol;
    @FXML
    private TableColumn<Product, Double> productsPriceCol;
    
    // These are all used with getting the TableView line item and returning the correct Product and Part.
    public static Part selectedPartLineItem; 
    public static int selectedPartLineItemIndex;
    public static Product selectedProductLineItem;
    public static int selectedProductLineItemIndex;    
    
    @FXML // Opens the AddPart View.
    private void openAddPartView(ActionEvent event) throws IOException 
    {
        Parent openAddPartViewParent = FXMLLoader.load(getClass().getResource("/View/AddPart.fxml"));  
        Scene openAddPartScene = new Scene(openAddPartViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();       
        window.setScene(openAddPartScene);
        window.show();
    }
    
    @FXML // Opens the AddProduct View.
    private void openAddProductView(ActionEvent event) throws IOException 
    {
        Parent openAddProductViewParent = FXMLLoader.load(getClass().getResource("/View/AddProduct.fxml"));  
        Scene openAddProductScene = new Scene(openAddProductViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(openAddProductScene);
        window.show();
    }

    @FXML // Exit Button on Main Screen with confirmation prompt.
    void onActionExit(ActionEvent event) 
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit Application");
        alert.setHeaderText("Exit Application!");
        alert.setContentText("Click 'OK' to exit the application. \n"
                            + "Click 'Cancel' to return to the Main Screen.");

        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK)
        {
            // user chose OK and app closes.
            System.exit(0);
        } 
        else 
        {
            // user chose CANCEL or closed the dialog.
        }   
    }
    
    @FXML
    void handleDeletePart(ActionEvent event) throws IOException
    {
        Part part = partsTableView.getSelectionModel().getSelectedItem();
        
        if (part == null)
        {
            // Informational alert if the user fails to select a line item from the TableView.
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Select A Part");
            alert.setHeaderText("You need to select a Part that you would like to delete.");
            alert.setContentText("Click 'OK' to continue.");
            alert.showAndWait();
        }
        else
        {
            // Confirmation delete when deleting a part.
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Part Delete Action");
            alert.setHeaderText("Permanently deleted the selected part from the parts list? \n\n"
                                + "Note: This will NOT delete the part from any associated products.");
            alert.setContentText("Click 'OK' to proceed with the deletion. \n"
                                + "Click 'Cancel' to change your mind.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // user chose OK and part is deleted.
                Inventory.deletePart(part);            
            } 
            else 
            {
                // user chose CANCEL or closed the dialog.
            }
        }    
    }
    
    @FXML
    void handleDeleteProduct(ActionEvent event) throws IOException
    {
        Product product = productsTableView.getSelectionModel().getSelectedItem();
        
        if (product == null)
        {
            // Informational alert if the user fails to select a line item from the TableView.
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Select A Product");
            alert.setHeaderText("You need to select a Product that you would like to delete.");
            alert.setContentText("Click 'OK' to continue.");
            alert.showAndWait();            
        }
        else
        {   
            // Confirmation delete when deleting a Product.
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Product Delete Action");
            alert.setHeaderText("Permanently deleted the selected Product?");
            alert.setContentText("Click 'OK' to proceed with the deletion. \n"
                                + "Click 'Cancel' to change your mind.");

            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK)
            {
                // user chose OK and product is deleted.
                Inventory.deleteProduct(product);           
            } 
            else 
            {
                // user chose CANCEL or closed the dialog
            }   
        }
    }
    
    @FXML
    void handleModifyPart(ActionEvent event) throws IOException
    {
        selectedPartLineItem = partsTableView.getSelectionModel().getSelectedItem();
        selectedPartLineItemIndex = Inventory.getAllParts().indexOf(selectedPartLineItem);
                 
        if (selectedPartLineItem != null)
        {            
            Parent openModifyPartViewParent = FXMLLoader.load(getClass().getResource("/View/ModifyPart.fxml"));  
            Scene openModifyPartScene = new Scene(openModifyPartViewParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(openModifyPartScene);
            window.show();
        }
        else
        {
            // Informational alert in the event the user does not select a line item from the TableView.
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Select A Part");
            alert.setHeaderText("You need to select a Part that you would like to modify.");
            alert.setContentText("Click 'OK' to continue.");
            alert.showAndWait();
        }
    }
    
    @FXML
    void handleModifyProduct(ActionEvent event) throws IOException
    {
        selectedProductLineItem = productsTableView.getSelectionModel().getSelectedItem();
        selectedProductLineItemIndex = Inventory.getAllProducts().indexOf(selectedProductLineItem);
        
        if (selectedProductLineItem != null)
        {
            Parent openModifyPartViewParent = FXMLLoader.load(getClass().getResource("/View/ModifyProduct.fxml"));  
            Scene openModifyPartScene = new Scene(openModifyPartViewParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(openModifyPartScene);
            window.show();            
        }  
        else
        {
            // Informational alert in the event the user does not select a line item from the TableView.
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Select A Product");
            alert.setHeaderText("You need to select a Product that you would like to modify.");
            alert.setContentText("Click 'OK' to continue.");
            alert.showAndWait();
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {   
        // Initialization of the Products Columns in the Tableview.
        productsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInvCountCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsTableView.setItems(Inventory.getAllProducts());    
        
        // Initialization of the Parts Columns in the Tableview.
        partsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvCountCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTableView.setItems(Inventory.getAllParts());               
    

    /*
    * START the code blocks for the Parts Search functionality.
    */        
        // Wrap the ObservableList for all parts into a FilteredList.
        FilteredList<Part> filteredPartsData = new FilteredList<>(Inventory.getAllParts(), p -> true);
        
        // Set the filter Predicate whenever the filter changes.
        partsSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPartsData.setPredicate(part -> 
            {
                // If filter text is empty, display all parts.
                if (newValue == null || newValue.isEmpty()) 
                {
                    return true;
                }
                
                // Compare part name for every part with partsSearchField text.
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (part.getName().toLowerCase().contains(lowerCaseFilter)) 
                {
                    return true; // Filter matches Part Name.                    
                }
                else if (String.valueOf(part.getId()).indexOf(lowerCaseFilter) != -1) // Filter matches Part Id.
                {
                    return true;
                }
                
                return false; // Does not match.
            });
        });
        
        // Wrap the FilteredList in a SortedList. 
        SortedList<Part> sortedData = new SortedList<>(filteredPartsData);
        
        // Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(partsTableView.comparatorProperty());
        
        // Add sorted (and filtered) data to the table.
        partsTableView.setItems(sortedData);
    /*
    * END the code blocks for the Parts Search functionality.
    */
    
    
    /*
    * START the code blocks for the Products Search functionality.
    */ 
        // Wrap the ObservableList for all products into a FilteredList.
        FilteredList<Product> filteredProductsData = new FilteredList<>(Inventory.getAllProducts(), p -> true);
        
        // Set the filter Predicate whenever the filter changes.
        productsSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProductsData.setPredicate(product -> 
            {
                
                // If filter text is empty, display all products.
                if (newValue == null || newValue.isEmpty()) 
                {
                    return true;
                }
                
                // Compare part name for every product with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (product.getName().toLowerCase().contains(lowerCaseFilter)) 
                {
                    return true; // Filter matches Product Name.
                }                
                else if (String.valueOf(product.getId()).indexOf(lowerCaseFilter) != -1) // Filter matches Product ID.
                {
                    return true;
                }
                
                return false; // Does not match.                
            });
        });
        
        // Wrap the FilteredList in a SortedList. 
        SortedList<Product> sortedProductData = new SortedList<>(filteredProductsData);
        
        // Bind the SortedList comparator to the TableView comparator.
        sortedProductData.comparatorProperty().bind(productsTableView.comparatorProperty());
        
        // Add sorted (and filtered) data to the table.
        productsTableView.setItems(sortedProductData);
    /*
    * START the code blocks for the Products Search functionality.
    */ 
    }
}