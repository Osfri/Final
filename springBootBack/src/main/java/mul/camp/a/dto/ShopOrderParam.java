package mul.camp.a.dto;

public class ShopOrderParam {
	private int pageNumber;	// 현재 페이지 번호
	private int start, end;	// 페이지의 글 범위 (start~end) (1~10)(11~20)...
	private String name;	// 주문자 성명
	private String location;// 주소
	private String title;	// 주문품목
	private String photo;	// 주문품목 사진
	private int cnt;		// 주문수량
	private String code;	// 병원코드
	
	public ShopOrderParam() {}

	public ShopOrderParam(int pageNumber, int start, int end, String name, String location, String title, String photo,
			int cnt, String code) {
		super();
		this.pageNumber = pageNumber;
		this.start = start;
		this.end = end;
		this.name = name;
		this.location = location;
		this.title = title;
		this.photo = photo;
		this.cnt = cnt;
		this.code = code;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ShopOrderParam [pageNumber=" + pageNumber + ", start=" + start + ", end=" + end + ", name=" + name
				+ ", location=" + location + ", title=" + title + ", photo=" + photo + ", cnt=" + cnt + ", code=" + code
				+ "]";
	}

	
	
}
