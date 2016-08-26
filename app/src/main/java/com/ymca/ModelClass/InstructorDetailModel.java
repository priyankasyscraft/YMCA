package com.ymca.ModelClass;

import java.util.ArrayList;

/**
 * Created by Soni on 23-Aug-16.
 */
public class InstructorDetailModel {

    private String instructorImgUrl;
    private String instructorName;
    private String instructorInfo;

    private ArrayList<ClassesModelClass> modelClasses = new ArrayList<>();




    public String getInstructorInfo() {
        return instructorInfo;
    }

    public void setInstructorInfo(String instructorInfo) {
        this.instructorInfo = instructorInfo;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorImgUrl() {
        return instructorImgUrl;
    }

    public void setInstructorImgUrl(String instructorImgUrl) {
        this.instructorImgUrl = instructorImgUrl;
    }

    public ArrayList<ClassesModelClass> getModelClasses() {
        return modelClasses;
    }

    public void setModelClasses(ArrayList<ClassesModelClass> modelClasses) {
        this.modelClasses = modelClasses;
    }  public void addModelClasses(ClassesModelClass modelClasses) {
        this.modelClasses.add(modelClasses);
    }  public void clearModelClasses() {
        this.modelClasses.clear();
    }
}
