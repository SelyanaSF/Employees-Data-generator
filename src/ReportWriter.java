import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReportWriter {

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String headers = "Employee Name,Salary,Hours,Weekly Pay";


    public void printSalaryReport(ArrayList<Employees>list,
                                  String fileName) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName);
            fw.append(headers);

            //add line separator
            fw.append(NEW_LINE_SEPARATOR);

            // create employee data row
            for (Employees emp:list) {
                writeEmp(fw, emp);
            }

        } catch (Exception e){

            System.out.println("Error in CSVFileWriter");
            e.printStackTrace();

        } finally {

            try {
                fw.flush();
                fw.close();
            } catch (IOException e) {
                System.out.println("Error while closing fileWriter..");
                e.printStackTrace();
            } catch (NullPointerException e){
                System.out.println("Error while flushing fileWriter..");
            }
        }
    }
    private void writeEmp(FileWriter fw, Employees emp) throws IOException {
        fw.append(emp.getName());
        fw.append(COMMA_DELIMITER);

        fw.append(String.valueOf(emp.getHourSalary()));
        fw.append(COMMA_DELIMITER);

        fw.append(String.valueOf(emp.getHoursWorked()));
        fw.append(COMMA_DELIMITER);

        double weekPay = emp.getHourSalary()* emp.getHoursWorked();
        DecimalFormat df = new DecimalFormat("#.##");
        weekPay = Double.parseDouble(df.format(weekPay));
        fw.append(String.valueOf(weekPay));
        fw.append(NEW_LINE_SEPARATOR);
    }
}
