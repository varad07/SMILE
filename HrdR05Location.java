package in.ecgc.smile.erp.reporting.jasper.fe.model.hrd;

import java.util.Date;

public class HrdR05Location {

	private String place;

	private Date fromDate;

	private Date toDate;

	private String capacityWorked;

	private String deptOffice;

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getCapacityWorked() {
		return capacityWorked;
	}

	public void setCapacityWorked(String capacityWorked) {
		this.capacityWorked = capacityWorked;
	}

	public String getDeptOffice() {
		return deptOffice;
	}

	public void setDeptOffice(String deptOffice) {
		this.deptOffice = deptOffice;
	}

	@Override
	public String toString() {
		return "HrdR05Location [place=" + place + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", capacityWorked=" + capacityWorked + ", deptOffice=" + deptOffice + "]";
	}

}
