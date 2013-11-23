package edu.umn.csci5801.model;

import java.util.ArrayList;
import java.util.List;

public class ProgressSummary {
    private Student student;
    private Department department;
    private Degree degreeSought;
    private Term termBegan;
    private List<Professor> advisors;
    private List<Professor> committee;
    private List<String> notes;
    private List<RequirementCheckResult> requirementCheckResults = new ArrayList<RequirementCheckResult>();

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

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public void addRequirementResult(
            RequirementCheckResult requirementCheckResult) {
        requirementCheckResults.add(requirementCheckResult);
    }

    public List<RequirementCheckResult> getRequirementCheckResults() {
        return requirementCheckResults;
    }

    public void setRequirementCheckResults(
            List<RequirementCheckResult> requirementCheckResults) {
        this.requirementCheckResults = requirementCheckResults;
    }

}
