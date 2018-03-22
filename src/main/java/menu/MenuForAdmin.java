package menu;

import Cources.Course;
import Cources.ReadWriteCourseFile;
import Cources.ReadWriteCourseRelation;
import User.EditUserMenu;
import User.ReadWriteUserFile;
import User.User;
import menu.extras.*;

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
        while (running) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Select option");
            System.out.println("1) create user      2) delete user      3) edit user      \n" +
                    "4) delete course    5) create course    6) show user list \n" +
                    "7) register user to course    8) remove user from course  \n" +
                    "9) show course list 10)show course     11) Edit course    \n" +
                    "12)Exit");
            selectOperation(scanner.nextInt());
        }
    }

    private void selectOperation(int selected) {
        switch (selected){
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

        if (input.equalsIgnoreCase("exit")){
            return;
        }else {
            if (input.equalsIgnoreCase("yes")) {
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

            } else {
                System.out.println("wrong input");
                createUser();
            }
        }


    }

    private Integer generateID(){
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
        for (Integer i : courses.keySet()) {
            if (input.equalsIgnoreCase(courses.get(i).getName())){
                return true;
            }
        }
        return false;
    }

    private Integer generateIDforCourse(){
        Integer ID = 0;
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
        String description = null;
        String courseID ;
        courseID = generateIDforCourse().toString();

        while (true){
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

    }

    @Override
    public void viewUsers() {
        updateUsers();
        printTable.printUserHeader();
        for (Integer i: users.keySet()) {
            System.out.println("ID: " + users.get(i).getPersonalNumber() +", Name: " + users.get(i).getFirstName()
                + ", Last name: " + users.get(i).getLastName());
        }

    }

    @Override
    public void deleteCourse() {
        ReadWriteCourseFile readWriteCourseFile = new ReadWriteCourseFile();
        this.courses = updateCourses();
        System.out.println("Enter course id");
        Scanner scanner = new Scanner(System.in);
        Integer input = scanner.nextInt();
        boolean courseFound =  false;
        while (true) {
            for (Integer i : courses.keySet()) {
                if(i==input){
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
        Integer input = scanner.nextInt();
        boolean found =  false;
        while (true) {
            for (Integer i : users.keySet()) {
                if(i==input){
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
        Integer input = scanner.nextInt();
        boolean found =  false;
        while (true) {
            for (Integer i : users.keySet()) {
                if(i==input){
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
        this.courses = updateCourses();
        printTable.printCoursesHeader();
        for (Integer i: courses.keySet()) {
            printTable.printCoursesList(courses.get(i).getCourseID(),courses.get(i).getName(),courses.get(i).getDescription());
        }

    }

    @Override
    public void showCourse() {
        this.courses = updateCourses();
        this.users   = updateUsers();
        System.out.println("Enter course id");
        Scanner scanner = new Scanner(System.in);
        Integer input = scanner.nextInt();
        boolean courseFound =  false;
        while (true) {
            for (Integer i : courses.keySet()) {
                if(i==input){
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
        this.courseRealtions = updateCourseRelations();
        this.users = updateUsers();
        printTable.printDescription(courses.get(i).getName(),courses.get(i).getDescription());
        printTable.printCourseHeader();
        for (String line: courseRealtions.get(i)) {
            printTable.printCourse(users.get(Integer.parseInt(line)).getFirstName(),
                    users.get(Integer.parseInt(line)).getLastName(),users.get(Integer.parseInt(line)).getRole().toString());
        }
    }

    @Override
    public void register() {
        this.courseRealtions = updateCourseRelations();
        this.users = updateUsers();
        this.courses = updateCourses();
        System.out.println("Enter course id");
        Scanner scanner = new Scanner(System.in);
        Integer input = scanner.nextInt();
        boolean courseFound =  false;
        while (true) {
            for (Integer i : courses.keySet()) {
                if(i==input){
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
        Integer input = scanner.nextInt();
        boolean found = false;
        while (true) {
            for (Integer i : users.keySet()) {
                if(i==input){
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

    private void checkIfUserExistsForRemove(Integer courseID){
        System.out.println("Enter person id");
        Scanner scanner = new Scanner(System.in);
        Integer input = scanner.nextInt();
        boolean found = false;
        while (true) {
            for (Integer i : users.keySet()) {
                if(i==input){
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
        this.courseRealtions = updateCourseRelations();
        this.users = updateUsers();
        this.courses = updateCourses();
        System.out.println("Enter course id");
        Scanner scanner = new Scanner(System.in);
        Integer input = scanner.nextInt();
        boolean courseFound =  false;
        while (true) {
            for (Integer i : courses.keySet()) {
                if(i==input){
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
        Integer input = scanner.nextInt();
        boolean courseFound =  false;
        while (true) {
            for (Integer i : courses.keySet()) {
                if(i==input){
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
        while (running){
            System.out.println("1) Change name 2) Change description 3) Exit");
            int input = scanner.nextInt();
            switch (input){
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
