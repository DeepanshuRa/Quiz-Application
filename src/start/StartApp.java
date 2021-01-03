
package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Admin;
import models.Question;
import models.Quiz;
import models.QuizResult;
import models.QuizResultDetails;
import models.Student;

public class StartApp extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        createtables();
        
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/AdminHome.fxml"));
        //Stage stage = new Stage();
        Scene scene = new Scene(root);
        
        Image icon = new Image("/image/quiz.jpg");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
        
        
    }
    
    private void createtables(){
        Quiz.createtable();
        Question.createtable();
        Student.createtable();
        QuizResult.createtable();
        QuizResultDetails.createtable();
        Admin.createtable();
    }
}
