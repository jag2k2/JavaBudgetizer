package flb.components;

import flb.components.listeners.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;
import flb.util.*;

public class MonthSelectorImp {
    private final JPanel datePane;
    private final JButton prev;
    private final JButton next;
    private enum Months {January, February, March, April, May, June, July, August, September, October, November, December}
    private final JComboBox<Months> month;
    private final JFormattedTextField year;

    public MonthSelectorImp() {
        this.datePane = new JPanel();
        this.prev = new JButton("Prev");
        this.month = new JComboBox<>(Months.values());
        this.year = new JFormattedTextField();
        this.next = new JButton("Next");

        //prev.addActionListener(new UserDecrementsMonth(this);

        layout();
        setToCurrentDate();
    }

    protected void layout(){
        year.setPreferredSize(new Dimension(50,25));

        datePane.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(5,5,5,5)));
        datePane.setLayout(new BoxLayout(datePane, BoxLayout.X_AXIS));
        datePane.add(prev);
        datePane.add(month);
        datePane.add(year);
        datePane.add(next);
    }

    protected void setToCurrentDate(){
        Calendar now = new GregorianCalendar();
        year.setText(Integer.toString(now.get(Calendar.YEAR)));
        month.setSelectedIndex(now.get(Calendar.MONTH));
    }

    public JPanel getPane() {
        return datePane;
    }

    public WhichMonth getSelectedDate() {
        int selectedYear = Integer.parseInt(year.getText());
        int selectedMonth = month.getSelectedIndex();
        return new WhichMonth(selectedYear, selectedMonth);}
}

