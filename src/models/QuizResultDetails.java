
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;


public class QuizResultDetails {

    static void saveQuizResultDetails(QuizResult aThis, Map<Question, String> userAnswers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private Integer id;
    private Question question;
    private String useranswer;
    private QuizResult quizresult;
    
    public static class MetaData{
        public static final String TABLENAME = "quizresultdetails";
        public static final String ID = "id";
        public static final String USERANSWER = "useranswer";
        public static final String QUESTIONID = "questionid";
        public static final String QUIZRESULTID = "quizresultid";
        
    }

    public QuizResultDetails() {
    }

    public QuizResultDetails(Integer id, Question question, String useranswer, QuizResult quizresult) {
        this.id = id;
        this.question = question;
        this.useranswer = useranswer;
        this.quizresult = quizresult;
    }

    public QuizResultDetails(Question question, String useranswer, QuizResult quizresult) {
        this.question = question;
        this.useranswer = useranswer;
        this.quizresult = quizresult;
    }

    public Integer getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public String getUseranswer() {
        return useranswer;
    }

    public QuizResult getQuizresult() {
        return quizresult;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setUseranswer(String useranswer) {
        this.useranswer = useranswer;
    }

    public void setQuizresult(QuizResult quizresult) {
        this.quizresult = quizresult;
    }
    
    public static void createtable(){
        try{
            String raw = "create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "%s int not null,"
                    + "%s int not null,"
                    + "%s varchar(200) not null,"
                    + "FOREIGN KEY (%s) REFERENCES %s(%s),"
                    + "FOREIGN KEY (%s) REFERENCES %s(%s))";
            
            String query = String.format(raw,MetaData.TABLENAME,
                                            MetaData.ID,
                                           MetaData.QUIZRESULTID,
                                            MetaData.QUESTIONID,
                                            MetaData.USERANSWER,
                                            MetaData.QUIZRESULTID,
                                            QuizResult.MetaData.TABLE_NAME,
                                            QuizResult.MetaData.ID,
                                            MetaData.QUESTIONID,
                                            Question.MetaData.TABLE_NAME,
                                            Question.MetaData.QUESTIONID);
            
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
    
    
    public static boolean saveQuizResultDetail(QuizResult quizresult, Map<Question,String> useranswer){
        try{
            String raw = "insert into %s (%s,%s,%s) values(?,?,?)";
            
            String query = String.format(raw,MetaData.TABLENAME,
                                             MetaData.QUIZRESULTID,
                                             MetaData.QUESTIONID,
                                             MetaData.USERANSWER);
            
            String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query);
            Set<Question> questions = useranswer.keySet();  
            for(Question question : questions){
                ps.setInt(1, quizresult.getId());
                ps.setInt(2, question.getQuestionid());
                ps.setString(3, useranswer.get(question));
                ps.addBatch();
            }
            
            int[] result = ps.executeBatch();
            if(result.length > 0){
                return true;
            }
        
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
