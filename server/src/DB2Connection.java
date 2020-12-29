
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONObject;

public class DB2Connection {


    private String toJSonAccount(int id, String name, int sum){
        StringBuffer sBuffer = new StringBuffer("{");
        sBuffer.append("\"Client_id\":\"" + String.valueOf(id) + "\",\n");
        sBuffer.append("\"Name\":\" " + name + "\",\n");
        sBuffer.append("\"State\":\"" + String.valueOf(sum) + "\"\n");
        sBuffer.append("};\n");
        return sBuffer.toString();
    }
    private String toJSonTransaction(int id, int client_id, int sum){
        StringBuffer sBuffer = new StringBuffer("{");
        sBuffer.append("\"Trans_id\":\"" + String.valueOf(id) + "\",\n");
        sBuffer.append("\"Client_id\":\" " + String.valueOf(client_id) + "\",\n");
        sBuffer.append("\"Balance\":\"" + String.valueOf(sum) + "\"\n");
        sBuffer.append("};\n");
        return sBuffer.toString();
    }

    public void addAccount(String name, int sum ) throws SQLException {

        ResultSet resultSetAccount = this.statement.executeQuery("SELECT * FROM BANK");
        int size = 0;
        while(resultSetAccount.next()) {
            resultSetAccount.getInt(1);
            resultSetAccount.getString(2);
            resultSetAccount.getInt(3);
            size++;
        }
        size+=1;
        resultSetAccount.close();

        JSONObject jsonObject = new JSONObject(name);
        System.out.println(jsonObject.getString("name"));


        String query = "INSERT INTO BANK (CLIENT_ID, NAME, STATE) VALUES ("+size+",'"+jsonObject.getString("name")+"',"+sum+");";
        System.out.println("Add account : ");
        System.out.println(query);
        statement.execute(query);


    }


    public String showAllAccounts() throws SQLException {

        ResultSet resultSetAccount = this.statement.executeQuery("SELECT * FROM BANK");
        System.out.println("List of accounts ");
        System.out.println("ID\tName\tSum");
        System.out.println("==\t================\t=======");
        StringBuffer sBuffer = new StringBuffer();
        while(resultSetAccount.next()) {
            sBuffer.append(this.toJSonAccount(resultSetAccount.getInt(1), resultSetAccount.getString(2), resultSetAccount.getInt(3)));
        }
        resultSetAccount.close();
        return sBuffer.toString();
    }

    public String showAccount(int id) throws SQLException {
        ResultSet resultSetAccount = this.statement.executeQuery("SELECT * FROM BANK WHERE CLIENT_ID="+ id);
        System.out.println("find account by id ");
        System.out.println("ID\tName\tSum");
        System.out.println("==\t================\t=======");
        StringBuffer sBuffer = new StringBuffer();
        while(resultSetAccount.next()) {
            sBuffer.append(this.toJSonAccount(resultSetAccount.getInt(1),
                    resultSetAccount.getString(2) ,
                    resultSetAccount.getInt(3)));
        }
        resultSetAccount.close();
        return sBuffer.toString();
    }

    //private void addTransaction( int accountId, int sum) throws SQLException {
    public void addTransaction( String s) throws SQLException {
        ResultSet resultSetAccount = this.statement.executeQuery("SELECT * FROM TRANSACTION");
        int size = 0;
        while(resultSetAccount.next()) {
            resultSetAccount.getInt(1);
            resultSetAccount.getString(2);
            size++;
        }
        resultSetAccount.close();

        JSONObject jsonObject = new JSONObject(s);
        int accountId = jsonObject.getInt("client_id" );

        int balance = 0;
        ResultSet resultSetAccountBalance = this.statement.executeQuery("SELECT BALANCE FROM TRANSACTION WHERE TRANS_ID=(SELECT MAX(TRANS_ID) FROM TRANSACTION WHERE CLIENT_ID = " + accountId + ");");
        while(resultSetAccountBalance.next()) {
            balance = resultSetAccountBalance.getInt(1);
        }
        resultSetAccountBalance.close();


        System.out.println("add transaction ");
        size++;

        int sum =0;
        int res= 0;
        if(jsonObject.has("add")){
            sum = jsonObject.getInt("add");
            res = balance + sum;
        } else if (jsonObject.has("withdraw")){
            sum = jsonObject.getInt("withdraw");
            if(balance <=0 || balance - sum <0){
                System.out.println("Not enough money to complete the operation");
            } else{
                res = balance - sum;
            }
        }

        statement
                .executeUpdate(
                        "INSERT INTO TRANSACTION (TRANS_ID, CLIENT_ID , BALANCE) VALUES ("+size+","+
                                accountId+","+res+");");
    }

    public String showTransaction( int accountId) throws SQLException {
        System.out.println("show transaction for account");
        ResultSet resultSetAccount = this.statement.executeQuery("SELECT * FROM TRANSACTION WHERE CLIENT_ID = "+accountId+";" );
        System.out.println("ID\tac_id\tSum");
        System.out.println("==\t================\t=======");

        StringBuffer sBuffer = new StringBuffer("");
        while(resultSetAccount.next()) {
            sBuffer.append(this.toJSonTransaction(resultSetAccount.getInt(1),
                    resultSetAccount.getInt(2) ,
                    resultSetAccount.getInt(3)));
        }
        resultSetAccount.close();
        return sBuffer.toString();

    }


    private  Connection connection;
    private Statement statement;

    public DB2Connection() throws SQLException{
        try {
            //Class.forName("com.ibm.db2.jcc.DB2Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException cnfex) {
            System.out.println("Problem in"
                    + " loading or registering IBM DB2 JDBC driver");
            cnfex.printStackTrace();
        }

        // Step 2: Opening database connection
        try {

            // Step 2.A: Create and
            // get connection using DriverManager class
            //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Courses", "root", "11111");
                    //"jdbc:db2://localhost:5035/DALLASB",


            // Step 2.B: Creating JDBC Statement
            statement = connection.createStatement();
        }
        /*finally {
            // Step 3: Closing database connection
            try {
                if(null != connection) {
                    // cleanup resources, once after processing

                    statement.close();

                    // and then finally close connection
                    connection.close();
                }
            }
            */
            catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }




    }
}
