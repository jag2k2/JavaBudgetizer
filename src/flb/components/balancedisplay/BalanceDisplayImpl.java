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
    private final CategoryStore categoryStore;
    private final MonthDisplay monthDisplay;

    public BalanceDisplayImpl(TransactionStore transactionStore, CategoryStore categoryStore, GoalStore goalStore, MonthDisplay monthDisplay){
        this.categoryStore = categoryStore;
        this.monthDisplay = monthDisplay;
        this.balance = new JFormattedTextField(NumberFormat.getCurrencyInstance());

        transactionStore.addStoreChangeObserver(this);
        categoryStore.addStoreChangeObserver(this);
        goalStore.addStoreChangeObserver(this);
        monthDisplay.addViewChangeObserver(this);

        layout2();
    }

    protected void layout2() {
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
        float balanceAmount = categoryStore.getBalance(monthDisplay.getMonth());
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
        balance.setValue(categoryStore.getBalance(monthDisplay.getMonth()));
    }
}
