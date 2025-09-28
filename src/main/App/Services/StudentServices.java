package Services;

import Interfaces.IDegreeWorkRepository;
import Models.DegreeWork;
import Models.FormatA;
import Utilities.Logger;

public class StudentServices {
    private final IDegreeWorkRepository repository;

    public StudentServices(IDegreeWorkRepository repository) {
        this.repository = repository;
    }
    /**
     * Retorna el DegreeWork asociado a la cuenta del estudiante.
     *
     * @param studentAccountId ID de la cuenta del estudiante
     * @return DegreeWork o null si no existe
     */
    public DegreeWork getDegreeWorkByStudent(int studentAccountId) {
        DegreeWork resultDegreeWork = repository.getDegreeWorkByIdStudent(studentAccountId);
        if(resultDegreeWork == null) {
            Logger.warn(StudentServices.class, "No se encontró DegreeWork para el estudiante con ID: " + studentAccountId);
            return null;
        }

        else {
            Logger.info(StudentServices.class, "Retornando DegreeWork para el estudiante con ID: " + studentAccountId + " ID degreework: " + resultDegreeWork.getIdDegreeWork());
            return resultDegreeWork;
        }
    }

    /**
     * Retorna el FormatA asociado al DegreeWork del estudiante.
     *
     * @param studentAccountId ID de la cuenta del estudiante
     * @return FormatA o null si no existe
     */
    public FormatA getFormatAByStudent(int studentAccountId) {
        int degreeWorkId = repository.getIdDegreeWorkByAccountId(studentAccountId);
        if (degreeWorkId == 0) return null;
        return repository.getFormatAByDegreeWorkId(degreeWorkId);
    }
}
