import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.sql.Connection;
import java.sql.*;

public class Employee {
    private JPanel Main;
    private JLabel Employee;
    private JLabel employeeName;
    private JLabel salary;
    private JLabel mobile;
    private JTextField txtEmployee;
    private JTextField txtSalary;
    private JTextField txtMobile;
    private JButton btnSave;
    private JTable table1;
    private JButton btndelete;
    private JButton btnupdate;
    private JButton btnsearch;
    private JTextField txtsearch;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee");
        frame.setContentPane(new Employee().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/dbyourcompany", "root","");
            System.out.println("Successs");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }



    void table_load(){
        try
        {
            pst = con.prepareStatement("select * from employee");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public Employee() {
        connect();
        table_load();
    btnSave.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            String empname, salary, mobile;

            empname = txtEmployee.getText();
            salary = txtSalary.getText();
            mobile = txtMobile.getText();

            try {
                pst = con.prepareStatement("insert into employee(empname,salary,mobile)values(?,?,?)");
                pst.setString(1, empname);
                pst.setString(2, salary);
                pst.setString(3, mobile);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record Added!");
                table_load();
                txtEmployee.setText("");
                txtSalary.setText("");
                txtMobile.setText("");
                txtEmployee.requestFocus();
            }

            catch (SQLException e1)
            {

                e1.printStackTrace();
            }

        }
    });


        btnsearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String empid = txtsearch.getText();

                    pst = con.prepareStatement("select empname,salary,mobile from employee where id = ?");
                    pst.setString(1, empid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String empname = rs.getString(1);
                        String emsalary = rs.getString(2);
                        String emmobile = rs.getString(3);

                        txtEmployee.setText(empname);
                        txtSalary.setText(emsalary);
                        txtMobile.setText(emmobile);

                    }
                    else
                    {
                        txtEmployee.setText("");
                        txtSalary.setText("");
                        txtMobile.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Employee");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        btnupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String empid, empname, salary, mobile;

                empname = txtEmployee.getText();
                salary = txtSalary.getText();
                mobile = txtMobile.getText();
                empid = txtsearch.getText();

                try {
                    pst = con.prepareStatement("update employee set empname = ?,salary = ?,mobile = ? where id = ?");
                    pst.setString(1, empname);
                    pst.setString(2, salary);
                    pst.setString(3, mobile);
                    pst.setString(4, empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updateee!!!!!");
                    table_load();
                    txtEmployee.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtEmployee.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        btndelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String empid;
                empid = txtsearch.getText();

                try {
                    pst = con.prepareStatement("delete from employee  where id = ?");

                    pst.setString(1, empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Delete!");
                    table_load();
                    txtEmployee.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtEmployee.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }

            }
        });
    }
}
