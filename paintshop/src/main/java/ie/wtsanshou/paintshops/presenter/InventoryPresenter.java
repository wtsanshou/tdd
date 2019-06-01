package ie.wtsanshou.paintshops.presenter;

import ie.wtsanshou.paintshops.model.Inventory;

import java.util.List;

public interface InventoryPresenter {
    List<String> presentedInventory(Inventory inventory);
}
