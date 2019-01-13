package main;

/**
 * Class to create and manage a car record.
 * @author Elena Roncolino
 */
public class Car {
    private String name, brand, fuelType;
    private int id, cubicCapacity, ps, kw, cylinders, userId;

    /**
     * Car constructor.
     * @param name The name of the car.
     * @param brand The name of the car's brand.
     * @param cubicCapacity The cubic capacity of the car.
     * @param ps The ps of the car.
     * @param kw The kw of the car.
     * @param cylinders The number of cylinders of the car.
     * @param fuelType The fuel type of the car.
     * @param userId The id of the user that created this record.
     */
    public Car(String name, String brand, int cubicCapacity, int ps, int kw, int cylinders, String fuelType, int userId){
        this.name = name;
        this.brand = brand;
        this.cubicCapacity = cubicCapacity;
        this.ps = ps;
        this.kw = kw;
        this.cylinders = cylinders;
        this.fuelType = fuelType;
        this.userId = userId;
    }

    /**
     * Car id getter.
     * @return int The car id.
     */
    public int getCarId(){
        return id;
    }

    /**
     * Car id setter.
     * @param carId The car id.
     */
    public void setCarId(int carId){
        id = carId;
    }

    /**
     * Car name getter.
     * @return String The car name.
     */
    public String getCarName(){
        return name;
    }

    /**
     * Car brand getter.
     * @return String The car brand.
     */
    public String getCarBrand(){
        return brand;
    }

    /**
     * Fuel type getter.
     * @return String The car fuel type.
     */
    public String getFuelType(){
        return fuelType;
    }

    /**
     * Cubic capacity getter.
     * @return int The car cubic capacity.
     */
    public int getCubicCapacity(){
        return cubicCapacity;
    }

    /**
     * Ps getter.
     * @return int The number of ps.
     */
    public int getPs(){
        return ps;
    }

    /**
     * KW getter.
     * @return int The number of KW.
     */
    public int getKw(){
        return kw;
    }

    /**
     * Cylinders getter.
     * @return int The number of cylinders.
     */
    public int getCylinders(){
        return cylinders;
    }

    /**
     * Method that gets the id of the car owner.
     * @return int The id if the car owner.
     */
    public int getCarOwnerId(){
        return userId;
    }
}
