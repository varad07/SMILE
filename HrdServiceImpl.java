package in.ecgc.smile.erp.reporting.jasper.fe.service.hrd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.reporting.jasper.fe.model.hrd.HrdLookupOption;
import in.ecgc.smile.erp.reporting.jasper.fe.model.hrd.HrdR05;
import in.ecgc.smile.erp.reporting.jasper.fe.proxy.hrd.HrdClient;

@Service
public class HrdServiceImpl implements HrdService {

	@Autowired
	private HrdClient hrdClient;

	private static final Logger logger = LoggerFactory.getLogger(HrdServiceImpl.class);

	@Override
	public List<HrdR05> getHrdR05Report(String empNo, String desigCd, String officeId, String fromDate,
			String toDate) {
		logger.info("Inside HrdServiceImpl#getHrdR05Report");
		return hrdClient.getHrdR05Report(empNo, desigCd, officeId, fromDate, toDate);
	}

	@Override
	public List<HrdLookupOption> getAllEmployeeOptions() {
		return hrdClient.getAllEmployeeOptions();
	}

	@Override
	public List<HrdLookupOption> getAllDesignationOptions() {
		return hrdClient.getAllDesignationOptions();
	}

	@Override
	public List<HrdLookupOption> getAllOfficeOptions() {
		return hrdClient.getAllOfficeOptions();
	}

}
