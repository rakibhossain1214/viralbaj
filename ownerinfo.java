package com.example.viralbaj;

public class ownerinfo {
    String ownerid, ownername, owneremail, ownermobileno;

    public ownerinfo() {
    }

    public ownerinfo(String ownerid, String ownername, String owneremail, String ownermobileno) {
        this.ownerid = ownerid;
        this.ownername = ownername;
        this.owneremail = owneremail;
        this.ownermobileno = ownermobileno;
    }

    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getOwneremail() {
        return owneremail;
    }

    public void setOwneremail(String owneremail) {
        this.owneremail = owneremail;
    }

    public String getOwnermobileno() {
        return ownermobileno;
    }

    public void setOwnermobileno(String ownermobileno) {
        this.ownermobileno = ownermobileno;
    }
}
