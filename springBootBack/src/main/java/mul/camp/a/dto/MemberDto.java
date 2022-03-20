package mul.camp.a.dto;

public class MemberDto {
	//id, name , email , pw , phonenumber , code , auth , alarm , alarmtime , point
	private String id;
	private String name;
	private String email;
	private String pw;
	private String phonenumber;
	private String code;
	private int auth;
	private int alarm;
	private int alarmtime;
	private int point;
	
	public MemberDto() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public MemberDto(String id, String name, String email, String pw, String phonenumber, String code, int auth, int alarm, int alarmtimel, int point) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.pw = pw;
		this.phonenumber = phonenumber;
		this.code = code;
		this.auth = auth;
		this.alarm = alarm;
		this.alarmtime = alarmtimel;
		this.point = point;
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

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	public int getAlarm() {
		return alarm;
	}

	public void setAlarm(int alarm) {
		this.alarm = alarm;
	}

	public int getAlarmtime() {
		return alarmtime;
	}

	public void setAlarmtime(int alarmtime) {
		this.alarmtime = alarmtime;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	@Override
	public String toString() {
		return "MemberDto{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", pw='" + pw + '\'' +
				", phonenumber='" + phonenumber + '\'' +
				", code='" + code + '\'' +
				", auth=" + auth +
				", alarm=" + alarm +
				", alarmtime=" + alarmtime +
				", point=" + point +
				'}';
	}
}
