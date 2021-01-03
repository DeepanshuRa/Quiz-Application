
package controllers.student;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import listeners.NewScreenListener;
import models.Quiz;
import models.Student;

public class QuizCardLayoutController implements Initializable {

    @FXML
    private Label title;
    @FXML
    private Label noq;
    @FXML
    private Button start;
    
    private Quiz quiz;
    
    private NewScreenListener screenlistener;
    
    private Student student;

    public void setStudent(Student student) {
        this.student = student;
    }
    
    

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.title.setText(this.quiz.getTitle());
    }

    
    
    public void setScreenlistener(NewScreenListener screenlistener) {
        this.screenlistener = screenlistener;
    }

    
    
    

    public void setNoq(String noq) {
        this.noq.setText(noq);
    }

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void startQuiz(ActionEvent event) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/student/QuestionScreen.fxml"));
        try{
          Parent node = fxmlloader.load();
          QuestionScreenController questionscreen = fxmlloader.getController();
          questionscreen.setStudent(this.student);
          questionscreen.setQuiz(this.quiz);
          questionscreen.setScreenlistener(this.screenlistener);
          this.screenlistener.changeScreen(node);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
}
