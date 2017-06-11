package core;

import java.util.HashMap;

public class Camera
{
    public static HashMap<String, Integer> carsSpeed = new HashMap<>();

    public static Car getNextCar()
    {
        //Local variable Double
        String randomNumber = Double.toString(Math.random()).substring(2, 5);
        //Local variable
        Integer randomHeight = (int) (1000 + 3500. * Math.random());
        //Local variable Doubke
        Double randomWeight = 600 + 10000 * Math.random();
        Car car = new Car(randomNumber, randomHeight, randomWeight, Math.random() > 0.5);
        if(Math.random() < 0.15) {
            car.setIsSpecial();
        }
        Police.resetCalled();

        return car;
    }

    public static Integer getCarSpeed(Car car)
    {
        //Local variable String
        String carNumber = car.getNumber();
        if(!carsSpeed.containsKey(carNumber)) {
            carsSpeed.put(carNumber, (int) (180 * Math.random()));
        }
        return carsSpeed.get(carNumber);
    }
}