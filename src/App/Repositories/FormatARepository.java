package App.Repositories;

import App.DataBase.DbConnection;
import App.Interfaces.IRepository;
import App.Models.FormatA;
import java.util.List;

public class FormatARepository implements IRepository<FormatA> {

    private final DbConnection dbConnection;

    public FormatARepository() {
        this.dbConnection = new DbConnection();
    }

    @Override
    public void toAdd(FormatA prmItem) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<FormatA> toGetAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public FormatA toGetByString(String prmString) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void toDeleteByString(String prmString) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void toUpdate(FormatA prmItem) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
