package ie.wtsanshou.paintshops.presenter

import ie.wtsanshou.paintshops.model.Inventory
import spock.lang.Specification

import static ie.wtsanshou.paintshops.model.PaintType.GLOSS
import static ie.wtsanshou.paintshops.model.PaintType.MATTE

class InventoryPresenterImpl_Test extends Specification {

    InventoryPresenter inventoryPresenter

    void setup() {
        inventoryPresenter = new InventoryPresenterImpl()
    }

    def "given 1 color 1 Matte paint should return a list with M"() {
        given:
        def color = 1
        def paints = [1: MATTE]
        def inventory = new Inventory(color, paints)
        when:
        def paintList = inventoryPresenter.presentedInventory(inventory)
        then:
        paintList == ["M"]
    }

    def "given 1 color 1 Gloss paint should return a list with G"() {
        given:
        def color = 1
        def paints = [1: GLOSS]
        def inventory = new Inventory(color, paints)
        when:
        def paintList = inventoryPresenter.presentedInventory(inventory)
        then:
        paintList == ["G"]
    }

    def "given 0 color should return an empty list"() {
        given:
        def color = 0
        def paints = [:]
        def inventory = new Inventory(color, paints)
        when:
        def paintList = inventoryPresenter.presentedInventory(inventory)
        then:
        paintList == []
    }

    def "given 2 color 2 Matte paint should return a list with M and G"() {
        given:
        def color = 2
        def paints = [1: MATTE, 2: MATTE]
        def inventory = new Inventory(color, paints)
        when:
        def paintList = inventoryPresenter.presentedInventory(inventory)
        then:
        paintList == ["M", "M"]
    }

    def "given 2 color 1 Matte paint should return a list with M and G"() {
        given:
        def color = 2
        def paints = [1: MATTE]
        def inventory = new Inventory(color, paints)
        when:
        def paintList = inventoryPresenter.presentedInventory(inventory)
        then:
        paintList == ["M", "G"]
    }

    def "given 2 color 1 Gloss paint should return a list with M and G"() {
        given:
        def color = 2
        def paints = [1: GLOSS]
        def inventory = new Inventory(color, paints)
        when:
        def paintList = inventoryPresenter.presentedInventory(inventory)
        then:
        paintList == ["G", "G"]
    }

    def "given -1 color  should return a list with No solution exists"() {
        given:
        def color = -1
        def paints = [:]
        def inventory = new Inventory(color, paints)
        when:
        def paintList = inventoryPresenter.presentedInventory(inventory)
        then:
        paintList == ["No solution exists"]
    }
}
