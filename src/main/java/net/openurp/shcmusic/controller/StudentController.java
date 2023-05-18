package net.openurp.shcmusic.controller;

import net.openurp.shcmusic.dao.StudentDao;
import net.openurp.shcmusic.model.Count;
import net.openurp.shcmusic.model.Student;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StudentController {
  private final StudentDao studentDao;

  public StudentController(StudentDao studentDao) {
    this.studentDao = studentDao;
  }

  @QueryMapping(name = "student_count")
  public Count count() {
    return new Count((int) this.studentDao.count());
  }

  @QueryMapping(name = "student_find_by_code")
  public Student findByCode(@Argument String code) {
    return this.studentDao.findByCode(code);
  }

  @QueryMapping(name = "student_limit")
  public List<Student> limit(@Argument int pageIndex, @Argument int pageSize) {
    return this.studentDao.search(pageIndex, pageSize);
  }
}
