package com.isprogramming;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskAndSubTasks extends JFrame{
    private JPanel panel1;
    private JTextField tStartDate;
    private JTextField tDueDate;
    private JTextField taskName;
    private JSpinner taskDays;
    private JButton addTaskButton;
    private JTable supTaskTable;
    private JButton addSupTaskButton;
    private JButton removeSupTaskButton;
    DefaultTableModel tableModel = new DefaultTableModel();
    int p_id=AddProject.p_id;
    int task_id=0;
    TaskAndSubTasks(){
        setTitle("Tasks And SubTasks");
        setSize(800, 600);
        add(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addSupTaskButton.setEnabled(false);
        removeSupTaskButton.setEnabled(false);
        supTaskTable.setModel(tableModel);
        tableModel.addColumn("Name");
        tableModel.addColumn("Days");
        tableModel.addColumn("startDate");
        tableModel.addColumn("dueDate");
       /* UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

        frame.add(datePicker);*/
        addSupTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tableModel.insertRow(tableModel.getRowCount(), new Object[]{"t", 0, "1/1/2000", "1/1/2000"});
            }
        });
        removeSupTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int[] rows = supTaskTable.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    tableModel.removeRow(rows[i] - i);
                }
            }
        });
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/isproject?characterEncoding=latin1", "root", "12345678");
                    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(tStartDate.getText());
                    Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(tDueDate.getText());
                    java.sql.Date startDate = new java.sql.Date(date1.getTime());
                    java.sql.Date endDate = new java.sql.Date(date2.getTime());
                    String query = " insert into project (t_name,numOfHours,startDate,dueDate,p_id)"
                            + " values (?,?,?,?,?)";
                    PreparedStatement preparedStmt = con.prepareStatement(query);
                    preparedStmt.setString(1, taskName.getText());
                    int hours=(Integer)taskDays.getValue()*24;
                    preparedStmt.setInt(2, hours);
                    preparedStmt.setDate(3, startDate);
                    preparedStmt.setDate(4, endDate);
                    preparedStmt.setInt(5, p_id);
                    preparedStmt.executeUpdate();
                    
                    con.close();
                    addSupTaskButton.setEnabled(true);
                    removeSupTaskButton.setEnabled(true);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
    }
}
