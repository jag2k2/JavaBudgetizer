package flb.components.editors.tables;

public interface SummaryTableTester {
    void setSelectedRow(int row);
    void editCellAt(int row, int col);
    void setEditorGoal(float goalAmount);
}
