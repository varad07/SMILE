package in.ecgc.smile.erp.reporting.jasper.fe.proxy.hrd;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import in.ecgc.smile.erp.reporting.jasper.fe.model.hrd.HrdLookupOption;
import in.ecgc.smile.erp.reporting.jasper.fe.model.hrd.HrdR05;
import in.ecgc.smile.erp.reporting.jasper.fe.proxy.FeignErrorDecoder;

@FeignClient(name = "${api.gateway}", contextId = "HrdClient", fallbackFactory = HrdClientFallBack.class, configuration = FeignErrorDecoder.class)
public interface HrdClient {

	@GetMapping("erp-reporting-jasper-be/hrd-reports/HRD_R05/{empNo}/{desigCd}/{officeId}/{fromDate}/{toDate}")
	List<HrdR05> getHrdR05Report(@PathVariable("empNo") String empNo, @PathVariable("desigCd") String desigCd,
			@PathVariable("officeId") String officeId, @PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate);

	@GetMapping("erp-reporting-jasper-be/hrd-reports/HRD_R05/lookups/employees")
	List<HrdLookupOption> getAllEmployeeOptions();

	@GetMapping("erp-reporting-jasper-be/hrd-reports/HRD_R05/lookups/designations")
	List<HrdLookupOption> getAllDesignationOptions();

	@GetMapping("erp-reporting-jasper-be/hrd-reports/HRD_R05/lookups/offices")
	List<HrdLookupOption> getAllOfficeOptions();

}
