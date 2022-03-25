package mul.camp.a.dto;

public class FoodDto {
	
	private String code;
	private String fdate;
	private String menu;
	private String photo;
	
	public FoodDto() {}

	public FoodDto(String code, String fdate, String menu, String photo) {
		super();
		this.code = code;
		this.fdate = fdate;
		this.menu = menu;
		this.photo = photo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFdate() {
		return fdate;
	}

	public void setFdate(String fdate) {
		this.fdate = fdate;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "FoodDto [code=" + code + ", fdate=" + fdate + ", menu=" + menu + ", photo=" + photo + "]";
	}
	
}
