package ie.wtsanshou.paintshops.converter;

import ie.wtsanshou.paintshops.Factory.CustomerFactory;
import ie.wtsanshou.paintshops.Factory.PaintFactory;
import ie.wtsanshou.paintshops.exception.ColorConverterException;
import ie.wtsanshou.paintshops.exception.CustomerConverterException;
import ie.wtsanshou.paintshops.model.CustomerPreference;
import ie.wtsanshou.paintshops.model.Paint;
import ie.wtsanshou.paintshops.model.PaintType;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

import static ie.wtsanshou.paintshops.model.PaintType.GLOSS;
import static ie.wtsanshou.paintshops.model.PaintType.MATTE;

@Slf4j
public class CustomerConverterImpl implements CustomerConverter {

    private ColorConverter colorConverter;
    private CustomerFactory customerFactory;
    private PaintFactory paintFactory;

    public CustomerConverterImpl(ColorConverter colorConverter, CustomerFactory customerFactory, PaintFactory paintFactory) {
        this.colorConverter = colorConverter;
        this.customerFactory = customerFactory;
        this.paintFactory = paintFactory;
    }

    public CustomerPreference convert(String customerColors) throws CustomerConverterException, ColorConverterException {
        if (customerColors == null)
            throw new CustomerConverterException("customerColors is null");
        if (customerColors.isEmpty())
            return new CustomerPreference.EmptyCustomerPreference();

        List<Paint> paints = getPaints(customerColors);

        log.debug("Converter one customer: " + customerColors);
        return customerFactory.create(paints);
    }

    private List<Paint> getPaints(String customerColors) throws CustomerConverterException, ColorConverterException {
        List<Paint> paints = new LinkedList<>();
        try {
            String[] cc = customerColors.trim().split(" ");

            if (cc.length % 2 != 0)
                throw new CustomerConverterException("The number of paint color and paint type are not matched");

            addPaints(paints, cc);
        } catch (CustomerConverterException | ColorConverterException e) {
            log.error("Customer converter exception: " + customerColors);
            throw e;
        }
        return paints;
    }

    private void addPaints(List<Paint> paints, String[] customerColors) throws ColorConverterException, CustomerConverterException {
        for (int matteNum = 0, i = 0; i < customerColors.length; i += 2) {
            int color = colorConverter.convert(customerColors[i]);

            PaintType paintType = getPaint(customerColors, i);

            if (paintType == MATTE) matteNum++;
            if (matteNum > 1)
                throw new CustomerConverterException("There are more than 1 MATTE type of color for this customer");

            Paint paint = paintFactory.create(color, paintType);

            addMatteToTheEnd(paints, paintType, paint);
        }
    }

    private PaintType getPaint(String[] customerColors, int i) throws CustomerConverterException {
        if (neitherGrossNorMatte(customerColors[i + 1]))
            throw new CustomerConverterException("The color type is neither Gloss nor Matte");
        return customerColors[i + 1].equals("G") ? GLOSS : MATTE;
    }

    boolean neitherGrossNorMatte(String customerColor) {
        return !customerColor.equals("G") && !customerColor.equals("M");
    }

    private void addMatteToTheEnd(List<Paint> paints, PaintType paintType, Paint paint) {
        if (paintType == MATTE) paints.add(paint);
        else paints.add(0, paint);
    }

}
