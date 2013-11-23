package edu.umn.csci5801.model;

import java.util.List;

public class StudentRecord {
    private Student student;
    private Department department;
    private Degree degreeSought;
    private Term termBegan;
    private List<Professor> advisors;
    private List<Professor> committee;
    private List<CourseTaken> coursesTaken;
    private List<MilestoneSet> milestonesSet;
    private List<String> notes;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Degree getDegreeSought() {
        return degreeSought;
    }

    public void setDegreeSought(Degree degreeSought) {
        this.degreeSought = degreeSought;
    }

    public Term getTermBegan() {
        return termBegan;
    }

    public void setTermBegan(Term termBegan) {
        this.termBegan = termBegan;
    }

    public List<Professor> getAdvisors() {
        return advisors;
    }

    public void setAdvisors(List<Professor> advisors) {
        this.advisors = advisors;
    }

    public List<Professor> getCommittee() {
        return committee;
    }

    public void setCommittee(List<Professor> committee) {
        this.committee = committee;
    }

    public List<CourseTaken> getCoursesTaken() {
        return coursesTaken;
    }

    public void setCoursesTaken(List<CourseTaken> coursesTaken) {
        this.coursesTaken = coursesTaken;
    }

    public List<MilestoneSet> getMilestonesSet() {
        return milestonesSet;
    }

    public void setMilestonesSet(List<MilestoneSet> milestonesSet) {
        this.milestonesSet = milestonesSet;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((advisors == null) ? 0 : advisors.hashCode());
        result = prime * result
                + ((committee == null) ? 0 : committee.hashCode());
        result = prime * result
                + ((coursesTaken == null) ? 0 : coursesTaken.hashCode());
        result = prime * result
                + ((degreeSought == null) ? 0 : degreeSought.hashCode());
        result = prime * result
                + ((milestonesSet == null) ? 0 : milestonesSet.hashCode());
        result = prime * result
                + ((department == null) ? 0 : department.hashCode());
        result = prime * result + ((student == null) ? 0 : student.hashCode());
        result = prime * result
                + ((termBegan == null) ? 0 : termBegan.hashCode());
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
        StudentRecord other = (StudentRecord) obj;
        if (advisors == null) {
            if (other.advisors != null)
                return false;
        } else if (!advisors.equals(other.advisors))
            return false;
        if (committee == null) {
            if (other.committee != null)
                return false;
        } else if (!committee.equals(other.committee))
            return false;
        if (coursesTaken == null) {
            if (other.coursesTaken != null)
                return false;
        } else if (!coursesTaken.equals(other.coursesTaken))
            return false;
        if (degreeSought != other.degreeSought)
            return false;
        if (milestonesSet == null) {
            if (other.milestonesSet != null)
                return false;
        } else if (!milestonesSet.equals(other.milestonesSet))
            return false;
        if (department == null) {
            if (other.department != null)
                return false;
        } else if (!department.equals(other.department))
            return false;
        if (student == null) {
            if (other.student != null)
                return false;
        } else if (!student.equals(other.student))
            return false;
        if (termBegan == null) {
            if (other.termBegan != null)
                return false;
        } else if (!termBegan.equals(other.termBegan))
            return false;
        return true;
    }

}
