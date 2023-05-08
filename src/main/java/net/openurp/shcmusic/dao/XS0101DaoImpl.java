package net.openurp.shcmusic.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.openurp.shcmusic.model.XS0101;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class XS0101DaoImpl implements XS0101DaoCustom {
  @Autowired
  @PersistenceContext
  private EntityManager entityManager;

  public List<XS0101> search(int pageIndex, int pageSize) {
    return this.entityManager
        .createQuery("from " + XS0101.class.getName() + " order by xh")
        .setFirstResult((pageIndex - 1) * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
  }
}
