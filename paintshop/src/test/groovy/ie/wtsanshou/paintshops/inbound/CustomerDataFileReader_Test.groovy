package ie.wtsanshou.paintshops.inbound

import ie.wtsanshou.paintshops.exception.FilePathIllegalException
import spock.lang.Specification

class CustomerDataFileReader_Test extends Specification{

    CustomerDataReader customerDataReader

    def "given null file path should throw FilePathIllegalException"(){
        given:
        customerDataReader = new CustomerDataFileReader(null)
        when:
        customerDataReader.read()
        then:
        thrown FilePathIllegalException
    }

    def "given empty file path should throw FilePathIllegalException"(){
        given:
        customerDataReader = new CustomerDataFileReader("")
        when:
        customerDataReader.read()
        then:
        thrown FilePathIllegalException
    }

    def "given blank file path should throw FilePathIllegalException"(){
        given:
        customerDataReader = new CustomerDataFileReader("  ")
        when:
        customerDataReader.read()
        then:
        thrown FilePathIllegalException
    }

    def "given customerData1 file path should get the CustomerData"(){
        given:
        customerDataReader = new CustomerDataFileReader("customerData/customerData1.txt")
        when:
        List<String> customerData = customerDataReader.read()
        then:
        customerData.size() == 4
    }

    def "given customerData1 file path with blank space should get the CustomerData"(){
        given:
        customerDataReader = new CustomerDataFileReader("   customerData/customerData1.txt   ")
        when:
        List<String> customerData = customerDataReader.read()
        then:
        customerData.size() == 4
    }

    def "given wrong file path with blank space should get the CustomerData"(){
        given:
        customerDataReader = new CustomerDataFileReader("wrong file path")
        when:
        List<String> customerData = customerDataReader.read()
        then:
        customerData == []
    }
}
