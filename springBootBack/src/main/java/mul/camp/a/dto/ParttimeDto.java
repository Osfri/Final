package mul.camp.a.dto;

public class ParttimeDto {

    private String name;
    private String start_time;
    private String end_time;
    private String code;

    public ParttimeDto() {}

    @Override
	public String toString() {
		return "ParttimeDto [name=" + name + ", starttime=" + start_time + ", endtime=" + end_time + ", code=" + code
				+ "]";
	}

	public ParttimeDto(String name, String starttime, String endtime, String code) {
		super();
		this.name = name;
		this.start_time = starttime;
		this.end_time = endtime;
		this.code = code;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStarttime() {
        return start_time;
    }

    public void setStarttime(String starttime) {
        this.start_time = starttime;
    }

    public String getEndtime() {
        return end_time;
    }

    public void setEndtime(String endtime) {
        this.end_time = endtime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
