package com.learnnix.HelperClasses;

import java.io.Serializable;

public class ClassInfos implements Serializable {
    private int classId;
    private String className;
    private String classSubject;
    private String classDescription;
    private String profInCharge;

    public ClassInfos(int classId,String className, String classSubject, String classDescription) {
        this.classId = classId;
        this.className = className;
        this.classSubject = classSubject;
        this.classDescription = classDescription;
    }

    public ClassInfos(String className,String classSubject, String classDescription) {
        this.classSubject = classSubject;
        this.classDescription = classDescription;
        this.className = className;
    }

    public ClassInfos(String className,String classSubject, String classDescription, String profInCharge) {
        this.classSubject = classSubject;
        this.classDescription = classDescription;
        this.profInCharge = profInCharge;
        this.className = className;
    }

    public ClassInfos(int classId,String className, String classSubject, String classDescription, String profInCharge) {
        this.classId = classId;
        this.classSubject = classSubject;
        this.classDescription = classDescription;
        this.profInCharge = profInCharge;
        this.className = className;
    }

    public int getClassId() {
        return classId;
    }

    public String getClassSubject() {
        return classSubject;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public String getProfInCharge() {
        return profInCharge;
    }

    public String getClassName() {
        return className;
    }
}
