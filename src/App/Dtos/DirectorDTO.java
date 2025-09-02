package App.Dtos;

import App.Models.Account;
import App.Models.Director;

public class DirectorDTO {

    private Director director;
    private Account account;

    public DirectorDTO(Director director, Account account) {
        this.director = director;
        this.account = account;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DirectorDTO{" + "director=" + director + ", account=" + account + '}';
    }

}
