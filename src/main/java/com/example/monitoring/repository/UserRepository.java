package com.example.monitoring.repository;

import com.example.monitoring.domain.Role;
import com.example.monitoring.domain.User;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements IUserRepository {
    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean save(User user) {
        String sql = "INSERT INTO user(email, PASSWORD, NAME) VALUES (?, ?, ?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());

            pstmt.executeQuery();
            return true;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "select * from user where email=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,email);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserNo(Integer.parseInt(rs.getString("user_no")));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setRegistDate(rs.getString("regist_date"));
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<User> findByKey(String key) {
        String sql = "select * from user where user_no=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,key);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserNo(Integer.parseInt(rs.getString("user_no")));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setRegistDate(rs.getString("regist_date"));
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<User> findUsersByName(String name) {
        String sql = "SELECT * FROM user WHERE name LIKE ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"%" + name + "%");
            rs = pstmt.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setUserNo(Integer.parseInt(rs.getString("user_no")));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setRegistDate(rs.getString("regist_date"));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<User> findUsersByNameNRole(String name, Role role) {
        String sql = "select * from user WHERE name LIKE ? AND role = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + name + "%");
            pstmt.setString(2, String.valueOf(role));
            rs = pstmt.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setUserNo(Integer.parseInt(rs.getString("user_no")));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setRegistDate(rs.getString("regist_date"));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from user";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<User> users = new ArrayList<User>();
            while (rs.next()) {
                User user = new User();
                user.setUserNo(Integer.parseInt(rs.getString("user_no")));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setRegistDate(rs.getString("regist_date"));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public boolean findDuplicatedEmail(String email){
        String sql = "SELECT COUNT(*) FROM user WHERE email LIKE ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            rs.next();
            if(rs.getInt(1) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public boolean updateUserByUniqueKey(User user) {
        String sql = "update user set password=?, name=?, up_date=? where user_no=? ";
        Connection conn = null;
        PreparedStatement pstmt = null;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat up_date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,user.getPassword());
            pstmt.setString(2,user.getName());
            pstmt.setString(3,up_date.format(timestamp));
            pstmt.setString(4,Integer.toString(user.getUserNo()));
            pstmt.executeQuery();

            return true;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt);
        }
    }

    public void updateUserRoleByKey(String key, Role role) {
        String sql = "update user set role=?, up_date=? where user_no=? ";
        Connection conn = null;
        PreparedStatement pstmt = null;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat up_date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,String.valueOf(role));
            pstmt.setString(2,up_date.format(timestamp));
            pstmt.setString(3,key);
            pstmt.executeQuery();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt);
        }
    }


    public void deleteUserByKey(String key) {
        String sql = "DELETE FROM user WHERE user_no=? ";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,key);
            pstmt.executeQuery();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                {
                    rs.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn, PreparedStatement pstmt) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
