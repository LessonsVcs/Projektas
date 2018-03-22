package User;

import menu.extras.Roles;

import java.util.HashMap;
import java.util.Scanner;

public class Login {
    private ReadWriteUserFile readWriteUserFile = new ReadWriteUserFile();
    private HashMap <Integer,User> users = readWriteUserFile.getUsers();
    private String ID;
    private Roles role;
    public void loginToSystem(){
        readWriteUserFile.readUserFile();

        checkLogin();

    }
    private void checkLogin(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username");
        String username = scanner.nextLine();
        if (username.equalsIgnoreCase("exit")){
            return;
        }
        for (Integer i : users.keySet() ){
            if (username.equalsIgnoreCase(users.get(i).getUsername())){
                checkPassword(i);
                return;
            }
        }
        System.out.println("username doesn't exist");
        checkLogin();
    }
    private void checkPassword(Integer i){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter password");
        String password = scanner.nextLine();
        if (password.equalsIgnoreCase("exit")){
            return;
        }
        if (password.equals(users.get(i).getPassword())){
            System.out.println("login successful");
            setCurrentUserInfo(i);
        } else {
            System.out.println("wrong password");
            checkPassword(i);
        }
    }

    private void setCurrentUserInfo(Integer i) {
        this.ID = i.toString();
        this.role = users.get(i).getRole();
    }

    public String getID() {
        return ID;
    }

    public Roles getRole() {
        return role;
    }
}
