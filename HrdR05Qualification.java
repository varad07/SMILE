package in.ecgc.smile.erp.reporting.jasper.fe.model.hrd;

public class HrdR05Qualification {

	private String degreeCd;

	private String degreeName;

	private String boardUniv;

	private Integer yearOfPassing;

	private Float percentage;

	private String specialization;

	private String educationMedium;

	public String getDegreeCd() {
		return degreeCd;
	}

	public void setDegreeCd(String degreeCd) {
		this.degreeCd = degreeCd;
	}

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public String getBoardUniv() {
		return boardUniv;
	}

	public void setBoardUniv(String boardUniv) {
		this.boardUniv = boardUniv;
	}

	public Integer getYearOfPassing() {
		return yearOfPassing;
	}

	public void setYearOfPassing(Integer yearOfPassing) {
		this.yearOfPassing = yearOfPassing;
	}

	public Float getPercentage() {
		return percentage;
	}

	public void setPercentage(Float percentage) {
		this.percentage = percentage;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getEducationMedium() {
		return educationMedium;
	}

	public void setEducationMedium(String educationMedium) {
		this.educationMedium = educationMedium;
	}

	@Override
	public String toString() {
		return "HrdR05Qualification [degreeCd=" + degreeCd + ", degreeName=" + degreeName + ", boardUniv=" + boardUniv
				+ ", yearOfPassing=" + yearOfPassing + ", percentage=" + percentage + ", specialization="
				+ specialization + ", educationMedium=" + educationMedium + "]";
	}

}
