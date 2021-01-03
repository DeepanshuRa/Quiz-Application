package controllers.student;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.Question;


public class SingleQuestionController implements Initializable {

    @FXML
    private Label question;
    @FXML
    private Label option1;
    @FXML
    private Label option2;
    @FXML
    private Label option3;
    @FXML
    private Label option4;

    
    private Question questionobject;
    private String userAnswer;

    public void setValues(Question questionobject, String userAnswer) {
        this.questionobject = questionobject;
        if(userAnswer==null)
            userAnswer = "";
        else
            this.userAnswer = userAnswer;
        
        setText();
    }
    
    private void setText(){
        this.question.setText(this.questionobject.getQuestion());
        this.option1.setText(this.questionobject.getOption1());
        this.option2.setText(this.questionobject.getOption2());
        this.option3.setText(this.questionobject.getOption3());
        this.option4.setText(this.questionobject.getOption4());
        
        if(option1.getText().trim().equalsIgnoreCase(this.questionobject.getAnswer())){
            option1.setTextFill(Color.web("#26ae60"));
        }
        else if(option2.getText().trim().equalsIgnoreCase(this.questionobject.getAnswer())){
            option2.setTextFill(Color.web("#26ae60"));
        }
        else if(option3.getText().trim().equalsIgnoreCase(this.questionobject.getAnswer())){
            option3.setTextFill(Color.web("#26ae60"));
        }
        else if(option4.getText().trim().equalsIgnoreCase(this.questionobject.getAnswer())){
            option4.setTextFill(Color.web("#26ae60"));
        }
        
        
        if(!userAnswer.trim().equalsIgnoreCase(this.questionobject.getAnswer().trim())){
            if(option1.getText().trim().equalsIgnoreCase(this.userAnswer)){
            option1.setTextFill(Color.web("#B83227"));
        }
        else if(option2.getText().trim().equalsIgnoreCase(this.userAnswer)){
            option2.setTextFill(Color.web("#B83227"));
        }
        else if(option3.getText().trim().equalsIgnoreCase(this.userAnswer)){
            option3.setTextFill(Color.web("#B83227"));
        }
        else if(option4.getText().trim().equalsIgnoreCase(this.userAnswer)){
            option4.setTextFill(Color.web("#B83227"));
        }
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
