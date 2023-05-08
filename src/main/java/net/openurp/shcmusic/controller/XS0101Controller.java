package net.openurp.shcmusic.controller;

import java.util.List;
import net.openurp.shcmusic.dao.XS0101Dao;
import net.openurp.shcmusic.model.XS0101;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class XS0101Controller {
  private final XS0101Dao xs0101Dao;
  
  public XS0101Controller(XS0101Dao xs0101Dao) {
    this.xs0101Dao = xs0101Dao;
  }
  
  @QueryMapping(name = "allXS0101")
  public List<XS0101> all() {
    return this.xs0101Dao.findAll();
  }
  
  @QueryMapping(name = "xs0101Count")
  public int count() {
    return (int)this.xs0101Dao.count();
  }
  
  @QueryMapping(name = "xs0101ByXh")
  public XS0101 findByXh(@Argument String xh) {
    return this.xs0101Dao.findByXh(xh);
  }
  
  @QueryMapping(name = "xs0101")
  public List<XS0101> xs0101(@Argument int pageIndex, @Argument int pageSize) {
    return this.xs0101Dao.search(pageIndex, pageSize);
  }
}
