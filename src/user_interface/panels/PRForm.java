package user_interface.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import backend.Backend;
import data.Item;
import data.PurchaseRequisition;
import data.Status;
import user_interface.*;

// TODO: Data added doesnt update in the dropdown
public class PRForm extends BasePanel<PurchaseRequisition> {
    // private boolean viewOnly;
    protected String rowData;
    private PurchaseRequisition PR;
    private Item item;
    protected ItemListReq ItemList;

    protected FieldDropdown<data.Item> dropItemID;
    protected FieldText fieldItemName, fieldItemStock, fieldItemMinStock, fieldRestockVal, fieldStatus;
    protected JLabel greenLabel;
    protected JPanel editcfm, approvecfm;
    protected JButton editConfirm, editCancel, confirmButton, deleteButton, editButton, approveButton, rejectButton;

    public PRForm(Backend backend, MainMenu parent) {
        super("Purchase Requisition", parent, backend.db.purchaseRequisitionsMap, backend);
        this.backend = backend;

        dropItemID = new FieldDropdown<data.Item>("Item Name", backend.db.itemsMap, new ItemListReq());
        dropItemID.fieldCombo.setSelectedIndex(-1);
        contentPanel.add(dropItemID);

        fieldItemStock = new FieldText("Stock", true);
        fieldItemStock.setEditable(false);
        contentPanel.add(fieldItemStock);

        fieldItemMinStock = new FieldText("Min Stock", true);
        fieldItemMinStock.setEditable(false);
        contentPanel.add(fieldItemMinStock);

        fieldRestockVal = new FieldText("Item Ordered", true);
        contentPanel.add(fieldRestockVal);

        fieldStatus = new FieldText("Status");
        fieldStatus.setEditable(false);
        contentPanel.add(fieldStatus);

        greenLabel = new JLabel("Item added");
        greenLabel.setVisible(false);
        greenLabel.setForeground(Color.GREEN);
        greenLabel.setFont(new Font(greenLabel.getText(), Font.BOLD, 20));
        contentPanel.add(greenLabel);

        backButton.addActionListener(e -> {
            parent.showPanel("PRTable");
            dropItemID.fieldCombo.setSelectedIndex(-1);
            fieldItemStock.setData("");
            fieldItemMinStock.setData("");
            fieldRestockVal.setData("");
        });

        confirmButton = new JButton("Confirm");
        titleButtonPanel.add(confirmButton, 1);

        deleteButton = new JButton("Delete");
        editButton = new JButton("Edit");
        titleButtonPanel.add(deleteButton);
        titleButtonPanel.add(editButton);

        editcfm = new JPanel(new GridLayout(1, 2));
        editConfirm = new JButton("Confirm");
        editCancel = new JButton("Cancel");
        editcfm.add(editConfirm);
        editcfm.add(editCancel);
        editcfm.setMaximumSize(new Dimension(300, 30));
        contentPanel.add(editcfm);

        approvecfm = new JPanel(new GridLayout(1, 2));
        approveButton = new JButton("Approve");
        rejectButton = new JButton("Reject");
        approvecfm.add(approveButton);
        approvecfm.add(rejectButton);
        approvecfm.setMaximumSize(new Dimension(300, 30));
        contentPanel.add(approvecfm);

        // auto pull data from itemsmap to fill up the details
        dropItemID.fieldCombo.addActionListener(e -> {

            data.Item item = (dropItemID.getSelected() != null)
                    ? (data.Item) dropItemID.getSelected().getValue()
                    : null;
            if (item != null) {
                fieldItemStock.setData(String.valueOf(item.getStockLevel()));
                fieldItemMinStock.setData(String.valueOf(item.getReorderLevel()));
            }
        });

        // cfm button logic
        confirmButton.addActionListener(e -> {
            try {
                String itemID = dropItemID.getSelected().getValue().getId();
                int RestockVal = fieldRestockVal.getIntData();

                if (!itemID.isEmpty() || RestockVal != 0) {
                    backend.db.addPurchaseRequisition(new PurchaseRequisition(itemID, RestockVal, new java.util.Date(),
                            backend.getCurrentAccount().getId(), Status.PENDING));
                    greenLabel.setVisible(true);

                    dropItemID.fieldCombo.setSelectedIndex(-1);
                    fieldItemStock.setData("");
                    fieldItemMinStock.setData("");
                    fieldRestockVal.setData("");

                    Timer timer = new Timer(2000, g -> greenLabel.setVisible(false));
                    timer.setRepeats(false);
                    timer.start();
                }

            } catch (Exception err) {
                System.out.println(err);
            }
        });

        // delete button logic
        deleteButton.addActionListener(e -> {
            backend.db.purchaseRequisitionsMap.remove(PR.getId());
            parent.showPanel("PRTable");
        });

        editButton.addActionListener(e -> {
            editcfm.setVisible(true);
            fieldRestockVal.setEditable(true);
        });

        editConfirm.addActionListener(e -> {
            editcfm.setVisible(false);

            try {
                int RestockVal = fieldRestockVal.getIntData();

                if (PR.getQuantity() != RestockVal) {
                    backend.db.purchaseRequisitionsMap.get(PR.getId()).setQuantity(RestockVal);
                }

                fieldRestockVal.setEditable(false);

            } catch (Exception err) {
                System.out.println(err);
                fieldRestockVal.setEditable(false);
                fieldRestockVal.setData(String.valueOf(PR.getQuantity()));
            }

        });

        editCancel.addActionListener(e -> {
            fieldRestockVal.setEditable(false);
            fieldRestockVal.setData(String.valueOf(PR.getQuantity()));

            editcfm.setVisible(false);
        });

        approveButton.addActionListener(e -> {
            PR.setStatus(Status.APPROVED);
            fieldStatus.setData(PR.getStatus().toString());

            approvecfm.setVisible(false);
        });

        rejectButton.addActionListener(e -> {
            PR.setStatus(Status.REJECTED);
            fieldStatus.setData(PR.getStatus().toString());

            approvecfm.setVisible(false);
        });
    }

    public void createPR() {
        dropItemID.AddUpdateItems();
        createHideOrShow(true);
        editorHideOrShow(false);
        editHideOrShow(false);
        approveHideOrSHow(false);

        fieldStatus.setVisible(false);
        dropItemID.setEditable(true);
        fieldRestockVal.setEditable(true);
    }

    void createHideOrShow(boolean bool) {
        confirmButton.setVisible(bool);
    }

    void editorHideOrShow(boolean bool) {
        editButton.setVisible(bool);
        deleteButton.setVisible(bool);
    }

    void editHideOrShow(boolean bool) {
        editcfm.setVisible(bool);
    }

    void approveHideOrSHow(boolean bool) {
        approvecfm.setVisible(bool);
    }

    public void viewOnly() {
        createHideOrShow(false);
        editorHideOrShow(false);
        editHideOrShow(false);
        approveHideOrSHow(false);

        dropItemID.setEditable(false);
        fieldRestockVal.setEditable(false);
        fieldStatus.setVisible(true);
    }

    public void viewOnlyUpdate() {
        editorHideOrShow(true);
    }

    public void viewApprove() {
        if (PR.getStatus() == Status.PENDING) {
            approveHideOrSHow(true);
        }

        fieldStatus.setVisible(true);
    }

    public void setData() {
        dropItemID.AddUpdateItems();
        PR = backend.db.getPurchaseRequisition(rowData);
        item = backend.db.getItem(PR.getItemId());

        dropItemID.setData(item.getId());
        fieldItemStock.setData(String.valueOf(item.getStockLevel()));
        fieldItemMinStock.setData(String.valueOf(item.getReorderLevel()));
        fieldRestockVal.setData(String.valueOf(PR.getQuantity()));
        fieldStatus.setData(PR.getStatus().toString());
    }

    public void setRowNum(String data) {
        this.rowData = data;
    }
}

class ItemListReq extends ComboList<data.Item> {
    @Override
    public void setItem(Map<String, data.Item> items) {
        this.items = items;
    }

    @Override
    public String getName(String UUID) {
        return this.items.get(UUID).getItemName();
    }

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public String toString() {
        return this.items.get(UUID).getId();
    }

    @Override
    public void setValue() {
        this.values = new data.Item[this.items.size()];
        initGetValue();
    }

    @Override
    public data.Item getObject(Object ItemName) {
        for (data.Item item : items.values()) {
            if (item.getItemName() == String.valueOf(ItemName)) {
                return item;
            }
        }
        return null;
    }
}
