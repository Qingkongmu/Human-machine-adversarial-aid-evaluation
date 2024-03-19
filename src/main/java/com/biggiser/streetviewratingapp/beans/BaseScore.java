package com.biggiser.streetviewratingapp.beans;

public class BaseScore {
    public double safetyScore;
    public double comfortScore;
    public double convenienceScore;
    public double attractivenessScore;
    public String fileName;

    public BaseScore() {
        super();
        this.safetyScore = -1;
        this.comfortScore = -1;
        this.convenienceScore = -1;
        this.attractivenessScore = -1;
        this.fileName = "";
    }

    public BaseScore(String filename) {
        super();
        this.safetyScore = -1;
        this.comfortScore = -1;
        this.convenienceScore = -1;
        this.attractivenessScore = -1;
        this.fileName = filename;
    }

    public BaseScore(double safetyScore, double comfortScore, double convenienceScore, double attractivenessScore, String fileName) {
        super();
        this.safetyScore = safetyScore;
        this.comfortScore = comfortScore;
        this.convenienceScore = convenienceScore;
        this.attractivenessScore = attractivenessScore;
        this.fileName = fileName;
    }

    public double getScore(String str) {
        if (str.equals("safety")) {
            return safetyScore;
        } else if (str.equals("comfort")) {
            return comfortScore;
        } else if (str.equals("convenience")) {
            return convenienceScore;
        } else if (str.equals("attractiveness")) {
            return attractivenessScore;
        }
        return 0;
    }

    public double getsafetyScore() {
        return safetyScore;
    }

    public void setsafetyScore(double safetyScore) {
        this.safetyScore = safetyScore;
    }

    public double getcomfortScore() {
        return comfortScore;
    }

    public void setcomfortScore(double comfortScore) {
        this.comfortScore = comfortScore;
    }

    public double getconvenienceScore() {
        return convenienceScore;
    }

    public void setconvenienceScore(double convenienceScore) {
        this.convenienceScore = convenienceScore;
    }

    public void setattractivenessScore(double attractivenessScore) {
        this.attractivenessScore = attractivenessScore;
    }

    public double getattractivenessScore() {
        return attractivenessScore;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public String toString() {
        return String.format(
                "BaseScore [safetyScore=%s, comfortScore=%s, convenienceScore=%s, attractivenessScore=%s]",
                safetyScore, comfortScore, convenienceScore, attractivenessScore);
    }

}
