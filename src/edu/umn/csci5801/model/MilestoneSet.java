package edu.umn.csci5801.model;

public class MilestoneSet {
    private Milestone milestone;
    private Term term;

    public MilestoneSet() {
    }

    public MilestoneSet(Milestone milestone, Term term) {
        this.milestone = milestone;
        this.term = term;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((milestone == null) ? 0 : milestone.hashCode());
        result = prime * result + ((term == null) ? 0 : term.hashCode());
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
        MilestoneSet other = (MilestoneSet) obj;
        if (milestone != other.milestone)
            return false;
        if (term == null) {
            if (other.term != null)
                return false;
        } else if (!term.equals(other.term))
            return false;
        return true;
    }

}
