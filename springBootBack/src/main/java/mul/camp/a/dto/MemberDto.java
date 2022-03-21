package mul.camp.a.dto;

public class MemberDto {

	private String id;
	private String name;
	private String email;
	private String pwd;
	private String phoneNumber;
	private String code;
	private int auth;
	private int alarm;
	private int alarmTime;
	private int point;
	
	public MemberDto() {
	}

	public MemberDto(String id, String name, String email, String pwd, String phoneNumber, String code, int auth,
			int alarm, int alarmTime, int point) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.pwd = pwd;
		this.phoneNumber = phoneNumber;
		this.code = code;
		this.auth = auth;
		this.alarm = alarm;
		this.alarmTime = alarmTime;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public int getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(int alarmTime) {
		this.alarmTime = alarmTime;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", name=" + name + ", email=" + email + ", pwd=" + pwd + ", phoneNumber="
				+ phoneNumber + ", code=" + code + ", auth=" + auth + ", alarm=" + alarm + ", alarmTime=" + alarmTime
				+ ", point=" + point + "]";
	}

}
