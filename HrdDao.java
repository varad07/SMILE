package in.ecgc.smile.erp.reporting.jasper.be.repository.hrd;

import java.util.List;

import in.ecgc.smile.erp.reporting.jasper.be.model.hrd.HrdLookupOption;
import in.ecgc.smile.erp.reporting.jasper.be.model.hrd.HrdR05;
import in.ecgc.smile.erp.reporting.jasper.be.model.hrd.HrdR05Location;
import in.ecgc.smile.erp.reporting.jasper.be.model.hrd.HrdR05Qualification;

public interface HrdDao {

	/* Fetch employee master details for HRD_R05, filtered by empNo/desigCd/officeId.
	 * Pass "ALL" for any filter that should not be applied - "ALL" is one of the
	 * selectable values in each filter's own dropdown, not a separate toggle. */
	List<HrdR05> getEmployeeMasterDetails(String empNo, String desigCd, String officeId);

	/* Fetch the list of active (status = 'A') qualifications for a single employee */
	List<HrdR05Qualification> getQualificationsByEmpNo(Integer empNo);

	/* Fetch the transfer/posting history (Locations Served) for a single employee from
	 * the PAN (Personal Action Notice) transaction table, filtered to transfer actions
	 * only. Pass "ALL" for fromDate/toDate to skip the period-overlap filter. */
	List<HrdR05Location> getLocationsServedByEmpNo(Integer empNo, String fromDate, String toDate);

	/* Dropdown lookups for the HRD_R05 filter screen */
	List<HrdLookupOption> getAllEmployeeOptions();

	List<HrdLookupOption> getAllDesignationOptions();

	/* No readable office-name master is confirmed accessible from this reporting
	 * database yet (the real office master lives in a separate microservice's own
	 * database, not proven to be replicated here) - so this returns the distinct
	 * office codes actually in use, with the code itself as the label. */
	List<HrdLookupOption> getAllOfficeOptions();

}
