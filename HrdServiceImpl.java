package in.ecgc.smile.erp.reporting.jasper.be.service.hrd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.reporting.jasper.be.model.hrd.HrdLookupOption;
import in.ecgc.smile.erp.reporting.jasper.be.model.hrd.HrdR05;
import in.ecgc.smile.erp.reporting.jasper.be.repository.hrd.HrdDao;

@Service
public class HrdServiceImpl implements HrdService {

	@Autowired
	private HrdDao hrdDao;

	private static final Logger logger = LoggerFactory.getLogger(HrdServiceImpl.class);

	@Override
	public List<HrdR05> getHrdR05Report(String empNo, String desigCd, String officeId, String fromDate,
			String toDate) {
		logger.info("Inside HrdServiceImpl#getHrdR05Report");

		validateDate("fromDate", fromDate);
		validateDate("toDate", toDate);

		List<HrdR05> employees = hrdDao.getEmployeeMasterDetails(empNo, desigCd, officeId);

		for (HrdR05 employee : employees) {
			employee.setQualifications(hrdDao.getQualificationsByEmpNo(employee.getEmpNo()));
			employee.setLocationsServed(hrdDao.getLocationsServedByEmpNo(employee.getEmpNo(), fromDate, toDate));
		}

		return employees;
	}

	@Override
	public List<HrdLookupOption> getAllEmployeeOptions() {
		return hrdDao.getAllEmployeeOptions();
	}

	@Override
	public List<HrdLookupOption> getAllDesignationOptions() {
		return hrdDao.getAllDesignationOptions();
	}

	@Override
	public List<HrdLookupOption> getAllOfficeOptions() {
		return hrdDao.getAllOfficeOptions();
	}

	/* fromDate/toDate must be either the literal "ALL" sentinel (skip the filter) or a
	 * real date in yyyy-MM-dd format. Fails fast with a clear message instead of letting
	 * a malformed date crash the DAO layer with an unhandled IllegalArgumentException. */
	private void validateDate(String fieldName, String value) {
		if (value == null || "ALL".equalsIgnoreCase(value)) {
			return;
		}
		try {
			java.sql.Date.valueOf(value);
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException(
					fieldName + " must be \"ALL\" or a valid date in yyyy-MM-dd format, got: " + value);
		}
	}

}
