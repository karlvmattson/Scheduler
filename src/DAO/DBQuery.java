package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBQuery {

    private static PreparedStatement statement; //Statement reference

    /**
     * Create PreparedStatement object
     *
     * @param sqlStatement the SQL query to set up
     */
    public static void setPreparedStatement(String sqlStatement) throws SQLException {
        statement = DBConnection.conn.prepareStatement(sqlStatement);
    }

    /**
     * @return the PreparedStatement
     */
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }

    /**
     * Run the PreparedStatement with try/catch
     *
     * @return the result of the query
     */
    public static ResultSet executePreparedStatement() {
        ResultSet result = null;
        try {
            statement.execute();
            result = statement.getResultSet();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Run the PreparedStatement with try/catch
     *
     * @param sqlQuery query to be run
     * @return the result of the query
     */
    public static ResultSet executePreparedStatement(String sqlQuery) {
        ResultSet result = null;
        try {
            DBQuery.setPreparedStatement(sqlQuery);
            PreparedStatement statement = DBQuery.getPreparedStatement();
            statement.execute();
            result = statement.getResultSet();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
