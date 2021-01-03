
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import models.Admin;
import models.Student;
import org.controlsfx.control.Notifications;

public class AddAdminController implements Initializable {

    @FXML
    private TableView<Admin> admintable;
    @FXML
    private TableColumn<Admin, String> adminidcolumn;
    @FXML
    private TableColumn<Admin, String> emailcolumn;
    @FXML
    private TableColumn<Admin, String> passwordcolumn;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField password;
    @FXML
    private JFXButton savebutton;
    @FXML
    private JFXButton reset;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        renderTable();
    }    

    
    private void renderTable() {
        List<Admin> admins = Admin.getAll();
        admintable.getItems().clear();
        
        this.adminidcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.passwordcolumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        
        admintable.getItems().addAll(admins);
    }
    
    
    @FXML
    private void saveAdmin(ActionEvent event) {
        String email = this.email.getText().trim();
        String password  = this.password.getText().trim();
        String message = null;
        
        Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([a-z0-9-]+(\\.[a-z0-9-]+)*?\\.[a-z]{2,6}|(\\d{1,3}\\.){3}\\d{1,3})(:\\d{4})?$");
        if(!p.matcher(email).matches()){
            message = "Please enter valid email";
            
        }
        else if(password.length()<6 || password.isEmpty()){
            message = "Password must be 6 char long";
        }
        
        if(message!=null){
            Notifications.create().title("Fill Details correctly")
                    .darkStyle()
                    .text(message)
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.TOP_CENTER)
                    .showError();
            
        }
        else{
            Admin a = new Admin(email, password);
            
            if(a.isExist()){
                Notifications.create().title("Failed!")
                    .darkStyle()
                    .text("Admin already registered...")
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.TOP_CENTER)
                    .showError();
                return;
            }
            
            a = a.save();
            
            if(a!=null){
                Notifications.create().title("Success")
                    .darkStyle()
                    .text("Successfully Registered")
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.TOP_CENTER)
                    .showInformation();
                this.reset();
                admintable.getItems().add(a);
            }
            else{
                
                Notifications.create().title("Failed")
                    .darkStyle()
                    .text("Admin registration failed..")
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.TOP_CENTER)
                    .showError();
            }
            
        }
    }

    @FXML
    private void resetform(ActionEvent event) {
        reset();
    }
    
    private void reset(){
        this.email.clear();
        this.password.clear();
        
    }
    
}
