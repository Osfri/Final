package mul.camp.a.dto;

public class CalendarDto {

	private String id;
	private String name;
	private String wdate;
	private String time;
	private String memo;
	
	public CalendarDto(){
		
	}

	public CalendarDto(String id, String name, String wdate, String time, String memo) {
		super();
		this.id = id;
		this.name = name;
		this.wdate = wdate;
		this.time = time;
		this.memo = memo;
	}

	public CalendarDto(String id, String wdate) {
		super();
		this.id = id;
		this.wdate = wdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return "CalendarDto [id=" + id + ", name=" + name + ", wdate=" + wdate + ", time=" + time + ", memo=" + memo
				+ "]";
	}

}
