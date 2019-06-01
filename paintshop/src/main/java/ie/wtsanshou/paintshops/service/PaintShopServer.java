package ie.wtsanshou.paintshops.service;

import ie.wtsanshou.paintshops.model.CustomerData;
import ie.wtsanshou.paintshops.model.Inventory;

public interface PaintShopServer {
    Inventory preparePaints(CustomerData customerData);
}
