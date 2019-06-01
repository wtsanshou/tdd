package ie.wtsanshou.paintshops.service;

import ie.wtsanshou.paintshops.model.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaintShopServerImpl implements PaintShopServer {

    @Override
    public Inventory preparePaints(CustomerData customerData) {

        List<CustomerPreference> customerPreferences = customerData.getCustomerPreferences();
        customerPreferences.sort(Comparator.comparingInt((a) -> a.getPaints().size()));

        Map<Integer, PaintType> inventory = new HashMap<>();
        return findInventory(inventory, customerPreferences, 0)
                ? Inventory.builder().colorNum(customerData.getColor()).paints(inventory).build()
                : new Inventory.NoSolution();
    }

    private boolean findInventory(Map<Integer, PaintType> inventory, List<CustomerPreference> customerPreferences, int fromCustomer) {

        for (int i = fromCustomer; i < customerPreferences.size(); i++) {

            List<Paint> paints = customerPreferences.get(i).getPaints();
            int[] count = countCommonColorAndConflictColorOfTheCustomer(inventory, paints);

            if (allPaintsAreConflictWithInventory(paints, count[0]))
                return false;
            if (nonPaintInInventory(paints, count[1]))
                return ifFoundSolution(inventory, customerPreferences, i, paints);
        }
        return true;
    }

    private int[] countCommonColorAndConflictColorOfTheCustomer(Map<Integer, PaintType> inventory, List<Paint> paints) {
        int ith = 0;
        int conflict = 0;
        for (; ith < paints.size(); ith++) {
            if (isAlreadyInInventory(inventory, paints.get(ith)))
                break;
            if (isConflictWithInventory(inventory, paints.get(ith)))
                conflict++;
        }
        return new int[]{conflict, ith};
    }

    private boolean allPaintsAreConflictWithInventory(List<Paint> paints, int conflictNum) {
        return conflictNum == paints.size();
    }

    private boolean nonPaintInInventory(List<Paint> paints, int numOfPaintsNotInInventory) {
        return numOfPaintsNotInInventory == paints.size();
    }

    private boolean isConflictWithInventory(Map<Integer, PaintType> inventory, Paint paint) {
        int color = paint.getColor();
        PaintType paintTypeInInventory = inventory.get(color);
        PaintType currentPaintType = paint.getType();
        return paintTypeInInventory != null && (paintTypeInInventory != currentPaintType);
    }

    private boolean isAlreadyInInventory(Map<Integer, PaintType> inventory, Paint paint) {
        int color = paint.getColor();
        PaintType paintTypeInInventory = inventory.get(color);
        PaintType currentPaintType = paint.getType();
        return paintTypeInInventory != null && (paintTypeInInventory == currentPaintType);
    }

    private Boolean ifFoundSolution(Map<Integer, PaintType> inventory, List<CustomerPreference> customerPreferences, int i, List<Paint> paints) {
        for (Paint paint : paints) {
            int color = paint.getColor();
            if (inventory.get(color) == null) {
                inventory.put(color, paint.getType());
                if (findInventory(inventory, customerPreferences, i + 1)) return true;
                inventory.put(color, null);
            }
        }
        return false;
    }
}

