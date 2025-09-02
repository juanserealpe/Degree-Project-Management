package App.Interfaces;

import App.Models.Account;

public interface IAccountRepository {

    public Account toGetById(int accountId);
}
