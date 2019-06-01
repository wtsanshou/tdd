package ie.wtsanshou.paintshops.converter

import ie.wtsanshou.paintshops.Factory.CustomerFactory
import ie.wtsanshou.paintshops.Factory.CustomerFactoryImpl
import ie.wtsanshou.paintshops.Factory.CustomerDataFactory
import ie.wtsanshou.paintshops.Factory.CustomerDataFactoryImpl
import ie.wtsanshou.paintshops.Factory.PaintFactory
import ie.wtsanshou.paintshops.Factory.PaintFactoryImpl
import ie.wtsanshou.paintshops.exception.ColorConverterException
import ie.wtsanshou.paintshops.exception.CustomerConverterException
import ie.wtsanshou.paintshops.exception.CustomerDataConverterException
import ie.wtsanshou.paintshops.model.CustomerData
import spock.lang.Specification

class CustomerDataConverterImpl_Test extends Specification{

    CustomerDataConverter customerDataConverter

    void setup(){
        ColorConverter colorConverter = new ColorConverterImpl()
        CustomerFactory customerFactory = new CustomerFactoryImpl();
        PaintFactory paintFactory = new PaintFactoryImpl()
        CustomerConverter customerConverter = new CustomerConverterImpl(colorConverter, customerFactory, paintFactory)
        CustomerDataFactory customerDataFactory = new CustomerDataFactoryImpl()
        customerDataConverter = new CustomerDataConverterImpl(colorConverter, customerConverter, customerDataFactory)
    }

    def "given null input should throw CustomerDataConverterException"(){
        given:
        List<String> input = null
        when:
        customerDataConverter.convert(input)
        then:
        thrown CustomerDataConverterException
    }

    def "given empty input should return an EmptyCustomerData"(){
        given:
        List<String> input = new LinkedList<>()
        when:
        CustomerData customerData = customerDataConverter.convert(input)
        then:
        customerData.getColor() == 0
        customerData.getCustomerPreferences().size() == 0
    }

    def "given one color without customer input should return an CustomerData with one color"(){
        given:

        List<String> input = ["1"]
        when:
        CustomerData customerData = customerDataConverter.convert(input)
        def customers = customerData.getCustomerPreferences()
        then:
        customerData.getColor() == 1
        customers.size() == 0
    }

    def "given one color and one customer input should return an Order with one color and one customer"(){
        given:

        List<String> input = ["1", "1 G"]
        when:
        CustomerData customerData = customerDataConverter.convert(input)
        def customers = customerData.getCustomerPreferences()
        then:
        customerData.getColor() == 1
        customers.size() == 1
    }

    def "given wrong color number input should catch ColorConverterException"(){
        given:

        List<String> input = ["G", "1 G"]
        when:
        customerDataConverter.convert(input)
        then:
        notThrown ColorConverterException
    }

    def "given wrong customer  input should catch CustomerConverterException"(){
        given:

        List<String> input = ["1", "1G"]
        when:
        customerDataConverter.convert(input)
        then:
        notThrown CustomerConverterException
    }

    def "given two color and two customer input should return an Order with two color and two customer"(){
        given:
        List<String> input = ["2", "1 G", "2 M"]
        when:
        CustomerData customerData = customerDataConverter.convert(input)
        def customers = customerData.getCustomerPreferences()
        then:
        customerData.getColor() == 2
        customers.size() == 2
    }


    def "given two color and X customer input should return an Order with two color and X customer"(){
        given:
        List<String> input = ["5",
                              "2 M",
                              "5 G",
                              "1 G",
                              "5 G 1 G 4 M",
                              "3 G",
                              "5 G",
                              "3 G 5 G 1 G",
                              "3 G",
                              "2 M",
                              "5 G 1 G",
                              "2 M",
                              "5 G",
                              "4 M",
                              "5 G 4 M"
        ]
        when:
        CustomerData customerData = customerDataConverter.convert(input)
        def customers = customerData.getCustomerPreferences()
        then:
        customerData.getColor() == 5
        customers.size() == 14
    }
}
