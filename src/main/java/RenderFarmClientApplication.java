import service.TaskService;
import service.UserService;

import java.util.Scanner;

public class RenderFarmClientApplication {
    public static final String REGISTRATION = "registration";
    public static final String LOGIN = "login";
    public static final String HELP = "help";
    public static final String ADD_TASK = "add";
    public static final String GET_ALL_TASKS = "all";
    public static final String GET_HISTORY_OF_TASK = "history";

    public static void main(String[] args) {
        String userId = "0";
        try {
            System.out.println("Hello! You're on Render Farm prototype!\n" +
                    "Haven't registered yet?\n" +
                    "Type \"" + REGISTRATION + "\" - for registration in Render Farm\n" +
                    "Have account?\n" +
                    "Type \"" + LOGIN + "\"\n" +
                    "Or type \"" + HELP +"\" for more information.");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase(REGISTRATION)) {
                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.println("Confirm password: ");
                    String confirmedPassword = scanner.nextLine();
                    while (!password.equals(confirmedPassword)) {
                        System.out.println("Confirmed password doesn't match. Confirm password again: ");
                        confirmedPassword = scanner.nextLine();
                    }
                    UserService.registerUser(username, password);
                }
                if (command.equalsIgnoreCase(LOGIN)) {
                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    userId = String.valueOf(UserService.loginUser(username, password));
                }
                if (command.equalsIgnoreCase(ADD_TASK)) {
                    System.out.println("Enter title of the task: ");
                    String title = scanner.nextLine();
                    TaskService.addTask(userId, title);
                }
                if (command.equalsIgnoreCase(GET_ALL_TASKS)) {
                    TaskService.getAllTasks(userId);
                }
                if (command.equalsIgnoreCase(GET_HISTORY_OF_TASK)) {
                    System.out.println("Which task would you like to check. Press id:");
                    String taskId = scanner.nextLine();
                    TaskService.getTask(userId, taskId);
                }
                if (command.equalsIgnoreCase(HELP)) {
                    System.out.println(
                            REGISTRATION + " - registration in Render Farm\n" +
                            LOGIN + " - login in Render Farm\n" +
                            ADD_TASK + " - add new task\n" +
                            GET_ALL_TASKS + " - get all tasks\n" +
                            GET_HISTORY_OF_TASK + " - get history of changing status of the task");
                }

            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
