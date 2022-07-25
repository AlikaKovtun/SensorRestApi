package ua.alika.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.alika.springcourse.dto.SensorDTO;
import ua.alika.springcourse.models.Sensor;
import ua.alika.springcourse.services.SensorService;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals((clazz));
    }

    @Override
    public void validate(Object o, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) o;
        if (sensorService.findByName(sensorDTO.getName()).isPresent()) {
            errors.rejectValue("name", "", "This name is already taken!");
        }
    }

    public void isSensorPresent(SensorDTO sensor, Errors errors) {
        if (sensorService.findByName(sensor.getName()).isEmpty()) {
            errors.rejectValue("sensor", "", "This sensor doesn't exist!");
        }
    }
}
