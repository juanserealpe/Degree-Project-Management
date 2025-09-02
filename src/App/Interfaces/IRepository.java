package App.Interfaces;

import java.util.List;

public interface IRepository <T>{
    void toAdd(T prmItem);
    List<T> toGetAll();
    T toGetByString(String prmString);
    void toDeleteByString(String prmString);
    void toUpdate(T prmItem);
}
