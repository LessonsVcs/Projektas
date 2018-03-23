package menu;

import menu.extras.UpdateLists;
import menu.extras.UserInterface;

import java.util.Scanner;

public class MenuForUser extends UpdateLists implements UserInterface {
    private boolean running = true;
    private String myID;

    MenuForUser(String myID){
        this.myID=myID;
    }


    @Override
    public void menu() {
        while (running) {


            Scanner scanner = new Scanner(System.in);
            System.out.println("Select option");
            System.out.println("1) create user      2) delete user      3) edit user      \n" +
                    "4) delete course    5) create course    6) show user list \n" +
                    "7) register user to course    8) remove user from course  \n" +
                    "9) show course list 10)show course     11) Exit");
            selectOperation(scanner);
        }
    }

    private void selectOperation(Scanner scanner) {
        switch (scanner.nextInt()){
            case 1:
                break;
        }
    }

    @Override
    public void viewCourses() {

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
