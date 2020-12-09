package flb.components.balancedisplay;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.NumberFormat;
import flb.components.editor.*;
import flb.components.monthselector.*;
import flb.datastores.*;

public class BalanceDisplayImpl extends JComponent implements ViewChangeObserver, StoreChangeObserver {
    private final JFormattedTextField balance;
    private final BalanceStore balanceStore;
    private final MonthDisplay monthDisplay;

    public BalanceDisplayImpl(BalanceStore balanceStore, MonthDisplay monthDisplay){
        this.balanceStore = balanceStore;
        this.monthDisplay = monthDisplay;
        this.balance = new JFormattedTextField(NumberFormat.getCurrencyInstance());

        balanceStore.addStoreChangeObserver(this);
        monthDisplay.addViewChangeObserver(this);

        JLabel balanceLabel = new JLabel("Balance");
        Border blackBorder = new LineBorder(Color.BLACK);
        Border margin = BorderFactory.createEmptyBorder(5,5,5,5);
        this.setBorder(new CompoundBorder(blackBorder, margin));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(balanceLabel);
        balance.setPreferredSize(new Dimension(100,28));
        balance.setEditable(false);
        balance.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(balance);
    }

    @Override
    public void update() {
        float balanceAmount = balanceStore.getBalance(monthDisplay.getMonth());
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
        balance.setValue(balanceStore.getBalance(monthDisplay.getMonth()));
    }
}
