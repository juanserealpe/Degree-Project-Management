package Repositories;

import Enums.EnumModality;
import Enums.EnumState;
import Enums.EnumTypeProcess;
import Interfaces.IDegreeWorkRepository;
import Models.DegreeWork;
import Models.FormatA;
import Models.User;
import Utilities.BaseRepository;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DegreeWorkRepository extends BaseRepository implements IDegreeWorkRepository {
    /**
     * Constructor que recibe la conexión a la base de datos.
     *
     * @param connection Conexión SQL activa.
     */
    public DegreeWorkRepository(Connection connection) {
        super(connection);
    }
    @Override
    public List<DegreeWork> getDegreeWorksByDirectorId(int pIdDirector){
        String vScript = "SELECT idDegreeWork, idDirector, idCoDirector, modality FROM degreeWork WHERE idDirector = ?;";
        Object[] vParams = new Object[]{pIdDirector};
        if(!(makeRetrieve(vScript, vParams))) return null;
        return getDataAtDegreeWorks();
    }
    @Override
    public List<FormatA> getFormatsAByDirectorId(int pIdDirector){
        String vScript = "SELECT f.* FROM FormatA f INNER JOIN degreeWork d ON f.idDegreeWork = d.idDegreeWork WHERE d.idDirector = ?";
        Object[] vParams = new Object[]{pIdDirector};
        if(!(makeRetrieve(vScript, vParams))) return null;
        return getDataAtFormatsA();
    }
    @Override
    public List<Integer> getIdsStudentsByDegreeWorkId(int pIdDegreeWork){
        String vScript = "SELECT idAccount FROM Student_DegreeWork WHERE idDegreeWork = ?;";
        Object[] vParams = new Object[]{pIdDegreeWork};
        if(!(makeRetrieve(vScript, vParams))) return null;
        return getDataAtStudentsId();
    }
    @Override
    public FormatA getFormatAByDegreeWorkId(int pIdDegreeWork){
        String vScript = "SELECT title, currentStatus, attempt, date, url, observation FROM FormatA WHERE idDegreeWork = ?;";
        Object[] vParams = new Object[]{pIdDegreeWork};
        if(!(makeRetrieve(vScript, vParams))) return null;
        List<FormatA> vFormatsA = getDataAtFormatsA();
        if(vFormatsA == null || vFormatsA.isEmpty()) return null;
        else return getDataAtFormatsA().get(0);
    }
    @Override
    public List<DegreeWork> getAllDegreeWorks(){
        String vScript = "SELECT idDegreeWork, idDirector, idCodirector, modality, date, state FROM degreeWork";
        if(!(makeRetrieve(vScript, null))) return null;
        return getDataAtDegreeWorks();
    }
    @Override
    public List<FormatA> getAllFormatsA(){
        String vScript = "SELECT idDegreeWork, title, currentStatus, attempt, date, url, observation FROM FormatA;";
        if(!(makeRetrieve(vScript, null))) return null;
        return getDataAtFormatsA();
    }
    @Override
    public List<FormatA> getPendingFormatsA(){
        String vScript = "SELECT idDegreeWork, title, currentStatus, attempt, date, url, observation FROM FormatA WHERE currentStatus = 'ESPERA';";
        if(!(makeRetrieve(vScript, null))) return null;
        return getDataAtFormatsA();
    }
    @Override
    public User getUserByAccountId(int pIdAccount){
        String vScript = "SELECT name, lastName, phone FROM USER WHERE idUser = ?";
        Object[] vParams = new Object[]{pIdAccount};
        if(!(makeRetrieve(vScript, vParams))) return null;
        return getDataAtUser();
    }
    @Override
    public String getEmailByAccountId(int pIdAccount){
        String vScript = "SELECT email FROM Account WHERE idAccount = ?";
        Object[] vParams = new Object[]{pIdAccount};
        if(!(makeRetrieve(vScript, vParams))) return null;
        return (String) resultScript.getPayload().get(0).get("email");
    }
    @Override
    public boolean evaluateFormatAByDegreeWorkId(int pIdDegreeWork , String pObservation, EnumState pNewState){
        String vScript = "UPDATE FormatA SET currentStatus = ?, observation = ? WHERE idDegreeWork = ?";
        Object [] vParams = {pNewState.toString(), pObservation, pIdDegreeWork};
        if(!(makeUpdate(vScript,vParams))) return false;
        if(!(pNewState.equals(EnumState.RECHAZADO))) return true;
        vScript = "UPDATE FormatA SET attempt = attempt + 1 WHERE idDegreeWork = ?";
        vParams = new Object[]{pIdDegreeWork};
        return makeUpdate(vScript,vParams);
    }
    @Override
    public int getIdDegreeWorkByAccountId(int pIdAccount){
        String vScript = "SELECT idDegreeWork FROM Student_DegreeWork WHERE idAccount = ?;";
        Object[] vParams = new Object[]{pIdAccount};
        if(!(makeRetrieve(vScript, vParams))) return 0;
        return (int) resultScript.getPayload().get(0).get("idDegreeWork");
    }
    @Override
    public int getFormatAAttempts(int pIdFormatA){
        String vScript = "SELECT attempt FROM FormatA WHERE idFormatA = ?;";
        Object[] vParams = new Object[]{pIdFormatA};
        int vAttempts = 0;
        if(makeRetrieve(vScript,vParams)) return (int)resultScript.getPayload().get(0).get("attempt");
        else return -1;
    }
    @Override
    public DegreeWork getDegreeWorkByIdStudent(int pIdStudentAccount) {
        String vScript = "SELECT d.idDegreeWork, d.idDirector, d.idCoDirector, d.idProgram, d.modality " +
                "FROM degreeWork d " +
                "INNER JOIN Student_DegreeWork sd ON d.idDegreeWork = sd.idDegreeWork " +
                "WHERE sd.idAccount = ?;";

        Object[] vParams = new Object[]{pIdStudentAccount};

        if (!(makeRetrieve(vScript, vParams))) return null;

        List<DegreeWork> degreeWorks = getDataAtDegreeWorks();
        if (degreeWorks == null || degreeWorks.isEmpty()) return null;

        return degreeWorks.get(0);
    }


    private List<FormatA> getDataAtFormatsA(){
        if(resultScript.getPayload() == null|| resultScript.getPayload().isEmpty()) return null;
        List<FormatA> vFormatsA = new ArrayList<>();
        SimpleDateFormat vFormatter = new SimpleDateFormat("yyyy-MM-dd");
        for(int idx = 0; idx < resultScript.getPayload().size(); idx++){
            String vTitle = (String) resultScript.getPayload().get(idx).get("title");
            String vState = (String) resultScript.getPayload().get(idx).get("currentStatus");
            int vAttempt = (int) resultScript.getPayload().get(idx).get("attempt");
            String vUrl = (String) resultScript.getPayload().get(idx).get("url");
            String vObservation = (String) resultScript.getPayload().get(idx).get("observation");
            Date vDate;
            try {
                vDate = vFormatter.parse((String)resultScript.getPayload().get(idx).get("date"));
            } catch (ParseException e) {
                vDate = null;
            }
            vFormatsA.add(new FormatA(vDate, EnumState.valueOf(vState), EnumTypeProcess.FORMAT_A,vTitle,vUrl,vObservation,vAttempt));
        }
        return vFormatsA;
    }
    private List<DegreeWork> getDataAtDegreeWorks(){
        if(resultScript.getPayload() == null|| resultScript.getPayload().isEmpty()) return null;
        List<Map<String, Object>> vDataDegreeWork = resultScript.getPayload();
        List<DegreeWork> vDegreeWorks = new ArrayList<>();
        SimpleDateFormat vFormatter = new SimpleDateFormat("yyyy-MM-dd");
        for(int idx = 0; idx < vDataDegreeWork.size(); idx++){
            int vIdDirector = (int) vDataDegreeWork.get(idx).get("idDirector");
            Integer vIdCodirectorObj = (Integer) vDataDegreeWork.get(idx).get("idCoDirector");
            int vIdCodirector = (vIdCodirectorObj != null) ? vIdCodirectorObj : 0; // usa 0 o el valor que quieras por defecto
            int vIdDegreeWork = (int) vDataDegreeWork.get(idx).get("idDegreeWork");
            EnumModality vModality = EnumModality.valueOf((String)vDataDegreeWork.get(idx).get("modality"));
            Date vDate;
            try {
                Object rawDate = resultScript.getPayload().get(idx).get("date");

                if (rawDate != null) {
                    vDate = vFormatter.parse(rawDate.toString());
                } else {
                    vDate = null; // no hay fecha en BD
                }
            } catch (Exception e) {
                vDate = null; // fecha inválida o formato incorrecto
            }

            vDegreeWorks.add(new DegreeWork(vIdDegreeWork,getIdsStudentsByDegreeWorkId(vIdDegreeWork),vIdDirector,vIdCodirector,null,vModality,vDate));
        }
        return vDegreeWorks;
    }
    private List<Integer> getDataAtStudentsId(){
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
        String vPhone;
        if(resultScript.getPayload().get(0).get("phone") == null) vPhone = null;
        else vPhone = (String) resultScript.getPayload().get(0).get("phone");
        return new User(vName,vLastName,vPhone);
    }
}
