import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;
/*
public class GetAccountsEndpoint extends BaseEndpoint implements HttpHandler {

    private String toJSonAccount(int id, String name, int sum){
        StringBuffer sBuffer = new StringBuffer("{");
        sBuffer.append("\"id\":" + String.valueOf(id) + ",");
        sBuffer.append("\"Owner\": " + name + "\",");
        sBuffer.append("\"sum\":" + String.valueOf(sum) + "");
        sBuffer.append("}");
        return sBuffer.toString();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String reqURI = exchange.getRequestURI().toString();
        System.out.println(reqURI+ "; method: " + exchange.getRequestMethod());
        setHttpExchangeResponseHeaders(exchange);
        if("GET".equals(exchange.getRequestMethod())){
            int id = getIdFromGetRequest(reqURI);
            if(id == -1){
                try{
                    List<Account> accountList = Bank.getAccounts();
                    handleResponse(exchange, accountList);
                } catch (SQLException e){
                    OutputStream outputStream = exchange.getResponseBody();
                    exchange.sendResponseHeaders(500, 0);
                    outputStream.flush();
                    outputStream.close();
                    }
                } else{
                try{
                    Account account = Bank.getAccount(id);
                    if(account == null ){
                        exchange.sendResponseHeaders(500, 0);
                    }
                    handleResponse(exchange, account);
                } catch (SQLException e){
                    OutputStream outputStream = exchange.getResponseBody();
                    exchange.sendResponseHeaders(500, 0);
                    outputStream.flush();
                    outputStream.close();
                }

            }
        }
        if("OPTIONS".equals(exchange.getRequestMethod())){
            OutputStream outputStream = exchange.getResponseBody();
            exchange.sendResponseHeaders(200, 0);
            outputStream.flush();
            outputStream.close();
        }

    }

    private void handleResponse( HttpExchange exchange, List<Account> accountList) throws IOException{
        StringBuilder builder = new StringBuilder("[\n");
        for( int i=0; i< accountList.size(); ++i){
            builder.append(accountList.get(i).toJson(1));
            if(i!= accountList.size()-1){
                builder.append(",\n");
            }
        }
        builder.append("\n]");

        String res = builder.toString();
        OutputStream outputStream = exchange.getResponseBody();
        exchange.sendResponseHeaders(200, res.length());
        outputStream.write(res.getBytes());
        outputStream.flush();
        outputStream.close();
    }
    private void handleResponse( HttpExchange exchange, Account account) throws IOException{
        String accJson = account.toJson(0);
        OutputStream outputStream = exchange.getResponseBody();
        exchange.sendResponseHeaders(200, accJson.length());
        outputStream.write(res.getBytes());
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


}


 */