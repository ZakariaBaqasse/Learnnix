package com.learnnix.HelperClasses;

import java.io.Serializable;

public class ProfessorInfos implements Serializable {
    private int profId;
    private String profUsername;
    private String profSpeciality;
    private String profPassword;
    public ProfessorInfos(int profId, String profUsername, String profSpeciality) {
        this.profId = profId;
        this.profUsername = profUsername;
        this.profSpeciality = profSpeciality;
    }

    public ProfessorInfos(String profUsername, String profSpeciality, String profPassword) {
        this.profUsername = profUsername;
        this.profSpeciality = profSpeciality;
        this.profPassword = profPassword;
    }

    public String getProf_username() {
        return profUsername;
    }

    public String getProf_speciality() {
        return profSpeciality;
    }

    public int getProfId() {
        return profId;
    }

    public String getProfPassword() {
        return profPassword;
    }
}
