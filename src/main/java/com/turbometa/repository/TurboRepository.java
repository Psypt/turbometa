package com.turbometa.repository;

import com.turbometa.model.Turbo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TurboRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Turbo> ROW_MAPPER = (rs, i) -> {
        Turbo turbo = new Turbo();
        turbo.setRef(rs.getString("ref"));
        turbo.setBrand(rs.getString("brand"));
        turbo.setModel(rs.getString("model"));
        turbo.setGeo(rs.getString("geo"));
        return turbo;
    };

    public TurboRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Turbo findByRef(String ref) {
        return jdbcTemplate.queryForObject("SELECT * FROM turbo WHERE ref = ?", ROW_MAPPER, ref);
    }

    public List<Turbo> findAll() {
        return jdbcTemplate.query("SELECT * FROM turbo ORDER BY brand, model", ROW_MAPPER);
    }

    public List<Turbo> search(String query) {
        String param = "%" + query + "%";
        return jdbcTemplate.query(
                "SELECT * FROM turbo WHERE ref LIKE ? OR brand LIKE ? OR model LIKE ? ORDER BY brand, model",
                ROW_MAPPER, param, param, param);
    }

    public void insert(Turbo turbo) {
        jdbcTemplate.update(
                "INSERT INTO turbo (ref, brand, model, geo) VALUES (?, ?, ?, ?)",
                turbo.getRef(), turbo.getBrand(), turbo.getModel(), turbo.getGeo());
    }

    public void update(Turbo turbo) {
        jdbcTemplate.update(
                "UPDATE turbo SET brand = ?, model = ?, geo = ? WHERE ref = ?",
                turbo.getBrand(), turbo.getModel(), turbo.getGeo(), turbo.getRef());
    }

    public void delete(String ref) {
        jdbcTemplate.update("DELETE FROM turbo WHERE ref = ?", ref);
    }

    public boolean exists(String ref) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM turbo WHERE ref = ?", Integer.class, ref);
        return count > 0;
    }
}
