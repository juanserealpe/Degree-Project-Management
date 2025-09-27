package Services;

import Enums.EnumState;
import Models.DegreeWork;
import Models.FormatA;
import Repositories.DegreeWorkRepository;

import java.util.List;

public class CoordinatorService {
    private DegreeWorkRepository repository;
    private String script;
    private Object[] params;

    public boolean evaluateFormatAByDegreeWorkId(int pIdDegreeWork, String pObservation, EnumState pNewState){
        repository.evaluateFormatAByDegreeWorkId(pIdDegreeWork,pObservation,pNewState);
        return false;
    }
    public boolean evaluateFormatAByFormatAId(int pIdDegreeWork, String pObservation, EnumState pNewState){
        int vAttempts = repository.getFormatAAttempts(pIdDegreeWork);
        if(vAttempts > 3 || vAttempts < 0) return false;
        return repository.evaluateFormatAByDegreeWorkId(pIdDegreeWork,pObservation,pNewState);
    }
    public List<DegreeWork> getDegreeWorks(){
        return repository.getAllDegreeWorks();
    }
    public List<FormatA> getAllFormatsA(){
        return repository.getAllFormatsA();
    }
    public List<FormatA> getPendingFormatsA(){
        return repository.getPendingFormatsA();
    }

}
