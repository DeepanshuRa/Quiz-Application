
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Question {
    Quiz quiz;
    Integer questionid;
    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    String answer;
    
    public static class MetaData{
        public static final String TABLE_NAME = "questions";
        public static final String QUESTION = "question";
        public static final String QUESTIONID = "id";
        public static final String OPTION1 = "option1";
        public static final String OPTION2 = "option2";
        public static final String OPTION3 = "option3";
        public static final String OPTION4 = "option4";
        public static final String ANSWER = "answer";
        public static final String QUIZID = "quizid";
        
    }
    
    public Question(){
        
    }

    @Override
    public String toString() {
        return this.question;
    }

    
    
    public Question(Quiz quiz, String question, String option1, String option2, String option3, String option4, String answer) {
        this.quiz = quiz;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public Integer getQuestionid() {
        return questionid;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setQuestionid(Integer questionid) {
        this.questionid = questionid;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    //other method
    
    public static void createtable(){
        try{
            String raw = "create table %s (%S INTEGER PRIMARY KEY AUTOINCREMENT, question VARCHAR(500),"
                + "%s VARCHAR(500),"
                + "%s VARCHAR(500),"
                + "%s VARCHAR(500),"
                + "%s VARCHAR(500),"
                + "%s VARCHAR(500),"
                + "%s INTEGER, FOREIGN KEY (%s) REFERENCES %s(%s));";
            
            String query = String.format(raw,MetaData.TABLE_NAME,
                                            MetaData.QUESTIONID,
                                             MetaData.OPTION1,
                                             MetaData.OPTION2,
                                             MetaData.OPTION3,
                                             MetaData.OPTION4,
                                             MetaData.ANSWER,
                                             MetaData.QUIZID,
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
    
    public boolean save(){
        boolean flag = false;
        try{
            
             String raw = "insert into %s (%s,%s,%s,%s,%s,%s,%s) values (?,?,?,?,?,?,?)";
            String query = String.format(raw, MetaData.TABLE_NAME,
                                                MetaData.QUESTION,
                                                MetaData.OPTION1,
                                                MetaData.OPTION2,
                                                MetaData.OPTION3,
                                                MetaData.OPTION4,
                                                MetaData.ANSWER,
                                                MetaData.QUIZID);
        String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, this.question);
            ps.setString(2, this.option1);
            ps.setString(3, this.option2);
            ps.setString(4, this.option3);
            ps.setString(5, this.option4);
            ps.setString(6, this.answer);
            ps.setInt(7, this.quiz.getQuizid());
            int i = ps.executeUpdate();
            
            System.out.println("updtaed rows "+i);
        
            flag = true;
        }catch(Exception e){
            e.printStackTrace();
            flag =true;
        }
        return flag;
    }
    
    
}
