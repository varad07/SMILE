package in.ecgc.smile.erp.reporting.jasper.fe.model.hrd;

public class HrdLookupOption {

	private String code;

	private String label;

	public HrdLookupOption() {
	}

	public HrdLookupOption(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "HrdLookupOption [code=" + code + ", label=" + label + "]";
	}

}
