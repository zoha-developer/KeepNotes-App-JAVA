package com.example.keepnotes;

//Note class h ye

import com.google.firebase.Timestamp;

public class details {
     String Titl;
     String Contnt;
     Timestamp time;

    public details() {
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getContnt() {
        return Contnt;
    }

    public void setContnt(String contnt) {
        Contnt = contnt;
    }

    public String getTitl() {
        return Titl;
    }

    public void setTitl(String titl) {
        Titl = titl;
    }
}

