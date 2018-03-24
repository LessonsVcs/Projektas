package User;

import menu.extras.Roles;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class EditUserMenu {
    private HashMap<Integer, User> users;

    public void menu(Integer id, HashMap<Integer, User> users){
        this.users = users;
        boolean running = true;
        boolean changes = false;
        Scanner scanner = new Scanner(System.in);

        while (running){
            System.out.println("Select what to edit");
            System.out.println("1) first name    2) last name     3) password  \n" +
                    "4) username      5) date of birth 6) email     \n" +
                    "7) address       8) change role                \n" +
                    "9) exit edit menu   ");
            String input = scanner.nextLine();
            switch (Integer.parseInt(input)){
                case 1:
                    System.out.println("Enter new name");
                    users.get(id).setFirstName(scanner.nextLine());
                    changes= true;
                    break;
                case 2:
                    System.out.println("Enter new last name");

                    users.get(id).setLastName(scanner.nextLine());
                    changes= true;
                    break;
                case 3:
                    System.out.println("Enter new password");
                    users.get(id).setPassword(scanner.nextLine());
                    changes= true;
                    break;
                case 4:
                    changeUsername(id, users, scanner);
                    changes= true;
                    break;
                case 5:
                    setDateOfBirth(id, users, scanner);
                    changes= true;
                    break;
                case 6:
                    System.out.println("Enter new  email");
                    users.get(id).setEmail(scanner.nextLine());
                    changes= true;
                    break;
                case 7:
                    System.out.println("Enter new address");
                    users.get(id).setAddress(scanner.nextLine());
                    changes = true;
                    break;
                case 8:
                    changeRole(id, users, scanner);
                    changes = true;
                    break;
                case 9:
                    if(changes){
                        running = saveUsers(users, scanner);
                    } else {
                        running = false;
                    }
                    break;

                default:
                    System.out.println("Wrong input");
            }
        }
    }

    private void changeRole(Integer id, HashMap<Integer, User> users, Scanner scanner) {

        while (true){
            System.out.println("Enter role : admin, user, lecturer");
            String tmp = scanner.nextLine();
            if(tmp.equalsIgnoreCase("admin") || tmp.equalsIgnoreCase("user") ||
                    tmp.equalsIgnoreCase("lecturer")){
                if(tmp.equalsIgnoreCase("admin") && users.get(id).getRole()!= Roles.ADMIN){
                    users.get(id).setRole(Roles.ADMIN);
                    break;
                } if(tmp.equalsIgnoreCase("lecturer" )&& users.get(id).getRole()!=Roles.LECTURER){
                    users.get(id).setRole(Roles.LECTURER);
                    break;
                } if(tmp.equalsIgnoreCase("user" )&& users.get(id).getRole()!=Roles.USER) {
                    users.get(id).setRole(Roles.USER);
                    break;
                } else{
                    System.out.println("can't change to same role");
                }
            } else {
                System.out.println("this role doesn't exist");
            }
        }
    }

    private boolean saveUsers(HashMap<Integer, User> users, Scanner scanner) {
        boolean running;
        while (true){
            System.out.println("Changes are made, do you want to save? Yes/No");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no")){
                running = false;
                if (response.equalsIgnoreCase("yes")){
                    ReadWriteUserFile readWriteUserFile = new ReadWriteUserFile();
                    readWriteUserFile.setUsers(users);
                    readWriteUserFile.writeUserFile();
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

    private void setDateOfBirth(Integer id, HashMap<Integer, User> users, Scanner scanner) {
        while (true){
            System.out.println("enter new birth date. Year-Month-day Ex: 2000-10-10");
            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date date = format.parse(scanner.nextLine());
                users.get(id).setDateOfBirth(date);
                break;
            }catch (Exception e){
                System.out.println("wrong input");
            }
        }
    }

    private void changeUsername(Integer id, HashMap<Integer, User> users, Scanner scanner) {
        while (true){
            System.out.println("Enter new username");
            String username = scanner.nextLine();
            if (checkName(username)){
                System.out.println("this username already exist");
            } else {
                users.get(id).setUsername(username);
                break;
            }

        }
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
}
