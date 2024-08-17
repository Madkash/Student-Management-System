import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.sql.Connection;

public class smsGUI extends JFrame {

    private JLabel askUserQuestion;
    private JButton option1;
    private JButton option2;
    private JButton option3;
    private JButton option4;
    private JButton option5;
    private JTextField optionField;
    private JButton searchButton;
    private static String id;
    private static String name;
    private static int age;
    private static String grade;
    private int checkStudentID = 0;
    private JLabel student_id;
    private JLabel student_name;
    private JLabel student_age;
    private JLabel student_grade;
    Connection conn = sqlDatabase.getConnection();
    private int step = 0;
    public smsGUI() {
        super("Student Management System");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(850, 550);

        setLocationRelativeTo(null);

        setLayout((null));

        setResizable(false);
        addGuiComponents();

        revalidate();
        repaint();
    }

    private void addGuiComponents() {
        askUserQuestion = new JLabel("What would you like to do?");
        askUserQuestion.setBounds(125, 100, 600, 54);
        askUserQuestion.setFont(new Font("Dialog", Font.BOLD, 40));
        askUserQuestion.setHorizontalAlignment(SwingConstants.CENTER);
        add(askUserQuestion);

        option1 = new JButton("Add Student");
        option1.setBounds(125, 160, 550, 50);
        option1.setFont(new Font("Dialog", Font.PLAIN, 36));
        //option1.setHorizontalAlignment(SwingConstants.CENTER);
        option1.setFocusable(false);
        add(option1);
        option1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        option2 = new JButton("Update Student Information");
        option2.setBounds(125, 220, 550, 50);
        option2.setFont(new Font("Dialog", Font.PLAIN, 36));
        //option2.setHorizontalAlignment(SwingConstants.CENTER);
        option2.setFocusable(false);
        add(option2);
        option2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudentInformation();
            }
        });

        option3 = new JButton("Delete Student");
        option3.setBounds(125, 280, 550, 50);
        option3.setFont(new Font("Dialog", Font.PLAIN, 36));
        //option3.setHorizontalAlignment(SwingConstants.CENTER);
        option3.setFocusable(false);
        add(option3);
        option3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        option4 = new JButton("View Student Information");
        option4.setBounds(125, 340, 550, 50);
        option4.setFont(new Font("Dialog", Font.PLAIN, 36));
        //option4.setHorizontalAlignment(SwingConstants.CENTER);
        option4.setFocusable(false);
        add(option4);
        option4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewStudentInfo();
            }
        });

        option5 = new JButton("View All Students");
        option5.setBounds(125, 400, 550, 50);
        option5.setFont(new Font("Dialog", Font.PLAIN, 36));
        //option5.setHorizontalAlignment(SwingConstants.CENTER);
        option5.setFocusable(false);
        add(option5);
        option5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllStudents();
            }
        });

        revalidate();
        repaint();
    }

    public void textAndButton() {
        optionField = new JTextField();
        optionField.setBounds(15, 15, 735, 45);
        optionField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(optionField);

        searchButton = new JButton("search");
        searchButton.setFocusable(false);
        searchButton.setCursor((Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)));
        searchButton.setBounds(750, 15, 75, 45);
        searchButton.setFont(new Font("Dialog", Font.PLAIN, 12));
        add(searchButton);

        option1.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);
        option4.setVisible(false);
        option5.setVisible(false);

        revalidate();
        repaint();
    }
    private void addStudent() {
        textAndButton();
        askUserQuestion.setText("Enter ID: ");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = optionField.getText();

                if (userInput.replaceAll("\\s", "").length() <= 0 && step < 4) {
                    return;
                }

                optionField.setText(null);

                switch (step) {
                    case 0:
                        id = userInput;
                        askUserQuestion.setText("Enter Name:");
                        break;
                    case 1:
                        name = userInput;
                        askUserQuestion.setText("Enter Age:");
                        break;
                    case 2:
                        age = Integer.parseInt(userInput);
                        askUserQuestion.setText("Enter Grade:");
                        break;
                    case 3:
                        grade = userInput;
                        sms.addStudent(new Student(id, name, age, grade));
                        askUserQuestion.setText("Student Added!");
                        searchButton.setText("Exit");
                        optionField.setVisible(false);
                        break;
                    case 4:
                        step = -1; // reset for the next student
                        askUserQuestion.setText(null);
                        searchButton.setVisible(false);
                        addGuiComponents();
                       // askUserQuestion.setText();
                }
                step++;
            }
        });

    }

    private void updateStudentInformation() {
        textAndButton();
        askUserQuestion.setText("Enter Student ID: ");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = optionField.getText();

                if (userInput.replaceAll("\\s", "").length() <= 0 && (step != 1 && step != 4)) {
                    return;
                }

                if (checkStudentID != 1 && sms.checkID(userInput) == 1) {
                    checkStudentID = 1;
                }

                if (checkStudentID != 1) {
                    switch (step) {
                        case 0:
                            askUserQuestion.setText("Student ID not found");
                            searchButton.setText("Exit");
                            optionField.setVisible(false);
                            break;
                        case 1:
                            step = -1;
                            askUserQuestion.setText((null));
                            searchButton.setVisible(false);
                            addGuiComponents();
                    }
                }else{
                    optionField.setText(null);
                    switch (step) {
                        case 0:
                            id = userInput;
                            askUserQuestion.setText("Enter New Name:");
                            break;
                        case 1:
                            name = userInput;
                            askUserQuestion.setText("Enter New Age:");
                            break;
                        case 2:
                            age = Integer.parseInt(userInput);
                            askUserQuestion.setText("Enter New Grade:");
                            break;
                        case 3:
                            grade = userInput;
                            sms.updateStudentInformation(id, name, age, grade);
                            askUserQuestion.setBounds(50, 100, 750, 54);
                            askUserQuestion.setText("Successfully updated student info!");
                            searchButton.setText("Exit");
                            optionField.setVisible(false);
                            break;
                        case 4:
                            step = -1; // reset for the next student
                            checkStudentID = 0;
                            askUserQuestion.setText(null);
                            searchButton.setVisible(false);
                            addGuiComponents();
                    }
                }

                step++;
            }
        });
    }

    private void deleteStudent() {
        textAndButton();
        askUserQuestion.setText("Enter Student ID: ");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = optionField.getText();

                if (userInput.replaceAll("\\s", "").length() <= 0 && step != 1) {
                    return;
                }

                if (checkStudentID != 1 && sms.checkID(userInput) == 1) {
                    checkStudentID = 1;
                }
                if (checkStudentID == 1) {
                    switch (step) {
                        case 0:
                            sms.deleteStudent(userInput);
                            optionField.setText(null);
                            askUserQuestion.setText("Successfully deleted student!");
                            optionField.setVisible(false);
                            searchButton.setText("Exit");
                            break;
                        case 1:
                            searchButton.setVisible(false);
                            askUserQuestion.setText(null);
                            checkStudentID = 0;
                            addGuiComponents();
                            step = -1;
                    }
                }else{
                    switch (step) {
                        case 0:
                            askUserQuestion.setText("Student ID not found");
                            searchButton.setText("Exit");
                            optionField.setVisible(false);
                            break;
                        case 1:
                            step = -1;
                            askUserQuestion.setText((null));
                            searchButton.setVisible(false);
                            addGuiComponents();
                    }
                }step++;
            }
        });
    }

    private void viewStudentInfo() {
        textAndButton();
        askUserQuestion.setText("Enter Student ID: ");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = optionField.getText();

                if (userInput.replaceAll("\\s", "").length() <= 0 && step != 1) {
                    return;
                }

                if (checkStudentID != 1 && sms.checkID(userInput) == 1) {
                    checkStudentID = 1;
                }
                if (checkStudentID == 1) {
                    switch (step) {
                        case 0:
                            optionField.setVisible(false);
                            askUserQuestion.setText(null);

                            Student s1 = sms.viewStudentInformation(userInput);
                            student_id = new JLabel("ID: " + s1.getId());
                            student_id.setBounds(125, 160, 550, 50);
                            student_id.setFont(new Font("Dialog", Font.PLAIN, 36));
                            //student_id.setHorizontalAlignment(SwingConstants.CENTER);
                            add(student_id);
                            student_id.setVisible(true);

                            student_name  = new JLabel("Name: " + s1.getName());
                            student_name.setBounds(125, 220, 550, 50);
                            student_name.setFont(new Font("Dialog", Font.PLAIN, 36));
                            //option2.setHorizontalAlignment(SwingConstants.CENTER);
                            add(student_name);

                            student_age = new JLabel("Age: " + s1.getAge());
                            student_age.setBounds(125, 280, 550, 50);
                            student_age.setFont(new Font("Dialog", Font.PLAIN, 36));
                            //option3.setHorizontalAlignment(SwingConstants.CENTER);
                            add(student_age);

                            student_grade = new JLabel("Grade: " + s1.getGrade());
                            student_grade.setBounds(125, 340, 550, 50);
                            student_grade.setFont(new Font("Dialog", Font.PLAIN, 36));
                            //option4.setHorizontalAlignment(SwingConstants.CENTER);
                            add(student_grade);

                            searchButton.setText("Exit");
                            revalidate();
                            repaint();
                            break;
                        case 1:
                            searchButton.setVisible(false);
                            step = -1;
                            checkStudentID = 0;
                            askUserQuestion.setText(null);
                            student_name.setVisible(false);
                            student_id.setVisible(false);
                            student_grade.setVisible(false);
                            student_age.setVisible(false);
                            addGuiComponents();
                    }
                }else{
                    switch (step) {
                        case 0:
                            askUserQuestion.setText("Student ID not found");
                            searchButton.setText("Exit");
                            optionField.setVisible(false);
                            break;
                        case 1:
                            step = -1;
                            askUserQuestion.setText((null));
                            searchButton.setVisible(false);
                            addGuiComponents();
                    }
                }
                step++;
            }
        });
    }

    private void viewAllStudents() {
        textAndButton();
        searchButton.setText("Exit");
        optionField.setVisible(false);
        askUserQuestion.setText(null);

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Dialog", Font.PLAIN, 20));
        textArea.setEditable(false);

        List<Student> Students = sms.listStudents();

        for (Student s : Students) {
            textArea.append("ID: " + s.getId() + "\n");
            textArea.append("Name: " + s.getName() + "\n");
            textArea.append("Age: " + s.getAge() + "\n");
            textArea.append("Grade: " + s.getGrade() + "\n");
            textArea.append("\n-----------------------------\n\n");
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(50, 100, 750, 300);
        add(scrollPane);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.setVisible(false);
                searchButton.setVisible(false);
                addGuiComponents();
            }
        });

        revalidate();
        repaint();
    }

}
