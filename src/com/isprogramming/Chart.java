package com.isprogramming;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class Chart extends JFrame{
    private JPanel panel1;
    private JPanel chartPanelGUI;
    int p_id = AddProject.p_id;
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    Chart() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/isproject?characterEncoding=latin1", "root", "12345678");
        stmt = con.createStatement();
        setTitle("Tasks And SubTasks");
        setSize(800, 600);
        add(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        IntervalCategoryDataset dataset = getCategoryDataset();
        final JFreeChart chart = ChartFactory.createGanttChart(
                "Chart",  // chart title
                "Tasks",              // domain axis label
                "Date",              // range axis label
                dataset,             // data
                true,                // include legend
                false,                // tooltips
                false                // urls
        );
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setDomainZoomable(true);
        chartPanelGUI.setLayout(new BorderLayout());
        chartPanelGUI.add(chartPanel, BorderLayout.NORTH);
    }
    private IntervalCategoryDataset getCategoryDataset() throws SQLException {
        TaskSeriesCollection dataset = new TaskSeriesCollection();
        rs=stmt.executeQuery("select t_name,startDate,dueDate from task left JOIN suptasks\n"+
                "ON task.t_id = suptasks.t_id\n"+
                "GROUP BY t_name;\n");
        TaskSeries series1 = new TaskSeries("Actual Date");
        while (rs.next()){
            LocalDate date = rs.getDate(2).toLocalDate();
            Instant i = date.atStartOfDay().toInstant(ZoneOffset.UTC);
            LocalDate datee = rs.getDate(3).toLocalDate();
            Instant ii = datee.atStartOfDay().toInstant(ZoneOffset.UTC);
            series1.add(new Task(rs.getString(1),
                    Date.from(i),
                    Date.from(ii)
            ));
        }
        /*series1.add(new Task("Requirement",
                Date.from(LocalDate.of(2017, 7, 3).atStartOfDay().toInstant(ZoneOffset.UTC)),
                Date.from(LocalDate.of(2017, 7, 7).atStartOfDay().toInstant(ZoneOffset.UTC))
        ));

        series1.add(new Task("Design",
                Date.from(LocalDate.of(2017, 7, 10).atStartOfDay().toInstant(ZoneOffset.UTC)),
                Date.from(LocalDate.of(2017, 7, 14).atStartOfDay().toInstant(ZoneOffset.UTC))
        ));

        series1.add(new Task("Coding",
                Date.from(LocalDate.of(2017, 7, 17).atStartOfDay().toInstant(ZoneOffset.UTC)),
                Date.from(LocalDate.of(2017, 7, 21).atStartOfDay().toInstant(ZoneOffset.UTC))
        ));

        series1.add(new Task("Testing",
                Date.from(LocalDate.of(2017, 7, 24).atStartOfDay().toInstant(ZoneOffset.UTC)),
                Date.from(LocalDate.of(2017, 7, 28).atStartOfDay().toInstant(ZoneOffset.UTC))
        ));

        series1.add(new Task("Deployment",
                Date.from(LocalDate.of(2017, 07, 31).atStartOfDay().toInstant(ZoneOffset.UTC)),
                Date.from(LocalDate.of(2017, 8, 4).atStartOfDay().toInstant(ZoneOffset.UTC))
        ));*/


        /*TaskSeries series2 = new TaskSeries("Actual Date");
        series2.add(new Task("Requirement",
                Date.from(LocalDate.of(2017, 7, 3).atStartOfDay().toInstant(ZoneOffset.UTC)),
                Date.from(LocalDate.of(2017, 7, 05).atStartOfDay().toInstant(ZoneOffset.UTC))
        ));

        series2.add(new Task("Design",
                Date.from(LocalDate.of(2017, 7, 6).atStartOfDay().toInstant(ZoneOffset.UTC)),
                Date.from(LocalDate.of(2017, 7, 17).atStartOfDay().toInstant(ZoneOffset.UTC))
        ));

        series2.add(new Task("Coding",
                Date.from(LocalDate.of(2017, 7, 18).atStartOfDay().toInstant(ZoneOffset.UTC)),
                Date.from(LocalDate.of(2017, 7, 27).atStartOfDay().toInstant(ZoneOffset.UTC))
        ));

        series2.add(new Task("Testing",
                Date.from(LocalDate.of(2017, 7, 28).atStartOfDay().toInstant(ZoneOffset.UTC)),
                Date.from(LocalDate.of(2017, 8, 1).atStartOfDay().toInstant(ZoneOffset.UTC))
        ));

        series2.add(new Task("Deployment",
                Date.from(LocalDate.of(2017, 8, 2).atStartOfDay().toInstant(ZoneOffset.UTC)),
                Date.from(LocalDate.of(2017, 8, 4).atStartOfDay().toInstant(ZoneOffset.UTC))
        ));*/


        dataset.add(series1);
//        dataset.add(series2);
        return dataset;
    }

}
