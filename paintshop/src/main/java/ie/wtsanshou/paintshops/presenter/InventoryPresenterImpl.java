package ie.wtsanshou.paintshops.presenter;

import ie.wtsanshou.paintshops.model.Inventory;
import ie.wtsanshou.paintshops.model.PaintType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ie.wtsanshou.paintshops.model.PaintType.MATTE;

public class InventoryPresenterImpl implements InventoryPresenter {

    private final List<String> no_solution_exists = Arrays.asList("No solution exists");
    @Override
    public List<String> presentedInventory(Inventory inventory) {
        int colorNum = inventory.getColorNum();
        if(colorNum == -1)
            return no_solution_exists;

        Map<Integer, PaintType> paints = inventory.getPaints();
        String[] res = new String[colorNum];
        for (int i = 0; i < colorNum; i++)
            res[i] = paints.get(i + 1) == MATTE ? "M" : "G";

        return Arrays.asList(res);
    }
}
