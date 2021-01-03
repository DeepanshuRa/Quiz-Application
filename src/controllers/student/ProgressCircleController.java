
package controllers.student;

import java.awt.Color;
import java.awt.Paint;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;



public class ProgressCircleController implements Initializable {

    @FXML
    private Circle circle;
    @FXML
    private Label no;

    public void setNo(Integer no) {
        this.no.setText(no+"");
    }

    public void setCurrentQuestionColor(){
        circle.setFill(javafx.scene.paint.Paint.valueOf("blue"));
        no.setTextFill(javafx.scene.paint.Paint.valueOf("white"));
    }

    public void setWrongQuestionColor(){
        circle.setFill(javafx.scene.paint.Paint.valueOf("red"));
        no.setTextFill(javafx.scene.paint.Paint.valueOf("white"));
    }
    
    public void setRightQuestionColor(){
        circle.setFill(javafx.scene.paint.Paint.valueOf("green"));
        no.setTextFill(javafx.scene.paint.Paint.valueOf("white"));
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
