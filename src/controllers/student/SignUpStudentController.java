
package controllers.student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Student;
import org.controlsfx.control.Notifications;


public class SignUpStudentController implements Initializable {

    @FXML
    private VBox formcontainer;
    @FXML
    private JFXTextField firstname;
    @FXML
    private JFXTextField lastname;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField password;
    @FXML
    private JFXTextField mobileno;
    @FXML
    private JFXRadioButton male;
    @FXML
    private JFXRadioButton female;
    @FXML
    private JFXButton signup;
    @FXML
    private JFXButton reset;
    @FXML
    private JFXButton back;
    
    private ToggleGroup radiogroup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        radiobuttonsetup();
    }    

    private void radiobuttonsetup(){
        radiogroup = new ToggleGroup();
        male.setToggleGroup(radiogroup);
        female.setToggleGroup(radiogroup);
    }
    
    
    @FXML
    private void signup(ActionEvent event) {
        String firstname = this.firstname.getText().trim();
        String lastname = this.lastname.getText().trim();
        String email = this.email.getText().trim();
        String password  = this.password.getText().trim();
        String mobileno = this.mobileno.getText().trim();
        Character gender = null;
        
        String message = null;
        
        Toggle selected = radiogroup.getSelectedToggle();
        if(selected == female){
            gender = 'F';
        }
        else if(selected == male){
            gender = 'M';
        }
        else{
            message = "Please select gender";
        }
        
        
        Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([a-z0-9-]+(\\.[a-z0-9-]+)*?\\.[a-z]{2,6}|(\\d{1,3}\\.){3}\\d{1,3})(:\\d{4})?$");
        
        if(firstname.length()<4 || firstname.isEmpty()){
            message = "Enter valid firstname";
        }
        else if(lastname.length()<2 || lastname.isEmpty()){
            message = "Enter valid lastname";
        }
        else if(!p.matcher(email).matches()){
            message = "Please enter valid email";
            
        }
        else if(password.length()<6 || password.isEmpty()){
            message = "Password must be 6 char long";
        }
        else if(mobileno.length()<10 || mobileno.isEmpty()){
            message = "Enter valid mobile number";
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
            //save student
            Student s = new Student(firstname,lastname,mobileno,gender,email,password);
            
            if(s.isExist()){
                Notifications.create().title("Failed!")
                    .darkStyle()
                    .text("Student already registered...")
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.TOP_CENTER)
                    .showError();
                return;
            }
            System.out.println(s);
            s = s.save();
            System.out.println(s);
            if(s!=null){
                Notifications.create().title("Success")
                    .darkStyle()
                    .text("Successfully Registered")
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.TOP_CENTER)
                    .showInformation();
                this.reset();
                
                this.backScreen();
            }
            else{
                
                Notifications.create().title("Failed")
                    .darkStyle()
                    .text("Student registration failed..")
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
        this.firstname.clear();
        this.lastname.clear();
        this.email.clear();
        this.mobileno.clear();
        this.password.clear();
        
    }

    @FXML
    private void back(ActionEvent event) {
        backScreen();
    }
    
    private void backScreen(){
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        try {
            Parent node = fxmlloader.load();
            Stage stage = (Stage)male.getScene().getWindow();
            Scene scene = new Scene(node);
            stage.setScene(scene);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
