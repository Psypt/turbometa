package com.turbometa.repository;

import com.turbometa.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<User> ROW_MAPPER = (rs, i) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setActive(rs.getBoolean("active"));
        user.setAdmin(rs.getBoolean("is_admin"));
        return user;
    };

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM app_user WHERE id = ?", ROW_MAPPER, id);
    }

    public User findByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM app_user WHERE email = ?", ROW_MAPPER, email);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM app_user ORDER BY username", ROW_MAPPER);
    }

    public List<User> search(String query) {
        String param = "%" + query + "%";
        return jdbcTemplate.query(
                "SELECT * FROM app_user WHERE username LIKE ? OR email LIKE ? ORDER BY username",
                ROW_MAPPER, param, param);
    }

    public void insert(User user) {
        jdbcTemplate.update(
                "INSERT INTO app_user (username, email, password_hash, active, is_admin) VALUES (?, ?, ?, ?, ?)",
                user.getUsername(), user.getEmail(), user.getPasswordHash(),
                user.isActive() ? 1 : 0, user.isAdmin() ? 1 : 0);
    }

    public void update(User user) {
        jdbcTemplate.update(
                "UPDATE app_user SET username = ?, email = ?, password_hash = ?, active = ?, is_admin = ? WHERE id = ?",
                user.getUsername(), user.getEmail(), user.getPasswordHash(),
                user.isActive() ? 1 : 0, user.isAdmin() ? 1 : 0, user.getId());
    }

    public void updateWithoutPassword(User user) {
        jdbcTemplate.update(
                "UPDATE app_user SET username = ?, email = ?, active = ?, is_admin = ? WHERE id = ?",
                user.getUsername(), user.getEmail(),
                user.isActive() ? 1 : 0, user.isAdmin() ? 1 : 0, user.getId());
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM app_user WHERE id = ?", id);
    }

    public int countActiveAdmins() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM app_user WHERE active = 1 AND is_admin = 1", Integer.class);
    }

    public boolean emailExists(String email, Long excludeId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM app_user WHERE email = ? AND id != ?", Integer.class, email, excludeId);
        return count > 0;
    }

    public boolean emailExists(String email) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM app_user WHERE email = ?", Integer.class, email);
        return count > 0;
    }
}
