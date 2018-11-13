package com.example.a84965.bookstoreadmin.model;

public class TheLoaiCheckList {
    private int TL_Ma;
    private String TL_Ten;
    private boolean isChecked;

    public TheLoaiCheckList(int TL_Ma, String TL_Ten, boolean isChecked) {
        this.TL_Ma = TL_Ma;
        this.TL_Ten = TL_Ten;
        this.isChecked = isChecked;
    }

    public int getTL_Ma() {
        return TL_Ma;
    }

    public void setTL_Ma(int TL_Ma) {
        this.TL_Ma = TL_Ma;
    }

    public String getTL_Ten() {
        return TL_Ten;
    }

    public void setTL_Ten(String TL_Ten) {
        this.TL_Ten = TL_Ten;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
