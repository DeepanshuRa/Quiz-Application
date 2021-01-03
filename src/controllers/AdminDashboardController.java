
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeView;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Quiz;
import models.QuizResult;
import models.Student;

public class AdminDashboardController implements Initializable {

    @FXML
    private BarChart barchart;
    @FXML
    private JFXButton logout;
    @FXML
    private JFXTreeView<?> treeview;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Map<Quiz, Integer> data = Quiz.getStudentCount();
        
        ObservableList<XYChart.Series> series = barchart.getData();
        
        XYChart.Series mainseries = new XYChart.Series();
        Set<Quiz> quizzes = data.keySet();
        
        for(Quiz quiz : quizzes){
            mainseries.getData().add(new XYChart.Data<>
                                        (quiz.getTitle()+"("+data.get(quiz)+")", data.get(quiz)));
            
            
        }    
        series.add(mainseries);
    }    

    @FXML
    private void showList(MouseEvent event) {
        Map<Quiz, List<Map<Student, Integer>>> data = QuizResult.getStudentNames();
        TreeItem root = new TreeItem("Quizzes");
        Set<Quiz> quizzes = data.keySet();
        
        for(Quiz quiz : quizzes){
            TreeItem quiztree = new TreeItem(quiz.getTitle());
            List<Map<Student, Integer>> value = data.get(quiz);
            
            for(Map<Student, Integer> key : value){
                Set<Student> students = key.keySet();
                for(Student s : students){
                    quiztree.getChildren().add(new TreeItem(s.getFirstname()+" "+s.getLastname()+"\t\t\t\t "+s.getEmail()+
                                                            "\t\t\t\t Marks  = ("+key.get(s)+")"));
                }
            }
            quiztree.setExpanded(true);
            root.getChildren().add(quiztree);
            
        }
        root.setExpanded(true);
        this.treeview.setRoot(root);
    }

    @FXML
    private void logOut(ActionEvent event) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        try {
            Parent node = fxmlloader.load();
            Stage stage = (Stage)logout.getScene().getWindow();
            Scene scene = new Scene(node);
            stage.setScene(scene);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
