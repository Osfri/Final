package mul.camp.a.dto;

public class FoodParam {
	private String id;		// 아이디
	private int pageNumber;	// 현재 페이지 번호
	private int start, end;	// 페이지의 글 범위 (start~end) (1~10)(11~20)...
	
	public FoodParam() {}

	public FoodParam(String id, int pageNumber, int start, int end) {
		super();
		this.id = id;
		this.pageNumber = pageNumber;
		this.start = start;
		this.end = end;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "FoodParam [id=" + id + ", pageNumber=" + pageNumber + ", start=" + start + ", end=" + end + "]";
	}
	
	

}
