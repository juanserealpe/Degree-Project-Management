package Repositories;

import main.App.Enums.EnumState;
import main.App.Models.FormatA;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Alexp
 *
 **/
public class CoordinatorRepository extends Repository {

    public CoordinatorRepository(){
    }
    public List<FormatA> getFormatAPending(){
        String script = "SELECT * FROM FormatA WHERE currentStatus = ?;";
        Object[] params = { EnumState.ESPERA.toString() };
        if(!(makeQuery(script, params))) return null;
        List<Map<String, Object>> vData = resultScript.getPayload();
        return null;
    }

    public boolean updateStateFormatA(int pIdStudent, EnumState pNewState) {
        String script = "UPDATE FormatAs SET current_status = ? WHERE idStudent = ?";
        Object[] pParams = {pNewState.toString(), pIdStudent};
        if(makeDml(script, pParams)) return updateCountAttemps(pIdStudent);
        else return false;
    }

    private boolean updateCountAttemps(int pIdStudent){
        String script = "UPDATE FormatA SET attempt = attempt + 1 WHERE idStudent = ?";
        Object[] pParams = {pIdStudent};
        return makeDml(script,pParams);
    }
}