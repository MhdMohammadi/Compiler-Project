public class Code {
    private String text = "";

    public void addCode(String code){
        text = text + code + '\n';
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
