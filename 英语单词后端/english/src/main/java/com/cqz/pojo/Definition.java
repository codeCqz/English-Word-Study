package com.cqz.pojo;

public class Definition {
    private String word;
    private String part;
    private String translation;

    public Definition() {
    }

    public Definition(String word, String part, String translation) {
        this.word = word;
        this.part = part;
        this.translation = translation;
    }

    public Definition(String word, String translation) {
        this.part=null;
        this.word = word;
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return "Definition{" +
                "word='" + word + '\'' +
                ", part='" + part + '\'' +
                ", translation='" + translation + '\'' +
                '}';
    }
}
