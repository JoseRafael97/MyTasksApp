package br.com.mytasks.validation;


import br.com.mytasks.exception.InvalidArgumentsException;

public class HoursValidator {


    public void hourValidator(int hour) throws InvalidArgumentsException {
        if (hour <= -1 || hour >= Integer.MAX_VALUE) {
            throw new InvalidArgumentsException("Invalid Hour.");
        }
    }
}