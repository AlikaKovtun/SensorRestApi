package ua.alika.springcourse.dto;

import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotNull;

public class MeasurementDTO {
    @Range(min = -100, max = 100, message = "Temperature should be between -100 and 100 characters")
    private double value;

    @NotNull
    private Boolean raining;

    @NotNull
    private SensorDTO sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
