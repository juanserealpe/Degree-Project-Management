package Services;

import Enums.EnumState;
import Interfaces.IDegreeWorkRepository;
import Models.DegreeWork;
import Models.FormatA;

import java.util.List;

public class CoordinatorService {

    private final IDegreeWorkRepository repository;

    public CoordinatorService(IDegreeWorkRepository repository) {
        this.repository = repository;
    }

    public boolean evaluateFormatAByDegreeWorkId(int pIdDegreeWork, String pObservation, EnumState pNewState)
    {return repository.evaluateFormatAByDegreeWorkId(pIdDegreeWork,pObservation,pNewState);}

    public boolean evaluateFormatAByFormatAId(int pIdFormatA, String pObservation, EnumState pNewState){
        int vAttempts = repository.getFormatAAttempts(pIdFormatA);
        if(vAttempts > 3 || vAttempts < 0) return false;
        return repository.evaluateFormatAByDegreeWorkId(pIdFormatA,pObservation,pNewState);
    }

    public List<DegreeWork> getDegreeWorks(){return repository.getAllDegreeWorks();}

    public List<FormatA> getAllFormatsA(){return repository.getAllFormatsA();}

    public List<FormatA> getPendingFormatsA(){return repository.getPendingFormatsA();}
}
