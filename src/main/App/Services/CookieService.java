package Services;

import Interfaces.IFileService;
import Interfaces.IRepository;
import Models.Account;
import Models.Cookie;
import Repositories.CookieRepository;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CookieService {
    private final String path = System.getProperty("user.home") + File.separator + "ProjectManager" + File.separator;
    private final String filename = "cookie.txt";
    private IFileService  fileService;
    private IRepository<Cookie> cookieRepository;
    //TODO: private UserRepository userRepository;
    public CookieService() {
        this.fileService = new FileService();
        cookieRepository = new CookieRepository();
        try{
            //TODO: userRepository = new CredentialRepository();
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public void setCookie(Account account) {
        String uuid = UUID.randomUUID().toString();
        Instant expiration = Instant.now().plus(7, ChronoUnit.DAYS);

        Cookie cookie = new Cookie(uuid, expiration, account.getIdAccount());

        try {
            cookieRepository.add(cookie);
            fileService.saveFile(path, filename, uuid);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Account getAccountByCookie() {
        try {
            String content = fileService.readFile(path, filename);
            Cookie cookie = cookieRepository.getByString(content);
            //TODO: return userRepository.getById(cookie.getuserId());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}