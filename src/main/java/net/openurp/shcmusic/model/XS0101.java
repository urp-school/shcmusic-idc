package net.openurp.shcmusic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "XS0101", schema = "idc")
public class XS0101 {
  @Id
  @GeneratedValue
  private Long id;

  private String xh;

  private String xm;

  private String xbm;

  private String csrq;

  private String sfzjh;

  public void setId(Long id) {
    this.id = id;
  }

  public void setXh(String xh) {
    this.xh = xh;
  }

  public void setXm(String xm) {
    this.xm = xm;
  }

  public void setXbm(String xbm) {
    this.xbm = xbm;
  }

  public void setCsrq(String csrq) {
    this.csrq = csrq;
  }

  public void setSfzjh(String sfzjh) {
    this.sfzjh = sfzjh;
  }

  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof XS0101))
      return false;
    XS0101 other = (XS0101) o;
    if (!other.canEqual(this))
      return false;
    return getId().equals(other.getId());
  }

  protected boolean canEqual(Object other) {
    return other instanceof XS0101;
  }


  public String toString() {
    return "XS0101(id=" + getId() + ", xh=" + getXh() + ", xm=" + getXm() + ", xbm=" + getXbm() + ", csrq=" + getCsrq() + ", sfzjh=" + getSfzjh() + ")";
  }

  public XS0101() {
  }

  public XS0101(Long id, String xh, String xm, String xbm, String csrq, String sfzjh) {
    this.id = id;
    this.xh = xh;
    this.xm = xm;
    this.xbm = xbm;
    this.csrq = csrq;
    this.sfzjh = sfzjh;
  }

  public Long getId() {
    return this.id;
  }

  public String getXh() {
    return this.xh;
  }

  public String getXm() {
    return this.xm;
  }

  public String getXbm() {
    return this.xbm;
  }

  public String getCsrq() {
    return this.csrq;
  }

  public String getSfzjh() {
    return this.sfzjh;
  }
}
