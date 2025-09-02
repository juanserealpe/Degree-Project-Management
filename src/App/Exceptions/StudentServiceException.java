package App.Exceptions;

public class StudentServiceException extends AppException {

    public StudentServiceException(String message) {
        super(message);
    }

    public static StudentServiceException noFoundStudents() {
        return new StudentServiceException(
                "No se han registrado datos de estudiantes aún."
        );
    }

    public static StudentServiceException noFoundStudent() {
        return new StudentServiceException(
                "El estudiante buscado no existe."
        );
    }
}
