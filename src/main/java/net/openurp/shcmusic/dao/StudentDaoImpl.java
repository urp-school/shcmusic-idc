package net.openurp.shcmusic.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.openurp.shcmusic.model.Student;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StudentDaoImpl implements StudentDaoCustom {
  @Autowired
  @PersistenceContext
  private EntityManager entityManager;

  public List<Student> search(int pageIndex, int pageSize) {
    return this.entityManager
      .createQuery("from " + Student.class.getName() + " order by code")
      .setFirstResult((pageIndex - 1) * pageSize)
      .setMaxResults(pageSize)
      .getResultList();
  }
}
