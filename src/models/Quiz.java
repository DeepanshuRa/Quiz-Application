
package models;

import com.sun.javafx.collections.MappingChange;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Quiz {
    private Integer quizid;
    private String title;

    public Quiz() {
    }
    
    
    public Quiz(String title) {
        this.title = title;
    }
    
    public static class MetaData{
        public static final String TABLE_NAME = "quiz";
        public static final String QUIZID = "quizid";
        public static final String TITLE = "title";
    }

    public void setQuizid(Integer quizid) {
        this.quizid = quizid;
    }

    

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getQuizid() {
        return quizid;
    }

    public String getTitle() {
        return title;
    }
    
    @Override
    public String toString() {
        return this.title;
    }
    
    //other methods
    
    public static void createtable(){
        try{
            
            String raw = "create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s VARCHAR(50));";
            String query = String.format(raw, MetaData.TABLE_NAME,
                                                MetaData.QUIZID,
                                                MetaData.TITLE);
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
    
    public int save(){
        try{
             String raw = "insert into %s (%s) values (?)";
            String query = String.format(raw, MetaData.TABLE_NAME, MetaData.TITLE);
        String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, this.title);
            int i = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }
        
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
        
        return -1;
    }
    
    public boolean save(ArrayList<Question> questions){
        boolean flag = true;
        this.quizid = this.save();
        
        for(Question q:questions){
            flag = flag && q.save();
            System.out.println(flag);
        }
        return flag;
    }
    
    
    public static Map<Quiz, List<Question>> getAll(){
        Map<Quiz, List<Question>> quizes = new HashMap<>();
        Quiz key = null;
                
        try{
            String query = String.format("select %s.%s,%s,%s,%s,%s,%s,%s,%s,%s from %s JOIN %s ON "
                    + "%s.%s=%s.%s;",
                    MetaData.TABLE_NAME,
                    MetaData.QUIZID,
                    MetaData.TITLE,
                    Question.MetaData.QUESTIONID,
                    Question.MetaData.QUESTION,
                    Question.MetaData.OPTION1,
                    Question.MetaData.OPTION2,
                    Question.MetaData.OPTION3,
                    Question.MetaData.OPTION4,
                    Question.MetaData.ANSWER,
                    MetaData.TABLE_NAME,
                    Question.MetaData.TABLE_NAME,
                    Question.MetaData.TABLE_NAME,
                    Question.MetaData.QUIZID,
                    MetaData.TABLE_NAME,
                    MetaData.QUIZID);
            
        String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Quiz temp = new Quiz();
                temp.setQuizid(rs.getInt(1));
                temp.setTitle(rs.getString(2));
                
                Question tempquestion = new Question();
                tempquestion.setQuestionid(rs.getInt(3));
                tempquestion.setQuestion(rs.getString(4));
                tempquestion.setOption1(rs.getString(5));
                tempquestion.setOption2(rs.getString(6));
                tempquestion.setOption3(rs.getString(7));
                tempquestion.setOption4(rs.getString(8));
                tempquestion.setAnswer(rs.getString(9));
                
                if(key!=null && key.equals(temp)){
                    quizes.get(key).add(tempquestion);
                }
                else{
                    ArrayList<Question> value = new ArrayList<>();
                    value.add(tempquestion);
                    quizes.put(temp, value);
                }
                key = temp;
            }
        
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return quizes;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        
        if(!(obj instanceof Quiz))
            return false;
        Quiz t = (Quiz)obj;
        if(this.quizid == t.quizid){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        
        return Objects.hash(quizid, title);
    }

    
   public static Map<Quiz, Integer> getAllQuestionCount(){
        Map<Quiz, Integer> quizes = new HashMap<>();
       
                
        try{
            String query = String.format("select %s.%s,%s,COUNT(*)as question_count "
                    + "from %s INNER JOIN %s ON %s.%s=%s.%s GROUP BY %s.%s",
                    MetaData.TABLE_NAME,
                    MetaData.QUIZID,
                    MetaData.TITLE,
                    MetaData.TABLE_NAME,
                    Question.MetaData.TABLE_NAME,
                    Question.MetaData.TABLE_NAME,
                    Question.MetaData.QUIZID,
                    MetaData.TABLE_NAME,
                    MetaData.QUIZID,
                    MetaData.TABLE_NAME,
                    MetaData.QUIZID);
            
        String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Quiz temp = new Quiz();
                temp.setQuizid(rs.getInt(1));
                temp.setTitle(rs.getString(2));
                int count = rs.getInt(3);
                
                quizes.put(temp, count);
                
            }
        
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return quizes;
    }
   
   //get questions using quiz
   public List<Question> getQuestions(){
        List<Question> questions = new ArrayList<>();
        
        
                
        try{
            String query = String.format("select %s,%s,%s,%s,%s,%s,%s from %s where %s=?",
                    Question.MetaData.QUESTIONID,
                    Question.MetaData.QUESTION,
                    Question.MetaData.OPTION1,
                    Question.MetaData.OPTION2,
                    Question.MetaData.OPTION3,
                    Question.MetaData.OPTION4,
                    Question.MetaData.ANSWER,
                    
                    Question.MetaData.TABLE_NAME,
                    MetaData.QUIZID);
            
        String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,this.quizid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                
                
                Question tempquestion = new Question();
                tempquestion.setQuestionid(rs.getInt(1));
                tempquestion.setQuestion(rs.getString(2));
                tempquestion.setOption1(rs.getString(3));
                tempquestion.setOption2(rs.getString(4));
                tempquestion.setOption3(rs.getString(5));
                tempquestion.setOption4(rs.getString(6));
                tempquestion.setAnswer(rs.getString(7));
                tempquestion.setQuiz(this);
                questions.add(tempquestion);
            }
        
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return questions;
    }
   
   
   public Integer getNoOfQuestion(){
       String query = String.format("select count(*) from %s where %s=?",
                                    Question.MetaData.TABLE_NAME,
                                    Question.MetaData.QUIZID);
       try{
            String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, this.quizid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getInt(1);
            }
              
        }catch(Exception e){
            e.printStackTrace();
        }
       return 0;
   }
   
   
   public static Map<Quiz, Integer> getStudentCount(){
       Map<Quiz, Integer> data = new HashMap<>();
       
       String query = String.format("select %s.%s,%s.%s,count(*),%s.%s from %s "
                                    + "LEFT JOIN %s ON %s.%s=%s.%s GROUP BY %s.%s",
                                    MetaData.TABLE_NAME,
                                    MetaData.QUIZID,
                                    MetaData.TABLE_NAME,
                                    MetaData.TITLE,
                                    QuizResult.MetaData.TABLE_NAME,
                                    QuizResult.MetaData.ID,
                                    MetaData.TABLE_NAME,
                                    QuizResult.MetaData.TABLE_NAME,
                                    QuizResult.MetaData.TABLE_NAME,
                                    QuizResult.MetaData.QUIZID,
                                    MetaData.TABLE_NAME,
                                    MetaData.QUIZID,
                                    MetaData.TABLE_NAME,
                                    MetaData.QUIZID);
       try{
            String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Quiz quiz = new Quiz();
                quiz.setQuizid(rs.getInt(1));
                quiz.setTitle(rs.getString(2));
                
                int count = 0;
                Integer resultId = rs.getInt(4);
                if(resultId != 0){
                    count = rs.getInt(3);
                }
                data.put(quiz, count);
            }
              
        }catch(Exception e){
            e.printStackTrace();
        }
       return data;
   }
   
    
}
