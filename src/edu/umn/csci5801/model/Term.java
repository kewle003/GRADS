package edu.umn.csci5801.model;

public class Term {
    private Semester semester;
    private Integer year;

    public Term() {
    }

    public Term(Semester semester, Integer year) {
        this.semester = semester;
        this.year = year;
    }

    public Term getNext() {
        Semester nextSemester = this.semester.next();
        if (nextSemester != null) {
            return new Term(nextSemester, this.year);
        }
        return new Term(Semester.SPRING, this.year + 1);
    }

    public Term getNext(int numTerm) {
        if (numTerm <= 0) {
            throw new IllegalArgumentException(
                    "numTerm must be a positive number");
        }
        if (numTerm == 1) {
            return getNext();
        } else {
            return getNext().getNext(numTerm - 1);
        }
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((semester == null) ? 0 : semester.hashCode());
        result = prime * result + ((year == null) ? 0 : year.hashCode());
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
        Term other = (Term) obj;
        if (semester != other.semester)
            return false;
        if (year == null) {
            if (other.year != null)
                return false;
        } else if (!year.equals(other.year))
            return false;
        return true;
    }

}
