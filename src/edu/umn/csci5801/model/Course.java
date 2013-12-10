package edu.umn.csci5801.model;

public class Course {
    private String name;
    private String id;
    private String numCredits;
    private CourseArea courseArea = CourseArea.NONE;

    public Course() {
    }

    public Course(String name, String id, String numCredits,
            CourseArea courseArea) {
        this.name = name;
        this.id = id;
        this.numCredits = numCredits;
        if(courseArea!=null){
        	this.courseArea = courseArea;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumCredits() {
        return numCredits;
    }

    public void setNumCredits(String numCredits) {
        this.numCredits = numCredits;
    }

    public CourseArea getCourseArea() {
        return courseArea;
    }

    public void setCourseArea(CourseArea courseArea) {
        this.courseArea = courseArea;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((courseArea == null) ? 0 : courseArea.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((numCredits == null) ? 0 : numCredits.hashCode());
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
        Course other = (Course) obj;
        if (courseArea != other.courseArea)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (numCredits == null) {
            if (other.numCredits != null)
                return false;
        } else if (!numCredits.equals(other.numCredits))
            return false;
        return true;
    }

}
