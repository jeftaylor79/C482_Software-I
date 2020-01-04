/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jeffery
 */
public class Inventory 
{
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();  
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int productIdCount = 2; // Counter to keep track of Auto Generated Product Id, adjusted for existing test data.
    private static int partIdCount = 9; // Counter to keep track of Auto Generated Part Id, adjusted for test data.
    
    public static void addPart(Part newPart) 
    {
        allParts.add(newPart);
    }
    
    public static void addProduct(Product newProduct) 
    {
       allProducts.add(newProduct);
    }
    
    public static Part lookupPart(int partId) 
    {
        for (Part p : Inventory.getAllParts()) 
        {
            if (p.getId() == partId) 
            {
                return p;
            }
        }   
        return null;
    }
    
    public static Product lookupProduct(int productId) 
    {
        for (Product p : Inventory.getAllProducts())
        {
            if (p.getId() == productId) 
            {
                return p;
            }
        }
        return null;
    }
    
    public static ObservableList<Part> lookupPart(String partName) 
    {
        return allParts;
    }
    
    public static ObservableList<Product> lookupProduct(String productName) 
    {
        return allProducts;
    }
    
    public static void updatePart(int index, Part selectedPart) 
    {
        allParts.set(index, selectedPart);
    }
    
    public static void updateProduct(int index, Product selectedProduct) 
    {
        allProducts.set(index, selectedProduct);
    }
    
    public static void deletePart(Part selectedPart) 
    {
        allParts.remove(selectedPart);    
    }
    
    public static void deleteProduct(Product selectedProduct) 
    {
        allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() 
    {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() 
    {
        return allProducts;
    }   
    
    public static int getProductIdCount() 
    {
        productIdCount++;
        return productIdCount;
    }
    
    public static int getPartIdCount() 
    {
        partIdCount++;
        return partIdCount;
    }
    
}
