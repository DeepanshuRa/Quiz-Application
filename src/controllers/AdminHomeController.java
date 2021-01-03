
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;


public class AdminHomeController implements Initializable {
    
    @FXML
    private TabPane admintabpane;
    @FXML
    private Tab addstudenttab;
    @FXML
    private Tab addquiztab;
    @FXML
    private Tab admindashboard;
    @FXML
    private Tab addAdmin;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO

            Parent node = FXMLLoader.load(getClass().getResource("/fxml/AddQuiz.fxml"));
            addquiztab.setContent(node);
            
            Parent node1 = FXMLLoader.load(getClass().getResource("/fxml/AddStudent.fxml"));
            addstudenttab.setContent(node1);
            
            Parent node2 = FXMLLoader.load(getClass().getResource("/fxml/AdminDashboard.fxml"));
            admindashboard.setContent(node2);
            
            Parent node3 = FXMLLoader.load(getClass().getResource("/fxml/AddAdmin.fxml"));
            addAdmin.setContent(node3);
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }    
    
}
