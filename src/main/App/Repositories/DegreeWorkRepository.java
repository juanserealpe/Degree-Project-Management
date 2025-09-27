package Repositories;

import Enums.EnumModality;
import Enums.EnumProgram;
import Enums.EnumState;
import Enums.EnumTypeProcess;
import Models.DegreeWork;
import Models.FormatA;
import Models.User;
import Utilities.BaseRepository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DegreeWorkRepository extends BaseRepository {
    /**
     * Constructor que recibe la conexión a la base de datos.
     *
     * @param connection Conexión SQL activa.
     */
    public DegreeWorkRepository(Connection connection) {
        super(connection);
    }

    public List<DegreeWork> getDegreeWorksByDirectorId(int pIdDirector){
        String vScript = "SELECT idDegreeWork, idDirector, idCoDirector, modality FROM degreeWork WHERE idDirector = ?;";
        Object[] vParams = new Object[]{pIdDirector};
        if(!(makeRetrieve(vScript, vParams))) return null;
        return getDataAtDegreeWorks();
    }
    public List<FormatA> getFormatsAByDirectorId(int pIdDirector){
        String vScript = "SELECT f.* FROM FormatA f INNER JOIN degreeWork d ON f.idDegreeWork = d.idDegreeWork WHERE d.idDirector = ?";
        Object[] vParams = new Object[]{pIdDirector};
        if(!(makeRetrieve(vScript, vParams))) return null;
        return getDataAtFormatsA();
    }
    public List<Integer> getIdsStudentsByIdDegreeWork(int pIdDegreeWork){
        String vScript = "SELECT idAccount FROM Student_DegreeWork WHERE idDegreeWork = ?;";
        Object[] vParams = new Object[]{pIdDegreeWork};
        if(!(makeRetrieve(vScript, vParams))) return null;
        return getDataAtIdsStudents();
    }
    public FormatA getFormatAByDegreeWorkId(int pIdDegreeWork){
        String vScript = "SELECT title, currentStatus, attempt, date, url, observation FROM FormatA WHERE idDegreeWork = ?;";
        Object[] vParams = new Object[]{pIdDegreeWork};
        if(!(makeRetrieve(vScript, vParams))) return null;
        List<FormatA> vFormatsA = getDataAtFormatsA();
        if(vFormatsA == null || vFormatsA.isEmpty()) return null;
        else return getDataAtFormatsA().get(0);
    }
    public List<DegreeWork> getDegreeWorksByCoordinator(){
        String vScript = "SELECT idDegreeWork, idDirector, idCodirector, modality FROM degreeWork";
        if(!(makeRetrieve(vScript, null))) return null;
        return getDataAtDegreeWorks();
    }
    public List<FormatA> getFormatsAByCoordinator(){
        String vScript = "SELECT idDegreeWork, title, currentStatus, attempt, date, url, observation FROM FormatA;";
        if(!(makeRetrieve(vScript, null))) return null;
        return getDataAtFormatsA();
    }
    public User getUserByIdAccount(int pIdAccount){
        String vScript = "SELECT name, lastName, phone FROM USER WHERE idUser = ?";
        Object[] vParams = new Object[]{pIdAccount};
        if(!(makeRetrieve(vScript, vParams))) return null;
        return getDataAtUser();
    }
    public String getEmailByIdAccount(int pIdAccount){
        String vScript = "SELECT email FROM Account WHERE idAccount = ?";
        Object[] vParams = new Object[]{pIdAccount};
        if(!(makeRetrieve(vScript, vParams))) return null;
        return (String) resultScript.getPayload().get(0).get("email");
    }
    private List<FormatA> getDataAtFormatsA(){
        if(resultScript.getPayload() == null|| resultScript.getPayload().isEmpty()) return null;
        List<FormatA> vFormatsA = new ArrayList<>();
        for(int idx = 0; idx < resultScript.getPayload().size(); idx++){
            String vTitle = (String) resultScript.getPayload().get(idx).get("title");
            String vState = (String) resultScript.getPayload().get(idx).get("currentStatus");
            int vAttempt = (int) resultScript.getPayload().get(idx).get("attempt");
            //Date vDate = (Date)(resultScript.getPayload().get(idx).get("date"));
            String vUrl = (String) resultScript.getPayload().get(idx).get("url");
            String vObservation = (String) resultScript.getPayload().get(idx).get("observation");
            vFormatsA.add(new FormatA(null, EnumState.valueOf(vState), EnumTypeProcess.FORMAT_A,vTitle,vUrl,vObservation,vAttempt));
        }
        return vFormatsA;
    }
    private List<DegreeWork> getDataAtDegreeWorks(){
        if(resultScript.getPayload() == null|| resultScript.getPayload().isEmpty()) return null;
        List<Map<String, Object>> vDataDegreeWork = resultScript.getPayload();
        List<DegreeWork> vDegreeWorks = new ArrayList<>();
        for(int idx = 0; idx < vDataDegreeWork.size(); idx++){
            int vIdDirector = (int) vDataDegreeWork.get(idx).get("idDirector");
            int vIdCodirector = (int) vDataDegreeWork.get(idx).get("idCoDirector");
            int vIdDegreeWork = (int) vDataDegreeWork.get(idx).get("idDegreeWork");
            EnumModality vModality = EnumModality.valueOf((String)vDataDegreeWork.get(idx).get("modality"));
            vDegreeWorks.add(new DegreeWork(getIdsStudentsByIdDegreeWork(vIdDegreeWork),vIdDirector,vIdCodirector,null,vModality,null));
        }
        return vDegreeWorks;
    }
    private List<Integer> getDataAtIdsStudents(){
        if(resultScript.getPayload() == null|| resultScript.getPayload().isEmpty()) return null;
        List<Integer> vIdsStudents = new ArrayList<>();
        for(int idx = 0; idx < resultScript.getPayload().size(); idx++)
            vIdsStudents.add((Integer) resultScript.getPayload().get(idx).get("idAccount"));
        return vIdsStudents;
    }
    private User getDataAtUser(){
        if(resultScript.getPayload() == null|| resultScript.getPayload().isEmpty()) return null;
        String vName = (String) resultScript.getPayload().get(0).get("name");
        String vLastName = (String) resultScript.getPayload().get(0).get("lastName");
        String vPhone = (String) resultScript.getPayload().get(0).get("phone");
        return new User(vName,vLastName,vPhone);
    }

}
