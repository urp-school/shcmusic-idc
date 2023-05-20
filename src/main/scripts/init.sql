CREATE OR REPLACE VIEW idc.students
 AS
 SELECT std.id,
    std.code AS code,
    std.name AS name,
	grade.code as grade_code,
	eduType.name as edu_type_name,
	level.name as level_name,
	depart.name as depart_name,
	major.name as major_name,
	direction.name as direction_name,
	std.duration as duration,
	stdType.name as std_type_name,
	studyType.name as study_type_name,
	status.name as status_name,
	advisor.name as advisor_name,
    gender.name AS gender_name,
	nation.name as nation_name,
	idType.name as id_type_name,
	person.code as idcard
   FROM base.students std
        inner join base.people as person on std.person_id=person.id
		inner join code.genders as gender on std.gender_id=gender.id
		inner join base.student_states as ss on std.state_id=ss.id
		inner join base.departments as depart on ss.department_id=depart.id
		inner join base.majors as major on ss.major_id=major.id
		inner join base.grades as grade on ss.grade_id=grade.id
		inner join code.student_statuses as status on ss.status_id=status.id
		inner join base.c_education_types eduType on eduType.id=std.edu_type_id
		inner join code.study_types studyType on studyType.id=std.study_type_id
		inner join base.c_std_types as stdType on stdType.id=std.std_type_id
		inner join code.education_levels level on level.id=std.level_id
		inner join code.id_types idType on idType.id=person.id_type_id
		left outer join base.directions direction on ss.direction_id=direction_id
		left outer join code.nations nation on nation.id=
		left outer join base.staffs advisor on std.advisor_id=advisor.id;

