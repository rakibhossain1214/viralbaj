package com.example.viralbaj;

public class service {
    String serviceid, servicestatus, servicemilestone, servicecategory, serviceownerid, serviceownername,
            servicetime, servicedate,
            buyingaccounttype, buyingaccountnumber, buyingtrxid, servicecost, serviceidea, milestonereached;

    public service() {
    }

    public service(String serviceid, String servicestatus, String servicemilestone, String servicecategory, String serviceownerid, String serviceownername, String servicetime, String servicedate, String buyingaccounttype, String buyingaccountnumber, String buyingtrxid, String servicecost, String serviceidea, String milestonereached) {
        this.serviceid = serviceid;
        this.servicestatus = servicestatus;
        this.servicemilestone = servicemilestone;
        this.servicecategory = servicecategory;
        this.serviceownerid = serviceownerid;
        this.serviceownername = serviceownername;
        this.servicetime = servicetime;
        this.servicedate = servicedate;
        this.buyingaccounttype = buyingaccounttype;
        this.buyingaccountnumber = buyingaccountnumber;
        this.buyingtrxid = buyingtrxid;
        this.servicecost = servicecost;
        this.serviceidea = serviceidea;
        this.milestonereached = milestonereached;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getServicestatus() {
        return servicestatus;
    }

    public void setServicestatus(String servicestatus) {
        this.servicestatus = servicestatus;
    }

    public String getServicemilestone() {
        return servicemilestone;
    }

    public void setServicemilestone(String servicemilestone) {
        this.servicemilestone = servicemilestone;
    }

    public String getServicecategory() {
        return servicecategory;
    }

    public void setServicecategory(String servicecategory) {
        this.servicecategory = servicecategory;
    }

    public String getServiceownerid() {
        return serviceownerid;
    }

    public void setServiceownerid(String serviceownerid) {
        this.serviceownerid = serviceownerid;
    }

    public String getServiceownername() {
        return serviceownername;
    }

    public void setServiceownername(String serviceownername) {
        this.serviceownername = serviceownername;
    }

    public String getServicetime() {
        return servicetime;
    }

    public void setServicetime(String servicetime) {
        this.servicetime = servicetime;
    }

    public String getServicedate() {
        return servicedate;
    }

    public void setServicedate(String servicedate) {
        this.servicedate = servicedate;
    }

    public String getBuyingaccounttype() {
        return buyingaccounttype;
    }

    public void setBuyingaccounttype(String buyingaccounttype) {
        this.buyingaccounttype = buyingaccounttype;
    }

    public String getBuyingaccountnumber() {
        return buyingaccountnumber;
    }

    public void setBuyingaccountnumber(String buyingaccountnumber) {
        this.buyingaccountnumber = buyingaccountnumber;
    }

    public String getBuyingtrxid() {
        return buyingtrxid;
    }

    public void setBuyingtrxid(String buyingtrxid) {
        this.buyingtrxid = buyingtrxid;
    }

    public String getServicecost() {
        return servicecost;
    }

    public void setServicecost(String servicecost) {
        this.servicecost = servicecost;
    }

    public String getServiceidea() {
        return serviceidea;
    }

    public void setServiceidea(String serviceidea) {
        this.serviceidea = serviceidea;
    }

    public String getMilestonereached() {
        return milestonereached;
    }

    public void setMilestonereached(String milestonereached) {
        this.milestonereached = milestonereached;
    }
}
