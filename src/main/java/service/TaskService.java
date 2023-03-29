package service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static util.Endpoints.ADD_TASK_ENDPOINT;
import static util.Endpoints.ALL_TASKS_ENDPOINT;
import static util.Endpoints.TASK_ID_QUERY_PARAM;
import static util.Endpoints.TASK_PATH;
import static util.Endpoints.URL_ENDPOINT;

public class TaskService {
    private static final String ADD_TASK = URL_ENDPOINT + ADD_TASK_ENDPOINT;
    private static final String GET_ALL_TASKS = URL_ENDPOINT + ALL_TASKS_ENDPOINT;
    private static final String GET_TASK = URL_ENDPOINT + TASK_PATH + TASK_ID_QUERY_PARAM;

    private static final String USER_HEADER = "User";
    private static final String TASK_ID_KEY = "id";
    private static final String STATUS_KEY = "status";
    private static final String TITLE_KEY = "title";
    private static final String MESSAGE_KEY = "message";
    private static final String TASK_KEY = "task";
    private static final String TASKS_KEY = "tasks";
    private static final String CREATE_AT_KEY = "createAt";
    private static final String UPDATE_AT_KEY = "updateAt";
    private static final String RENDERING = "RENDERING";
    private static final String COMPLETE = "COMPLETE";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();


    public static void addTask(String userId, String title) throws IOException, InterruptedException {
        JSONObject requestJson = new JSONObject();
        requestJson.put(TITLE_KEY, title);
        String requestBody = requestJson.toString();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ADD_TASK))
                .header("Content-Type", "application/json")
                .header(USER_HEADER, userId)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        String responseBody = CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).body();
        JSONObject responseJson = new JSONObject(responseBody);
        System.out.println(responseJson.getString(MESSAGE_KEY));
    }

    public static void getAllTasks(String userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GET_ALL_TASKS))
                .header("Content-Type", "application/json")
                .header(USER_HEADER, userId)
                .GET()
                .build();
        String responseBody = CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).body();
        JSONObject responseJson = new JSONObject(responseBody);
        JSONArray array = responseJson.getJSONArray(TASKS_KEY);
        if (!array.isEmpty()) {
            System.out.println("The list of your tasks:");
            for (int i = 0; i < array.length(); i++) {
                JSONObject e = array.getJSONObject(i);
                int id = e.getInt(TASK_ID_KEY);
                String title = e.getString(TITLE_KEY);
                String status = e.getString(STATUS_KEY);
                System.out.println(id + ": " + title + " - " + status);
            }
        } else {
            System.out.println("You haven't added any task yet.");
        }

    }

    public static void getTask(String userId, String taskId) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GET_TASK + taskId))
                .header("Content-Type", "application/json")
                .header(USER_HEADER, userId)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        int httpStatus = response.statusCode();
        String responseBody = response.body();
        JSONObject responseJson = new JSONObject(responseBody);
        if (httpStatus == 200) {
            JSONObject taskJson = responseJson.getJSONObject(TASK_KEY);
            int id = taskJson.getInt(TASK_ID_KEY);
            String status = taskJson.getString(STATUS_KEY);
            String createAt = taskJson.getString(CREATE_AT_KEY);
            String updateAt = taskJson.getString(UPDATE_AT_KEY);
            if (RENDERING.equals(status)) {
                System.out.println("Task with id " + id +
                        " was created at " + createAt +
                        " and is rendering now.");
            }
            if (COMPLETE.equals(status)) {
                System.out.println("Task with id " + id +
                        " was created at " + createAt +
                        " and was completed at " + updateAt);
            }
        }
        if (httpStatus == 400) {
            System.out.println(responseJson.getString(MESSAGE_KEY));
            System.out.println("Couldn't find task by id " + taskId);
        }
        if (httpStatus == 500) {
            System.out.println("Oops, something went wrong.");
        }

    }


}



