package edu.umn.csci5801.model;

import java.util.ArrayList;
import java.util.List;

public class RequirementCheckResult {
    private String name;
    private boolean passed;
    private List<String> errorMsgs = null;
    private CheckResultDetails details;

    public void addErrorMsg(String errorMsg) {
        if (errorMsgs == null)
            errorMsgs = new ArrayList<String>();
        errorMsgs.add(errorMsg);
        setPassed(false);
    }

    public RequirementCheckResult(String name) {
        this.name = name;
        this.passed = true;
    }

    public RequirementCheckResult(String name, boolean passed) {
        this.name = name;
        this.passed = passed;
    }

    public RequirementCheckResult(String name, boolean passed,
            List<String> errorMsgs) {
        this.name = name;
        this.passed = passed;
        this.errorMsgs = errorMsgs;
    }

    public RequirementCheckResult(String name, boolean passed,
            CheckResultDetails details) {
        this.name = name;
        this.passed = passed;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public List<String> getErrorMsgs() {
        return errorMsgs;
    }

    public void setErrorMsgs(List<String> errorMsgs) {
        this.errorMsgs = errorMsgs;
    }

    public CheckResultDetails getDetails() {
        return details;
    }

    public void setDetails(CheckResultDetails details) {
        this.details = details;
    }

}
