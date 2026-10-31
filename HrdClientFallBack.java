package in.ecgc.smile.erp.reporting.jasper.fe.proxy.hrd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import in.ecgc.smile.erp.reporting.jasper.fe.model.hrd.HrdLookupOption;
import in.ecgc.smile.erp.reporting.jasper.fe.model.hrd.HrdR05;

public class HrdClientFallBack implements HrdClient {

	private static final Logger logger = LoggerFactory.getLogger(HrdClientFallBack.class);

	@Override
	public List<HrdR05> getHrdR05Report(String empNo, String desigCd, String officeId, String fromDate,
			String toDate) {
		logger.info("Inside HrdClientFallBack#getHrdR05Report - erp-reporting-jasper-be is down");
		return null;
	}

	@Override
	public List<HrdLookupOption> getAllEmployeeOptions() {
		logger.info("Inside HrdClientFallBack#getAllEmployeeOptions - erp-reporting-jasper-be is down");
		return Collections.emptyList();
	}

	@Override
	public List<HrdLookupOption> getAllDesignationOptions() {
		logger.info("Inside HrdClientFallBack#getAllDesignationOptions - erp-reporting-jasper-be is down");
		return Collections.emptyList();
	}

	@Override
	public List<HrdLookupOption> getAllOfficeOptions() {
		logger.info("Inside HrdClientFallBack#getAllOfficeOptions - erp-reporting-jasper-be is down");
		return Collections.emptyList();
	}

}
