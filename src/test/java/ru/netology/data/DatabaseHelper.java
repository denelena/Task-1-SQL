package ru.netology.data;

import javax.management.InvalidApplicationException;
import java.sql.*;

public class DatabaseHelper {

    private static String dbUrl = "jdbc:mysql://localhost:3306/sample-app";
    private static String dbUser = "Karl";
    private static String dbPwd = "KlaraUkralaKlarnet";

    //Attempts to query the MySql database for the most recent Verification Code that was created for a given user
    public static int getVerificationCode(String userName) throws SQLException, InvalidApplicationException {
        int foundCode = -1;

        String query = "SELECT c.code FROM auth_codes AS c INNER JOIN users AS u ON u.id = c.user_id WHERE u.login = ? order by c.created desc;";

        //deliberately not catching SQL exception; пусть обвалит тест
        Connection cnn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
        PreparedStatement dbCommand = cnn.prepareStatement(query);
        dbCommand.setString(1, userName);

        ResultSet rs = dbCommand.executeQuery();
        if(rs.next()) {
            foundCode = rs.getInt("code"); //topmost row is the most recent, we need only that one
        }
        else{
           throw new InvalidApplicationException("Verification code not found for user '" + userName + "'.") ;
        }

        return foundCode;
    }

    public static void resetCodes(){
        //delete all codes from auth_codes table. That seems to reset invalid login count in SUT
        try {
            Connection cnn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
            Statement dbCommand = cnn.createStatement();
            dbCommand.execute("delete from auth_codes;");
        }
        catch(Exception e){}
    }
}
