package com.trivia.triviamat;

public class Question {
    protected int qID;
    protected String qContent;
    protected String qContentType;
    protected String qContentLink;
    protected String qAns1;
    protected String qAns2;
    protected String qAns3;
    protected String qAns4;
    protected int qTrueAns;

    public Question(int qID, String qContent, String qContentType, String qContentLink, String qAns1, String qAns2, String qAns3, String qAns4, int qTrueAns) {
        this.qID = qID;
        this.qContent = qContent;
        this.qContentType = qContentType;
        this.qContentLink = qContentLink;
        this.qAns1 = qAns1;
        this.qAns2 = qAns2;
        this.qAns3 = qAns3;
        this.qAns4 = qAns4;
        this.qTrueAns = qTrueAns;
    }

    public int getqID() {
        return qID;
    }

    public void setqID(int qID) {
        this.qID = qID;
    }

    public String getqContent() {
        return qContent;
    }

    public void setqContent(String qContent) {
        this.qContent = qContent;
    }

    public String getqContentType() {
        return qContentType;
    }

    public void setqContentType(String qContentType) {
        this.qContentType = qContentType;
    }

    public String getqContentLink() {
        return qContentLink;
    }

    public void setqContentLink(String setqContentLink) {
        this.qContentLink = setqContentLink;
    }

    public String getqAns1() {
        return qAns1;
    }

    public void setqAns1(String qAns1) {
        this.qAns1 = qAns1;
    }

    public String getqAns2() {
        return qAns2;
    }

    public void setqAns2(String qAns2) {
        this.qAns2 = qAns2;
    }

    public String getqAns3() {
        return qAns3;
    }

    public void setqAns3(String qAns3) {
        this.qAns3 = qAns3;
    }

    public String getqAns4() {
        return qAns4;
    }

    public void setqAns4(String qAns4) {
        this.qAns4 = qAns4;
    }

    public int getqTrueAns() {
        return qTrueAns;
    }

    public void setqTrueAns(int qTrueAns) {
        this.qTrueAns = qTrueAns;
    }
}
