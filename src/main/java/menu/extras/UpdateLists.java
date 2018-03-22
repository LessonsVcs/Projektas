package menu.extras;

import Cources.Course;
import Cources.ReadWriteCourseFile;
import Cources.ReadWriteCourseRelation;
import User.ReadWriteUserFile;
import User.User;

import java.util.HashMap;
import java.util.List;

public class UpdateLists {
     public HashMap<Integer,List<String>> updateCourseRelations() {
        ReadWriteCourseRelation readWriteCourseRelation = new ReadWriteCourseRelation();
        readWriteCourseRelation.ReadCourseRealation();
        return readWriteCourseRelation.getCourseRealtions();
     }
     public HashMap<Integer, User> updateUsers() {
     ReadWriteUserFile readWriteUserFile = new ReadWriteUserFile();
     readWriteUserFile.readUserFile();
     return readWriteUserFile.getUsers();
     }
    public HashMap<Integer, Course> updateCourses(){
    ReadWriteCourseFile readWriteCourseFile = new ReadWriteCourseFile();
    readWriteCourseFile.readCoursefile();
    return readWriteCourseFile.getCourses();
    }
}
