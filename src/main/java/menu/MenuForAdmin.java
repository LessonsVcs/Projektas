package menu;

import Cources.Course;
import Cources.ReadWriteCourseFile;
import Cources.ReadWriteCourseRelation;
import User.EditUserMenu;
import User.ReadWriteUserFile;
import User.User;
import menu.extras.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MenuForAdmin extends UpdateLists implements AdminInterface,LecturerInterface,UserInterface {
    private String myID;
    private boolean running = true;
    private HashMap<Integer,List<String>> courseRealtions = new HashMap<>();
    private HashMap<Integer, Course> courses= new HashMap();
    private HashMap<Integer, User> users = new HashMap();
    private PrintTable printTable = new PrintTable();
    MenuForAdmin(String myID){
        this.myID=myID;
    }

    public void menu(){
        //Menu for selecting operation
        while (running) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Select option");
            System.out.println("1) create user      2) delete user      3) edit user      \n" +
                    "4) delete course    5) create course    6) show user list \n" +
                    "7) register user to course    8) remove user from course  \n" +
                    "9) show course list 10)show course     11) Edit course    \n" +
                    "12)Exit");
            selectOperation(scanner.nextLine());
        }
    }

    private void selectOperation(String selected) {
        //Selecting operation from menu
        switch (Integer.parseInt(selected)){
            case 1:
                createUser();
                break;
            case 2:
                deleteUser();
                break;
            case 3:
                editUser();
                break;
            case 4:
                deleteCourse();
                break;
            case 5:
                addCourse();
                break;
            case 6:
                viewUsers();
                break;
            case 7:
                register();
                break;
            case 8:
                removeUserFromCourse();
                break;
            case 9:
                viewCourses();
                break;
            case 10:
                showCourse();
                break;
            case 11:
                editCourses();
                break;
            case 12:
                exit();
                break;
            default:
                System.out.println("Wrong input");
        }
    }

    @Override
    public void exit() {
        //exist admin menu
        this.running = false;
    }

    @Override
    public void createUser() {

        ReadWriteUserFile readWriteUserFile = new ReadWriteUserFile();
        this.users = updateUsers();
        String username;
        String password;
        String firstName;
        String lastName;
        Roles role;
        String email = null;
        Date dateOfBirth  = null;
        Integer personalNumber;
        String address = null;
        System.out.println("Create simple user? Yes/No or exit");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        //Create user simple or express. Simple doesn't have: email, dateOfBirth, address,
        if (input.equalsIgnoreCase("exit")){
            return;
        }else {
            if (input.equalsIgnoreCase("yes")) {
                //Check if username is free
                while (true){
                    System.out.println("enter username");
                    username = scanner.nextLine();
                    if(checkName(username)){
                        System.out.println("this username is already taken");
                    } else {
                        break;
                    }
                }

                System.out.println("enter password");
                password = scanner.nextLine();
                System.out.println("enter first name");
                firstName = scanner.nextLine();
                System.out.println("enter last name");
                lastName = scanner.nextLine();
                //Assign role from ENUM
                while (true){
                    System.out.println("enter role");
                    String tmp = scanner.nextLine();
                    if(tmp.equalsIgnoreCase("admin") || tmp.equalsIgnoreCase("user") ||
                            tmp.equalsIgnoreCase("lecturer")){
                        if(tmp.equalsIgnoreCase("admin")){
                            role = Roles.ADMIN;
                        }
                        if(tmp.equalsIgnoreCase("lecturer")){
                            role = Roles.LECTURER;
                        }else{
                            role = Roles.USER;
                        }
                        break;
                    } else {
                        System.out.println("this role doesn't exist");
                    }
                }
                personalNumber = generateID();
                try {
                    users.put(personalNumber,new User(firstName,lastName,password,username,
                                role, email, dateOfBirth, address,personalNumber.toString()));
                }catch (Exception e){
                    System.out.println(e);
                }
                readWriteUserFile.setUsers(users);
                readWriteUserFile.writeUserFile();
            } else if (input.equalsIgnoreCase("no")) {
                while (true){
                    System.out.println("enter username");
                    username = scanner.nextLine();
                    if(checkName(username)){
                        System.out.println("this username is already taken");
                    } else {
                        break;
                    }
                }

                System.out.println("enter password");
                password = scanner.nextLine();
                System.out.println("enter first name");
                firstName = scanner.nextLine();
                System.out.println("enter last name");
                lastName = scanner.nextLine();
                //Assign role from ENUM
                while (true){
                    System.out.println("enter role");
                    String tmp = scanner.nextLine();
                    if(tmp.equalsIgnoreCase("admin") || tmp.equalsIgnoreCase("user") ||
                            tmp.equalsIgnoreCase("lecturer")){
                        if(tmp.equalsIgnoreCase("admin")){
                            role = Roles.ADMIN;
                        }
                        if(tmp.equalsIgnoreCase("lecturer")){
                            role = Roles.LECTURER;
                        }else{
                            role = Roles.USER;
                        }
                        break;
                    } else {
                        System.out.println("this role doesn't exist");
                    }
                }
                personalNumber = generateID();
                System.out.println("enter email");
                email = scanner.nextLine();
                System.out.println("enter email");
                address = scanner.nextLine();
                while (true){
                    System.out.println("enter new birth date. Year-Month-day Ex: 2000-10-10");
                    try {
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        dateOfBirth = format.parse(scanner.nextLine());
                        break;
                    }catch (Exception e){
                        System.out.println("wrong input");
                    }
                }
                try {
                    users.put(personalNumber,new User(firstName,lastName,password,username,
                            role, email, dateOfBirth, address,personalNumber.toString()));
                }catch (Exception e){
                    System.out.println(e);
                }
                readWriteUserFile.setUsers(users);
                readWriteUserFile.writeUserFile();
            } else {
                System.out.println("wrong input");
                createUser();
            }
        }


    }

    private Integer generateID(){
        //Generate UUID for user
        Integer ID = 0;
        boolean noMatching ;
        while (true) {
            noMatching = true;
            ID = users.size();
            ID ++ ;
            for (Integer i : users.keySet()) {
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

    private boolean checkName(String input){
        //returns true if username exists
        for (Integer i : users.keySet()) {
            if (input.equalsIgnoreCase(users.get(i).getUsername())){
                return true;
            }
        }
        return false;
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

    @Override
    public void addCourse() {
        ReadWriteCourseFile readWriteCourseFile = new ReadWriteCourseFile();
        this.courses = updateCourses();
        Scanner scanner = new Scanner(System.in);
        String name;
        String description;
        String courseID ;
        courseID = generateIDforCourse().toString();
        Date startDate= null;
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
        System.out.println("Enter description");
        description = scanner.nextLine();
        courses.put(Integer.parseInt(courseID),new Course(name,description,courseID,startDate));
        readWriteCourseFile.setCourses(courses);
        readWriteCourseFile.writeCourseFile();

    }

    @Override
    public void viewUsers() {
        //Prints out all users : ID, First name, Last name
        users = updateUsers();
        printTable.printUserHeader();
        for (Integer i: users.keySet()) {
            printTable.printUserList(users.get(i).getPersonalNumber(),users.get(i).getFirstName(),users.get(i).getLastName());
        }

    }

    @Override
    public void deleteCourse() {
        ReadWriteCourseFile readWriteCourseFile = new ReadWriteCourseFile();
        this.courses = updateCourses();
        System.out.println("Enter course id");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        boolean courseFound =  false;
        while (true) {
            //Checks if course with that ID exists and removes
            for (Integer i : courses.keySet()) {
                if(i==Integer.valueOf(input)){
                    courseFound = true;
                    courses.remove(i);
                    readWriteCourseFile.writeCourseFile();
                    System.out.println("Course removed");
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
    public void deleteUser() {
        ReadWriteUserFile readWriteUserFile = new ReadWriteUserFile();
        this.users = updateUsers();
        System.out.println("Enter user id");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        boolean found =  false;
        while (true) {
            //Checks if user with that ID exist and removes
            for (Integer i : users.keySet()) {
                if(i==Integer.valueOf(input)){
                    found = true;
                    users.remove(i);
                    readWriteUserFile.writeUserFile();
                    System.out.println("User removed");
                    break;
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
    public void editUser() {
        EditUserMenu editUserMenu = new EditUserMenu();
        this.users = updateUsers();
        System.out.println("Enter user id");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        boolean found =  false;
        while (true) {
            //Checks if user exits
            for (Integer i : users.keySet()) {
                if(i==Integer.parseInt(input)){
                    found = true;
                    editUserMenu.menu(i,users);

                    break;
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
    public void viewCourses() {
        //Prints out table : ID, Name, Description
        this.courses = updateCourses();
        printTable.printCoursesHeader();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        for (Integer i: courses.keySet()) {
            printTable.printCoursesList(courses.get(i).getCourseID(),courses.get(i).getName(),courses.get(i).getDescription(),
                format.format(courses.get(i).getStartDate()));
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

    private void checkIfUserExists(Integer courseID){
        System.out.println("Enter person id");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        boolean found = false;
        while (true) {
            //Checks if user Exist
            for (Integer i : users.keySet()) {
                if(i==Integer.parseInt(input)){
                    found = true;
                    addToCourse(i,courseID);
                    break;
                }
            }
            if (found){
                break;
            }else {
                System.out.println("User doesn't exist");
            }
        }

    }

    private void addToCourse(Integer userID, Integer courseID){
        //Adds selected user to selected course
        boolean found = false;

        for (Integer i : courseRealtions.keySet()) {
            if(i==courseID){
                found = true;
                if(isAlreadyIncourse(userID, i)){
                    System.out.println("User already is in this course");
                } else {
                    courseRealtions.get(i).add(userID.toString());
                }
                break;
            }
        }
        if (!found){
            List<String> list = new ArrayList<>();
            list.add(userID.toString());
            courseRealtions.put(courseID,list);
        }
        ReadWriteCourseRelation readWriteCourseRelation = new ReadWriteCourseRelation();
        readWriteCourseRelation.setCourseRealtions(courseRealtions);
        readWriteCourseRelation.writeCourseRealation();

    }

    private boolean isAlreadyIncourse(Integer userID, Integer i) {

        for(String user: courseRealtions.get(i)){
            if (user.equalsIgnoreCase(userID.toString())){
                return true;
            }
        }
        return false;
    }


    private void checkIfUserExistsForRemove(Integer courseID){
        System.out.println("Enter person id");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        boolean found = false;
        while (true) {
            //Checks if user Exists
            for (Integer i : users.keySet()) {
                if(i==Integer.parseInt(input)){
                    found = true;
                    removeFromCourse(i,courseID);
                    break;
                }
            }
            if (found){
                break;
            }else {
                System.out.println("User doesn't exist");
            }
        }

    }

    private void removeFromCourse(Integer userID, Integer courseID){
        boolean found = false;
        while (true) {
            for (Integer i : courseRealtions.keySet()) {
                if(i==courseID){
                    found = true;
                    courseRealtions.get(i).remove(userID.toString());
                    break;
                }
            }
            if (found){
                ReadWriteCourseRelation readWriteCourseRelation = new ReadWriteCourseRelation();
                readWriteCourseRelation.setCourseRealtions(courseRealtions);
                readWriteCourseRelation.writeCourseRealation();
                break;
            } else {
                System.out.println("Entered user isn't in this course");
            }

        }
    }

    @Override
    public void removeUserFromCourse() {
        //removes selected user from course
        this.courseRealtions = updateCourseRelations();
        this.users = updateUsers();
        this.courses = updateCourses();
        System.out.println("Enter course id");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        boolean courseFound =  false;
        while (true) {
            for (Integer i : courses.keySet()) {
                if(i==Integer.parseInt(input)){
                    courseFound = true;
                    checkIfUserExistsForRemove(i);
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
    public void editCourses() {
        courses = updateCourses();
        System.out.println("Enter course id");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        boolean courseFound =  false;
        while (true) {
            //Checks if course exists
            for (Integer i : courses.keySet()) {
                if(i==Integer.parseInt(input)){
                    courseFound = true;
                    editCourseMenu(i);
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
                    System.out.println("Enter new description");
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

}
