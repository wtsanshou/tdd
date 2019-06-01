package ie.wtsanshou.paintshops.converter

import ie.wtsanshou.paintshops.Factory.CustomerFactory
import ie.wtsanshou.paintshops.Factory.CustomerFactoryImpl
import ie.wtsanshou.paintshops.Factory.PaintFactory
import ie.wtsanshou.paintshops.Factory.PaintFactoryImpl
import ie.wtsanshou.paintshops.exception.ColorConverterException
import ie.wtsanshou.paintshops.exception.CustomerConverterException
import spock.lang.Specification

import static ie.wtsanshou.paintshops.model.PaintType.GLOSS
import static ie.wtsanshou.paintshops.model.PaintType.MATTE

class CustomerConverterImpl_Test extends Specification {

    CustomerConverter customerConverter;

    void setup() {
        ColorConverter colorConverter = new ColorConverterImpl()
        CustomerFactory customerFactory = new CustomerFactoryImpl()
        PaintFactory paintFactory = new PaintFactoryImpl()
        customerConverter = new CustomerConverterImpl(colorConverter, customerFactory, paintFactory)
    }

    def "Given input null should throw CustomerConverterException"() {
        given:
        def input = null
        when:
        customerConverter.convert(input)
        then:
        thrown CustomerConverterException
    }

    def "Given input empty should return EmptyCustomer"() {
        given:
        def input = ""
        when:
        def customer = customerConverter.convert(input)
        then:
        customer.getPaints().size() == 0
    }

    def "Given input one color with G should return a customer with the color with Gloss type"() {
        given:
        def input = "1 G"
        when:
        def customer = customerConverter.convert(input)
        def paints = customer.getPaints()
        then:
        paints.size() == 1
        paints[0].getColor() == 1
        paints[0].getType() == GLOSS
    }

    def "Given input one color with leading or tailing space should return a customer with the color with Gloss type"() {
        given:
        def input = "  1 G   "
        when:
        def customer = customerConverter.convert(input)
        def paints = customer.getPaints()
        then:
        paints.size() == 1
        paints[0].getColor() == 1
        paints[0].getType() == GLOSS
    }

    def "Given input one color with M should return a customer with the color with Matte type"() {
        given:
        def input = "1 M"
        when:
        def customer = customerConverter.convert(input)
        def paints = customer.getPaints()
        then:
        paints.size() == 1
        paints[0].getColor() == 1
        paints[0].getType() == MATTE
    }

    def "Given input two colors with G and M should return a customer with two color with Gloss and Matte type"() {
        given:
        def input = "1 M 2 G"
        when:
        def customer = customerConverter.convert(input)
        def paints = customer.getPaints()
        then:
        paints.size() == 2
        paints[0].getColor() == 2
        paints[0].getType() == GLOSS
        paints[1].getColor() == 1
        paints[1].getType() == MATTE
    }

    def "Given input two colors with G and G should return a customer with two color both with Gloss type"() {
        given:
        def input = "1 G 2 G"
        when:
        def customer = customerConverter.convert(input)
        def paints = customer.getPaints()
        then:
        paints.size() == 2
        paints[0].getColor() == 2
        paints[0].getType() == GLOSS
        paints[1].getColor() == 1
        paints[1].getType() == GLOSS
    }

    def "Given input two colors with M and M should throw CustomerConverterException"() {
        given:
        def input = "1 M 2 M"
        when:
        customerConverter.convert(input)
        then:
        thrown CustomerConverterException
    }

    def "Given input three colors should return a customer with three color"() {
        given:
        def input = "2 G 3 M 4 G"
        when:
        def customer = customerConverter.convert(input)
        def paints = customer.getPaints()
        then:
        paints.size() == 3
    }

    def "Given input one color but not G or M type should throw CustomerConverterException"() {
        given:
        def input = "1 A"
        when:
        customerConverter.convert(input)
        then:
        thrown CustomerConverterException
    }

    def "Given input two color but only one G type should throw CustomerConverterException"() {
        given:
        def input = "1 G 2"
        when:
        customerConverter.convert(input)
        then:
        thrown CustomerConverterException
    }

    def "Given input two color but only one M type should throw CustomerConverterException"() {
        given:
        def input = "1 2 M"
        when:
        customerConverter.convert(input)
        then:
        thrown CustomerConverterException
    }

    def "Given input one colors but without space should throw CustomerConverterException"() {
        given:
        def input = "1M"
        when:
        customerConverter.convert(input)
        then:
        thrown CustomerConverterException
    }

    def "Given input two colors without space between color and type should throw ColorConverterException"() {
        given:
        def input = "1M 2G"
        when:
        customerConverter.convert(input)
        then:
        thrown ColorConverterException
    }

    def "Given input two colors without spaces at all should throw CustomerConverterException"() {
        given:
        def input = "1M2G"
        when:
        customerConverter.convert(input)
        then:
        thrown CustomerConverterException
    }

    def "Given input two colors but missing space after color should throw CustomerConverterException"() {
        given:
        def input = "1M 2 G"
        when:
        customerConverter.convert(input)
        then:
        thrown CustomerConverterException
    }

    def "Given input two colors but missing space after type should throw CustomerConverterException"() {
        given:
        def input = "1 M2 G"
        when:
        customerConverter.convert(input)
        then:
        thrown CustomerConverterException
    }

    def "Given input one colors with wrong type and color CustomerData should throw ColorConverterException"() {
        given:
        def input = "M 1"
        when:
        customerConverter.convert(input)
        then:
        thrown ColorConverterException
    }


}
