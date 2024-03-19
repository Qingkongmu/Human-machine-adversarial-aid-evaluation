package com.biggiser.streetviewratingapp.beans;

public class LogBean {
    private Integer logid;
    private Integer userid;
    private String imgname;
    private double safetyuser;
    private double comfortuser;
    private double convenienceuser;
    private double attractivenessuser;
    private double safetypredict;
    private double comfortpredict;
    private double conveniencepredict;
    private double attractivenesspredict;

    public LogBean(Integer logid, Integer userid, String imgname, double safetyuser, double comfortuser,
                   double convenienceuser, double attractivenessuser, double safetypredict,
                   double comfortpredict, double conveniencepredict, double attractivenesspredict) {
        super();
        this.logid = logid;
        this.userid = userid;
        this.imgname = imgname;
        this.safetyuser = safetyuser;
        this.comfortuser = comfortuser;
        this.convenienceuser = convenienceuser;
        this.attractivenessuser = attractivenessuser;
        this.safetypredict = safetypredict;
        this.comfortpredict = comfortpredict;
        this.conveniencepredict = conveniencepredict;
        this.attractivenesspredict = attractivenesspredict;
    }

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    public double getsafetyuser() {
        return safetyuser;
    }

    public void setsafetyuser(double safetyuser) {
        this.safetyuser = safetyuser;
    }

    public double getcomfortuser() {
        return comfortuser;
    }

    public void setcomfortuser(double comfortuser) {
        this.comfortuser = comfortuser;
    }

    public double getconvenienceuser() {
        return convenienceuser;
    }

    public void setconvenienceuser(double convenienceuser) {
        this.convenienceuser = convenienceuser;
    }

    public double getattractivenessuser() {
        return attractivenessuser;
    }

    public void setattractivenessuser(double attractivenessuser) {
        this.attractivenessuser = attractivenessuser;
    }

    public double getsafetypredict() {
        return safetypredict;
    }

    public void setsafetypredict(double safetypredict) {
        this.safetypredict = safetypredict;
    }

    public double getcomfortpredict() {
        return comfortpredict;
    }

    public void setcomfortpredict(double comfortpredict) {
        this.comfortpredict = comfortpredict;
    }

    public double getconveniencepredict() {
        return conveniencepredict;
    }

    public void setconveniencepredict(double conveniencepredict) {
        this.conveniencepredict = conveniencepredict;
    }

    public double getattractivenesspredict() {
        return attractivenesspredict;
    }

    public void setattractivenesspredict(double attractivenesspredict) {
        this.attractivenesspredict = attractivenesspredict;
    }


}
