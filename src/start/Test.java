
package start;

import Exceptions.LoginException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import models.Admin;
import models.Quiz;
import models.QuizResult;
import models.QuizResultDetails;
import models.Student;
import util.DataCollector;


public class Test {
 public static void main(String[] args){
       
     Admin a = new Admin();
     a.setEmail("tanu@gmail.com");
     a.setPassword("12345678");
     a.save();
}
}
