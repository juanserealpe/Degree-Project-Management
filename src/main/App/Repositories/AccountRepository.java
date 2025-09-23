package Repositories;

import Interfaces.IRepository;
import Models.Account;
import Utilities.BaseRepository;

import java.util.List;

public class AccountRepository extends BaseRepository implements IRepository<Account> {


    @Override
    public void add(Account entity) {}

    @Override
    public void update(Account entity) {}

    @Override
    public void delete(Account entity) {

    }

    @Override
    public Account getById(int id) {
        return null;
    }

    @Override
    public List<Account> getAll() {
        return List.of();
    }
}
