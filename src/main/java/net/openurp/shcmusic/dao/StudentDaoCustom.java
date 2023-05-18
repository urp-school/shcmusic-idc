package net.openurp.shcmusic.dao;

import java.util.List;
import net.openurp.shcmusic.model.Student;

public interface StudentDaoCustom {
  List<Student> search(int pageIndex, int pageSize);
}
