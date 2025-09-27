package Services;

import DataBase.DbConnection;
import Dtos.UserRegisterDTO;
import Interfaces.IFileService;
import Interfaces.IRepository;
import Models.Cookie;
import Repositories.CookieRepository;
import Repositories.CredentialRepository;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CookieService {
    private final String path = System.getProperty("user.dir") + "/Secrets/ProjectManager/";
    private final String filename = "cookie.txt";
    private IFileService  fileService;
    private IRepository<Cookie> cookieRepository;
    private CredentialRepository userRepository;
    public CookieService() {
        this.fileService = new FileService();
        cookieRepository = new CookieRepository();
        try{
            userRepository = new CredentialRepository(DbConnection.getConnection());
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public void setCookie(int accountId) {
        String uuid = UUID.randomUUID().toString();
        Instant expiration = Instant.now().plus(7, ChronoUnit.DAYS);

        Cookie cookie = new Cookie(uuid, expiration, accountId);

        try {
            cookieRepository.add(cookie);
            fileService.saveFile(path, filename, uuid);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public UserRegisterDTO getUserRegisterDTOByCookie() {
        try {
            String content = fileService.readFile(path, filename);
            Cookie cookie = cookieRepository.getByString(content);
            if(cookie == null) return null;
            //Si existe la cookie, pero ya expiro, retornar null
            if(cookie.getDuration().isBefore(Instant.now())) return null;
            return userRepository.getById(cookie.getUserId());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void ResetCookie() {
        try {
            fileService.saveFile(path, filename, "");
        } catch (IOException e) {
            System.out.println("Error to reset cookie " + e);
        }
    }
}