package Cources;

import java.util.Date;

public class Course {
    private String  name;
    private String  description;
    private String  courseID;
    private Date startDate;

    public Course(String name, String description, String courseID, Date startDate){
        this.name = name;
        this.description = description;
        this.courseID = courseID;
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getCourseID() {
        return courseID;
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
