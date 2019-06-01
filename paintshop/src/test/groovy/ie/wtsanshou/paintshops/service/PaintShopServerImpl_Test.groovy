package ie.wtsanshou.paintshops.service

import ie.wtsanshou.paintshops.model.CustomerPreference
import ie.wtsanshou.paintshops.model.CustomerData
import ie.wtsanshou.paintshops.model.Inventory
import ie.wtsanshou.paintshops.model.Paint
import spock.lang.Specification

import static ie.wtsanshou.paintshops.model.PaintType.GLOSS
import static ie.wtsanshou.paintshops.model.PaintType.MATTE

class PaintShopServerImpl_Test extends Specification{

    PaintShopServer paintShopServer

    void setup(){
        paintShopServer = new PaintShopServerImpl();
    }

    def "given Empty order should return empty list of paints"(){
        given:
        CustomerData order = new CustomerData.EmptyCustomerData()
        when:
        def paints = paintShopServer.preparePaints(order).getPaints()
        then:
        paints != null
    }

    def "given one order with one customer and one color should return a map of the one paint"(){
        given:
        def inputPaints = [new Paint(1, GLOSS)]
        def customer = [new CustomerPreference(inputPaints)]
        def order = new CustomerData(1,customer)
        when:
        def paints = paintShopServer.preparePaints(order).getPaints()
        then:
        paints.size() == 1
        paints.get(1) == GLOSS
    }

    def "given one order with two customer with one color should return a map of the two paint"(){
        given:
        def inputPaints1 = [new Paint(1, GLOSS)]
        def inputPaints2 = [new Paint(2, MATTE)]
        def customer = [new CustomerPreference(inputPaints1), new CustomerPreference(inputPaints2)]
        def order = new CustomerData(2,customer)
        when:
        def paints = paintShopServer.preparePaints(order).getPaints()
        then:
        paints.size() == 2
        paints.get(1) == GLOSS
        paints.get(2) == MATTE
    }

    def "given one order with two customer with two color should return a map of the two paint"(){
        given:
        def inputPaints1 = [new Paint(1, GLOSS), new Paint(2, GLOSS)]
        def inputPaints2 = [new Paint(1, GLOSS), new Paint(2, GLOSS)]
        def customer = [new CustomerPreference(inputPaints1), new CustomerPreference(inputPaints2)]
        def order = new CustomerData(2,customer)
        when:
        def paints = paintShopServer.preparePaints(order).getPaints()
        then:
        paints.size() == 1
        paints.get(1) == GLOSS
    }

    def "given the first customer like only color be Matte, the rest are all Gloss should return a map of the Matte and Gloss paint"(){
        given:
        def inputPaints1 = [new Paint(1, GLOSS), new Paint(2, GLOSS)]
        def inputPaints2 = [new Paint(1, MATTE)]
        def customer = [new CustomerPreference(inputPaints1), new CustomerPreference(inputPaints2)]
        def order = new CustomerData(2,customer)
        when:
        def paints = paintShopServer.preparePaints(order).getPaints()
        then:
        paints.size() == 2
        paints.get(1) == MATTE
        paints.get(2) == GLOSS
    }

    def "given the two customer one color, one like Matte; one like Gloss, should return an empty map"(){
        given:
        def inputPaints1 = [new Paint(1, GLOSS)]
        def inputPaints2 = [new Paint(1, MATTE)]
        def customer = [new CustomerPreference(inputPaints1), new CustomerPreference(inputPaints2)]
        def order = new CustomerData(1,customer)
        when:
        def inventory = paintShopServer.preparePaints(order)
        def paints = inventory.getPaints()
        then:
        inventory.getColorNum() == -1
        paints.size() == 0
    }

    def "given the two customer one color, both like Gloss, should return an map with Gloss paint"(){
        given:
        def inputPaints1 = [new Paint(1, GLOSS)]
        def inputPaints2 = [new Paint(1, GLOSS)]
        def customer = [new CustomerPreference(inputPaints1), new CustomerPreference(inputPaints2)]
        def order = new CustomerData(1,customer)
        when:
        def paints = paintShopServer.preparePaints(order).getPaints()
        then:
        paints.size() == 1
        paints.get(1) == GLOSS
    }

    def "given the first customer like first color be Matte, the rest are all Gloss should return a map of the two Gloss paint"(){
        given:
        def inputPaints1 = [new Paint(2, GLOSS), new Paint(1, MATTE)]
        def inputPaints2 = [new Paint(1, GLOSS), new Paint(2, GLOSS)]
        def customer = [new CustomerPreference(inputPaints1), new CustomerPreference(inputPaints2)]
        def order = new CustomerData(2,customer)
        when:
        def paints = paintShopServer.preparePaints(order).getPaints()
        then:
        paints.size() == 1
        paints.get(2) == GLOSS
    }

    def "given the both customer like second color be Matte, the rest are all Gloss should return a map of the two Gloss paint"(){
        given:
        def inputPaints1 = [new Paint(1, GLOSS), new Paint(2, MATTE)]
        def inputPaints2 = [new Paint(1, GLOSS), new Paint(2, GLOSS)]
        def customer = [new CustomerPreference(inputPaints1), new CustomerPreference(inputPaints2)]
        def order = new CustomerData(2,customer)
        when:
        def paints = paintShopServer.preparePaints(order).getPaints()
        then:
        paints.size() == 1
        paints.get(1) == GLOSS
    }

    def "given the customers1 should return a map of the two Gloss paint"(){
        given:
        def inputPaints1 = [new Paint(1, GLOSS), new Paint(2, MATTE)]
        def inputPaints2 = [new Paint(1, MATTE)]
        def customer = [new CustomerPreference(inputPaints1), new CustomerPreference(inputPaints2)]
        def order = new CustomerData(2,customer)
        when:
        def paints = paintShopServer.preparePaints(order).getPaints()
        then:
        paints.size() == 2
        paints.get(1) == MATTE
        paints.get(2) == MATTE
    }

    def "given the customers2 should return a map of the two Gloss paint"(){
        given:
        def inputPaints1 = [new Paint(5, GLOSS), new Paint(3, GLOSS),new Paint(1, MATTE)]
        def inputPaints2 = [new Paint(4, GLOSS), new Paint(2, GLOSS),new Paint(3, MATTE)]
        def inputPaints3 = [new Paint(5, MATTE)]
        def customer = [new CustomerPreference(inputPaints1), new CustomerPreference(inputPaints2), new CustomerPreference(inputPaints3)]
        def order = new CustomerData(5,customer)
        when:
        def paints = paintShopServer.preparePaints(order).getPaints()
        then:
        paints.size() == 3
        paints.get(3) == GLOSS
        paints.get(4) == GLOSS
        paints.get(5) == MATTE
    }

    def "given the customers3 should return a map of the two Gloss paint"(){
        given:
        def inputPaints1 = [new Paint(5, GLOSS), new Paint(3, GLOSS),new Paint(1, GLOSS)]
        def inputPaints2 = [new Paint(5, GLOSS), new Paint(1, GLOSS)]
        def inputPaints3 = [new Paint(5, GLOSS),new Paint(4, MATTE)]
        def inputPaints4 = [new Paint(2, MATTE)]
        def inputPaints5 = [new Paint(5, GLOSS)]
        def inputPaints6 = [new Paint(1, GLOSS)]
        def inputPaints7 = [new Paint(1, GLOSS), new Paint(5, GLOSS),new Paint(4, MATTE)]
        def inputPaints8 = [new Paint(3, GLOSS)]
        def inputPaints9 = [new Paint(5, GLOSS)]
        def inputPaints10 = [new Paint(3, GLOSS)]
        def inputPaints11 = [new Paint(2, MATTE)]
        def inputPaints12 = [new Paint(2, MATTE)]
        def inputPaints13 = [new Paint(5, GLOSS)]
        def inputPaints14 = [new Paint(4, MATTE)]
        def customer = [new CustomerPreference(inputPaints1),
                        new CustomerPreference(inputPaints2),
                        new CustomerPreference(inputPaints3),
                        new CustomerPreference(inputPaints4),
                        new CustomerPreference(inputPaints5),
                        new CustomerPreference(inputPaints6),
                        new CustomerPreference(inputPaints7),
                        new CustomerPreference(inputPaints8),
                        new CustomerPreference(inputPaints9),
                        new CustomerPreference(inputPaints10),
                        new CustomerPreference(inputPaints11),
                        new CustomerPreference(inputPaints12),
                        new CustomerPreference(inputPaints13),
                        new CustomerPreference(inputPaints14)]
        def order = new CustomerData(5,customer)
        when:
        def paints = paintShopServer.preparePaints(order).getPaints()
        then:
        paints.size() <= 5
        paints.get(1) == GLOSS
        paints.get(2) == MATTE
        paints.get(3) == GLOSS
        paints.get(4) == MATTE
        paints.get(5) == GLOSS
    }
}
