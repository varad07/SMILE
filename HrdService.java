package in.ecgc.smile.erp.reporting.jasper.fe.service.hrd;

import java.util.List;

import in.ecgc.smile.erp.reporting.jasper.fe.model.hrd.HrdLookupOption;
import in.ecgc.smile.erp.reporting.jasper.fe.model.hrd.HrdR05;

public interface HrdService {

	List<HrdR05> getHrdR05Report(String empNo, String desigCd, String officeId, String fromDate, String toDate);

	List<HrdLookupOption> getAllEmployeeOptions();

	List<HrdLookupOption> getAllDesignationOptions();

	List<HrdLookupOption> getAllOfficeOptions();

}
