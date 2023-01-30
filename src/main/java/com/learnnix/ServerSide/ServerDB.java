package com.learnnix.ServerSide;

import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.ProfessorInfos;
import com.learnnix.HelperClasses.StudentInfos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServerDB {
    private final Connection connection;
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
            PreparedStatement selectStatement = connection.prepareStatement("select class_id,class_name,class_subject,class_description,prof_id from classes");
            ResultSet set = selectStatement.executeQuery();
            while (set.next()){
                ClassInfos classData = new ClassInfos(set.getInt("class_id"),set.getString("class_name"),set.getString("class_subject"),set.getString("class_description"),getProfessor(set.getInt("prof_id")).getProf_username());
                classes.add(classData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  classes;
    }
    public boolean addClass(ClassInfos classData,String profName){
        boolean addedSuccessfully = false;
        if(classNameExist(classData.getClassName())){
            addedSuccessfully = false;
        }
        else{
            try {
                int profId = getProfId(profName);
                PreparedStatement statement = connection.prepareStatement("insert into classes(class_name,class_subject,class_description,prof_id) values (?,?,?,?)");
                statement.setString(1, classData.getClassName());
                statement.setString(2, classData.getClassSubject());
                statement.setString(3, classData.getClassDescription());
                statement.setInt(4, profId);
                if(statement.executeUpdate()>0){
                    addedSuccessfully = true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return addedSuccessfully;
    }

    public boolean updateClassInfo(ClassInfos classInfos){
        boolean updatedSuccessfully = false;
            int profId = getProfId(classInfos.getProfInCharge());
            try {
                PreparedStatement statement = connection.prepareStatement("update classes set class_name=?, class_subject=?,class_description=?,prof_id=? where class_id=?");
                statement.setString(1, classInfos.getClassName());
                statement.setString(2, classInfos.getClassSubject());
                statement.setString(3, classInfos.getClassDescription());
                statement.setInt(4, profId);
                statement.setInt(5, classInfos.getClassId());
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
                classData = new ClassInfos(id,set.getString("class_name"),set.getString("class_subject"), set.getString("class_description"),getProfessor(set.getInt("prof_id")).getProf_username());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return classData;
    }

    public boolean classNameExist(String className){
        boolean exist = false;
        try {
            PreparedStatement statement = connection.prepareStatement("select * from classes where class_name=?");
            statement.setString(1,className);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                exist = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  exist;
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
    ////////////////////////////////////////////////////////Student Functions///////////////////////////////
    public boolean studentLogin(StudentInfos student){
        boolean loginState = false;
        try {
            PreparedStatement statement = connection.prepareStatement("select * from students where student_email = ? AND student_password = ?");
            statement.setString(1,student.getStudentEmail());
            statement.setString(2,student.getStudentPassword());
            ResultSet set = statement.executeQuery();
            if(set.next()){
                loginState = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loginState;
    }

    public String registerStudent(StudentInfos student){
        String registerState = "";
        if(getStudentByEmail(student.getStudentEmail())!=null){
            registerState = "Cannot Create account! Email already taken";
        }else{
            try {
                PreparedStatement statement = connection.prepareStatement("insert into students(student_username,student_email,student_password) values (?,?,?)");
                statement.setString(1,student.getStudentUsername());
                statement.setString(2,student.getStudentEmail());
                statement.setString(3,student.getStudentPassword());
                if(statement.executeUpdate()>0){
                    registerState = "Account created Successfully! Now Login with your Credentials";
                }else{
                    registerState = "Something went wrong! Please try again later";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return registerState;
    }

    public StudentInfos getStudentByEmail(String studentEmail){
        StudentInfos student = null;
        try {
            PreparedStatement statement = connection.prepareStatement("select * from students where student_email = ?");
            statement.setString(1,studentEmail);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                student = new StudentInfos(set.getInt("student_id"),set.getString("student_username"),set.getString("student_email"), set.getString("student_password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
      return student;
    }
    public List<ClassInfos> getJoinedClasses(String studentEmail){
        List<ClassInfos> joinedClasses = new ArrayList<>();
        StudentInfos student = getStudentByEmail(studentEmail);
        try {
            PreparedStatement statement = connection.prepareStatement("select classes.class_id,classes.class_name,classes.class_subject,classes.class_description,classes.prof_id\n" +
                                                                          "from student_class\n" +
                                                                          "inner join classes On classes.class_id = student_class.class_id AND student_class.student_id = ?");
            statement.setInt(1,student.getStudentId());
            ResultSet set = statement.executeQuery();
            while (set.next()){
                ClassInfos classInfos = new ClassInfos(set.getInt("class_id"),set.getString("class_name"),set.getString("class_subject"), set.getString("class_description"),getProfessor(set.getInt("prof_id")).getProf_username());
                joinedClasses.add(classInfos);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return joinedClasses;
    }

    //retourne la liste de tous les classes a l'exception de celles deja integre par l'etudiant
    public List<ClassInfos> getNonJoinedClasses(String studentEmail){
        //variable qui va contenir la liste des classes non interees
        List<ClassInfos> filteredClasses = new ArrayList<>();
        //recuperer la liste de tous les listes
        List<ClassInfos> allClasses = getAllClasses();
        //recuperer la liste des classes que l'etudiant a integre
        List<ClassInfos> joinedClasses = getJoinedClasses(studentEmail);
        //si la liste des classes integrees est null
        if(joinedClasses==null){
            filteredClasses = allClasses;
        }else{
            //sinon
            //filtrer (enlever) les classes que l'etudiant a deja integre de la liste des classes disponibles
            filteredClasses = allClasses.stream().filter(classData->!joinedClasses.contains(classData)).toList();
        }
        return filteredClasses;
    }

    public boolean joinClass(String studentEmail,int classId){
        boolean joinState = false;
        int studentId = getStudentByEmail(studentEmail).getStudentId();
        try {
            PreparedStatement statement = connection.prepareStatement("insert into student_class(class_id,student_id) values (?,?)");
            statement.setInt(1,classId);
            statement.setInt(2,studentId);
            if(statement.executeUpdate()>0){
                joinState = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return joinState;
    }

    public ArrayList<String> getClassMembers(int classId){
        ArrayList<String> members = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("select students.student_email from student_class\n" +
                                                                          "inner join students on students.student_id = student_class.student_id AND student_class.class_id = ?");
            statement.setInt(1,classId);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                members.add(set.getString("student_email"));
            }
            PreparedStatement statement1 = connection.prepareStatement("select professors.prof_name from classes\n" +
                                                                           "inner join professors on professors.id = classes.prof_id AND classes.class_id = ?");
            statement1.setInt(1,classId);
            ResultSet set1 = statement1.executeQuery();
            while (set1.next()){
                members.add(set1.getString("prof_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return members;
    }
    ///////////////////////////////////////Professor functions/////////////////////////////
    public boolean professorLogin(String username,String password){
        boolean loginState = false;
        try {
            PreparedStatement statement = connection.prepareStatement("select * from professors where prof_name=? and prof_password=?");
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet set = statement.executeQuery();
            if(set.next()) loginState = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loginState;
    }

    public ArrayList<ClassInfos> getAssignedClasses(String profName){
        ArrayList<ClassInfos> assignedClasses = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("select classes.class_id,classes.class_name,class_subject,class_description from classes inner join professors on professors.id = classes.prof_id AND professors.prof_name = ?");
            statement.setString(1,profName);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                assignedClasses.add(new ClassInfos(set.getInt("class_id"),set.getString("class_name"), set.getString("class_subject"), set.getString("class_description"), profName ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return assignedClasses;
    }
    public void saveFilePath(String fileName,int classId){
        try {
            PreparedStatement statement = connection.prepareStatement("insert into files(file_name,class_id) values (?,?)");
            statement.setString(1,fileName);
            statement.setInt(2,classId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getStudentsByClass(int classID){
        ArrayList<String> students = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("select students.student_email from student_class\n" +
                                                                          "inner join students on students.student_id = student_class.student_id AND student_class.class_id = ?");
            statement.setInt(1,classID);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                students.add(set.getString("student_email"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    public ArrayList<String> getFilesByClass(int classID){
        ArrayList<String> files = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("select file_name from files where class_id = ?");
            statement.setInt(1,classID);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                files.add(set.getString("file_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return files;
    }
}
