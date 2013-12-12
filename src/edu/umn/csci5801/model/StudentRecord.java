package edu.umn.csci5801.model;

import java.util.ArrayList;
import java.util.List;

public class StudentRecord implements Cloneable{
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
    
    @Override
    public StudentRecord clone(){
        StudentRecord other = new StudentRecord();
        other.setStudent(new Student(new String(this.student.getFirstName()), new String(this.student.getLastName()), this.student.getId()));
        other.setDepartment(this.getDepartment());
        other.setDegreeSought(this.getDegreeSought());
        other.setTermBegan(new Term(this.getTermBegan().getSemester(), this.getTermBegan().getYear()));
        //advisor list
        List<Professor> advisors = new ArrayList<Professor>();
        for(Professor p : this.getAdvisors()){
            advisors.add(new Professor(new String(p.getFirstName()), new String(p.getLastName()), p.getDepartment()));
        }
        other.setAdvisors(advisors);
        //committee list
        List<Professor> committee = new ArrayList<Professor>();
        for(Professor p : this.getCommittee()){
            committee.add(new Professor(new String(p.getFirstName()), new String(p.getLastName()), p.getDepartment()));
        }
        other.setCommittee(committee);
        //CourseTaken list
        List<CourseTaken> courses = new ArrayList<CourseTaken>();
        for(CourseTaken c : this.getCoursesTaken()){
            Course actualClass = c.getCourse();
            courses.add(new CourseTaken(
               new Course(new String(actualClass.getName()), new String(actualClass.getId()), new String(actualClass.getNumCredits()), actualClass.getCourseArea()), 
               new Term(c.getTerm().getSemester(), c.getTerm().getYear()),
               c.getGrade()
                    ));
            
        }
        other.setCoursesTaken(courses);
        //MilestoneSet list
        List<MilestoneSet> milestones = new ArrayList<MilestoneSet>();
        for(MilestoneSet m : this.getMilestonesSet()){
            milestones.add(new MilestoneSet(m.getMilestone(), new Term(m.getTerm().getSemester(), m.getTerm().getYear())));
        }
        other.setMilestonesSet(milestones);
        //Notes list
        List<String> notes = new ArrayList<String>();
            for(String n : this.getNotes()){
                if(n!=null){
                    notes.add(new String(n));
                }
            }
        other.setNotes(notes);
        
        return other;
    }
    
    public String toString(){
        StudentRecord sr = this;
        StringBuilder sb = new StringBuilder();
        sb.append("Student Record of: ");
        sb.append(sr.getStudent().getFirstName()+" "+sr.getStudent().getLastName());
        sb.append("\n\n");
        sb.append("Department: "+sr.getDepartment().name());
        sb.append("\n");
        sb.append("Degree Sought: "+sr.getDegreeSought().name());
        sb.append("\n");
        sb.append("Enrolled: "+sr.getTermBegan().getSemester().name() + " "+sr.getTermBegan().getYear());
        sb.append("\n");
        sb.append("Advisors:");
        for(Professor p : sr.getAdvisors()){
            sb.append(" "); sb.append(p.getFirstName()); sb.append(" "); sb.append(p.getLastName()); sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1); sb.append("\n");
        sb.append("Committee:");
        for(Professor p : sr.getCommittee()){
            sb.append(" "); sb.append(p.getFirstName()); sb.append(" "); sb.append(p.getLastName()); sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1); sb.append("\n");
        sb.append("Courses:");
        for(CourseTaken c : sr.getCoursesTaken()){
            sb.append(" "); sb.append(c.getCourse().getId());
        }
        sb.append("\n");
        sb.append("Milestones:");
        for(MilestoneSet ms : sr.getMilestonesSet()){
            sb.append(" "); sb.append(ms.getMilestone().name());
        }
        sb.append("\n");
        sb.append("Notes:\n");
        for(String n : sr.getNotes()){
            sb.append("  "); sb.append(n); sb.append("\n");
        }
        sb.append("------ END ------");
        
        return sb.toString();
    }

}
