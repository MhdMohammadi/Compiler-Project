public class Code {
    private String text = "";

    public void addCode(String code){
        text = text + code + '\n';
    }

    public void addCode(Code code){
        text = text + code.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
