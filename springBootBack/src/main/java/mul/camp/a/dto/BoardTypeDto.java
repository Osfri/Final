package mul.camp.a.dto;

public class BoardTypeDto {
    private int type;
    private String name;
    private String code;
    private int auth;

    public BoardTypeDto(){}

    public BoardTypeDto(int type, String name, String code, int auth) {
        this.type = type;
        this.name = name;
        this.code = code;
        this.auth = auth;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "BoardTypeDto{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", auth=" + auth +
                '}';
    }
}
