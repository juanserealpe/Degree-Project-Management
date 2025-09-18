package App.Repositories;

import App.DataBase.DbConnection;
import App.Interfaces.IRepository;
import App.Models.Cookie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CookieRepository implements IRepository<Cookie>{
    private final DbConnection dbConnection;
    public CookieRepository() {
        this.dbConnection = new  DbConnection();
    }

    @Override
    public void toAdd(Cookie prmItem) {
        String sql = "INSERT INTO Cookie (CookieUUID, Duration, UserId) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.toConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, prmItem.CookieUUID);
            pstmt.setTimestamp(2, java.sql.Timestamp.from(prmItem.Duration));
            pstmt.setInt(3, prmItem.UserId);

            pstmt.executeUpdate();
            System.out.println("Cookie agregada exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar cookie: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public List<Cookie> toGetAll() {
        List<Cookie> cookies = new ArrayList<>();
        String sql = "SELECT CookieUUID, Duration, UserId FROM Cookie";

        try (Connection conn = dbConnection.toConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Cookie cookie = new Cookie();
                cookie.CookieUUID = rs.getString("CookieUUID");
                cookie.Duration = rs.getTimestamp("Duration").toInstant();
                cookie.UserId = rs.getInt("UserId");

                cookies.add(cookie);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener cookies: " + e.getMessage());
            e.printStackTrace();
        }

        return cookies;
    }


    @Override
    public Cookie toGetByString(String prmString) {
        String sql = "SELECT UserId FROM Cookie WHERE CookieUUID = ?";

        try (Connection conn = dbConnection.toConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, prmString);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("UserId");
                    return new Cookie(prmString, null, userId);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener UserId desde Cookie: " + e.getMessage());
        }

        return null;
    }



    @Override
    public void toDeleteByString(String prmString) {

    }

    @Override
    public void toUpdate(Cookie prmItem) {

    }
}
