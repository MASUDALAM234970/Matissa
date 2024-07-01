package com.error.mantissa.model;

public class SectionModel
{
    private double SectionYear;
    private String SectionName;

    public SectionModel() {
    }

    public SectionModel(double sectionYear, String sectionName) {
        SectionYear = sectionYear;
        SectionName = sectionName;
    }

    public double getSectionYear() {
        return SectionYear;
    }

    public void setSectionYear(double sectionYear) {
        SectionYear = sectionYear;
    }

    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }
}
