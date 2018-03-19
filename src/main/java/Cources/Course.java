package User;

public class Course {
    private String  name;
    private String  description;
    private Integer courseID;

    Course(String name, String description, Integer courseID){
        this.name = name;
        this.description = description;
        this.courseID = courseID;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
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
