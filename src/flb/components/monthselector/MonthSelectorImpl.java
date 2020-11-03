package flb.components.monthselector;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ItemListener;

import flb.components.monthselector.listeners.UserSelectsSpecificMonth;
import flb.components.monthselector.listeners.UserSelectsSpecificYear;
import flb.components.monthselector.listeners.UserSpinsMonth;
import flb.components.editors.MonthChangeListener;
import flb.util.*;

public class MonthSelectorImpl implements MonthChangeListener {
    private final JPanel datePane;
    private final MonthSelectorModelImpl monthModel;
    private final JButton prev;
    private final JButton next;
    private enum Months {January, February, March, April, May, June, July, August, September, October, November, December}
    private final JComboBox<Months> month;
    private final JFormattedTextField year;
    private final ItemListener userSelectsSpecificMonth;

    public MonthSelectorImpl() {
        this.datePane = new JPanel();
        this.prev = new JButton("Prev");
        this.month = new JComboBox<>(Months.values());
        this.year = new JFormattedTextField();
        this.next = new JButton("Next");
        this.monthModel = new MonthSelectorModelImpl();
        this.userSelectsSpecificMonth = new UserSelectsSpecificMonth(monthModel);

        prev.setActionCommand("decrement");
        prev.addActionListener(new UserSpinsMonth(monthModel));

        next.setActionCommand("increment");
        next.addActionListener(new UserSpinsMonth(monthModel));

        month.addItemListener(userSelectsSpecificMonth);
        year.addActionListener(new UserSelectsSpecificYear(monthModel));

        addMonthChangeListener(this);

        layout();
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

    public void addMonthChangeListener(MonthChangeListener monthChangeListener) {
        monthModel.addMonthChangeListener(monthChangeListener);
    }

    @Override
    public void update(WhichMonth selectedDate) {
        year.setText(Integer.toString(monthModel.getYear()));
        month.removeItemListener(userSelectsSpecificMonth);
        month.setSelectedIndex(monthModel.getMonth());
        month.addItemListener(userSelectsSpecificMonth);
    }

    public JPanel getPane() {
        return datePane;
    }

    public void setToCurrentMonth() {
        monthModel.setToCurrentMonth();
    }

    public WhichMonth getSelectedMonth() {
        return monthModel.getSelectedMonth();
    }

    public void setMonth(int monthValue) {
        monthModel.setMonth(monthValue);
    }

    public void setYear(int yearValue) {
        monthModel.setYear(yearValue);
    }
}

