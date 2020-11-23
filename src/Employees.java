import java.time.LocalDate;

public class Employees extends Person {

    private double hourSalary;
    private int hoursWorked;

    public Employees(String name, LocalDate birth,
                     Gender gender, String email,
                     double hourSalary, int hoursWorked){
        super(name,birth,gender,email);
        this.hourSalary = hourSalary;
        this.hoursWorked = hoursWorked;
    }
    public Employees(String name,
                     double hourSalary, int hoursWorked){
        super(name);
        this.hourSalary = hourSalary;
        this.hoursWorked = hoursWorked;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public double getHourSalary() {
        return hourSalary;
    }

    public String print(){
        return this.name + " " + this.hourSalary + " " + this.hoursWorked;
    }
}
