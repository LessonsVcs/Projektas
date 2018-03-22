package Cources;

public class Course {
    private String  name;
    private String  description;
    private String  courseID;

    public Course(String name, String description, String courseID){
        this.name = name;
        this.description = description;
        this.courseID = courseID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
