import java.io.*;
import java.sql.SQLException;
import java.util.StringTokenizer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class AccountsEndpoint extends BaseEndpoint implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException{
        try {
            String requestParamValue=null;
            String requestURI = httpExchange.getRequestURI().toString();
            System.out.println(requestURI);
            System.out.println(httpExchange.getRequestMethod());
            //setHttpExchangeResponseHeaders(httpExchange);
            if("GET".equals(httpExchange.getRequestMethod())) {
                System.out.println("AccountsEndpoint: GET handled");
                int id = getIdFromGetRequest(requestURI);
                System.out.println(id);
                if(id == -1) {
                    requestParamValue = handleGetRequest(httpExchange);
                } else{
                    if(getTransFromGetRequest(requestURI)){
                        requestParamValue = handleGetTransRequest(httpExchange, id);
                    } else{
                        requestParamValue = handleGetRequest(httpExchange, id);
                    }
                }
                // requestParamValue = "Val";
            }
            else if("POST".equals(httpExchange.getRequestMethod())) {
                System.out.println("AccountsEndpoint: Post handled");
                requestParamValue = handlePostRequest(httpExchange);
                System.out.println(requestParamValue);
                if(getAddFromGetRequest(requestURI)) {
                    try {
                        DB2Connection item = new DB2Connection();
                        item.addAccount(requestParamValue, 1);
                    } catch (SQLException e) {
                        setHttpExchangeResponseHeaders(httpExchange);
                        httpExchange.sendResponseHeaders(500, 0);
                        e.printStackTrace();
                    }
                }
            }
            else{
                System.out.println("AccountsEndpoint: Nothing handled");
            }
            handleResponse(httpExchange,requestParamValue);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }


    private String handleGetRequest(HttpExchange httpExchange) throws SQLException {
        String reqURI = httpExchange.getRequestURI().toString();
        System.out.println("Handling request URI: " + reqURI);
        DB2Connection item;
        item = new DB2Connection();
        return item.showAllAccounts();


    }

    private String handlePostRequest(HttpExchange httpExchange) throws IOException {

        //System.out.println(httpExchange.getRequestBody().toString());
        //return httpExchange.getRequestBody().toString();


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
        htmlResponse = requestParamValue;
        super.setHttpExchangeResponseHeaders(httpExchange);
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }

        private int getIdFromGetRequest(String uri){
            StringTokenizer tokenizer  = new StringTokenizer(uri, "/");
            int id = -1;
            while(tokenizer.hasMoreTokens()){
                String token  = tokenizer.nextToken();
                if("accounts".equals(token)){
                    if(tokenizer.hasMoreTokens()){
                        try{
                            id = Integer.parseInt(tokenizer.nextToken());
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            //////?????
            return id;
        }

    private boolean getTransFromGetRequest(String uri){
        boolean trans= false;
        StringTokenizer tokenizer  = new StringTokenizer(uri, "/");
        while(tokenizer.hasMoreTokens()){
            String token  = tokenizer.nextToken();
            if("transactions".equals(token)){
                trans=true;
            }
        }
        //////?????
        return trans;
    }
    private boolean getAddFromGetRequest(String uri){
        boolean trans= false;
        StringTokenizer tokenizer  = new StringTokenizer(uri, "/");
        while(tokenizer.hasMoreTokens()){
            String token  = tokenizer.nextToken();
            if("add".equals(token)){
                trans=true;
            }
        }
        //////?????
        return trans;
    }

    private String handleGetRequest(HttpExchange httpExchange, int id) throws SQLException {
        String reqURI = httpExchange.getRequestURI().toString();
        System.out.println("Handling request URI: " + reqURI);
        DB2Connection item;
        item = new DB2Connection();
        return item.showAccount(id);

    }
    private String handleGetTransRequest(HttpExchange httpExchange, int id) throws SQLException {
        String reqURI = httpExchange.getRequestURI().toString();
        System.out.println("Handling request URI: " + reqURI);
        DB2Connection item;
        item = new DB2Connection();
        return item.showTransaction(id);

    }
}
