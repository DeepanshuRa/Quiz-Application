
package controllers.student;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import models.Question;
import models.Quiz;


public class QuizResultScreenController implements Initializable {

    @FXML
    private PieChart attemptedchart;
    @FXML
    private PieChart scorechart;
    @FXML
    private VBox questionscontainer;
    
    
    private Map<Question,String> userAnswers;
    private Integer noofrightanswers;
    private Quiz quiz;
    private List<Question> questionlist;
    private Integer notAttemped=0;
    private Integer attemped=0;

    public void setValues(Map<Question, String> userAnswers, Integer noofrightanswers, Quiz quiz, List<Question> questionlist) {
        this.userAnswers = userAnswers;
        this.noofrightanswers = noofrightanswers;
        this.quiz = quiz;
        this.questionlist = questionlist;
        
        this.attemped = this.userAnswers.keySet().size();
        this.notAttemped = this.questionlist.size() - attemped;
        
        setvaluestochart();
        renderQuestions();
    }

    
    private void renderQuestions(){
        for(int i=0;i<this.questionlist.size();i++){
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/student/SingleQuestion.fxml"));
        try{
          Parent node = fxmlloader.load();
            SingleQuestionController controller = fxmlloader.getController();
            controller.setValues(this.questionlist.get(i), this.userAnswers.get(this.questionlist.get(i)));
            questionscontainer.getChildren().add(node);
        }catch(Exception e){
            e.printStackTrace();
        }
        }
    }
    
    
    private void setvaluestochart(){
        ObservableList<PieChart.Data> attempedData = this.attemptedchart.getData();
        attempedData.add(new PieChart.Data(String.format("Attemped (%d)", this.attemped), this.attemped));
        attempedData.add(new PieChart.Data(String.format("Not Attemped (%d)", this.notAttemped), this.notAttemped));
        
        ObservableList<PieChart.Data> scoreData = this.scorechart.getData();
        scoreData.add(new PieChart.Data(String.format("Right Answers (%d)", this.noofrightanswers), this.noofrightanswers));
        scoreData.add(new PieChart.Data(String.format("Wrong Answers (%d)", this.attemped - this.noofrightanswers), this.attemped - this.noofrightanswers));
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
