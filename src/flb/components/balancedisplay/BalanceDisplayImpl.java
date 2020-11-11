package flb.components.balancedisplay;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.NumberFormat;

import flb.components.editors.MonthChangeListener;
import flb.components.editors.StoreChangeListener;
import flb.datastores.*;
import flb.util.WhichMonth;

public class BalanceDisplayImpl implements MonthChangeListener, StoreChangeListener {
    private final JFormattedTextField balance;
    private final JPanel panel;
    private final CategoryStore categoryStore;

    public BalanceDisplayImpl(CategoryStore categoryStore){
        this.categoryStore = categoryStore;
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
    public void update(WhichMonth selectedDate) {
        balance.setValue(categoryStore.getBalance(selectedDate));
    }

    @Override
    public void updateAndKeepSelection(WhichMonth selectedDate) {
        balance.setValue(categoryStore.getBalance(selectedDate));
    }
}
