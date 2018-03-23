package menu;

import menu.extras.LecturerInterface;
import menu.extras.UserInterface;

import java.util.Scanner;

public class MenuForLecturer implements LecturerInterface,UserInterface {
    private String myID;
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

    }

    @Override
    public void editCourses() {

    }

    @Override
    public void viewCourses() {

    }

    @Override
    public void addCourse() {

    }

    @Override
    public void viewUsers() {

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
}
