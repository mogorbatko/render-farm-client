import service.TaskService;
import service.UserService;

import java.util.Scanner;

public class RenderFarmClientApplication {
    public static final String REGISTRATION = "signup";
    public static final String LOGIN = "signin";
    public static final String HELP = "help";
    public static final String EXIT = "exit";
    public static final String ADD_TASK = "add";
    public static final String GET_ALL_TASKS = "all";
    public static final String GET_HISTORY_OF_TASK = "history";

    public static void main(String[] args) {
        String userId = "0";
        try {
            System.out.printf("""
                            Hello! You're on Render Farm prototype!
                            Haven't registered yet?
                            Type "%s" - for registration in Render Farm
                            Have account?
                            Type "%s"
                            Or type "%s" for more information.%n""",
                    REGISTRATION,
                    LOGIN,
                    HELP);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String command = scanner.nextLine();
                if (REGISTRATION.equalsIgnoreCase(command)) {
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
                if (LOGIN.equalsIgnoreCase(command)) {
                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    userId = String.valueOf(UserService.loginUser(username, password));
                }
                if (ADD_TASK.equalsIgnoreCase(command)) {
                    System.out.println("Enter title of the task: ");
                    String title = scanner.nextLine();
                    TaskService.addTask(userId, title);
                }
                if (GET_ALL_TASKS.equalsIgnoreCase(command)) {
                    TaskService.getAllTasks(userId);
                }
                if (GET_HISTORY_OF_TASK.equalsIgnoreCase(command)) {
                    System.out.println("Which task would you like to check. Press id:");
                    String taskId = scanner.nextLine();
                    TaskService.getTask(userId, taskId);
                }
                if (HELP.equalsIgnoreCase(command)) {
                    System.out.println(
                            REGISTRATION + " - registration in Render Farm\n" +
                            LOGIN + " - login in Render Farm\n" +
                            ADD_TASK + " - add new task\n" +
                            GET_ALL_TASKS + " - get all tasks\n" +
                            GET_HISTORY_OF_TASK + " - get history of changing status of the task");
                }
                if (EXIT.equalsIgnoreCase(command)) {
                    System.out.println("Bye!");
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
