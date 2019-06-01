package ie.wtsanshou.paintshops;

import ie.wtsanshou.paintshops.converter.CustomerDataConverter;
import ie.wtsanshou.paintshops.converter.CustomerDataConverterImpl;
import ie.wtsanshou.paintshops.exception.CustomerDataConverterException;
import ie.wtsanshou.paintshops.exception.FilePathIllegalException;
import ie.wtsanshou.paintshops.inbound.CustomerDataFileReader;
import ie.wtsanshou.paintshops.inbound.CustomerDataReader;
import ie.wtsanshou.paintshops.model.CustomerData;
import ie.wtsanshou.paintshops.model.Inventory;
import ie.wtsanshou.paintshops.outbound.InventoryConsoleWriter;
import ie.wtsanshou.paintshops.outbound.InventoryWriter;
import ie.wtsanshou.paintshops.presenter.InventoryPresenter;
import ie.wtsanshou.paintshops.presenter.InventoryPresenterImpl;
import ie.wtsanshou.paintshops.service.PaintShopServer;
import ie.wtsanshou.paintshops.service.PaintShopServerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

@Slf4j
public class AppMain {

    private static ApplicationContext applicationContext;
    private static CustomerDataConverter customerDataConverter;
    private static PaintShopServer paintShopServer;
    private static InventoryPresenter inventoryPresenter;
    private static InventoryWriter inventoryWriter;
    private static CustomerDataReader customerDataReader;
    private static String filePath= "customerData/customerData2.txt";
    private static String configLocation= "paintShop_applicationContext.xml";

    public static void main(String[] arg){
        init();
        try {
            List<String> customerData1 = customerDataReader.read();
            CustomerData customerData = customerDataConverter.convert(customerData1);

            Inventory inventory = paintShopServer.preparePaints(customerData);

            List<String> paintsList = inventoryPresenter.presentedInventory(inventory);

            inventoryWriter.write(paintsList);
        }catch (FilePathIllegalException e){
            log.error("Reading a wrong file path" + e);
        } catch (CustomerDataConverterException e) {
            log.error("Failed to convert data to customerData" + e);
        }

    }

    private static void init() {
        customerDataReader = new CustomerDataFileReader(filePath);
        applicationContext = new ClassPathXmlApplicationContext(configLocation);
        customerDataConverter = applicationContext.getBean(CustomerDataConverterImpl.class);
        paintShopServer = applicationContext.getBean(PaintShopServerImpl.class);
        inventoryPresenter = applicationContext.getBean(InventoryPresenterImpl.class);
        inventoryWriter = applicationContext.getBean(InventoryConsoleWriter.class);
    }
}
