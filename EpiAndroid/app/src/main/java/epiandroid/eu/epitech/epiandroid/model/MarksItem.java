package epiandroid.eu.epitech.epiandroid.model;

/**
 * Created by linard_f on 1/22/15.
 */
public class MarksItem {

    private String name;
    private String mark;

    public MarksItem(String name, String mark) {
        this.name = name;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
