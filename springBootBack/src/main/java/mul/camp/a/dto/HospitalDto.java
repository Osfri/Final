package mul.camp.a.dto;

public class HospitalDto {

    private String name;
    private String location;
    private String code;
    private int cnt;	//해당 병동 인원수
    private String manager;

    public HospitalDto(){}
    
    public HospitalDto(String name, String location, String code, int cnt, String manager) {
		super();
		this.name = name;
		this.location = location;
		this.code = code;
		this.cnt = cnt;
		this.manager = manager;
	}

    public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
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
    

    public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public void setLocation(String location) {
        this.location = location;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

	@Override
	public String toString() {
		return "HospitalDto [name=" + name + ", location=" + location + ", code=" + code + ", cnt=" + cnt + ", manager="
				+ manager + "]";
	}
    
}
