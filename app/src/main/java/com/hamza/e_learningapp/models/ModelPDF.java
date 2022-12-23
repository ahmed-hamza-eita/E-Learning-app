package com.hamza.e_learningapp.models;

import android.net.Uri;

public class ModelPDF {
    private String name ,courseId;
    private String uri;

    public ModelPDF(String uri,String courseId  , String name ) {
        this.name = name;
        this.courseId = courseId;
        this.uri = uri;
    }

    public ModelPDF() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
