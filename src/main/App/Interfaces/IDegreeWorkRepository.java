package Interfaces;

import Enums.EnumState;
import Models.DegreeWork;
import Models.FormatA;
import Models.User;

import java.util.List;

public interface IDegreeWorkRepository {
    List<DegreeWork> getDegreeWorksByDirectorId(int pIdDirector);

    List<FormatA> getFormatsAByDirectorId(int pIdDirector);

    List<Integer> getIdsStudentsByDegreeWorkId(int pIdDegreeWork);

    FormatA getFormatAByDegreeWorkId(int pIdDegreeWork);

    List<DegreeWork> getAllDegreeWorks();

    List<FormatA> getAllFormatsA();

    List<FormatA> getPendingFormatsA();

    User getUserByAccountId(int pIdAccount);

    String getEmailByAccountId(int pIdAccount);

    boolean evaluateFormatAByDegreeWorkId(int pIdDegreeWork, String pObservation, EnumState pNewState);

    int getIdDegreeWorkByAccountId(int pIdAccount);

    int getFormatAAttempts(int pIdFormatA);
}
