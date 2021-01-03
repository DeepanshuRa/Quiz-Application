package controllers.student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javax.management.Notification;
import listeners.NewScreenListener;
import models.Question;
import models.Quiz;
import models.QuizResult;
import models.Student;
import org.controlsfx.control.Notifications;



public class QuestionScreenController implements Initializable {
    
    
    @FXML
    private Label title;
    @FXML
    private Label timer;
    @FXML
    private Label question;
    @FXML
    private JFXRadioButton option1;
    @FXML
    private ToggleGroup options;
    @FXML
    private JFXRadioButton option2;
    @FXML
    private JFXRadioButton option3;
    @FXML
    private JFXRadioButton option4;
    @FXML
    private JFXButton next;
    @FXML
    private JFXButton submit;
    
    
    
    private Quiz quiz;
    private List<Question> questionlist;
    private Question currentquestion;
    int currentindex=0;
    
    private Map<Question, String> studentanswers = new HashMap<>();
    private Integer noOfRightAnsers = 0;
    @FXML
    private FlowPane progresspane;
    
    private Student student;

    public void setStudent(Student student) {
        this.student = student;
    }
    
    
    private NewScreenListener screenlistener;

    public void setScreenlistener(NewScreenListener screenlistener) {
        this.screenlistener = screenlistener;
    }
    
    
    
    
    private long min, sec, hr, totalsec=0;
    private Timer time;

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.title.setText(this.quiz.getTitle());
        this.getData();
    }
    
    private String format(long value){
        if(value<10){
            return 0+""+value;
        }
        else{
            return value+"";
        }
    }
    
    public void converttime(){
        min = TimeUnit.SECONDS.toMinutes(totalsec);
        sec = totalsec - (min*60);
        hr = TimeUnit.MINUTES.toHours(min);
        min = min - (hr*60);
        
        timer.setText(format(hr)+":"+format(min)+":"+format(sec));
        totalsec--;
    }
    
    private void setTimer(){
        totalsec = this.questionlist.size() * 30;
        this.time = new Timer();
        TimerTask timertask = new TimerTask() {
          @Override
          public void run() {
              Platform.runLater(new Runnable() {
                  public void run() {
                      converttime();
              if(totalsec<=0){
                  time.cancel();
                  timer.setText("00:00:00");
                  //saving data to database
                  submit(null);
                  Notifications.create().darkStyle().title("Error")
                    .text("Time Up")
                    .position(Pos.TOP_CENTER).showError();
              }
                  }
              });
          }
      };
      
      time.schedule(timertask, 0, 1000);
    }
    
    public void getData(){
        if(quiz!=null){
            this.questionlist = quiz.getQuestions();
            Collections.shuffle(questionlist);
            renderProgress();
            this.setnextquestion();
            setTimer();
        }
    }

    private void renderProgress(){
        for(int i=0;i<this.questionlist.size();i++){
        FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/fxml/student/ProgressCircle.fxml"));
        try {
            Parent node = fXMLLoader.load();
            ProgressCircleController progresscircle = fXMLLoader.getController();
            progresscircle.setNo(i+1);
            progresspane.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.submit.setDisable(true);
        
        
    }    

    @FXML
    private void nextQuestion(ActionEvent event) {
        {
            //checking answer
            JFXRadioButton selected = (JFXRadioButton)options.getSelectedToggle();
            String useranswer = selected.getText();
            String rightanswer = this.currentquestion.getAnswer();
            
            Node circlenode = this.progresspane.getChildren().get(currentindex-1);
            ProgressCircleController controller = (ProgressCircleController)circlenode.getUserData();
            if(useranswer.trim().equalsIgnoreCase(rightanswer.trim())){
                
                controller.setRightQuestionColor();
                this.noOfRightAnsers++;
            }
            else{
                controller.setWrongQuestionColor();
            }
            studentanswers.put(currentquestion, useranswer);
        }
        this.setnextquestion();
    }
    
    private void setnextquestion(){
        if(!(this.currentindex >= questionlist.size())){
            {
                Node circlenode = this.progresspane.getChildren().get(currentindex);
                ProgressCircleController controller = (ProgressCircleController)circlenode.getUserData();
                controller.setCurrentQuestionColor();
            }
            
            
            this.currentquestion = this.questionlist.get(currentindex);
            List<String> options = new ArrayList<>();
            options.add(currentquestion.getOption1());
            options.add(currentquestion.getOption2());
            options.add(currentquestion.getOption3());
            options.add(currentquestion.getOption4());
            Collections.shuffle(options);
            
            this.question.setText(currentquestion.getQuestion());
            this.option1.setText(options.get(0));
            this.option2.setText(options.get(1));
            this.option3.setText(options.get(2));
            this.option4.setText(options.get(3));
        }
        else{
            Notifications.create().darkStyle().title("NO question")
                    .text("There are no more question")
                    .position(Pos.TOP_CENTER).showInformation();
            
            this.next.setDisable(true);
            this.submit.setDisable(false);
        }
        currentindex++;
    }

    @FXML
    private void submit(ActionEvent event) {
        
        
        System.out.println(this.studentanswers);
        
        QuizResult quizresult = new QuizResult(this.quiz,student , noOfRightAnsers);
        boolean result = quizresult.save(this.studentanswers);
        if(result){
            
            Notifications.create().darkStyle().title("Success")
                    .text("Submitted successfully")
                    .position(Pos.TOP_CENTER)
                    .showInformation();
            
            time.cancel();
            openresultscreen();
        }else{
            Notifications.create().darkStyle().title("Error")
                    .text("Something went wrong...")
                    .position(Pos.TOP_CENTER)
                    .showError();
        }
    }
    
    
    private void openresultscreen(){
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/student/QuizResultScreen.fxml"));
        try{
          Parent node = fxmlloader.load();
          QuizResultScreenController controller = fxmlloader.getController();
          controller.setValues(studentanswers, noOfRightAnsers, quiz, questionlist);
          this.screenlistener.removetopscreen();
          this.screenlistener.changeScreen(node);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
