package mul.camp.a.dto;

public class ShopDto {
	private int seq;		// 상품시퀀스
	private String title;	// 상품명
	private String content;	// 상품설명
	private int price;		// 가격
	private String photo;	// 사진경로
	private int cnt;		// 재고
	private String code;		// 병원코드
	
	public ShopDto() {}

	public ShopDto(int seq, String title, String content, int price, String photo, int cnt, String code) {
		super();
		this.seq = seq;
		this.title = title;
		this.content = content;
		this.price = price;
		this.photo = photo;
		this.cnt = cnt;
		this.code = code;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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
		return "ShopDto [seq=" + seq + ", title=" + title + ", content=" + content + ", price=" + price + ", photo="
				+ photo + ", cnt=" + cnt + ", code=" + code + "]";
	}

	
	
}
