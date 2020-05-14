package web.dao;

import web.dataSets.UserDataset;
import web.executor.TExecutor;
import web.handlers.TResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public UserDataset get(long id) throws SQLException {
        TExecutor executor = new TExecutor();
        return executor.execQuery(connection, "select * from users where id=" + id, new TResultHandler<UserDataset>() {
            public UserDataset handle(ResultSet resultSet) throws SQLException {
                resultSet.next();
                UserDataset dataSet = new UserDataset(resultSet.getLong(1), resultSet.getString(2));
                return dataSet;
            }
        });
    }
}
