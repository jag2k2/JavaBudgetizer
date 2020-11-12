package flb.components.monthselector;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.*;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import flb.components.*;
import flb.components.monthselector.listeners.*;
import flb.components.editors.*;
import flb.util.*;

public class MonthSelectorImpl implements CustomComponent, MonthChanger, SelectedMonthGetter, SpecificMonthSetter,
        CurrentMonthSetter, MonthChangeObserver {
    private final JPanel datePane;
    private final MonthSelectorModelImpl monthModel;
    private final ArrayList<MonthChangeObserver> monthChangeObservers;
    private final JButton prev;
    private final JButton next;
    private enum Months {January, February, March, April, May, June, July, August, September, October, November, December}
    private final JComboBox<Months> month;
    private final JFormattedTextField yearField;
    private final ItemListener userSelectsSpecificMonth;

    public MonthSelectorImpl() {
        this.datePane = new JPanel();
        this.monthChangeObservers = new ArrayList<>();
        this.prev = new JButton("Prev");
        this.month = new JComboBox<>(Months.values());
        NumberFormat yearFormat = NumberFormat.getIntegerInstance();
        yearFormat.setGroupingUsed(false);
        yearFormat.setParseIntegerOnly(true);
        this.yearField = new JFormattedTextField(yearFormat);
        this.next = new JButton("Next");
        this.monthModel = new MonthSelectorModelImpl();
        this.userSelectsSpecificMonth = new UserSelectsSpecificMonth(this);

        layout();
    }

    protected void layout(){
        yearField.setPreferredSize(new Dimension(50,25));

        datePane.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(5,5,5,5)));
        datePane.setLayout(new BoxLayout(datePane, BoxLayout.X_AXIS));
        datePane.add(prev);
        datePane.add(month);
        datePane.add(yearField);
        datePane.add(next);

        prev.setActionCommand("decrement");
        prev.addActionListener(new UserSpinsMonth(this));

        next.setActionCommand("increment");
        next.addActionListener(new UserSpinsMonth(this));

        month.addItemListener(userSelectsSpecificMonth);
        yearField.addActionListener(new UserSelectsSpecificYear(this));

        addMonthChangeObserver(this);
    }

    @Override
    public void addMonthChangeObserver(MonthChangeObserver monthChangeObserver) {
        monthChangeObservers.add(monthChangeObserver);
    }

    @Override
    public void notifyMonthChange() {
        for (MonthChangeObserver monthChangeObserver : monthChangeObservers){
            monthChangeObserver.update();
        }
    }

    @Override
    public void update() {
        yearField.setText(Integer.toString(monthModel.getYear()));
        month.removeItemListener(userSelectsSpecificMonth);
        month.setSelectedIndex(monthModel.getMonth());
        month.addItemListener(userSelectsSpecificMonth);
    }

    @Override
    public JPanel getPanel() {
        return datePane;
    }

    @Override
    public void setToCurrentMonth() {
        monthModel.setToCurrentMonth();
        notifyMonthChange();
    }

    @Override
    public WhichMonth getSelectedMonth() {
        return monthModel.getSelectedMonth();
    }

    @Override
    public void setMonth(int monthValue) {
        monthModel.setMonth(monthValue);
        notifyMonthChange();
    }

    @Override
    public void setYear(int yearValue) {
        monthModel.setYear(yearValue);
        notifyMonthChange();
    }

    public void incrementMonth() {
        monthModel.incrementMonth();
        notifyMonthChange();
    }

    public void decrementMonth() {
        monthModel.decrementMonth();
        notifyMonthChange();
    }
}

