package web.executor;

import web.handlers.TResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TExecutor {
    public <T> T execQuery(Connection connection, String query, TResultHandler<T> handler) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(query);
        ResultSet resultSet = statement.getResultSet();
        T value = handler.handle(resultSet);
        resultSet.close();
        statement.close();
        return value;
    }
}
