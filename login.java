import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Main {
  public static void main(String[] args) throws Exception {
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
    server.createContext("/login", new LoginHandler());
    server.setExecutor(null); // Use the default executor
    server.start();
  }

  static class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
      if ("POST".equals(exchange.getRequestMethod())) {
        // Parse the request body
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String requestBody = br.readLine();
        JSONObject json = new JSONObject(requestBody);
        // Get the username and password from the request
        String username = json.getString("username");
        String password = json.getString("password");

        // Perform your evaluation logic here
        boolean success = evaluateCredentials(username, password);

        // Create the response JSON
        JSONObject responseJson = new JSONObject();
        responseJson.put("success", success);

        // Set the response headers
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, responseJson.toString().length());

        // Send the response
        OutputStream os = exchange.getResponseBody();
        os.write(responseJson.toString().getBytes("utf-8"));
        os.close();
      }
    }
  }
  static boolean evaluateCredentials(String username, String password) {
    // Add your evaluation logic here
    // You can check the credentials against a database, file, or any other storage mechanism

    // For demonstration purposes, we'll assume the credentials are valid if the username is "admin" and password