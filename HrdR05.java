package in.ecgc.smile.erp.reporting.jasper.fe.model.hrd;

import java.util.Date;
import java.util.List;

public class HrdR05 {

	private Integer empNo;

	private String empName;

	private Date doj;

	private String category;

	private String officeId;

	private String cadreRank;

	private String designation;

	private Date dob;

	private Date retirementDate;

	private Date lastPromotionDate;

	private String totalCompletedService;

	private List<HrdR05Qualification> qualifications;

	private List<HrdR05Location> locationsServed;

	public Integer getEmpNo() {
		return empNo;
	}

	public void setEmpNo(Integer empNo) {
		this.empNo = empNo;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getCadreRank() {
		return cadreRank;
	}

	public void setCadreRank(String cadreRank) {
		this.cadreRank = cadreRank;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getRetirementDate() {
		return retirementDate;
	}

	public void setRetirementDate(Date retirementDate) {
		this.retirementDate = retirementDate;
	}

	public Date getLastPromotionDate() {
		return lastPromotionDate;
	}

	public void setLastPromotionDate(Date lastPromotionDate) {
		this.lastPromotionDate = lastPromotionDate;
	}

	public String getTotalCompletedService() {
		return totalCompletedService;
	}

	public void setTotalCompletedService(String totalCompletedService) {
		this.totalCompletedService = totalCompletedService;
	}

	public List<HrdR05Qualification> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List<HrdR05Qualification> qualifications) {
		this.qualifications = qualifications;
	}

	public List<HrdR05Location> getLocationsServed() {
		return locationsServed;
	}

	public void setLocationsServed(List<HrdR05Location> locationsServed) {
		this.locationsServed = locationsServed;
	}

	@Override
	public String toString() {
		return "HrdR05 [empNo=" + empNo + ", empName=" + empName + ", doj=" + doj + ", category=" + category
				+ ", officeId=" + officeId + ", cadreRank=" + cadreRank + ", designation=" + designation + ", dob="
				+ dob + ", retirementDate=" + retirementDate + ", lastPromotionDate=" + lastPromotionDate
				+ ", totalCompletedService=" + totalCompletedService + ", qualifications=" + qualifications
				+ ", locationsServed=" + locationsServed + "]";
	}

}
