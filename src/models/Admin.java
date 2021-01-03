
package models;

import Exceptions.LoginException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Admin {
    private Integer id;
    private String email;
    private String password;
    
    
    public static class MetaData{
        public static final String TABLENAME = "admin";
        public static final String ID = "id";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
    }

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    
    
    public Admin(Integer id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Admin() {
    }

    public Integer getId() {
        return id;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" + "id=" + id + ", email=" + email + ", password=" + password + '}';
    }
    
    public static void createtable(){
        String raw = "create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "%s VARCHAR(20),"
                    + "%s VARCHAR(20))";
            
            String query = String.format(raw, MetaData.TABLENAME,
                                            MetaData.ID,
                                            MetaData.EMAIL,
                                            MetaData.PASSWORD);
            
        
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
    
    public Admin save(){
        String raw = "insert into %s (%s,%s) values (?,?)";
        String query = String.format(raw,   MetaData.TABLENAME,
                                            MetaData.EMAIL,
                                            MetaData.PASSWORD);
        
        
        try{
            String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, this.email);
            ps.setString(2, this.password);
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
        String query = String.format("select * from %s where %s=? and %s=?;",
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
                System.out.println("login success");
            }
            else{
                throw new LoginException("Login Failed");
            }
           
        
    }
    
    public static ArrayList<Admin> getAll(){
        ArrayList<Admin> admins = new ArrayList<>();
        
        String query = String.format("select %s,%s,%s from %s;",
                                        MetaData.ID,
                                        MetaData.EMAIL,
                                        MetaData.PASSWORD,
                                        MetaData.TABLENAME);
        
        
        try{
            String connectionUrl = "jdbc:sqlite:quiz.db";
        Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = con.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Admin a = new Admin();
                a.setId(rs.getInt(1));
                a.setEmail(rs.getString(2));
                a.setPassword(rs.getString(3));
                
                admins.add(a);
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
       return admins;
    }
    
}
