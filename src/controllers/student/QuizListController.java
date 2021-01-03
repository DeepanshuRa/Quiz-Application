
package controllers.student;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import listeners.NewScreenListener;
import models.Quiz;
import models.Student;


public class QuizListController implements Initializable {

    @FXML
    private FlowPane quizlistcontainer;
    
    Map<Quiz, Integer> quizes = null;
    private Set<Quiz> keys;
    
    private NewScreenListener screenlistener;
    
    private Student student;

    public void setStudent(Student student) {
        this.student = student;
    }
    
    

    public void setScreenlistener(NewScreenListener screenlistener) {
        this.screenlistener = screenlistener;
        
    }
    
    
    public void setCards(){
        for(Quiz quiz : keys){
           FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/student/QuizCardLayout.fxml")); 
           try{
           Parent node = fxmlloader.load();
           QuizCardLayoutController layout = fxmlloader.getController();
           layout.setStudent(this.student);
           layout.setQuiz(quiz);
           layout.setNoq(quizes.get(quiz)+"");
           
           layout.setScreenlistener(this.screenlistener);
           quizlistcontainer.getChildren().add(node);
        }catch(Exception e){
            e.printStackTrace();
        }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        quizes = Quiz.getAllQuestionCount();
         keys = quizes.keySet();
        
        
        
        
    }    
    
}
