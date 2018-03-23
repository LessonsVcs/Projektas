package menu;

import Cources.Course;
import Cources.ReadWriteCourseFile;
import Cources.ReadWriteCourseRelation;
import User.EditProfile;
import User.User;
import menu.extras.LecturerInterface;
import menu.extras.PrintTable;
import menu.extras.UpdateLists;
import menu.extras.UserInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MenuForLecturer extends UpdateLists implements LecturerInterface,UserInterface {
    private String myID;
    private HashMap<Integer,List<String>> courseRealtions = new HashMap<>();
    private HashMap<Integer, Course> courses= new HashMap();
    private HashMap<Integer, User> users = new HashMap();
    private PrintTable printTable = new PrintTable();
    private boolean running= true;
    MenuForLecturer(String myID){
        this.myID=myID;
    }


    @Override
    public void menu() {
        while (running) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Select option");
            System.out.println("1) edit my profile      2) edit my courses     3) View all courses      \n" +
                               "4) Create course        5) View users          6) Register to course    \n" +
                               "7) Show course          8) Show my courses     9) Exit                  \n" );
            selectOperation(scanner);
        }
    }

    private void selectOperation(Scanner scanner) {
        switch (scanner.nextInt()){
            case 1:
                editUser();
                break;
            case 2:
                editCourses();
                break;
            case 3:
                viewCourses();
                break;

            default:
                System.out.println("Incorrect input");
        }
    }

    @Override
    public void editUser() {
        this.users = updateUsers();
        EditProfile editProfile = new EditProfile();
        editProfile.menu(Integer.parseInt(myID),users);
    }

    @Override
    public void editCourses() {

    }

    @Override
    public void viewCourses() {

    }

    @Override
    public void addCourse() {
        ReadWriteCourseFile readWriteCourseFile = new ReadWriteCourseFile();
        this.courses = updateCourses();
        this.courseRealtions = updateCourseRelations();
        Scanner scanner = new Scanner(System.in);
        String name;
        String description;
        String courseID ;
        courseID = generateIDforCourse().toString();

        while (true){
            //checks if course name already exists
            System.out.println("Enter course name or 'exit' to leave");
            name = scanner.nextLine();
            if (name.equalsIgnoreCase("exit")){
                return;
            }
            if(checkNameCourse(name)){
                System.out.println("This name is already exist");
            } else {
                break;
            }

        }
        System.out.println("Enter description");
        description = scanner.nextLine();
        courses.put(Integer.parseInt(courseID),new Course(name,description,courseID));
        readWriteCourseFile.setCourses(courses);
        readWriteCourseFile.writeCourseFile();
        List<String> list = new ArrayList<>();
        list.add(myID.toString());
        courseRealtions.put(Integer.parseInt(courseID),list);
        ReadWriteCourseRelation readWriteCourseRelation = new ReadWriteCourseRelation();
        readWriteCourseRelation.setCourseRealtions(courseRealtions);
        readWriteCourseRelation.writeCourseRealation();
    }

    @Override
    public void viewUsers() {
        //Prints out all users : ID, First name, Last name
        updateUsers();
        printTable.printUserHeader();
        for (Integer i: users.keySet()) {
            System.out.println("ID: " + users.get(i).getPersonalNumber() +", Name: " + users.get(i).getFirstName()
                    + ", Last name: " + users.get(i).getLastName());
        }

    }

    @Override
    public void register() {

    }

    @Override
    public void showCourse() {

    }

    @Override
    public void exit() {
        this.running = false;
    }
    private boolean checkNameCourse(String input){
        //returns true if found same course name
        for (Integer i : courses.keySet()) {
            if (input.equalsIgnoreCase(courses.get(i).getName())){
                return true;
            }
        }
        return false;
    }

    private Integer generateIDforCourse(){
        // generates UUID for course
        Integer ID ;
        boolean noMatching ;
        while (true) {
            noMatching = true;
            ID = courses.size();
            ID ++ ;
            for (Integer i : courses.keySet()) {
                if(ID==i){
                    noMatching= false;
                }
            }
            if (noMatching){
                break;
            }

        }
        return ID;
    }

}
