import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Driver implements ActionListener {

    private MetalLookAndFeel metal;
    private JPanel cards;
    private JFrame myframe;
    private JTextField empNumber;
    private JTextField CSVTextField;
    private JTextField employeesName;
    private JTextField employeesSalary;
    private JTextField employeesHours;
    private String CSVName;
    private ArrayList<Employees> employeesdata = new ArrayList<>();
    private final Font myFont = new Font("Arial", Font.PLAIN, 15);
    private final ArrayList<JPanel> employeepanel = new ArrayList<>();

    public static void main(String[] args) {
        Driver d = new Driver();
    }

    public Driver() {

        metal = new MetalLookAndFeel();
        metal.setCurrentTheme(new MetalTheme());
        try {
            UIManager.setLookAndFeel(metal);
        }
        catch(UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        //Initialize window
        myframe = new JFrame("Employees Data");
        myframe.setLocationRelativeTo(null);

        ImageIcon image = new ImageIcon("s_logo.png");
        myframe.setIconImage(image.getImage());
        myframe.setUndecorated(true);
        myframe.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());

        //First Panel (first page)
        JPanel cardpanel1 = new JPanel();
        cardpanel1.setBackground(Color.DARK_GRAY);

        //label and text field #1
        String card1Question = "How many employees are there?";
        JLabel label1 = new JLabel(card1Question);
        label1.setFont(myFont);
        label1.setForeground(Color.white);
        cardpanel1.add(label1);
        empNumber = new JTextField(2);
        empNumber.setText("0");

        cardpanel1.add(label1);
        cardpanel1.add(empNumber);

        //label and text field #2
        String card1Question2 = "What is your CSV name?";
        JLabel label2 = new JLabel(card1Question2);
        label2.setFont(myFont);
        label2.setForeground(Color.white);
        CSVTextField = new JTextField(10);
        cardpanel1.add(label2);
        cardpanel1.add(CSVTextField);

        //button
        JButton next;
        next = new JButton("Next");
        next.setActionCommand("NEXT");
        next.addActionListener(this);

        cardpanel1.add(next);
        cards.add(cardpanel1);

        Container pane = myframe.getContentPane();
        pane.add(cards, BorderLayout.CENTER);

        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myframe.setResizable(false);
        int width = 350;
        int height = 200;
        myframe.setSize(width, height);
        myframe.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent AE) {

        CardLayout cl = (CardLayout) (cards.getLayout());
        String cmd = AE.getActionCommand();

        //for button with action 'NEXT'
        if (cmd.equals("NEXT")) {
            String ua1 = empNumber.getText();
            int numberOfEmployees = 0;
            try {
                numberOfEmployees = Integer.parseInt(ua1);
                CSVName = CSVTextField.getText();
                String CheckCSVName;
                if  (CSVName.length() > 4) {
                    CheckCSVName = CSVName.substring(CSVName.length() - 4);
                    if (!CheckCSVName.equals(".csv")) {
                        throw new UserInputException("Wrong FileName! Please have a filename with .csv");

                    }
                    createEmployeePanel(numberOfEmployees);
                    cl.next(cards);
                } else {
                    // throw error
                    throw new UserInputException("Wrong FileName! Please have a filename with .csv");
                }

            } catch (final NumberFormatException e) {
                createErrorPanel("You need to input Integer for number of employees!");
                cl.last(cards);
            } catch (UserInputException e) {
                e.printStackTrace();
            }
            //for button with action 'NEXT PAGES'
        } else if (cmd.equals("NEXT PAGES")) {
            cl.next(cards);

            //for button with action 'CREATE CSV'
        } else if (cmd.equals("CREATE CSV")) {
            try {
                try {
                    employeesdata = submit(employeepanel);
                } catch (NullPointerException NU){
                    System.out.println("Employee is empty! No employee to be printed");
                }
                ReportWriter rw = new ReportWriter();
                rw.printSalaryReport(employeesdata, CSVName);
                cl.last(cards);
            } catch (final NumberFormatException e) {
                createErrorPanel("You need to input Double for Salary and Integer for Hours!");
                cl.last(cards);
            }

            //for button with action 'REDO'
        } else if (cmd.equals("REDO")){
            myframe.dispose();
            Driver d = new Driver();
        }
    }


    public void createEmployeePanel(int numberOfEmployees) {

        // create number of JPanel based on number of employees
        for (int i = 1; i <= numberOfEmployees; i++) {

            JPanel employeecardPanel = new JPanel();
            employeecardPanel.setBackground(Color.DARK_GRAY);

            //label and text field #1
            JLabel labelEmployees = new JLabel("Employees #" + i + " name");
            labelEmployees.setFont(myFont);
            labelEmployees.setForeground(Color.white);
            employeesName = new JTextField(12);

            employeecardPanel.add(labelEmployees);
            employeecardPanel.add(employeesName);

            //label and text field #2
            String card2Question2 = "How much salary per hour?";
            JLabel labelSalary = new JLabel(card2Question2);
            labelSalary.setFont(myFont);
            labelSalary.setForeground(Color.white);
            employeesSalary = new JTextField(3);

            employeecardPanel.add(labelSalary);
            employeecardPanel.add(employeesSalary);

            //label and text field #3
            String card2Question3 = "How much hours worked in a week?";
            JLabel labelHours = new JLabel(card2Question3);
            labelHours.setFont(myFont);
            labelHours.setForeground(Color.white);
            employeesHours = new JTextField(3);

            employeecardPanel.add(labelHours);
            employeecardPanel.add(employeesHours);

            //button
            JButton nextEmp;
            if (i != numberOfEmployees) {
                nextEmp = new JButton("Next Pages");
                nextEmp.setActionCommand("NEXT PAGES");
                nextEmp.addActionListener(this);
            } else {
                nextEmp = new JButton("Create CSV");
                nextEmp.setActionCommand("CREATE CSV");
                nextEmp.addActionListener(this);
            }
            employeecardPanel.add(nextEmp);

            employeepanel.add(employeecardPanel);
        }

        for (JPanel each : employeepanel) {
            cards.add(each);
        }

        // CREATE FINAL PANEL to show end of app
        JPanel finalPanel = new JPanel();
        JLabel finalLabel = new JLabel("End of Application. Please check your CSV.");
        finalLabel.setForeground(Color.white);
        finalPanel.add(finalLabel);
        finalPanel.setBackground(Color.DARK_GRAY);
        cards.add(finalPanel);

    }

    private void createErrorPanel(String message){
        JPanel errorPanel = new JPanel();
        JLabel errorLabel = new JLabel(message);
        errorLabel.setForeground(Color.white);
        errorPanel.add(errorLabel);
        errorPanel.setBackground(Color.DARK_GRAY);

        JButton Redo;
        Redo = new JButton("Redo");
        Redo.setActionCommand("REDO");
        Redo.addActionListener(this);
        errorPanel.add(Redo);
        cards.add(errorPanel);
    }


    public class MetalTheme extends DefaultMetalTheme {
        @Override
        public ColorUIResource getMenuBackground() {
            return new ColorUIResource(Color.ORANGE);
        }
        public ColorUIResource getWindowTitleBackground() {
            return new ColorUIResource(Color.ORANGE);
        }
    }

    private ArrayList<Employees> submit(ArrayList<JPanel> listofPanel) {
        ArrayList<Employees> employeeArray = new ArrayList<>();
        ArrayList<String> cont = new ArrayList<>();

        for (JPanel panel : listofPanel) {

            Component[] components = panel.getComponents();

            for (Component comp : components) {
                if (comp instanceof JTextField) {
                    JTextField textField = (JTextField) comp;
                    String text = textField.getText();
                    cont.add(text);
                }
            }
            employeeArray.add(new Employees(cont.get(0), Double.parseDouble(cont.get(1)),
                    Integer.parseInt(cont.get(2))));
            cont.clear();
        }
        return employeeArray;
    }
}
