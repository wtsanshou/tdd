package ie.wtsanshou.paintshops.converter

import ie.wtsanshou.paintshops.converter.ColorConverter
import ie.wtsanshou.paintshops.converter.ColorConverterImpl
import ie.wtsanshou.paintshops.exception.ColorConverterException
import spock.lang.Specification

class ColorConverterImpl_Test extends Specification {

    ColorConverter colorConverter

    void setup() {
        colorConverter = new ColorConverterImpl()
    }

    def "Given input null should throw ColorConverterException"() {
        given:
        def input = null
        when:
        colorConverter.convert(input)
        then:
        thrown ColorConverterException
    }

    def "Given input empty should throw ColorConverterException"() {
        given:
        def input = ""
        when:
        colorConverter.convert(input)
        then:
        thrown ColorConverterException
    }

    def "Given input blank should throw ColorConverterException"() {
        given:
        def input = "   "
        when:
        colorConverter.convert(input)
        then:
        thrown ColorConverterException
    }

    def "Given input String 0 throw ColorConverterException"() {
        given:
        def input = "0"
        when:
        colorConverter.convert(input)
        then:
        thrown ColorConverterException
    }

    def "Given input String -1 should throw ColorConverterException"() {
        given:
        def input = "-1"
        when:
        colorConverter.convert(input)
        then:
        thrown ColorConverterException
    }

    def "Given input String -2147483648 should throw ColorConverterException"() {
        given:
        def input = "-2147483648"
        when:
        colorConverter.convert(input)
        then:
        thrown ColorConverterException
    }

    def "Given input String -2147483649 should throw ColorConverterException"() {
        given:
        def input = "-2147483649"
        when:
        colorConverter.convert(input)
        then:
        thrown ColorConverterException
    }

    def "Given input String 2147483648 should throw ColorConverterException"() {
        given:
        def input = "2147483648"
        when:
        colorConverter.convert(input)
        then:
        thrown ColorConverterException
    }

    def "Given input String 1 should return Integer 1"() {
        given:
        def input = "1"
        when:
        def color = colorConverter.convert(input)
        then:
        color == 1
    }

    def "Given input String 2147483647 should return Integer 2147483647"() {
        given:
        def input = "2147483647"
        when:
        def color = colorConverter.convert(input)
        then:
        color == Integer.MAX_VALUE
    }
}
