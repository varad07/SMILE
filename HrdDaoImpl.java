package in.ecgc.smile.erp.reporting.jasper.be.repository.hrd;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import in.ecgc.smile.erp.reporting.jasper.be.model.hrd.HrdLookupOption;
import in.ecgc.smile.erp.reporting.jasper.be.model.hrd.HrdR05;
import in.ecgc.smile.erp.reporting.jasper.be.model.hrd.HrdR05Location;
import in.ecgc.smile.erp.reporting.jasper.be.model.hrd.HrdR05Qualification;

@Repository
public class HrdDaoImpl implements HrdDao {

	private JdbcOperations jdbcOperations;
	private NamedParameterJdbcOperations namedParameterJdbcOperations;

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(HrdDaoImpl.class);

	public HrdDaoImpl(DataSource dataSource) {
		super();
		this.jdbcOperations = new JdbcTemplate(dataSource);
		this.namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
	}

	private static final RowMapper<HrdR05> EMPLOYEE_ROW_MAPPER = (rs, rowNum) -> {
		HrdR05 hrd = new HrdR05();
		hrd.setEmpNo(rs.getInt("emp_no"));

		StringBuilder empName = new StringBuilder();
		String prefix = rs.getString("prefix");
		String firstName = rs.getString("emp_firstname");
		String midName = rs.getString("emp_midname");
		String lastName = rs.getString("emp_lastname");
		if (prefix != null && !prefix.trim().isEmpty()) {
			empName.append(prefix.trim()).append(" ");
		}
		if (firstName != null && !firstName.trim().isEmpty()) {
			empName.append(firstName.trim()).append(" ");
		}
		if (midName != null && !midName.trim().isEmpty()) {
			empName.append(midName.trim()).append(" ");
		}
		if (lastName != null && !lastName.trim().isEmpty()) {
			empName.append(lastName.trim());
		}
		hrd.setEmpName(empName.toString().trim());

		hrd.setDoj(rs.getDate("join_dt"));
		hrd.setOfficeId(rs.getString("office_id"));
		hrd.setCadreRank(rs.getString("rank_desc"));
		hrd.setDesignation(rs.getString("desig_desc"));
		hrd.setCategory(rs.getString("category_desc"));
		hrd.setDob(rs.getDate("emp_dob"));
		hrd.setRetirementDate(rs.getDate("expected_retirement_date"));
		hrd.setLastPromotionDate(rs.getDate("last_prm_dt"));

		int serviceYears = rs.getInt("service_years");
		int serviceMonths = rs.getInt("service_months");
		int serviceDays = rs.getInt("service_days");
		hrd.setTotalCompletedService(serviceYears + " years " + serviceMonths + " months " + serviceDays + " days");

		return hrd;
	};

	private static final RowMapper<HrdR05Qualification> QUALIFICATION_ROW_MAPPER = (rs, rowNum) -> {
		HrdR05Qualification qualification = new HrdR05Qualification();
		qualification.setDegreeCd(rs.getString("degree_cd"));
		qualification.setDegreeName(rs.getString("degree_name"));
		qualification.setBoardUniv(rs.getString("board_univ"));

		Object yearOfPassing = rs.getObject("year_of_passing");
		qualification.setYearOfPassing(yearOfPassing != null ? rs.getInt("year_of_passing") : null);

		Object percentage = rs.getObject("percentage");
		qualification.setPercentage(percentage != null ? rs.getFloat("percentage") : null);

		qualification.setSpecialization(rs.getString("specialization"));
		qualification.setEducationMedium(rs.getString("education_medium"));
		return qualification;
	};

	@Override
	public List<HrdR05> getEmployeeMasterDetails(String empNo, String desigCd, String officeId) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT emp_mst.emp_no, emp_mst.prefix, emp_mst.emp_firstname, emp_mst.emp_midname, emp_mst.emp_lastname, ")
			.append("emp_mst.office_id, emp_mst.join_dt, emp_mst.emp_dob, emp_mst.expected_retirement_date, emp_mst.last_prm_dt, ")
			.append("desig_mst.description AS desig_desc, rank_mst.description AS rank_desc, emp_category_mst.category_desc AS category_desc, ")
			.append("DATE_PART('year', AGE(CURRENT_DATE, emp_mst.join_dt)) AS service_years, ")
			.append("DATE_PART('month', AGE(CURRENT_DATE, emp_mst.join_dt)) AS service_months, ")
			.append("DATE_PART('day', AGE(CURRENT_DATE, emp_mst.join_dt)) AS service_days ")
			.append("FROM emp_mgmt.ecgc_hrd_emp_mst emp_mst ")
			.append("LEFT JOIN emp_mgmt.ecgc_hrd_desig_mst desig_mst ON emp_mst.desig_cd = desig_mst.desig_id ")
			.append("LEFT JOIN emp_mgmt.ecgc_hrd_rank_mst rank_mst ON emp_mst.rank_cd = rank_mst.rank_id ")
			.append("LEFT JOIN emp_mgmt.ecgc_hrd_emp_other_trn othr_trn ON emp_mst.emp_no = othr_trn.emp_no ")
			.append("LEFT JOIN emp_mgmt.ecgc_hrd_category_mst emp_category_mst ON othr_trn.emp_catg_cd = emp_category_mst.category_cd ")
			.append("WHERE 1=1 ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		if (empNo != null && !"ALL".equalsIgnoreCase(empNo)) {
			sql.append("AND emp_mst.emp_no = :empNo ");
			params.addValue("empNo", Integer.parseInt(empNo));
		}
		if (desigCd != null && !"ALL".equalsIgnoreCase(desigCd)) {
			sql.append("AND emp_mst.desig_cd = :desigCd ");
			params.addValue("desigCd", desigCd);
		}
		if (officeId != null && !"ALL".equalsIgnoreCase(officeId)) {
			sql.append("AND emp_mst.office_id = :officeId ");
			params.addValue("officeId", officeId);
		}

		sql.append("ORDER BY emp_mst.emp_no");

		logger.info("HRD_R05 employee master query: {}", sql);

		return namedParameterJdbcOperations.query(sql.toString(), params, EMPLOYEE_ROW_MAPPER);
	}

	@Override
	public List<HrdR05Qualification> getQualificationsByEmpNo(Integer empNo) {

		String sql = "SELECT q.degree_cd, d.description AS degree_name, q.board_univ, q.year_of_passing, "
				+ "q.percentage, q.specialization, q.education_medium "
				+ "FROM emp_mgmt.ecgc_hrd_emp_qualfn_trn q "
				+ "JOIN emp_mgmt.ecgc_hrd_degree_mst d ON d.degree_cd = q.degree_cd "
				+ "WHERE q.emp_no = :empNo AND q.status = 'A'";

		MapSqlParameterSource params = new MapSqlParameterSource("empNo", empNo);

		return namedParameterJdbcOperations.query(sql, params, QUALIFICATION_ROW_MAPPER);
	}

	private static final RowMapper<HrdR05Location> LOCATION_ROW_MAPPER = (rs, rowNum) -> {
		HrdR05Location location = new HrdR05Location();
		location.setPlace(rs.getString("place"));
		location.setFromDate(rs.getDate("pan_eff_frm_dt"));
		location.setToDate(rs.getDate("pan_eff_to_dt"));
		location.setCapacityWorked(rs.getString("capacity_worked"));
		location.setDeptOffice(rs.getString("rev_dept_cd"));
		return location;
	};

	@Override
	public List<HrdR05Location> getLocationsServedByEmpNo(Integer empNo, String fromDate, String toDate) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT p.pan_eff_frm_dt, p.pan_eff_to_dt, p.rev_dept_cd, loc.description AS place, ")
			.append("desig.description AS capacity_worked ")
			.append("FROM emp_mgmt.ecgc_hrd_emp_pan_trn p ")
			.append("LEFT JOIN emp_mgmt.ecgc_logical_loc_mst loc ON p.rev_loc_cd = loc.logicalloc_cd ")
			.append("LEFT JOIN emp_mgmt.ecgc_hrd_desig_mst desig ON p.rev_desig_cd = desig.desig_id ")
			.append("WHERE p.emp_no = :empNo AND p.action_cd = 'TRFN' ");

		MapSqlParameterSource params = new MapSqlParameterSource("empNo", empNo);

		if (fromDate != null && !"ALL".equalsIgnoreCase(fromDate)) {
			sql.append("AND (p.pan_eff_to_dt IS NULL OR p.pan_eff_to_dt >= :fromDate) ");
			params.addValue("fromDate", java.sql.Date.valueOf(fromDate));
		}
		if (toDate != null && !"ALL".equalsIgnoreCase(toDate)) {
			sql.append("AND p.pan_eff_frm_dt <= :toDate ");
			params.addValue("toDate", java.sql.Date.valueOf(toDate));
		}

		sql.append("ORDER BY p.pan_eff_frm_dt");

		return namedParameterJdbcOperations.query(sql.toString(), params, LOCATION_ROW_MAPPER);
	}

	private static final RowMapper<HrdLookupOption> LOOKUP_ROW_MAPPER = (rs, rowNum) ->
		new HrdLookupOption(rs.getString("code"), rs.getString("label"));

	@Override
	public List<HrdLookupOption> getAllEmployeeOptions() {
		String sql = "SELECT CAST(emp_no AS VARCHAR) AS code, "
				+ "TRIM(COALESCE(prefix,'') || ' ' || COALESCE(emp_firstname,'') || ' ' || COALESCE(emp_midname,'') || ' ' || COALESCE(emp_lastname,'')) || ' (' || emp_no || ')' AS label "
				+ "FROM emp_mgmt.ecgc_hrd_emp_mst "
				+ "ORDER BY emp_firstname";
		return namedParameterJdbcOperations.query(sql, new MapSqlParameterSource(), LOOKUP_ROW_MAPPER);
	}

	@Override
	public List<HrdLookupOption> getAllDesignationOptions() {
		String sql = "SELECT desig_id AS code, description AS label "
				+ "FROM emp_mgmt.ecgc_hrd_desig_mst "
				+ "ORDER BY description";
		return namedParameterJdbcOperations.query(sql, new MapSqlParameterSource(), LOOKUP_ROW_MAPPER);
	}

	@Override
	public List<HrdLookupOption> getAllOfficeOptions() {
		String sql = "SELECT DISTINCT office_id AS code, office_id AS label "
				+ "FROM emp_mgmt.ecgc_hrd_emp_mst "
				+ "WHERE office_id IS NOT NULL "
				+ "ORDER BY office_id";
		return namedParameterJdbcOperations.query(sql, new MapSqlParameterSource(), LOOKUP_ROW_MAPPER);
	}

}
