package App.Interfaces;

import java.sql.Connection;

public interface IDbConnection {

    public Connection toConnect();

    public void toDisconect(Connection conn);
}
