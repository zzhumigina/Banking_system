import com.sun.net.httpserver.*;
// import org.apache.commons.text.StringEscapeUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.concurrent.*;

public class Endpoint2 extends BaseEndpoint implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException{
        try {
            String requestParamValue=null;
            String requestURI = httpExchange.getRequestURI().toString();
            System.out.println(requestURI);
            System.out.println(httpExchange.getRequestMethod());
            if("POST".equals(httpExchange.getRequestMethod())) {
                System.out.println("AddTransactionEndpoint: Post handled");
                requestParamValue = handlePostRequest(httpExchange);
                System.out.println(requestParamValue);
                try{
                    DB2Connection item = new DB2Connection();
                    item.addTransaction(requestParamValue);
                } catch (SQLException e){
                    setHttpExchangeResponseHeaders(httpExchange);
                    httpExchange.sendResponseHeaders(500, 0);
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("AccountsEndpoint: Nothing handled");
            }
            handleResponse(httpExchange,requestParamValue);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    private String handlePostRequest(HttpExchange httpExchange) throws IOException {

        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);
        int b;
        StringBuilder buf = new StringBuilder(512);
        while((b = reader.read()) != -1){
            buf.append((char) b);
        }
        reader.close();
        isr.close();
        return buf.toString();
    }

    private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        // StringBuilder htmlBuilder = new StringBuilder();
        // htmlBuilder.append("<html>").
        //         append("<body>").
        //         append("<h1>").
        //         append("Hello ")
        //         .append(requestParamValue)
        //         .append("</h1>")
        //         .append("</body>")
        //         .append("</html>");
        // String htmlResponse = StringEscapeUtils.escapeHtml4(htmlBuilder.toString());
        String htmlResponse = "{\"key\": \"value\"}";
        super.setHttpExchangeResponseHeaders(httpExchange);
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}