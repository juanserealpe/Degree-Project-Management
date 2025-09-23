package Exceptions;

/**
 * Excepción específica para errores en los servicios relacionados con estudiantes.
 *
 * Esta clase se utiliza para manejar situaciones donde no se encuentran
 * estudiantes registrados o cuando se busca un estudiante que no existe,
 * proporcionando métodos estáticos con mensajes claros para cada caso.
 *
 * @author juanserealpe
 */


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
