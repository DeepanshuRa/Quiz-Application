
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.util.Duration;
import models.Question;
import models.Quiz;
import org.controlsfx.control.Notifications;


public class AddQuizController implements Initializable {

    @FXML
    private JFXTextField quiztitle;
    @FXML
    private JFXTextArea question;
    @FXML
    private JFXTextField option1;
    @FXML
    private JFXTextField option2;
    @FXML
    private JFXTextField option3;
    @FXML
    private JFXTextField option4;
    @FXML
    private JFXRadioButton option1radio;
    @FXML
    private JFXRadioButton option2radio;
    @FXML
    private JFXRadioButton option3radio;
    @FXML
    private JFXRadioButton option4radio;
    @FXML
    private JFXButton addnextquestion;
    @FXML
    private JFXButton submitquiz;
    
    private ToggleGroup radiogroup;
    @FXML
    private JFXButton setquiztitlebutton;
    
    //my variables
    private Quiz quiz = null;
    private ArrayList<Question> questions = new ArrayList<>();
    
    @FXML
    private JFXTreeView<?> treeview;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        radiobuttonsetup();
        renderTreeView();
    }   
    
    private void renderTreeView(){
        Map<Quiz, List<Question>> data = Quiz.getAll();
        Set<Quiz> quizes = data.keySet();
        TreeItem root = new TreeItem("Quizes");
        for(Quiz q:quizes){
            TreeItem quiztreeitem = new TreeItem(q.toString());
            
            List<Question> questions = data.get(q);
            
            for(Question question : questions){
                TreeItem questiontreeitem = new TreeItem(question.toString());
                
                questiontreeitem.getChildren().add(new TreeItem("A : "+question.getOption1()));
                questiontreeitem.getChildren().add(new TreeItem("B : "+question.getOption2()));
                questiontreeitem.getChildren().add(new TreeItem("C : "+question.getOption3()));
                questiontreeitem.getChildren().add(new TreeItem("D : "+question.getOption4()));
                questiontreeitem.getChildren().add(new TreeItem("Answer = "+question.getAnswer()));
                
                quiztreeitem.getChildren().add(questiontreeitem);
            }
            
            quiztreeitem.setExpanded(true);
            root.getChildren().add(quiztreeitem);
        }
        
        root.setExpanded(true);
        this.treeview.setRoot(root);
    }
    
    private void radiobuttonsetup(){
        radiogroup = new ToggleGroup();
        option1radio.setToggleGroup(radiogroup);
        option2radio.setToggleGroup(radiogroup);
        option3radio.setToggleGroup(radiogroup);
        option4radio.setToggleGroup(radiogroup);
        
    }

    @FXML
    private void setquiztitle(ActionEvent event) {
        String title = quiztitle.getText();
        if(title.trim().isEmpty()){
            Notifications.create()
                    .darkStyle()
                    .hideAfter(Duration.millis(2000))
                    .title("Quiz Title")
                    .text("Enter valid quiz title")
                    .position(Pos.TOP_CENTER)
                    .showError();
        }
        else{
            quiztitle.setEditable(false);
            
            
            System.out.println("save title");
            this.quiz = new Quiz(title);
        }
    }
    
    private boolean validatefields(){
        
        if(quiz == null){
            Notifications.create().title("Quiz")
                    .darkStyle()
                    .text("please enter quiz title")
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.TOP_CENTER)
                    .showError();
            return false;
        }
       String que = this.question.getText();
        String op1 = this.option1.getText();
        String op2 = this.option2.getText();
        String op3 = this.option3.getText();
        String op4 = this.option4.getText();
        Toggle selectedradio = radiogroup.getSelectedToggle();
        System.out.println(selectedradio);
        if(que.trim().isEmpty() || 
                op1.trim().isEmpty() || 
                op2.trim().isEmpty() || 
                op3.trim().isEmpty() || op4.trim().isEmpty()){
            
            Notifications.create().title("Question")
                    .darkStyle()
                    .text("All fields are required...\n [Question, option1, option2, option3, option4] ")
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.TOP_CENTER)
                    .showError();
            return false;
            }
            else{
                String message = "Please Select a answer...";
            if(selectedradio == null){
                Notifications.create().title("Question")
                    .darkStyle()
                    .text(message)
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.TOP_CENTER)
                    .showError();
                return false;
            }
            else{
                
                return true;
            }
            
            
        }
    }

    @FXML
    private void addnextquestion(ActionEvent event) {
        addquestion();
        
        
    }
    
    
    private boolean addquestion(){
        boolean valid = validatefields();
        Question question = new Question();
        if(valid){
            //save question and next
            
            question.setOption1(option1.getText().trim());
            question.setOption2(option2.getText().trim());
            question.setOption3(option3.getText().trim());
            question.setOption4(option4.getText().trim());
            
            Toggle selected = radiogroup.getSelectedToggle();
            String ans = null;
            if(selected == option1radio){
                ans = option1.getText().trim();
            }
            else if(selected == option2radio){
                ans = option2.getText().trim();
            }
            else if(selected == option3radio){
                ans = option3.getText().trim();
            }
            else if(selected == option4radio){
                ans = option4.getText().trim();
            }
            question.setAnswer(ans);
            question.setQuestion(this.question.getText().trim());
            
            this.question.clear();
            option1.clear();
            option2.clear();
            option3.clear();
            option4.clear();
            
            questions.add(question);
            question.setQuiz(quiz);
            
            System.out.println(questions);
            System.out.println(quiz);
        }
        
        return valid;
    }

    @FXML
    private void submitquiz(ActionEvent event) {
        boolean flag = addquestion();
        if(flag == true){
            flag = quiz.save(questions);
            if(flag == true){
                Notifications.create().title("Success")
                    .darkStyle()
                    .text("Quiz Successfully saved")
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.TOP_CENTER)
                    .showInformation();
            }
            else{
                Notifications.create().title("Failed")
                    .darkStyle()
                    .text("Can't save quiz.. try again")
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.TOP_CENTER)
                    .showError();
            }
        }
        
        
    }
    
}
