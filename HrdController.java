package in.ecgc.smile.erp.reporting.jasper.be.controller.hrd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.reporting.jasper.be.model.hrd.HrdLookupOption;
import in.ecgc.smile.erp.reporting.jasper.be.model.hrd.HrdR05;
import in.ecgc.smile.erp.reporting.jasper.be.service.hrd.HrdService;

@RestController
@RequestMapping("/hrd-reports")
public class HrdController {

	@Autowired
	private HrdService hrdService;

	private static final Logger logger = LoggerFactory.getLogger(HrdController.class);

	@GetMapping("/HRD_R05/{empNo}/{desigCd}/{officeId}/{fromDate}/{toDate}")
	public ResponseEntity<List<HrdR05>> getHrdR05Report(@PathVariable("empNo") String empNo,
			@PathVariable("desigCd") String desigCd, @PathVariable("officeId") String officeId,
			@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate) {
		logger.info("Inside HrdController#getHrdR05Report");

		List<HrdR05> list = hrdService.getHrdR05Report(empNo, desigCd, officeId, fromDate, toDate);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/HRD_R05/lookups/employees")
	public ResponseEntity<List<HrdLookupOption>> getAllEmployeeOptions() {
		return new ResponseEntity<>(hrdService.getAllEmployeeOptions(), HttpStatus.OK);
	}

	@GetMapping("/HRD_R05/lookups/designations")
	public ResponseEntity<List<HrdLookupOption>> getAllDesignationOptions() {
		return new ResponseEntity<>(hrdService.getAllDesignationOptions(), HttpStatus.OK);
	}

	@GetMapping("/HRD_R05/lookups/offices")
	public ResponseEntity<List<HrdLookupOption>> getAllOfficeOptions() {
		return new ResponseEntity<>(hrdService.getAllOfficeOptions(), HttpStatus.OK);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleInvalidInput(IllegalArgumentException ex) {
		logger.warn("HRD_R05 invalid input: {}", ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
