import java.time.LocalDate;
import java.time.chrono.IsoChronology;

public class Person {

    public enum Gender{
        MALE, FEMALE
    }
    protected String name;
    protected String emailAddress;
    protected Gender sex;
    protected LocalDate birthday;

    public Person(String name, LocalDate birth,
           Gender gender, String email) {
        this.name = name;
        this.birthday = birth;
        this.sex = gender;
        this.emailAddress = email;
    }
    public Person(String name){
        this.name = name;
        this.birthday = null;
        this.sex = null;
        this.emailAddress = null;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public String getEmailAddress(){
        return this.emailAddress;
    }

    public LocalDate getBirthday(){
        return birthday;
    }

    public Gender getSex(){
        return this.sex;
    }

    public int getAge(){
        return birthday.until(IsoChronology.
                INSTANCE.dateNow()).getYears();
    }


}
