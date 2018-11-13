package com.example.a84965.bookstoreadmin.model;

public class TacGiaCheckList {
    private String TG_Ma;
    private String TG_Ten;
    private boolean isChecked;

    public TacGiaCheckList(String TG_Ma, String TG_Ten, boolean isChecked) {
        this.TG_Ma = TG_Ma;
        this.TG_Ten = TG_Ten;
        this.isChecked = isChecked;
    }

    public String getTG_Ma() {
        return TG_Ma;
    }

    public void setTG_Ma(String TG_Ma) {
        this.TG_Ma = TG_Ma;
    }

    public String getTG_Ten() {
        return TG_Ten;
    }

    public void setTG_Ten(String TG_Ten) {
        this.TG_Ten = TG_Ten;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
