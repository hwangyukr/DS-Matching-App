package Common;

import kr.ac.konkuk.ccslab.cm.info.CMConfigurationInfo;
import kr.ac.konkuk.ccslab.cm.info.CMDBInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    private Connection connection;
    private Statement statement;

    public DBManager(Connection connection, Statement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    public static DBManager getConnection(CMInfo cmInfo) throws SQLException {

        CMDBInfo dbInfo = cmInfo.getDBInfo();

        if(dbInfo.getConnection() == null || dbInfo.getStatement() == null) {

            CMConfigurationInfo confInfo = cmInfo.getConfigurationInfo();

            String url = dbInfo.getDBURL();
            String user = confInfo.getDBUser();
            String pass = confInfo.getDBPass();

            Connection connect = DriverManager.getConnection(url, user, pass);
            dbInfo.setConnection(connect);
            Statement st = connect.createStatement();
            dbInfo.setStatement(st);

            return new DBManager(connect,st);
        }

        return new DBManager(dbInfo.getConnection(), dbInfo.getStatement());
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }
}