package flb.components;

import flb.components.listeners.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;

import flb.tables.interfaces.MonthChangeListener;
import flb.util.*;

public class MonthSelectorImp {
    private final JPanel datePane;
    private final WhichMonth dateModel;
    private final JButton prev;
    private final JButton next;
    private enum Months {January, February, March, April, May, June, July, August, September, October, November, December}
    private final JComboBox<Months> month;
    private final JFormattedTextField year;
    private final ArrayList<MonthChangeListener> monthChangeListeners;

    public MonthSelectorImp() {
        this.datePane = new JPanel();
        this.prev = new JButton("Prev");
        this.month = new JComboBox<>(Months.values());
        this.year = new JFormattedTextField();
        this.next = new JButton("Next");
        Calendar currentDate = new GregorianCalendar();
        this.dateModel = new WhichMonth(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH));
        this.monthChangeListeners = new ArrayList<>();

        prev.setActionCommand("decrement");
        prev.addActionListener(new UserSpinsMonth(this));

        next.setActionCommand("increment");
        next.addActionListener(new UserSpinsMonth(this));

        layout();
        refresh();
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

    protected void refresh(){
        year.setText(Integer.toString(dateModel.getYear()));
        month.setSelectedIndex(dateModel.getMonth());
        for(MonthChangeListener monthChangeListener : monthChangeListeners){
            monthChangeListener.refresh(getSelectedDate());
        }
    }

    public JPanel getPane() {
        return datePane;
    }

    public WhichMonth getSelectedDate() {
        int selectedYear = Integer.parseInt(year.getText());
        int selectedMonth = month.getSelectedIndex();
        return new WhichMonth(selectedYear, selectedMonth);
    }

    public void incrementMonth() {
        dateModel.incrementMonth();
        refresh();
    }

    public void decrementMonth() {
        dateModel.decrementMonth();
        refresh();
    }

    public void addMonthChangeListener(MonthChangeListener monthChangeListener) {
        monthChangeListeners.add(monthChangeListener);
    }
}

