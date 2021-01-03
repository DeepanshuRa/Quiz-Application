
package controllers.student;

import controllers.student.smallLayout.QuizListItemController;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import listeners.AttemptedQuizClick;
import models.Quiz;
import models.QuizResult;
import models.Student;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.views.AbstractView;


public class AttemptedQuizController implements Initializable {

    @FXML
    private VBox list;
    
    private Student student;
    @FXML
    private Label tq;
    @FXML
    private Label aq;
    @FXML
    private Label ra;
    @FXML
    private Label wa;
    @FXML
    private PieChart attemptedchart;
    @FXML
    private PieChart rightwrongchart;

    public void setStudent(Student student) {
        this.student = student;
        setList();
    }
    
    private void setList(){
        Map<QuizResult,Quiz> map = QuizResult.getQuizzes(student);
        Set<QuizResult> quizzes = map.keySet();
        for(QuizResult quizresult : quizzes){
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/student/smallLayout/QuizListItem.fxml"));
        try{
          Parent node = fxmlloader.load();
            QuizListItemController controller = fxmlloader.getController();
            controller.setItemclicklistener(new AttemptedQuizClick() {
              @Override
              public void itemClicked(Integer nof, Integer noa) {
                  int attempted = noa;
                  int nonAttempted = nof - attempted;
                  int right = quizresult.getRightAnswers();
                  int wrong = attempted - right;
                  
                  tq.setText(nof.toString());
                  aq.setText(attempted+"");
                  ra.setText(right+"");
                  wa.setText(wrong+"");
                  
                  ObservableList<PieChart.Data> attemptedData = attemptedchart.getData();
                  attemptedData.clear();
                  attemptedData.add(new PieChart.Data("Attempted Questions ("+attempted+")", attempted));
                  attemptedData.add(new PieChart.Data("Non Attempted Questions ("+nonAttempted+")", nonAttempted));
                  
                  ObservableList<PieChart.Data> rightwrongData = rightwrongchart.getData();
                  rightwrongData.clear();
                  rightwrongData.add(new PieChart.Data("Right Answer", right));
                  rightwrongData.add(new PieChart.Data("Wrong Answer", wrong));
              }

              @Override
              public int getScreenX() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public int getScreenY() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public int getClientX() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public int getClientY() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public boolean getCtrlKey() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public boolean getShiftKey() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public boolean getAltKey() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public boolean getMetaKey() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public short getButton() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public EventTarget getRelatedTarget() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public void initMouseEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, AbstractView viewArg, int detailArg, int screenXArg, int screenYArg, int clientXArg, int clientYArg, boolean ctrlKeyArg, boolean altKeyArg, boolean shiftKeyArg, boolean metaKeyArg, short buttonArg, EventTarget relatedTargetArg) {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public AbstractView getView() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public int getDetail() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public void initUIEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, AbstractView viewArg, int detailArg) {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public String getType() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public EventTarget getTarget() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public EventTarget getCurrentTarget() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public short getEventPhase() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public boolean getBubbles() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public boolean getCancelable() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public long getTimeStamp() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public void stopPropagation() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public void preventDefault() {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public void initEvent(String eventTypeArg, boolean canBubbleArg, boolean cancelableArg) {
                  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }
          });
            controller.setData(map.get(quizresult), quizresult);
            this.list.getChildren().add(node);
        }catch(Exception e){
            e.printStackTrace();
        }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
