package App.Repositories;

import App.Models.FormatA;

import java.util.List;

/**
 *
 * @author Alexp
 *
 **/
public class CoordinatorRepository extends Repository{
    
    public CoordinatorRepository(){
    }

    /**
     * Retrieves all records from the "FormatoA" table as a list of FormatA objects.
     *
     * @return A List of FormatA objects representing the rows of the FormatoA table.
     * According to a single coordinator,in another case it will be conditioned.
     * Currently returns null, but can be updated to map the query result.
     *
     * This method defines the columns to retrieve, executes a SELECT query,
     * and fetches the result using the getDataFromColumns() method.
     ***/
    public List<FormatA> getFormatA(){
        String[] columns = {"id_format", "email_student", "email_director", "email_codirector", "title", "current_status", "attempts", "current_date","objective"};
        String script = "SELECT * FROM FormatoA;";
        executeScript(script, null, columns);
        Object[] varDatas = this.getDataFromColumns(columns);
        return null;
    }

    /**
     * Updates the "current_status" of the table FormatA record for a specific student.
     *
     * @param pEmailStudent The email of the student whose FormatA record will be updated.
     * @param pNewState The new status value to set in the current_status column.
     * @return true if the update was successful, false otherwise.
     *
     * This method uses executeScript() from the base Repository to safely
     * execute the UPDATE query with parameters.
     **/
    public boolean updateStateFormatA(String pEmailStudent, String pNewState) {
        String script = "UPDATE FormatoA SET current_status = ? WHERE email_student = ?";
        Object[] pParams = {pNewState, pEmailStudent};
        return executeScript(script, pParams);
    }
}
