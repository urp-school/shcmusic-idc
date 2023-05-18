package net.openurp.shcmusic.dao;

import net.openurp.shcmusic.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDao extends StudentDaoCustom, JpaRepository<Student, Long> {
  Student findByCode(String paramString);
}
