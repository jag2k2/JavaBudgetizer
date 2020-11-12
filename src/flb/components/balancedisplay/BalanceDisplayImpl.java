package flb.components.balancedisplay;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.NumberFormat;
import flb.components.editors.*;
import flb.components.monthselector.*;
import flb.datastores.*;

public class BalanceDisplayImpl implements MonthChangeObserver, StoreChangeObserver {
    private final JFormattedTextField balance;
    private final JPanel panel;
    private final CategoryStore categoryStore;
    private final SelectedMonthGetter selectedMonthGetter;

    public BalanceDisplayImpl(CategoryStore categoryStore, SelectedMonthGetter selectedMonthGetter){
        this.categoryStore = categoryStore;
        this.selectedMonthGetter = selectedMonthGetter;
        NumberFormat balanceFormat = NumberFormat.getCurrencyInstance();
        this.balance = new JFormattedTextField(balanceFormat);
        this.panel = new JPanel();

        layout();
    }

    protected void layout() {
        JLabel balanceLabel = new JLabel("Balance");
        Border blackBorder = new LineBorder(Color.BLACK);
        Border margin = BorderFactory.createEmptyBorder(5,5,5,5);
        panel.setBorder(new CompoundBorder(blackBorder, margin));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(balanceLabel);
        balance.setPreferredSize(new Dimension(100,28));
        balance.setEditable(false);
        balance.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(balance);
    }

    public JPanel getPane() {
        return panel;
    }

    @Override
    public void update() {
        float balanceAmount = categoryStore.getBalance(selectedMonthGetter.getSelectedMonth());
        balance.setValue(balanceAmount);
        if (balanceAmount > 0) {
            balance.setBackground(new Color(207, 255, 207));
        }
        else {
            balance.setBackground(null);
        }
    }

    @Override
    public void updateAndKeepSelection() {
        balance.setValue(categoryStore.getBalance(selectedMonthGetter.getSelectedMonth()));
    }
}
