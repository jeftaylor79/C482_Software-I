/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;


import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jeffery
 */
public class IMS extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Test data for Product.
        Product product1 = new Product(1, "Basketball Hoop Starter Set", 124.99, 5, 3, 9);
        Product product2 = new Product(2, "Amateur Golf Starter Set", 199.99, 10, 4, 13);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        
        // Test data for Parts.
        InHouse ipart1 = new InHouse(1, "Top Section - 3.5in. Round Basketball Pole", 15.53, 5, 2, 10, 18642783);
        InHouse ipart2 = new InHouse(2, "Middle Section - 3.5in. Round Basketball Pole ", 10.26, 5, 2, 10, 18642783);
        InHouse ipart3 = new InHouse(3, "Bottom Section - 3.5in. Round Basketball Pole ", 15.53, 5, 2, 10, 18642783);
        InHouse ipart4 = new InHouse(4, "Set - Nuts & Bolts - 3.5in. Round Basketball Pole", 15.53, 7, 5, 15, 18642899);
        Outsourced opart1 = new Outsourced(5, "48in. Backboard", 50.88, 5, 3, 12,"Spalding Corporation");
        Outsourced opart2 = new Outsourced(6, "Set - 18in. Rim & Nylon Net", 18.98, 5, 3, 15,"Spalding Corporation");
        InHouse ipart5 = new InHouse(7, "Donald Trump Titanium Driver", 59.95, 7, 5, 15, 18677457);
        InHouse ipart6 = new InHouse(8, "UnImpeachable Pro Putter", 25.95, 10, 5, 15, 18672458);
        Outsourced opart3 = new Outsourced(9, "101 Bucket of Top Flite Golf Balls", 14.38, 55, 15, 150,"Top Flite");
        Inventory.addPart(ipart1);
        Inventory.addPart(ipart2);
        Inventory.addPart(ipart3);
        Inventory.addPart(ipart4);
        Inventory.addPart(opart1);
        Inventory.addPart(opart2);
        Inventory.addPart(ipart5);
        Inventory.addPart(ipart6);
        Inventory.addPart(opart3);

        // Associated Parts to Products.
        product1.addAssociatedPart(ipart1);
        product1.addAssociatedPart(ipart2);
        product1.addAssociatedPart(ipart3);
        product1.addAssociatedPart(ipart4);
        product1.addAssociatedPart(opart1);
        product1.addAssociatedPart(opart2);
        product2.addAssociatedPart(ipart6);
        product2.addAssociatedPart(ipart5);
        product2.addAssociatedPart(opart3);
        
        launch(args);
    }
}
