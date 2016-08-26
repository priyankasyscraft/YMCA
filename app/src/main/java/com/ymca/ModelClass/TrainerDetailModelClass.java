package com.ymca.ModelClass;

import java.util.ArrayList;

/**
 * Created by Priyanka on 8/25/2016.
 */
public class TrainerDetailModelClass {

    private String trainerName;
    private String trainerImg;
    private String trainerExperience;
    private String trainerFunFact;
    private ArrayList<CustomTextModelClass> certificateList = new ArrayList<>();

    public String getTrainerFunFact() {
        return trainerFunFact;
    }

    public void setTrainerFunFact(String trainerFunFact) {
        this.trainerFunFact = trainerFunFact;
    }

    public String getTrainerExperience() {
        return trainerExperience;
    }

    public void setTrainerExperience(String trainerExperience) {
        this.trainerExperience = trainerExperience;
    }

    public String getTrainerImg() {
        return trainerImg;
    }

    public void setTrainerImg(String trainerImg) {
        this.trainerImg = trainerImg;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }


    public ArrayList<CustomTextModelClass> getCertificateList() {
        return certificateList;
    }

    public void setCertificateList(ArrayList<CustomTextModelClass> certificateList) {
        this.certificateList = certificateList;
    }

    public void addCertificateList(CustomTextModelClass certificateList) {
        this.certificateList.add(certificateList);
    }

    public void clearCertificateList() {
        this.certificateList.clear();
    }
}
