package menu;

import Cources.Course;
import Cources.ReadWriteCourseFile;
import Cources.ReadWriteCourseRelation;
import User.EditProfile;
import User.User;
import menu.extras.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MenuForLecturer extends UpdateLists implements LecturerInterface,UserInterface {
    private String myID;
    private HashMap<Integer,List<String>> courseRealtions = new HashMap<>();
    private HashMap<Integer, Course> courses= new HashMap();
    private HashMap<Integer, User> users = new HashMap();
    private PrintTable printTable = new PrintTable();
    private boolean running= true;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
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
        switch (Integer.parseInt(scanner.nextLine())){
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
        courses = updateCourses();
        Scanner scanner = new Scanner(System.in);
        boolean courseFound =  false;
        boolean canEdit = false;
        while (true) {
            System.out.println("Enter course id");
            String input = scanner.nextLine();
            //Checks if course exists
            for (Integer i : courses.keySet()) {
                if(i==Integer.parseInt(input)){
                    courseFound = true;
                    try {
                        for (String line : courseRealtions.get(i)) {
                            if (line.equalsIgnoreCase(myID)){
                                editCourseMenu(i);
                                canEdit = true;
                            }
                        }
                    } catch (Exception e){
                        System.out.println("There's no one in course ");
                    }
                    break;
                }
            }
            if (courseFound){
                if (!canEdit){
                    System.out.println("You cant edit this course");
                }
                break;
            }else {
                System.out.println("Course doesn't exist");
            }
        }

    }

    @Override
    public void viewCourses() {
        //Prints out table : ID, Name, Description
        this.courses = updateCourses();
        printTable.printCoursesHeader();
        for (Integer i: courses.keySet()) {
            printTable.printCoursesList(courses.get(i).getCourseID(),courses.get(i).getName(),courses.get(i).getDescription(),
                format.format(courses.get(i).getStartDate()));
        }
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
        Date startDate ;
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
        while (true){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            System.out.println("Enter start date yyyy-MM-dd");
            try {
                startDate = format.parse(scanner.nextLine());
                break;
            }catch (Exception e){
                System.out.println("Wrong format");
            }
        }
        courses.put(Integer.parseInt(courseID),new Course(name,description,courseID,startDate));
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
        this.courseRealtions = updateCourseRelations();
        this.users = updateUsers();
        this.courses = updateCourses();
        System.out.println("Enter course id");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        boolean courseFound =  false;
        while (true) {
            //Checks if course exists
            for (Integer i : courses.keySet()) {
                if(i==Integer.parseInt(input)){
                    courseFound = true;
                    checkIfUserExists(i);
                    break;
                }
            }
            if (courseFound){
                break;
            }else {
                System.out.println("Course doesn't exist");
            }
        }
    }

    @Override
    public void showCourse() {
        this.courses = updateCourses();
        this.users   = updateUsers();
        System.out.println("Enter course id");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        boolean courseFound =  false;
        while (true) {
            //Checks if course exists
            for (Integer i : courses.keySet()) {
                if(i==Integer.parseInt(input)){
                    courseFound = true;
                    showSelectedCourse(i);
                    break;
                }
            }
            if (courseFound){
                break;
            }else {
                System.out.println("Course doesn't exist");
            }
        }


    }

    private void checkIfUserExists(Integer courseID){
        System.out.println("Enter person id");
        Scanner scanner = new Scanner(System.in);
        String  input = scanner.nextLine();
        boolean found = false;
        while (true) {
            //Checks if user Exist
            for (Integer i : users.keySet()) {
                if(i==Integer.parseInt(input)){
                    if(users.get(i).getRole()!= Roles.ADMIN){
                        found = true;
                        addToCourse(i,courseID);
                        break;
                    } else {
                        found = true;
                        System.out.println("Can't add user with admin role");
                    }
                }
            }
            if (found){
                break;
            }else {
                System.out.println("User doesn't exist");
            }
        }

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

    private void addToCourse(Integer userID, Integer courseID){
        //Adds selected user to selected course
        boolean found = false;
        while (true) {
            for (Integer i : courseRealtions.keySet()) {
                if(i==courseID){
                    found = true;
                    courseRealtions.get(i).add(userID.toString());
                    break;
                }
            }
            if (found){
                break;
            } else {
                List<String> list = new ArrayList<>();
                list.add(userID.toString());
                courseRealtions.put(courseID,list);
            }
            ReadWriteCourseRelation readWriteCourseRelation = new ReadWriteCourseRelation();
            readWriteCourseRelation.setCourseRealtions(courseRealtions);
            readWriteCourseRelation.writeCourseRealation();
        }
    }

    private void showSelectedCourse(Integer i){
        //Prints out table who goes to course, First name, Last name, Role
        this.courseRealtions = updateCourseRelations();
        this.users = updateUsers();
        printTable.printDescription(courses.get(i).getName(),courses.get(i).getDescription());
        printTable.printCourseHeader();
        try {
            for (String line : courseRealtions.get(i)) {
                printTable.printCourse(users.get(Integer.parseInt(line)).getFirstName(),
                        users.get(Integer.parseInt(line)).getLastName(), users.get(Integer.parseInt(line)).getRole().toString());
            }
        } catch (Exception e){
            System.out.println("There's no one in course ");
        }
    }

    private void editCourseMenu(Integer id){
        Scanner scanner = new Scanner(System.in);
        boolean changes = false;
        boolean running = true;
        //Menu for editing course
        while (running){
            System.out.println("1) Change name 2) Change description 3) Change start Date 4) Exit");
            String input = scanner.nextLine();
            switch (Integer.parseInt(input)){
                case 1:
                    System.out.println("Enter new name");
                    courses.get(id).setName(scanner.nextLine());
                    changes= true;
                    break;
                case 2:
                    System.out.println("Enter new last name");
                    users.get(id).setLastName(scanner.nextLine());
                    changes= true;
                    break;
                case 3:
                    changes = changeDate(id, scanner);
                    break;
                case 4:
                    //Checks if anything changed, if so asks to save
                    if (changes){
                        running = toSaveCourseChanges(scanner);
                    } else {
                        running = false;

                    }
                    break;
                default:
                    System.out.println("Wrong input");
            }
        }

    }

    private boolean changeDate(Integer id, Scanner scanner) {
        boolean changes;
        while (true){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            System.out.println("Enter start date yyyy-MM-dd");
            try {
                courses.get(id).setStartDate(format.parse(scanner.nextLine()));
                changes=true;
                break;
            }catch (Exception e){
                System.out.println("Wrong format");
            }
        }
        return changes;
    }

    private boolean toSaveCourseChanges(Scanner scanner) {
        //Asks if user wants to save changes
        boolean running;
        while (true){
            System.out.println("Changes are made, do you want to save? Yes/No");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no")){
                running = false;
                if (response.equalsIgnoreCase("yes")){
                    ReadWriteCourseFile readWriteCourseFile = new ReadWriteCourseFile();
                    readWriteCourseFile.setCourses(courses);
                    readWriteCourseFile.writeCourseFile();
                    break;
                } else {
                    break;
                }
            }
            else {
                System.out.println("wrong input");
            }
        }
        return running;
    }


}
