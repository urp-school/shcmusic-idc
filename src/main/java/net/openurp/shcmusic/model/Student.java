package net.openurp.shcmusic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students", schema = "idc")
public class Student {
  @Id
  @GeneratedValue
  private Long id;

  private String code;

  private String name;

  private String gradeCode;

  private String eduTypeName;

  private String levelName;

  private String departName;

  private String majorName;

  private String directionName;

  private float duration;

  private String stdTypeName;

  private String studyTypeName;

  private String statusName;

  private String advisorName;

  private String genderName;

  private String nationName;

  private String idTypeName;

  private String idcard;

  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Student))
      return false;
    Student other = (Student) o;
    if (!other.canEqual(this))
      return false;
    return getId().equals(other.getId());
  }

  protected boolean canEqual(Object other) {
    return other instanceof Student;
  }

  public String toString() {
    return "Student(id=" + getId() + ", code=" + getCode() + ", name=" + getName() + ")";
  }

  public Student() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGradeCode() {
    return gradeCode;
  }

  public void setGradeCode(String gradeCode) {
    this.gradeCode = gradeCode;
  }

  public String getEduTypeName() {
    return eduTypeName;
  }

  public void setEduTypeName(String eduTypeName) {
    this.eduTypeName = eduTypeName;
  }

  public String getLevelName() {
    return levelName;
  }

  public void setLevelName(String levelName) {
    this.levelName = levelName;
  }

  public String getDepartName() {
    return departName;
  }

  public void setDepartName(String departName) {
    this.departName = departName;
  }

  public String getMajorName() {
    return majorName;
  }

  public void setMajorName(String majorName) {
    this.majorName = majorName;
  }

  public String getDirectionName() {
    return directionName;
  }

  public void setDirectionName(String directionName) {
    this.directionName = directionName;
  }

  public float getDuration() {
    return duration;
  }

  public void setDuration(float duration) {
    this.duration = duration;
  }

  public String getStdTypeName() {
    return stdTypeName;
  }

  public void setStdTypeName(String stdTypeName) {
    this.stdTypeName = stdTypeName;
  }

  public String getStudyTypeName() {
    return studyTypeName;
  }

  public void setStudyTypeName(String studyTypeName) {
    this.studyTypeName = studyTypeName;
  }

  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }

  public String getAdvisorName() {
    return advisorName;
  }

  public void setAdvisorName(String advisorName) {
    this.advisorName = advisorName;
  }

  public String getGenderName() {
    return genderName;
  }

  public void setGenderName(String genderName) {
    this.genderName = genderName;
  }

  public String getNationName() {
    return nationName;
  }

  public void setNationName(String nationName) {
    this.nationName = nationName;
  }

  public String getIdTypeName() {
    return idTypeName;
  }

  public void setIdTypeName(String idTypeName) {
    this.idTypeName = idTypeName;
  }

  public String getIdcard() {
    return idcard;
  }

  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }
}
