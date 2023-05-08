package net.openurp.shcmusic.dao;

import net.openurp.shcmusic.model.XS0101;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XS0101Dao extends XS0101DaoCustom, JpaRepository<XS0101, Long> {
  XS0101 findByXh(String paramString);
}
