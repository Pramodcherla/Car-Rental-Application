import java.util.*;

public class CarRentalSystem {

    private List<Car> cars;

    private List<Customer> customers;

    private  List<Rental> rentals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

   public void rentCar(Car car, Customer customer, int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car, customer, days));

        }else{
            System.out.println("Car is not available for rent");
        }
   }

   public void returnCar(Car car){
        Rental rentalToRemove = null;
        for (Rental r : rentals) {
            if (r.getCar() == car) {
                rentalToRemove = r;
                break;
            }
        }
        if(rentalToRemove != null){
            rentals.remove(rentalToRemove);

            car.returnCar();
        }
        else {
            System.out.println("Car was not Rented");
        }
   }




    public void menu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("========== Welcome to Car Rental System ===========");
        System.out.println("1. Rent a Car");
        System.out.println("2. Return a Car");
        System.out.println("3. exit");
        System.out.println("Enter your choice : ");

        int chioce = sc.nextInt();

        while (true) {

            if (chioce == 1) {
                System.out.println("=== Rent a Car===");
                System.out.println("Enter your name : ");
                sc = new Scanner(System.in);
                String customerName = sc.nextLine();

                System.out.println("Available Cars");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                    }
                }
                System.out.print("Enter the car Id you want to rent : ");
                String carId  = sc.nextLine();

                System.out.print("Enter the number of days for Rental: ");
                int rentalDays = sc.nextInt();
                sc.nextLine();

                Customer newCustomer = new Customer("CUS"+(customers.size()+1),customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars){
                    if(car.getCarId().equals(carId) && car.isAvailable()){
                        selectedCar = car;
                        break;
                    }
                }

                if(selectedCar != null){
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("======== Rental information =========");
                    System.out.println("Customer ID : "+newCustomer.getCustomerId());
                    System.out.println("Customer Name : "+newCustomer.getName());
                    System.out.println("Car : "+selectedCar.getBrand()+ " "+ selectedCar.getModel());
                    System.out.println("Rental Days : "+rentalDays);
                    System.out.printf("Total Price : $%.2f%n",totalPrice);

                    System.out.println("conform rental(Y/N): ");
                    String conform = sc.nextLine();

                    if(conform.equalsIgnoreCase("Y")){
                        rentCar(selectedCar,newCustomer,rentalDays);
                        System.out.println("Car rented successfully");
                    }else{
                        System.out.println("Rental canceled");
                    }
                }else {
                    System.out.println("Invalid car selected or car not available for rent");
                }
                menu();
            } else if (chioce==2) {
                System.out.println("=== Return car ===");
                System.out.print("Enter the car Id you want to Return: ");
                sc = new Scanner(System.in);
                String carId = sc.nextLine();

                Car carToReturn = null;
                for(Car car : cars){
                    if(car.getCarId().equals(carId) && !car.isAvailable()){
                        carToReturn = car;
                        break;
                    }
                }

                if(carToReturn != null){
                    Customer customer = null;
                    for(Rental rental : rentals){
                        if(rental.getCar()==carToReturn){
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if(customer != null){
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by "+customer.getName());
                    }
                    else {
                        System.out.println("car was not rented or rental information is missing");
                    }
                }else {
                    System.out.println("Invalid car id or car is not rented");
                }
                menu();

            } else if (chioce == 3) {
                break;
            }
            else {
                System.out.println("Invalid choice ,   enter a valid choice");
            }

        }
        System.out.println("Thank You for using Car Rental System");
        System.exit(0);

    }

}
