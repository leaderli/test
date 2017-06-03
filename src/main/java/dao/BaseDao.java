package dao;

import java.sql.*;

/**
 * Created by li on 6/2/17.
 */
public abstract class BaseDao {
    protected Connection connection;
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybookstore", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void releaseResource() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract <T> T query() throws Exception;

    public <T> T doSomeQuery() throws Exception{
        openConnection();
        T t = null;
        try {
            t = query();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseResource();
        }
        return t;
    }

    ;

    public static void main(String[] args) {
    }
}
