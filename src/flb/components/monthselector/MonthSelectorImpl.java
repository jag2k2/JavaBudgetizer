package flb.components.monthselector;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.*;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import flb.components.*;
import flb.components.monthselector.listeners.*;
import flb.components.editor.*;
import flb.util.*;

public class MonthSelectorImpl extends JComponent implements ViewChangeNotifier, MonthDisplay, MonthSelector,
        ViewChangeObserver {
    private final MonthSelectorModelImpl monthModel;
    private final ArrayList<ViewChangeObserver> viewChangeObservers;
    private final JButton prev;
    private final JButton next;
    private enum Months {January, February, March, April, May, June, July, August, September, October, November, December}
    private final JComboBox<Months> month;
    private final JFormattedTextField yearField;
    private final ItemListener userSelectsSpecificMonth;

    public MonthSelectorImpl() {
        this.viewChangeObservers = new ArrayList<>();
        this.prev = new JButton("Prev");
        this.month = new JComboBox<>(Months.values());
        NumberFormat yearFormat = NumberFormat.getIntegerInstance();
        yearFormat.setGroupingUsed(false);
        yearFormat.setParseIntegerOnly(true);
        this.yearField = new JFormattedTextField(yearFormat);
        this.next = new JButton("Next");
        this.monthModel = new MonthSelectorModelImpl();
        this.userSelectsSpecificMonth = new UserSelectsSpecificMonth(this);

        arrange();
    }

    protected void arrange(){
        yearField.setPreferredSize(new Dimension(50,25));

        this.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(5,5,5,5)));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(prev);
        this.add(month);
        this.add(yearField);
        this.add(next);

        prev.setActionCommand("decrement");
        prev.addActionListener(new UserSpinsMonth(this));

        next.setActionCommand("increment");
        next.addActionListener(new UserSpinsMonth(this));

        month.addItemListener(userSelectsSpecificMonth);
        yearField.addActionListener(new UserSelectsSpecificYear(this));

        addViewChangeObserver(this);
    }

    @Override
    public void addViewChangeObserver(ViewChangeObserver viewChangeObserver) {
        viewChangeObservers.add(viewChangeObserver);
    }

    @Override
    public void notifyViewChange() {
        for (ViewChangeObserver viewChangeObserver : viewChangeObservers){
            viewChangeObserver.update();
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
    public void setToCurrentMonth() {
        monthModel.setToCurrentMonth();
        notifyViewChange();
    }

    @Override
    public WhichMonth getMonth() {
        return monthModel.getSelectedMonth();
    }

    @Override
    public void setMonth(int monthValue) {
        monthModel.setMonth(monthValue);
        notifyViewChange();
    }

    @Override
    public void setYear(int yearValue) {
        monthModel.setYear(yearValue);
        notifyViewChange();
    }

    public void incrementMonth() {
        monthModel.incrementMonth();
        notifyViewChange();
    }

    public void decrementMonth() {
        monthModel.decrementMonth();
        notifyViewChange();
    }
}

