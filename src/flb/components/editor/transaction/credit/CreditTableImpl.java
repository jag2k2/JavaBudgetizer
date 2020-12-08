package flb.components.editor.transaction.credit;

import flb.components.editor.SimpleDollarRenderer;
import flb.components.editor.summary.SummarySelector;
import flb.components.editor.transaction.*;
import flb.listeners.*;
import flb.components.menus.MenuDisplayer;
import flb.tuples.*;
import flb.util.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import javax.swing.*;

public class CreditTableImpl implements CreditTable, CreditTableTester, StatusDisplayer {
    private final CreditTableModelImpl tableModel;
    private final HighlightingRowTable table;
    private final JPanel panel;
    private final JTextField statusBar;

    public CreditTableImpl(MenuDisplayer categorizeMenu, MenuDisplayer payGroupMenu, SummarySelector summarySelector) {
        this.tableModel = new CreditTableModelImpl();
        this.table = new HighlightingRowTable(tableModel, 1, summarySelector);
        this.panel = new JPanel(new BorderLayout());
        this.statusBar = new JTextField();

        SimpleDollarRenderer dollarRenderer = new SimpleDollarRenderer();
        dollarRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(1).setCellRenderer(dollarRenderer);
        table.add(categorizeMenu.getPopup());
        table.add(payGroupMenu.getPopup());
        List<Integer> validCategorizeColumns = new ArrayList<>(Collections.singletonList(2));
        table.addMouseListener(new UserRightClicksTableListener(categorizeMenu, validCategorizeColumns));
        List<Integer> validGroupingColumns = new ArrayList<>(Arrays.asList(0,1,3,4));
        table.addMouseListener(new UserRightClicksTableListener(payGroupMenu, validGroupingColumns));
        table.getSelectionModel().addListSelectionListener(new UserSelectsTransactionsListener(this));

        layout();
    }

    protected void layout(){
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setMinWidth(75);
        table.getColumnModel().getColumn(0).setMaxWidth(75);
        table.getColumnModel().getColumn(1).setMinWidth(75);
        table.getColumnModel().getColumn(1).setMaxWidth(75);
        table.getColumnModel().getColumn(2).setMinWidth(170);
        table.getColumnModel().getColumn(2).setMaxWidth(170);
        table.getColumnModel().getColumn(3).setMinWidth(120);
        table.getColumnModel().getColumn(4).setMinWidth(170);
        table.getColumnModel().getColumn(4).setMaxWidth(170);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        statusBar.setHorizontalAlignment(SwingConstants.RIGHT);
        statusBar.setBackground(new Color(214, 217, 223));

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(statusBar, BorderLayout.SOUTH);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void display(Transactions<CreditTransaction> tableContents){
        tableModel.updateTransactions(tableContents);
    }

    @Override
    public void displayAndKeepSelection(Transactions<CreditTransaction> tableContents){
        int[] selectedRows = table.getSelectedRows();
        tableModel.updateTransactions(tableContents);
        setSelectedRows(selectedRows);
    }

    @Override
    public void displaySelectionStatus() {
        String status = "Count: " + table.getSelectedRows().length + "     Sum: " + String.format("%.2f", getSelectedSum());
        statusBar.setText(status);
    }

    @Override
    public float getSelectedSum(){
        float sum = Float.NaN;
        for (int selectedRow : table.getSelectedRows()) {
            for (Transaction transaction : tableModel.getTransaction(selectedRow)){
                if (Float.isNaN(sum)){
                    sum = transaction.getAmount();
                }
                else {
                    sum += transaction.getAmount();
                }

            }
        }
        return sum;
    }

    @Override
    public Maybe<CreditTransaction> getTransaction(int row){
        return tableModel.getTransaction(row);
    }

    @Override
    public Transactions<CreditTransaction> getTransactions() {
        return tableModel.getTransactions();
    }

    @Override
    public Transactions<CreditTransaction> getSelectedTransactions(){
        Transactions<CreditTransaction> selectedTransactions = new TransactionsImpl<>();
        for (int row : table.getSelectedRows()){
            for (CreditTransaction transaction : tableModel.getTransaction(row)){
                selectedTransactions.add(transaction);
            }
        }
        return selectedTransactions;
    }

    @Override
    public void setSelectedRows(int[] selectedRows) {
        for (int row : selectedRows){
            table.getSelectionModel().addSelectionInterval(row, row);
        }
    }

    @Override
    public int[] getSelectedRows() {
        return table.getSelectedRows();
    }

    @Override
    public String getStatusText() {
        return statusBar.getText();
    }
}