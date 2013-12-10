package edu.umn.csci5801.model;

public class Professor extends Person {

    private Department department;

    public Professor() {
        super();
    }

    public Professor(String firstName, String lastName, Department department) {
        super(firstName, lastName);
        this.department = department;
    }

    public Professor(String firstName, String lastName) {
        super(firstName, lastName);
        this.department = Department.COMPUTER_SCIENCE;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((department == null) ? 0 : department.hashCode());
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
        Professor other = (Professor) obj;
        if (department != other.department || !super.equals(other))
            return false;
        return true;
    }

}
