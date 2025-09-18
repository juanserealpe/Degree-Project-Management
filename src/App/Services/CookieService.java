package App.Services;

import App.Interfaces.IFileService;
import App.Interfaces.IRepository;
import App.Models.Cookie;
import App.Models.User;
import App.Repositories.CookieRepository;
import App.Repositories.UserRepository;

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
    private UserRepository userRepository;
    public CookieService() {
        this.fileService = new FileService();
        cookieRepository = new CookieRepository();
        userRepository = new UserRepository();
    }
    public void setCookie(User user) {
        String uuid = UUID.randomUUID().toString();
        Instant expiration = Instant.now().plus(7, ChronoUnit.DAYS);

        Cookie cookie = new Cookie(uuid, expiration, user.getId());

        try {
            cookieRepository.toAdd(cookie);
            fileService.saveFile(path, filename, uuid);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByCookie() {
        try {
            String content = fileService.readFile(path, filename);
            Cookie cookie = cookieRepository.toGetByString(content);
            return userRepository.getById(cookie.getuserId());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}
