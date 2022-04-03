package mul.camp.a.dto;

public class BoardDto {

    private int seq;
    private String id;
    private String title;
    private String content;
    private int readcount;
    private String wdate;
    private int del;
    private int type;
    private String code;
    private int step;
    private int gr;
    private String image;

    public BoardDto(){}

    public BoardDto(int seq, String id, String title, String content, int readcount, String wdate, int del, int type, String code, int step, int gr, String image) {
        this.seq = seq;
        this.id = id;
        this.title = title;
        this.content = content;
        this.readcount = readcount;
        this.wdate = wdate;
        this.del = del;
        this.type = type;
        this.code = code;
        this.step = step;
        this.gr = gr;
        this.image = image;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getReadcount() {
        return readcount;
    }

    public void setReadcount(int readcount) {
        this.readcount = readcount;
    }

    public String getWdate() {
        return wdate;
    }

    public void setWdate(String wdate) {
        this.wdate = wdate;
    }

    public int getDel() {
        return del;
    }

    public void setDel(int del) {
        this.del = del;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getGr() {
        return gr;
    }

    public void setGr(int gr) {
        this.gr = gr;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "BoardDto{" +
                "seq=" + seq +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", readcount=" + readcount +
                ", wdate='" + wdate + '\'' +
                ", del=" + del +
                ", type=" + type +
                ", code='" + code + '\'' +
                ", step=" + step +
                ", gr=" + gr +
                ", image='" + image + '\'' +
                '}';
    }
}
