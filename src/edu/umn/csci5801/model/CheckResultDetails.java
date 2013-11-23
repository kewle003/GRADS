package edu.umn.csci5801.model;

import java.util.List;

public class CheckResultDetails {
    private Float gpa;
    private List<CourseTaken> courses;
    private List<String> other;

    public Float getGPA() {
        return gpa;
    }

    public void setGPA(Float gpa) {
        this.gpa = gpa;
    }

    public List<CourseTaken> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseTaken> courses) {
        this.courses = courses;
    }

    public List<String> getOther() {
        return other;
    }

    public void setOther(List<String> other) {
        this.other = other;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gpa == null) ? 0 : gpa.hashCode());
        result = prime * result + ((courses == null) ? 0 : courses.hashCode());
        result = prime * result + ((other == null) ? 0 : other.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CheckResultDetails other = (CheckResultDetails) obj;
        if (gpa == null) {
            if (other.getGPA() != null)
                return false;
        } else if (!gpa.equals(other.getGPA()))
            return false;
        if (courses == null) {
            if (other.getCourses() != null)
                return false;
        } else if (!courses.equals(other.getCourses()))
            return false;
        if (other == null) {
            if (other.getOther() != null)
                return false;
        } else if (!other.equals(other.getOther()))
            return false;
        return true;
    }

}
