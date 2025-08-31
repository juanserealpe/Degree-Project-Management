package App.interfaces;

import java.sql.Connection;

public interface IDataBaseConnection {
    Connection Connect();
    void Disconnect(Connection prmConnection);
}
