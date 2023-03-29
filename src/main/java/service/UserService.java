package service;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static util.Endpoints.LOGIN_USER_ENDPOINT;
import static util.Endpoints.REGISTER_USER_ENDPOINT;
import static util.Endpoints.URL_ENDPOINT;

public class UserService {
    private static final String REGISTER_USER = URL_ENDPOINT + REGISTER_USER_ENDPOINT;
    private static final String LOGIN_USER = URL_ENDPOINT + LOGIN_USER_ENDPOINT;
    private static final String USER_ID_KEY = "userId";
    private static final String MESSAGE_KEY = "message";
    private static final String USERNAME_KEY = "login";
    private static final String PASSWORD_KEY = "password";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public static void registerUser(String userName, String password) throws IOException, InterruptedException {
        JSONObject requestJson = new JSONObject();
        requestJson.put(USERNAME_KEY, userName);
        requestJson.put(PASSWORD_KEY, password);
        String requestBody = requestJson.toString();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(REGISTER_USER))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        int httpStatus = response.statusCode();
        String responseBody = response.body();
        JSONObject responseJson = new JSONObject(responseBody);
        if (httpStatus == 400) {
            System.out.println(responseJson.getString(MESSAGE_KEY));
            System.out.println("Type \"help\" or try again with another credentials.");
        }
        if (httpStatus == 200) {
            System.out.println(responseJson.getString(MESSAGE_KEY));
            System.out.println("Please, login");
        }
        if (httpStatus == 500) {
            System.out.println("Oops, something went wrong.");
        }

    }

    public static long loginUser(String userName, String password) throws IOException, InterruptedException {
        JSONObject requestJson = new JSONObject();
        requestJson.put(USERNAME_KEY, userName);
        requestJson.put(PASSWORD_KEY, password);
        String requestBody = requestJson.toString();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(LOGIN_USER))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        int httpStatus = response.statusCode();
        String responseBody = response.body();
        JSONObject responseJson = new JSONObject(responseBody);
        if (httpStatus == 400) {
            System.out.println(responseJson.getString(MESSAGE_KEY));
            System.out.println("Type \"help\" or try to register/login");
        }
        if (httpStatus == 200) {
            System.out.println(responseJson.getString(MESSAGE_KEY));
            System.out.println("What would you like to do?\n" +
                    "add - add new task\n" +
                    "all - get all tasks\n" +
                    "history - get history of changing status of task");
        }
        if (httpStatus == 500) {
            System.out.println("Oops, something went wrong.");
        }
        return getUserId(responseJson);
    }

    private static long getUserId(JSONObject responseJson) {
        if (responseJson.has(USER_ID_KEY)) {
            return responseJson.getLong(USER_ID_KEY);
        } else {
            return 0;
        }
    }

}
