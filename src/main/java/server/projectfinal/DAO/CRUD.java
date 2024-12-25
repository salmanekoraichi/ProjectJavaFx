package server.projectfinal.DAO;

import java.sql.ResultSet;
import java.util.List;

/**
 * This code is written by Salmane Koraichi
 **/
public interface CRUD<T,PK> {
    T findById(PK id);
    List<T> findAll();
    void save(T entity);
    void update(T entity);
    void delete(PK id);
    ResultSet load();
}
