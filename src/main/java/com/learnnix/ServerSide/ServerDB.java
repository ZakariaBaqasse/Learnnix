package com.learnnix.ServerSide;

import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.ProfessorInfos;
import com.learnnix.HelperClasses.StudentInfos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServerDB {
    private Connection connection;
    public ServerDB(String username,String password,String connectionString) throws SQLException {
        this.connection = DriverManager.getConnection(connectionString,username,password);
    }
    ////////////////////////////////////Fonction relative a l'admin///////////////////////////////////////////////////////////
    //fonction qui s'occupe du login de l'admin
    public boolean adminLogin(String username,String password){
        boolean successLogin = true;
        try {
            PreparedStatement statement = connection.prepareStatement("select * from admins where username = ? and password = ?");
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet set = statement.executeQuery();
            if(!set.next()){
                successLogin = false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return successLogin;
    }
    /**********************************CRUD Professeurs***************************************/
    //fonction qui retourne la liste des professeurs disponibles
    public List<ProfessorInfos> getAllProfs(){
        List<ProfessorInfos> professors = new ArrayList<>();
        try {
            PreparedStatement selectStatement = connection.prepareStatement("select id,prof_name,prof_speciality from professors");
            ResultSet set = selectStatement.executeQuery();
            while (set.next()){
                ProfessorInfos professor = new ProfessorInfos(set.getInt("id"),set.getString("prof_name"),set.getString("prof_speciality") );
                professors.add(professor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  professors;
    }

    //fonction pour ajouter un professeur a la base de donnees
    public boolean addProfessor(ProfessorInfos professor){
        boolean addedSuccessfully = false;
        try {
            PreparedStatement statement = connection.prepareStatement("insert into professors(prof_name,prof_password,prof_speciality) values (?,?,?)");
            statement.setString(1, professor.getProf_username());
            statement.setString(2, professor.getProfPassword());
            statement.setString(3, professor.getProf_speciality());
            if(statement.executeUpdate()>0){
                addedSuccessfully = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
      return addedSuccessfully;
    }
    public ProfessorInfos getProfessor(int id){
        ProfessorInfos professor = null;
        try {
            PreparedStatement statement = connection.prepareStatement("select * from professors where id=?");
            statement.setInt(1,id);
            ResultSet set = statement.executeQuery();
            while (set.next()){
               professor = new ProfessorInfos(set.getString("prof_name"), set.getString("prof_speciality"), set.getString("prof_password") );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return professor;
    }

    public boolean updateProfInfo(int id,String profName,String password,String profSpeciality){
        boolean updateSuccess = false;
        try {
            PreparedStatement statement = connection.prepareStatement("update professors set prof_name=?,prof_password=?,prof_speciality=? where id =?");
            statement.setString(1,profName);
            statement.setString(2,password);
            statement.setString(3,profSpeciality);
            statement.setInt(4,id);
            if(statement.executeUpdate()>0){
                updateSuccess = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
     return updateSuccess;
    }
   /**********************************CRUD Classes************************************************/
    public List<ClassInfos> getAllClasses(){
        List<ClassInfos> classes = new ArrayList<>();
        try {
            PreparedStatement selectStatement = connection.prepareStatement("select class_id,class_subject,class_description from classes");
            ResultSet set = selectStatement.executeQuery();
            while (set.next()){
                ClassInfos classData = new ClassInfos(set.getInt("class_id"),set.getString("class_subject"),set.getString("class_description") );
                classes.add(classData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  classes;
    }
    public boolean addClass(ClassInfos classData,String profName){
        boolean addedSuccessfully = false;
        try {
            int profId = getProfId(profName);
            PreparedStatement statement = connection.prepareStatement("insert into classes(class_subject,class_description,prof_id) values (?,?,?)");
            statement.setString(1, classData.getClassSubject());
            statement.setString(2, classData.getClassDescription());
            statement.setInt(3, profId);
            if(statement.executeUpdate()>0){
                addedSuccessfully = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return addedSuccessfully;
    }

    public boolean updateClassInfo(ClassInfos classInfos){
        boolean updatedSuccessfully = false;
        int profId = getProfId(classInfos.getProfInCharge());
        try {
            PreparedStatement statement = connection.prepareStatement("update classes set class_subject=?,class_description=?,prof_id=? where class_id=?");
            statement.setString(1, classInfos.getClassSubject());
            statement.setString(2, classInfos.getClassDescription());
            statement.setInt(3, profId);
            statement.setInt(4, classInfos.getClassId());
            if(statement.executeUpdate()>0){
                updatedSuccessfully = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updatedSuccessfully;
    }

    public ClassInfos getClass(int id){
        ClassInfos classData = null;
        try {
            PreparedStatement statement = connection.prepareStatement("select * from classes where class_id=?");
            statement.setInt(1,id);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                classData = new ClassInfos(id, set.getString("class_subject"), set.getString("class_description"),getProfessor(set.getInt("prof_id")).getProf_username());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return classData;
    }
    public int getProfId(String profName){
        int profId = 0;
        PreparedStatement getProfStmt = null;
        try {
            getProfStmt = connection.prepareStatement("select id from professors where prof_name=?");
            getProfStmt.setString(1,profName);
            ResultSet set = getProfStmt.executeQuery();
            while (set.next()){
                profId = set.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return profId;
    }

    public List<StudentInfos> getAllStudents(){
        List<StudentInfos> students = new ArrayList<>();
        try {
            PreparedStatement selectStatement = connection.prepareStatement("select student_id,student_username,student_email from students");
            ResultSet set = selectStatement.executeQuery();
            while (set.next()){
                StudentInfos student = new StudentInfos(set.getInt("student_id"),set.getString("student_username"),set.getString("student_email"));
                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }
}
