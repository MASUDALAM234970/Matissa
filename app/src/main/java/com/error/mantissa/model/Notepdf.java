package com.error.mantissa.model;

public class Notepdf
{
    String pdf_title,tname,ctitle,dept,sem,ses,unique_key;

    String  pdfUri;

    public String getPdfUri() {
        return pdfUri;
    }

    public void setPdfUri(String pdfUri) {
        this.pdfUri = pdfUri;
    }

    public Notepdf() {
    }

    public String getPdf_title() {
        return pdf_title;
    }

    public void setPdf_title(String pdf_title) {
        this.pdf_title = pdf_title;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getCtitle() {
        return ctitle;
    }

    public void setCtitle(String ctitle) {
        this.ctitle = ctitle;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getSes() {
        return ses;
    }

    public void setSes(String ses) {
        this.ses = ses;
    }

    public String getUnique_key() {
        return unique_key;
    }

    public void setUnique_key(String unique_key) {
        this.unique_key = unique_key;
    }
}
