package Main;

import Dtos.UserRegisterDTO;
import Enums.EnumProgram;
import Enums.EnumRole;
import Models.Account;
import Models.User;
import Repositories.UserRegisterRepository;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // 1️⃣ Crear User
        User user = new User("Juan", "Varona", "123456789");

        // 2️⃣ Crear Account asociado
        Account account = new Account(
                0, // Id se setea después del insert
                EnumProgram.ING_SISTEMAS, // tu enum
                "juan@example.com",
                "1234", // contraseña la maneja el DTO
                user,
                List.of(EnumRole.UNDERGRADUATE_STUDENT) // enum de roles
        );

        // 3️⃣ Crear DTO con datos completos
        UserRegisterDTO dto = new UserRegisterDTO(account, user, "1234");

        // 4️⃣ Crear repositorio
        UserRegisterRepository repo = new UserRegisterRepository();

        // 5️⃣ Probar inserción
        repo.add(dto);

        // 6️⃣ Resultado
        if (repo.getOperationResult().isSuccess()) {
            System.out.println("✅ Test OK: Usuario insertado con ID " + dto.getAccount().getIdAccount());
        } else {
            System.out.println("❌ Test Fail: " + repo.getOperationResult().getMessage());
        }
    }
}
