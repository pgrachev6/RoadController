import core.*;
import core.Camera;

public class RoadController // Class
{
    //Class variable Double
    public static Double passengerCarMaxWeight = 3500.0; // kg
    //Class variable Integer
    public static Integer passengerCarMaxHeight = 2000; // mm
    //Class variable Integer
    public static Integer controllerMaxHeight = 3500; // mm
    //Class variable Integer
    public static Integer passengerCarPrice = 100; // RUB
    //Class variable Integer
    public static Integer cargoCarPrice = 250; // RUB
    //Class variable Integer
    public static Integer vehicleAdditionalPrice = 200; // RUB
    //Class variable Integer
    public static Integer maxOncomingSpeed = 59; // km/h
    //Class variable Integer
    public static Integer speedFineGrade = 20; // km/h
    //Class variable Integer
    public static Integer finePerGrade = 500; // RUB
    //Class variable Integer
    public static Integer criminalSpeed = 160; // km/h

    public static void main(String[] args)
    {
        for(Integer i = 0; i < 10; i++)
        {
            Car car = Camera.getNextCar();
            System.out.println(car);
            System.out.println("Скорость: " + Camera.getCarSpeed(car) + " км/ч");

            /**
             * Пропускаем автомобили спецтранспорта
             */
            if(car.isSpecial()) {
                openWay();
                continue; //
            }

            /**
             * Проверка на наличие номера в списке номеров нарушителей
             */
            //Local variable Boolean
            Boolean policeCalled = false;
            for(String criminalNumber : Police.getCriminalNumbers())
            {
                //Local variable boolean
                String carNumber = car.getNumber();
                if(carNumber.equals(criminalNumber)) {
                    Police.call("автомобиль нарушителя с номером " + carNumber);
                    blockWay("не двигайтесь с места! За вами уже выехали!");
                    break;
                }
            }
            if(Police.wasCalled()) {
                continue;
            }

            /**
             * Проверяем высоту и массу автомобиля, вычисляем стоимость проезда
             */
            //Local variable Integer
            Integer carHeight = car.getHeight();
            //Local variable Integer
            Integer price = 0;
            if(carHeight > controllerMaxHeight)
            {
                blockWay("высота вашего ТС превышает высоту пропускного пункта!");
                continue;
            }
            else if(carHeight > passengerCarMaxHeight)
            {
                //Local variable Double
                Double weight = WeightMeter.getWeight(car);
                //Грузовой автомобиль
                if(weight > passengerCarMaxWeight)
                {
                    price = passengerCarPrice;
                    if(car.hasVehicle()) {
                        price = price + vehicleAdditionalPrice;
                    }
                }
                //Легковой автомобиль
                else {
                    price = cargoCarPrice;
                }
            }
            else {
                price = passengerCarPrice;
            }

            /**
             * Проверка скорости подъезда и выставление штрафа
             */
            //Local variable Integer
            Integer carSpeed = Camera.getCarSpeed(car);
            if(carSpeed > criminalSpeed)
            {
                Police.call("cкорость автомобиля - " + carSpeed + " км/ч, номер - " + car.getNumber());
                blockWay("вы значительно превысили скорость. Ожидайте полицию!");
                continue;
            }
            else if(carSpeed > maxOncomingSpeed)
            {
                //Local variable Integer
                Integer overSpeed = carSpeed - maxOncomingSpeed;
                //Local variable Integer
                Integer totalFine = finePerGrade * (1 + overSpeed / speedFineGrade);
                System.out.println("Вы превысили скорость! Штраф: " + totalFine + " руб.");
                price = price + totalFine;
            }

            /**
             * Отображение суммы к оплате
             */
            System.out.println("Общая сумма к оплате: " + price + " руб.");
        }

    }

    /**
     * Открытие шлагбаума
     */
    public static void openWay() {
        System.out.println("Шлагбаум открывается... Счастливого пути!");
    }

    public static void blockWay(String reason) {
        System.out.println("Проезд невозможен: " + reason);
    }
}