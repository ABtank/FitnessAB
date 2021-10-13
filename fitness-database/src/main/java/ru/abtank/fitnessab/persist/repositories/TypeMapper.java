package ru.abtank.fitnessab.persist.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.abtank.fitnessab.persist.entities.Type;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TypeMapper {
    private JdbcTemplate jdbcTemplate;
    private static final String QUERY_FIND_BY_ID = "SELECT type_id, name, descr FROM types WHERE type_id=?";
    private static final String QUERY_INSERT = "INSERT INTO types(name, descr) values (?, ?)";
    private static final String QUERY_UPDATE = "UPDATE types SET name=?, descr=? WHERE type_id=?";
    private static final String QUERY_DELETE = "DELETE FROM types WHERE type_id=?";

    @Autowired
    public TypeMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Type findById(Integer id) {
        Type type = Registry.getInstance().getIdentityMap().find(id);
        if (type == null) {
            type = jdbcTemplate.query(
                    QUERY_FIND_BY_ID,
                    (r, i) -> Type.builder()
                            .id(r.getInt(1))
                            .name(r.getString(2))
                            .descr(r.getString(3))
                            .build(), id).stream().findAny().orElse(null);
            Registry.getInstance().getIdentityMap().add(type);
        }
        return type;
    }

    public int [] insert(List<Type> types) {
        int[] typeIds = jdbcTemplate.batchUpdate(
                QUERY_INSERT,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        Type type = types.get(i);
                        preparedStatement.setString(2, type.getName());
                        preparedStatement.setString(3, type.getDescr());
                    }

                    @Override
                    public int getBatchSize() {
                        return types.size();
                    }
                }
        );
        types.stream().forEach(type -> Registry.getInstance().getIdentityMap().remove(type));
        return typeIds;
    }

    public void update(Type type) {
        jdbcTemplate.update(
                QUERY_UPDATE,
                type.getName(), type.getDescr());
        Registry.getInstance().getIdentityMap().add(type);
    }

    public void delete(Type type) {
        jdbcTemplate.update(QUERY_DELETE, type.getId());
        Registry.getInstance().getIdentityMap().remove(type);
    }
}
