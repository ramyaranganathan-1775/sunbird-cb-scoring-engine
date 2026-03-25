

package org.sunbird.scoringengine.schema.model;


public class Range {


    private String name;
    private Integer min;
    private Integer max;

    private Integer assignedValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getAssignedValue() {
        return assignedValue;
    }

    public void setAssignedValue(Integer assignedValue) {
        this.assignedValue = assignedValue;
    }
}
