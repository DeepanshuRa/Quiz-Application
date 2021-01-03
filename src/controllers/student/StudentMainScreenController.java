
package controllers.student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import listeners.NewScreenListener;
import models.Student;


public class StudentMainScreenController implements Initializable {

    @FXML
    private JFXButton back;
    @FXML
    private StackPane stackpanel;
    
    private Student student;
    @FXML
    private JFXTabPane studenttabpane;
    @FXML
    private Tab attemptedquiz;
    @FXML
    private Tab dashboard;
    @FXML
    private Label name;
    @FXML
    private Label mobile;
    @FXML
    private Label email;
    @FXML
    private Label password;
    @FXML
    private Label gender;
    @FXML
    private JFXButton logout;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
    }   

    

    
    public void setStudent(Student student) {
        this.student = student;
        addQuizListScreen();
        setAttemptedQuizScreen();
        setFields();
        
    }

    
    private void setFields(){
        name.setText(student.getFirstname()+" "+student.getLastname());
        mobile.setText(student.getMobileno());
        email.setText(student.getEmail());
        password.setText(student.getPassword());
        gender.setText(student.getGender().toString());
    }
    
    private void setAttemptedQuizScreen(){
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/student/AttemptedQuiz.fxml"));
        try{
            Parent node = fxmlloader.load();
            AttemptedQuizController controller = fxmlloader.getController();
            controller.setStudent(student);
        attemptedquiz.setContent(node);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
   
    
    
    private void addScreenToStackPane(Parent node){
        this.stackpanel.getChildren().add(node);
    }
    
    private void addQuizListScreen(){
        try{
           FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/student/QuizList.fxml"));
           
           Parent node = fxmlloader.load();
           QuizListController quizlistcontroller = fxmlloader.getController();
           quizlistcontroller.setStudent(this.student);
           quizlistcontroller.setScreenlistener(new NewScreenListener() {
               @Override
               public void changeScreen(Parent node) {
                   addScreenToStackPane(node);
               }

               @Override
               public void handle(Event event) {
                   
               }

               @Override
               public void removetopscreen() {
                   stackpanel.getChildren().remove(stackpanel.getChildren().size()-1);
               }
           });
           quizlistcontroller.setCards();
           stackpanel.getChildren().add(node);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    @FXML
    private void back(ActionEvent event) {
        ObservableList<Node> nodes = this.stackpanel.getChildren();
        if(nodes.size()==1){
            return;
        }
        this.stackpanel.getChildren().remove(nodes.size()-1);
    }

    @FXML
    private void logOut(ActionEvent event) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        try {
            Parent node = fxmlloader.load();
            Stage stage = (Stage)gender.getScene().getWindow();
            Scene scene = new Scene(node);
            stage.setScene(scene);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
