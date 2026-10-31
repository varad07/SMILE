package in.ecgc.smile.erp.reporting.jasper.fe.controller.hrd;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import in.ecgc.smile.erp.reporting.jasper.fe.model.hrd.HrdR05;
import in.ecgc.smile.erp.reporting.jasper.fe.service.hrd.HrdService;
import in.ecgc.smile.erp.sys.auth.service.UserInfoService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Controller
@RequestMapping("/hrd-reports")
public class HrdController {

	@Autowired
	private HrdService hrdService;

	@Autowired
	private UserInfoService userInfoService;

	private static final Logger logger = LoggerFactory.getLogger(HrdController.class);

	@GetMapping("/HRD_R05")
	public String hrdR05Form(Model model) {
		logger.info("Inside HrdController#hrdR05Form");
		model.addAttribute("hrdR05Filter", new HrdR05());
		model.addAttribute("employeeOptions", hrdService.getAllEmployeeOptions());
		model.addAttribute("designationOptions", hrdService.getAllDesignationOptions());
		model.addAttribute("officeOptions", hrdService.getAllOfficeOptions());
		return "views/hrd/HRD_R05";
	}

	@PostMapping("/HRD_R05")
	public String getHrdR05Report(
			@RequestParam(name = "empNo", defaultValue = "ALL") String empNo,
			@RequestParam(name = "desigCd", defaultValue = "ALL") String desigCd,
			@RequestParam(name = "officeId", defaultValue = "ALL") String officeId,
			@RequestParam(name = "fromDate", defaultValue = "ALL") String fromDate,
			@RequestParam(name = "toDate", defaultValue = "ALL") String toDate,
			Model model) {
		logger.info("Inside HrdController#getHrdR05Report empNo={} desigCd={} officeId={} fromDate={} toDate={}",
				empNo, desigCd, officeId, fromDate, toDate);

		List<HrdR05> report;
		try {
			report = hrdService.getHrdR05Report(empNo, desigCd, officeId, fromDate, toDate);
		} catch (ResponseStatusException ex) {
			logger.warn("HRD_R05 request rejected by backend: {}", ex.getReason());
			model.addAttribute("FailureMessage",
					"Invalid search criteria - please check the From/To Date fields (expected format: yyyy-MM-dd).");
			model.addAttribute("employeeOptions", hrdService.getAllEmployeeOptions());
			model.addAttribute("designationOptions", hrdService.getAllDesignationOptions());
			model.addAttribute("officeOptions", hrdService.getAllOfficeOptions());
			return "views/hrd/HRD_R05";
		}

		model.addAttribute("report", report);
		model.addAttribute("empNo", empNo);
		model.addAttribute("desigCd", desigCd);
		model.addAttribute("officeId", officeId);
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);

		if (report == null || report.isEmpty()) {
			model.addAttribute("FailureMessage", "No Records Found For Given Criteria");
		}

		return "views/hrd/HRD_R05_Results";
	}

	/* PDF download covers the core employee fields only - Qualifications and
	 * Locations Served are nested per-employee lists, which would need Jasper
	 * subreports to render reliably in a PDF. Full detail including both lists
	 * is available in the HTML results view above. */
	@PostMapping("/HRD_R05/pdf")
	public ResponseEntity<byte[]> downloadHrdR05Pdf(
			@RequestParam(name = "empNo", defaultValue = "ALL") String empNo,
			@RequestParam(name = "desigCd", defaultValue = "ALL") String desigCd,
			@RequestParam(name = "officeId", defaultValue = "ALL") String officeId,
			@RequestParam(name = "fromDate", defaultValue = "ALL") String fromDate,
			@RequestParam(name = "toDate", defaultValue = "ALL") String toDate) throws JRException {
		logger.info("Inside HrdController#downloadHrdR05Pdf");

		List<HrdR05> report;
		try {
			report = hrdService.getHrdR05Report(empNo, desigCd, officeId, fromDate, toDate);
		} catch (ResponseStatusException ex) {
			logger.warn("HRD_R05 PDF request rejected by backend: {}", ex.getReason());
			String msg = "Invalid search criteria - please check the From/To Date fields (expected format: yyyy-MM-dd).";
			return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(msg.getBytes());
		}

		JRBeanCollectionDataSource reportDataSource = new JRBeanCollectionDataSource(report, false);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("user_name", userInfoService.getName());

		InputStream input;
		try {
			input = new ClassPathResource("jasper-reports/hrd-reports/HRD_R05.jrxml").getInputStream();

			JasperDesign jasperDesign = JRXmlLoader.load(input);
			JasperReport compiledReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, parameters, reportDataSource);

			byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=HRD_R05.pdf");

			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);

		} catch (IOException e) {
			logger.error("Error generating HRD_R05 PDF", e);
		}

		return ResponseEntity.noContent().build();
	}

}
