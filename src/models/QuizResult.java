
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizResult {
    private Integer id;
    private Quiz quiz;
    private Student student;
    private Integer rightAnswers;
    private Timestamp timestamp;
    
    {
        this.timestamp = new Timestamp(new Date().getTime());
    }
    
    public static class MetaData{
        public static final String TABLE_NAME = "quizResult";
        public static final String ID = "id";
        public static final String QUIZID = "quizid";
        public static final String STUDENTID = "studentid";
        public static final String RIGHT_ANSWERS = "rightAnswers";
        public static final String TIMESTAMP = "date_time";
        
    }

    public QuizResult() {
    }

    
    public QuizResult(Integer id, Quiz quiz, Student student, Integer rightAnswers) {
        this.id = id;
        this.quiz = quiz;
        this.student = student;
        this.rightAnswers = rightAnswers;
    }

    public QuizResult(Quiz quiz, Student student, Integer rightAnswers) {
        this.quiz = quiz;
        this.student = student;
        this.rightAnswers = rightAnswers;
    }
    
    

    public Integer getId() {
        return id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public Student getStudent() {
        return student;
    }

    public Integer getRightAnswers() {
        return rightAnswers;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setRightAnswers(Integer rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    
    public static void createtable(){
        try{
            String raw = "create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "%s int not null,"
                    + "%s int not null,"
                    + "%s int not null,"
                    + "%s timestamp not null,"
                    + "FOREIGN KEY (%s) REFERENCES %s(%s),"
                    + "FOREIGN KEY (%s) REFERENCES %s(%s))";
            
            String query = String.format(raw,MetaData.TABLE_NAME,
                                            MetaData.ID,
                                            MetaData.STUDENTID,
                                            MetaData.QUIZID,
                                            MetaData.RIGHT_ANSWERS,
                                            MetaData.TIMESTAMP,
                                            MetaData.STUDENTID,
                                            Student.MetaData.TABLENAME,
                                            Student.MetaData.ID,
                                            MetaData.QUIZID,
                                            Quiz.MetaData.TABLE_NAME,
                                            Quiz.MetaData.QUIZID);
            
            String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query);
            boolean b = ps.execute();
            System.out.println("hiii there");
            System.out.println(b);
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    
    public boolean save(Map<Question, String> userAnswers){
        try{
            String raw = "insert into %s (%s,%s,%s,%s) values(?,?,?,CURRENT_TIMESTAMP)";
            
            String query = String.format(raw,MetaData.TABLE_NAME,
                                            MetaData.STUDENTID,
                                            MetaData.QUIZID,
                                            MetaData.RIGHT_ANSWERS,
                                            MetaData.TIMESTAMP);
            
            String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, this.getStudent().getId());
            ps.setInt(2, this.getQuiz().getQuizid());
            ps.setInt(3, this.getRightAnswers());
            int result = ps.executeUpdate();
            if(result >0){
                ResultSet key = ps.getGeneratedKeys();
                if(key.next()){
                    this.setId(key.getInt(1));
                    //we will save details
                    
                     return saveResultDetails(userAnswers);
                }
            }
        
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
    
    public static Map<QuizResult,Quiz> getQuizzes(Student student){
        Map<QuizResult,Quiz> data  = new HashMap<>();
        
        try{
            String raw = "select %s.%s,%s.%s,%s.%s,%s.%s from %s JOIN %s ON %s.%s=%s.%s where %s=?";
            
            String query = String.format(raw,MetaData.TABLE_NAME,
                                            MetaData.ID,
                                            MetaData.TABLE_NAME,
                                            MetaData.RIGHT_ANSWERS,
                                            Quiz.MetaData.TABLE_NAME,
                                            Quiz.MetaData.QUIZID,
                                            Quiz.MetaData.TABLE_NAME,
                                            Quiz.MetaData.TITLE,
                                            MetaData.TABLE_NAME,
                                            Quiz.MetaData.TABLE_NAME,
                                            MetaData.TABLE_NAME,
                                            MetaData.ID,
                                            Quiz.MetaData.TABLE_NAME,
                                            Quiz.MetaData.QUIZID,
                                            MetaData.STUDENTID);
            
            String connectionUrl = "jdbc:sqlite:quiz.db";
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, student.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                QuizResult quizresult = new QuizResult();
                quizresult.setId(rs.getInt(1));
                quizresult.setRightAnswers(rs.getInt(2));
                
                Quiz quiz = new Quiz();
                quiz.setQuizid(rs.getInt(3));
                quiz.setTitle(rs.getString(4));
                
                data.put(quizresult, quiz);
            }
        
        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }
    
    public Integer getNoOfAttemptedQuestion(){
       String query = String.format("select count(*) from %s where %s=?",
                                    QuizResultDetails.MetaData.TABLENAME,
                                    QuizResultDetails.MetaData.QUIZRESULTID);
       try{
            String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, this.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getInt(1);
            }
              
        }catch(Exception e){
            e.printStackTrace();
        }
       return 0;
   }
    
    
    public static Map<Quiz, List<Map<Student, Integer>>> getStudentNames(){
        
       Map<Quiz, List<Map<Student, Integer>>> data = new HashMap<>();
       
       Quiz temp = null;
        String query = String.format("SELECT %s.%s,%s.%s,"
                + "%s.%s,%s.%s "
                + "FROM %s join %s on \n" +
                    "%s.%s=%s.%s ORDER by %s.%s", 
                    Quiz.MetaData.TABLE_NAME,
                    Quiz.MetaData.QUIZID,
                    Quiz.MetaData.TABLE_NAME,
                    Quiz.MetaData.TITLE,
                    MetaData.TABLE_NAME,
                    MetaData.STUDENTID,
                    MetaData.TABLE_NAME,
                    MetaData.RIGHT_ANSWERS,
                    MetaData.TABLE_NAME,
                    Quiz.MetaData.TABLE_NAME,
                    MetaData.TABLE_NAME,
                    MetaData.QUIZID,
                    Quiz.MetaData.TABLE_NAME,
                    Quiz.MetaData.QUIZID,
                    MetaData.TABLE_NAME,
                    MetaData.QUIZID);
        
        try{
            String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Map<Student, Integer> test = new HashMap<>();
                
                Quiz quiz = new Quiz();
                quiz.setQuizid(rs.getInt(1));
                quiz.setTitle(rs.getString(2));
                
                Student student = Student.getStudentDetails(rs.getInt(3));
                Integer answer = rs.getInt(4);
                
                test.put(student, answer);
                
                
                if(temp!=null && temp.equals(quiz)){
                    data.get(temp).add(test);
                    
                }
                else{
                    List<Map<Student, Integer>> value = new ArrayList<>();
                    value.add(test);
                    data.put(quiz, value);
                    
                }
                temp = quiz;
            }
              
        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }
    
    
    private boolean saveResultDetails(Map<Question, String> userAnswers){
        return QuizResultDetails.saveQuizResultDetail(this, userAnswers);
    }
    
}
