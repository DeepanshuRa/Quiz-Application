package controllers;

import Exceptions.LoginException;
import constant.AdminEmailPassword;
import controllers.student.StudentMainScreenController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.management.Notification;
import models.Admin;
import models.Student;
import org.controlsfx.control.Notifications;


public class LoginController implements Initializable {
    
    
    @FXML
    private TextField adminemail;
    @FXML
    private TextField adminpassword;
    @FXML
    private Button adminloginbutton;
    @FXML
    private TextField studentemail;
    @FXML
    private TextField studentpassword;
    @FXML
    private Button studentloginbutton;
    @FXML
    private Tab studenttab;
    @FXML
    private Tab admintab;
    @FXML
    private Hyperlink signup;
    
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    
    @FXML
    private void loginStudent(ActionEvent event) {
       
        Student s = new Student(this.studentemail.getText().trim(), this.studentpassword.getText().trim());
        try{
            s.login();
            System.out.println(s);
            
            try{
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/student/StudentMainScreen.fxml"));
                Parent root = fxmlloader.load();
                StudentMainScreenController controller = fxmlloader.getController();
                controller.setStudent(s);
           Stage stage = (Stage)studentpassword.getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.setMaximized(true);
            }catch(Exception e){
                e.printStackTrace();
                System.exit(0);
            }
            
        }catch(Exception e){
            if(e instanceof LoginException){
                Notifications.create()
                        .darkStyle()
                        .title("Login Failed")
                        .text("Email or password incorrect")
                        .position(Pos.TOP_CENTER)
                        .showInformation();
            }
        }
    }

    @FXML
    private void loginAdmin(ActionEvent event) {
        Admin a = new Admin(this.adminemail.getText().trim(), this.adminpassword.getText().trim());
        try{
            a.login();
            System.out.println(a);
            
            try{
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/AdminHome.fxml"));
                Parent root = fxmlloader.load();
                
           Stage stage = (Stage)studentpassword.getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.setMaximized(true);
            }catch(Exception e){
                e.printStackTrace();
                System.exit(0);
            }
            
        }catch(Exception e){
            if(e instanceof LoginException){
                Notifications.create()
                        .darkStyle()
                        .title("Login Failed")
                        .text("Email or password incorrect")
                        .position(Pos.TOP_CENTER)
                        .showInformation();
            }
        }
    }

    @FXML
    private void signUp(ActionEvent event) {
        try{
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/student/SignUpStudent.fxml"));
                Parent root = fxmlloader.load();
                
           Stage stage = (Stage)studentpassword.getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
                       }catch(Exception e){
                e.printStackTrace();
                System.exit(0);
            }
    }
    
}
