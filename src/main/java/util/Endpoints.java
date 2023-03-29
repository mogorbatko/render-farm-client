package util;

public class Endpoints {
    public static final String URL_ENDPOINT = "http://localhost:8080";
    public static final String USER_PATH = "/user";
    public static final String REGISTER_PATH = "/register";
    public static final String LOGIN_PATH = "/login";
    public static final String TASK_PATH = "/task";
    public static final String ADD_PATH = "/add";
    public static final String All_PATH = "/all";
    public static final String TASK_ID_QUERY_PARAM = "?taskId=";
    public static final String REGISTER_USER_ENDPOINT = USER_PATH + REGISTER_PATH;
    public static final String LOGIN_USER_ENDPOINT = USER_PATH + LOGIN_PATH;
    public static final String ADD_TASK_ENDPOINT = TASK_PATH + ADD_PATH;
    public static final String ALL_TASKS_ENDPOINT = TASK_PATH + All_PATH;
}
