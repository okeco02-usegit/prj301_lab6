package models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.dto.User;
import utils.DBUtils;

public class UserDAO {

    public User login(String userName, String password) throws Exception {
        String sql = "SELECT UserName,[Password],LastName,IsAdmin "
                + "FROM dbo.Registration WHERE UserName=? AND [Password]=?";
        try (Connection con = DBUtils.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return readUser(rs);
            }
        }
        return null;
    }

    public List<User> searchByLastName(String value) throws Exception {
        List<User> list = new ArrayList<User>();
        String sql = "SELECT UserName,[Password],LastName,IsAdmin "
                + "FROM dbo.Registration WHERE LastName LIKE ? ORDER BY LastName";
        try (Connection con = DBUtils.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setNString(1, "%" + value + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(readUser(rs));
            }
        }
        return list;
    }

    public User find(String userName) throws Exception {
        String sql = "SELECT UserName,[Password],LastName,IsAdmin "
                + "FROM dbo.Registration WHERE UserName=?";
        try (Connection con = DBUtils.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return readUser(rs);
            }
        }
        return null;
    }

    public boolean exists(String userName) throws Exception {
        return find(userName) != null;
    }

    public boolean insert(User user) throws Exception {
        String sql = "INSERT INTO dbo.Registration"
                + "(UserName,[Password],LastName,IsAdmin) VALUES(?,?,?,?)";
        try (Connection con = DBUtils.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setNString(3, user.getLastName());
            ps.setBoolean(4, user.isAdmin());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(User user) throws Exception {
        String sql = "UPDATE dbo.Registration SET [Password]=?,"
                + "LastName=?,IsAdmin=? WHERE UserName=?";
        try (Connection con = DBUtils.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setNString(2, user.getLastName());
            ps.setBoolean(3, user.isAdmin());
            ps.setString(4, user.getUserName());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(String userName) throws Exception {
        String sql = "DELETE FROM dbo.Registration WHERE UserName=?";
        try (Connection con = DBUtils.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userName);
            return ps.executeUpdate() > 0;
        }
    }

    private User readUser(ResultSet rs) throws Exception {
        return new User(
                rs.getString("UserName"),
                rs.getString("Password"),
                rs.getString("LastName"),
                rs.getBoolean("IsAdmin"));
    }
}
