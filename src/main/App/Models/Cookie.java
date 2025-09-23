package Models;
import java.time.Instant;

public class Cookie {
    public int Id;
    public String CookieUUID;
    public Instant Duration; // UTC timestamp
    public int UserId;
    public Cookie(){

    }
    public Cookie(String cookieUUID, Instant duration, int userId) {
        CookieUUID = cookieUUID;
        Duration = duration;
        UserId = userId;
    }
    public int getuserId() {
        return UserId;
    }
    public String getCookieUUID() {
        return CookieUUID;
    }
    public Instant getDuration() {
        return Duration;
    }
    public int getUserId() {
        return UserId;
    }
    public void setUserId(int userId) {
        UserId = userId;
    }
    public void setCookieUUID(String cookieUUID) {
        CookieUUID = cookieUUID;
    }
    public void setDuration(Instant duration) {
        Duration = duration;
    }

}