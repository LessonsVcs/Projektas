package menu.extras;

public class PrintTable {
    public void printDescription(String name, String description){
        String leftAlignFormat = "| %-15s | %-33s |%n";
        System.out.format("+-----------------+-----------------+-----------------+%n");
        System.out.format("+   Course name   |     Description                   +%n");
        System.out.format("+-----------------+-----------------+-----------------+%n");
        System.out.format(leftAlignFormat, name,description);

    }
    public void printCourseHeader(){

        System.out.format("+-----------------+-----------------+-----------------+%n");
        System.out.format("|    Name         |   Last name     |       Role      |%n");
        System.out.format("+-----------------+-----------------+-----------------+%n");
    }
    public void printCourse(String name, String lastname, String role){
        String leftAlignFormat = "| %-15s | %-15s | %-15s |%n";
        System.out.format(leftAlignFormat,name,lastname,role);
        System.out.format("+-----------------+-----------------+-----------------+%n");
    }
    public void printCoursesHeader(){
        System.out.format("+------+---------------+------------------------------+%n");
        System.out.format("|  ID  |   Name        |        Description           |%n");
        System.out.format("+------+---------------+------------------------------+%n");
    }
    public void printCoursesList(String ID, String name, String description){
        String leftAlignFormat = "| %-4s | %-13s | %-28s |%n";
        System.out.format(leftAlignFormat,ID,name,description);
        System.out.format("+------+---------------+------------------------------+%n");

    }
    public void printUserHeader(){
        System.out.format("+------+---------------+------------------------------+%n");
        System.out.format("|  ID  |   Name        |        Description           |%n");
        System.out.format("+------+---------------+------------------------------+%n");
    }

}
