package App.Repositories;

import App.Models.FormatA;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alexp
 */
public class CoordinatorRepository extends Repository{
    
    public CoordinatorRepository(){
    }
    public List<FormatA> GetFormatA(){
        String[] columns = {"id_format", "email_student", "email_director", "email_codirector", "title", "current_status", "attempts", "current_date","objective"};
        script = "SELECT * FROM FormatoA;";
        List<Map<String, Object>> mapFormatA = this.ExecuteQuery(script,null,columns);
        return null;
    }    
    
    public int UpdateStateFormatA(String pEmailStudent, String pNewState) {
        script = "UPDATE FormatoA SET current_status = ? WHERE email_student = ?";
        Object[] pParams = {pNewState, pEmailStudent};
        return ExecuteUpdate(script, pParams);
    }

}
