package user;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class EditProfile {
    private HashMap<Integer, User> users;

    public void menu(Integer id, HashMap<Integer, User> users){
        this.users = users;
        boolean running = true;
        boolean changes = false;
        Scanner scanner = new Scanner(System.in);

        while (running){
            System.out.println("Select what to edit");
            System.out.println("1) first name     2) last name     3) password  \n" +
                               "4) date of birth  5) email         6) address   \n" +
                               "7) exit edit menu "                                );
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
                    setDateOfBirth(id, users, scanner);
                    changes= true;
                    break;
                case 5:
                    System.out.println("Enter new  email");
                    users.get(id).setEmail(scanner.nextLine());
                    changes= true;
                    break;
                case 6:
                    System.out.println("Enter new address");
                    users.get(id).setAddress(scanner.nextLine());
                    changes = true;
                    break;
                case 7:
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
}
