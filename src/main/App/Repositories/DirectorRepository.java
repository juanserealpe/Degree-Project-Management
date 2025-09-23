package Repositories;

import Interfaces.IRepository;
import Models.DegreeWork;
import Models.User;
import Utilities.BaseRepository;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class DirectorRepository extends BaseRepository implements IRepository<User> {
    /**
     * Constructor que recibe la conexión a la base de datos.
     *
     * @param connection Conexión SQL activa.
     */
    public DirectorRepository(Connection connection) {
        super(connection);
    }

    @Override
    public void add(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    public User getByString(String str) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }
    public List<DegreeWork> getDegreeWorks(int directorId) {
        String sqlStatment = "SELECT * FROM Account a INNER JOIN Account_Role b on a.idAccount = b.idAccount WHERE b.idRole = 2 AND a.idAccount = ?";
        boolean directorFound = makeRetrieve(sqlStatment, new Object[]{directorId});
        if(!directorFound) return null;

        Map<String, Object> degreeWorksRow = getOperationResult().getPayload().get(0);
        //TODO: terminar de implementarlo:D
        return  null;
    }
}
