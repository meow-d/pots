package user_interface.tables;

import java.util.ArrayList;
import java.util.HashMap;

import data.Item;
import data.Permission;
import data.PurchaseRequisition;
import data.Role;
import user_interface.MainMenu;

import user_interface.panels.PRForm;
import backend.Backend;

public class PRTable extends TablePanel<PurchaseRequisition> {
    protected MainMenu parent;
    protected PRForm PRPanel;
    private Role role;

    public PRTable(Backend backend, MainMenu parent) {
        super("PurchaseRequisition", 5, parent, backend.db.purchaseRequisitionsMap, new PurchaseRequisitionTableModel(),
                backend);
        this.backend = backend;
        this.parent = parent;
    }

    @Override
    public void createAddPanel() {
        role = backend.getCurrentAccount().getRole();
        PRPanel = parent.getPanel("AddPR", PRForm.class);

        if (role.hasPermission("PurchaseRequisition", Permission.CREATE)) {
            PRPanel.createPR();
        }
        parent.showPanel("AddPR");

    }

    @Override
    public void createEditPanel(int modelRow) {
        role = backend.getCurrentAccount().getRole();
        PRPanel = parent.getPanel("AddPR", PRForm.class);
        PRPanel.setRowNum(tableModel.getValueAt(modelRow, 6).toString());
        PRPanel.setData();

        PRPanel.viewOnly();

        if (role.hasPermission("PurchaseRequisition", Permission.UPDATE)) {
            PRPanel.viewOnlyUpdate();
        } else if (role == Role.PURCHASE_MANAGER) {
            PRPanel.viewApprove();
        }
        parent.showPanel("AddPR");
    }

    @Override
    public void refresh() {
        ArrayList<PurchaseRequisition> array = new ArrayList<>(items.values());
        tableModel.setItems(array);
        ((PurchaseRequisitionTableModel) tableModel).setStoreItems(backend.db.itemsMap);
    }
}

class PurchaseRequisitionTableModel extends TablePanelModel<PurchaseRequisition> {
    public HashMap<String, Item> itemsMap;

    void setStoreItems(HashMap<String, Item> items) {
        this.itemsMap = items;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    public Object getValueAt(int row, int column) {
        PurchaseRequisition purchaseRequisition = items.get(row);
        String itemName = itemsMap.get(purchaseRequisition.getItemId()).getItemName();
        switch (column) {
            case 0:
                return itemName;
            case 1:
                return purchaseRequisition.getQuantity();
            case 2:
                return simpleDateFormat.format(purchaseRequisition.getRequiredByDate());
            case 3:
                return purchaseRequisition.getSalesManagerId();
            case 4:
                return purchaseRequisition.getStatus();
            case 5:
                return "View";
            case 6:
                return purchaseRequisition.getId();
            default:
                return null;
        }
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Item";
            case 1:
                return "Quantity";
            case 2:
                return "Required By Date";
            case 3:
                return "Sales Manager";
            case 4:
                return "Status";
            case 5:
                return "View";
            default:
                return null;
        }
    }

    public boolean isCellEditable(int row, int column) {
        switch (column) {
            case 5:
                return true;
            default:
                return false;
        }
    }
}
