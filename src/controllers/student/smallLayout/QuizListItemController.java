
package controllers.student.smallLayout;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import listeners.AttemptedQuizClick;
import models.Quiz;
import models.QuizResult;

public class QuizListItemController implements Initializable {

    @FXML
    private Label title;
    
    private Quiz quiz;
    
    private QuizResult quizresult;
    @FXML
    private VBox item;
    
    private AttemptedQuizClick itemclicklistener;
    

    public void setData(Quiz quiz, QuizResult quizresult) {
        this.quiz = quiz;
        this.quizresult = quizresult;
        this.title.setText(this.quiz.getTitle());
    }

    public void setItemclicklistener(AttemptedQuizClick itemclicklistener) {
        this.itemclicklistener = itemclicklistener;
    }
    
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loadData(MouseEvent event) {
        System.out.println("item clicked");
        Integer noofattemptedquestion = quizresult.getNoOfAttemptedQuestion();
        Integer noofquestion = quiz.getNoOfQuestion();
        System.out.println(noofattemptedquestion + " "+ noofquestion);
        
        itemclicklistener.itemClicked(noofquestion, noofattemptedquestion);
    }
    
}
