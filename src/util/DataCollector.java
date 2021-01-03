
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Question;
import models.Quiz;
import models.Student;
import org.json.JSONArray;
import org.json.JSONObject;


public class DataCollector {
    
    public static void readAndSaveQuizesData() throws FileNotFoundException, IOException{
        
        String folderpath = "src/util/sampledata/quizes";
        File folder = new File(folderpath);
        String[] filename = folder.list();
        
        for(String s : filename){
         
            File file = new File(folder+"/"+s);
            System.out.println(file.exists());
            System.out.println(file.getAbsolutePath());
            
            FileReader filereader = new FileReader(file);
            BufferedReader bufferreader = new BufferedReader(filereader);
            
            StringBuilder stringbuilder = new StringBuilder();
            String line;
            while((line = bufferreader.readLine())!=null){
                    stringbuilder.append(line);
                }
            System.out.println(stringbuilder);
            
            JSONObject jsonobject = new JSONObject(stringbuilder.toString());
            JSONArray result = jsonobject.getJSONArray("results");
            Quiz quiz = new Quiz();
            ArrayList<Question> questions = new ArrayList<>();
            
            for(int i=0;i<result.length();i++){
                String objstring = result.get(i).toString();
                JSONObject object = new JSONObject(objstring);
                
                quiz.setTitle(object.getString("category"));
                
                Question question = new Question();
                question.setQuestion(object.getString("question"));
                
                JSONArray incorrectanswer = object.getJSONArray("incorrect_answers");
                question.setOption1(incorrectanswer.get(0).toString());
                question.setOption2(incorrectanswer.get(1).toString());
                question.setOption3(incorrectanswer.get(2).toString());
                question.setOption4(object.getString("correct_answer"));
                question.setAnswer(object.getString("correct_answer"));
                questions.add(question);
                question.setQuiz(quiz);
                
                System.out.println(question);
                System.out.println(quiz);
            }
            
            quiz.save(questions);
        }
    }
    
    public static void readAndSaveUsersData() throws FileNotFoundException, IOException{
        
        File file = new File("src/util/sampledata/users.json");
            System.out.println(file.exists());
            System.out.println(file.getAbsolutePath());
            
            FileReader filereader = new FileReader(file);
            BufferedReader bufferreader = new BufferedReader(filereader);
            
            StringBuilder stringbuilder = new StringBuilder();
            String line;
            while((line = bufferreader.readLine())!=null){
                    stringbuilder.append(line);
                }
            System.out.println(stringbuilder);
            
            JSONArray result = new JSONArray(stringbuilder.toString());
            
            for(int i=0;i<result.length();i++){
                String objString = result.get(i).toString();
                JSONObject object = new JSONObject(objString);
                Student student = new Student();
                student.setFirstname(object.getString("firstName"));
                student.setLastname(object.getString("lastName"));
                student.setEmail(object.getString("email"));
                student.setPassword(object.getInt("password")+"");
                student.setMobileno(object.getInt("phone")+"");
                student.setGender('M');
                student.save();
                
            }
            
           
        
    }
    public static void main(String[] args) throws FileNotFoundException, IOException{
        //readAndSaveQuizesData();
        readAndSaveUsersData();
    }
}

