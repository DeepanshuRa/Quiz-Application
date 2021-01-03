
package models;

import Exceptions.LoginException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Student {
    
    private Integer id;
    private String firstname;
    private String lastname;
    private String mobileno;
    private Character gender;
    private String email;
    private String password;
    
    public static class MetaData{
        public static final String TABLENAME = "student";
        public static final String ID = "id";
        public static final String FIRSTNAME = "firstname";
        public static final String LASTNAME = "lastname";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String MOBILENO = "mobile";
        public static final String GENDER = "gender";
    }

    public Student() {
    }

    public Student(String email, String password) {
        this.email = email;
        this.password = password;
    }

    
    
    public Student(String firstname, String lastname, String mobileno, Character gender, String email, String password) {
        
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobileno = mobileno;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    public Student(Integer id, String firstname, String lastname, String mobileno, Character gender, String email, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobileno = mobileno;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    

    public Integer getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMobileno() {
        return mobileno;
    }

    public Character getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{" + 
                "id=" + id +
                ", firstname=" + firstname +
                ", lastname=" + lastname + 
                ", mobileno=" + mobileno + 
                ", gender=" + gender + ", email=" + email + ", password=" + password + '}';
    }
    
    //create table
    
    public static void createtable(){
        String raw = "create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "%s VARCHAR(20),"
                    + "%s VARCHAR(20),"
                    + "%s VARCHAR(20),"
                    + "%s VARCHAR(20),"
                    + "%s VARCHAR(20),"
                    + "%s CHAR)";
            
            String query = String.format(raw, MetaData.TABLENAME,
                                            MetaData.ID,
                                            MetaData.FIRSTNAME,
                                            MetaData.LASTNAME,
                                            MetaData.EMAIL,
                                            MetaData.PASSWORD,
                                            MetaData.MOBILENO,
                                            MetaData.GENDER);
            
        
        try{
            
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
    
    //save student
    
    public Student save(){
        String raw = "insert into student (%s,%s,%s,%s,%s,%s) values (?,?,?,?,?,?)";
        String query = String.format(raw, MetaData.FIRSTNAME,
                                            MetaData.LASTNAME,
                                            MetaData.EMAIL,
                                            MetaData.PASSWORD,
                                            MetaData.MOBILENO,
                                            MetaData.GENDER);
        
        
        try{
            String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, this.firstname);
            ps.setString(2, this.lastname);
            ps.setString(3, this.email);
            ps.setString(4, this.password);
            ps.setString(5, this.mobileno);
            ps.setString(6, String.valueOf(this.gender));
            int i = ps.executeUpdate();
            ResultSet key = ps.getGeneratedKeys();
            if(key.next()){
                this.id = key.getInt(1);
            }
            System.out.println("updtaed rows "+i);
            
           return this;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<Student> getAll(){
        ArrayList<Student> students = new ArrayList<>();
        
        String query = String.format("select %s,%s,%s,%s,%s,%s,%s from %s;",
                            MetaData.ID,
                            MetaData.FIRSTNAME,
                            MetaData.LASTNAME,
                            MetaData.MOBILENO,
                            MetaData.EMAIL,
                            MetaData.PASSWORD,
                            MetaData.GENDER,
                            MetaData.TABLENAME);
        
        
        try{
            String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Student s = new Student();
                s.setId(rs.getInt(1));
                s.setFirstname(rs.getString(2));
                s.setLastname(rs.getString(3));
                s.setMobileno(rs.getString(4));
                s.setEmail(rs.getString(5));
                s.setPassword(rs.getString(6));
                s.setGender(rs.getString(7).charAt(0));
                
                students.add(s);
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
       return students;
    }
    
    public boolean isExist(){
        String query = String.format("select * from %s where %s=?;", MetaData.TABLENAME,MetaData.EMAIL);
        
        
        try{
            String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, this.email);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public void login() throws SQLException, ClassNotFoundException, LoginException{
        String query = String.format("select %s,%s,%s,%s,%s from %s where %s=? and %s=?;", 
                                        MetaData.ID,
                                        MetaData.FIRSTNAME,
                                        MetaData.LASTNAME,
                                        MetaData.MOBILENO,
                                        MetaData.GENDER,
                                        MetaData.TABLENAME,
                                        MetaData.EMAIL,
                                        MetaData.PASSWORD);
        
            String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, this.email);
            ps.setString(2, this.password);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                this.setId(rs.getInt(1));
                this.setFirstname(rs.getString(2));
                this.setLastname(rs.getString(3));
                this.setMobileno(rs.getString(4));
                this.setGender(rs.getString(5).charAt(0));
            }
            else{
                throw new LoginException("Login Failed");
            }
           
        
    }
    
    
    public static Student getStudentDetails(int studentid){
        Student s = new Student();
        String query = String.format("select %s,%s,%s,%s,%s,%s,%s from %s where %s=?;",
                            MetaData.ID,
                            MetaData.FIRSTNAME,
                            MetaData.LASTNAME,
                            MetaData.MOBILENO,
                            MetaData.EMAIL,
                            MetaData.PASSWORD,
                            MetaData.GENDER,
                            MetaData.TABLENAME,
                            MetaData.ID);
        
        
        try{
            String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, studentid);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                
                s.setId(rs.getInt(1));
                s.setFirstname(rs.getString(2));
                s.setLastname(rs.getString(3));
                s.setMobileno(rs.getString(4));
                s.setEmail(rs.getString(5));
                s.setPassword(rs.getString(6));
                s.setGender(rs.getString(7).charAt(0));
                
                return s;
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
       return s;
    }
    
}
