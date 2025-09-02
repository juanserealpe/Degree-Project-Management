package App.Models;

import java.util.List;

public class Director extends User {

    private List<FormatA> listFormat;

    public Director(String email, String names, String lastNames, int accountId, int programId, List<Role> listRole) {
        super(email, names, lastNames, accountId, programId, listRole);
    }

    public Director() {
    }

    public List<FormatA> getListFormat() {
        return listFormat;
    }

    public void setListFormat(List<FormatA> listFormat) {
        this.listFormat = listFormat;
    }
}
