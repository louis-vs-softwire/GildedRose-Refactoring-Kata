package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GildedRoseTest {
    // item names
    val elixirName = "Elixir of the Mongoose"
    val brieName = "Aged Brie"
    val sulfurasName = "Sulfuras, Hand of Ragnaros"
    val backstagePassName = "Backstage passes to a TAFKAL80ETC concert"
    val conjuredName = "Conjured Mana Cake"

    // app instance
    lateinit var app: GildedRose

    @BeforeEach
    fun init() {
        val items = arrayOf(
                Item("+5 Dexterity Vest", 10, 20),
                Item(brieName, 2, 0),
                Item(elixirName, 5, 7),
                Item(brieName, 5, 50),
                Item("No SellIn", 0, 5),
                Item("No Quality", 5, 0),
                Item("Neg SellIn", -1, 5),
                Item("Expired Elixir", 0, 5),
                Item(sulfurasName, 0, 80), //
                Item(sulfurasName, -1, 80),
                Item(backstagePassName, 15, 20),
                Item(backstagePassName, 10, 20),
                Item(backstagePassName, 5, 20),
                Item(conjuredName, 3, 6),
                Item(conjuredName, -1, 6),
                Item(conjuredName, -1, 1)
        )
        app = GildedRose(items)
    }

    @Test
    fun sulfurasQualityShouldNeverDecrease() {
        val item = app.getFirstItem(sulfurasName)
        assertNotNull(item)

        assertEquals(80, item?.quality)
        app.updateQuality()
        assertEquals(80, item?.quality)
    }

    @Test
    fun qualityShouldDecrease() {
        val item = app.getFirstItem(elixirName)
        assertNotNull(item)

        assertEquals(7, item?.quality)
        app.updateQuality()
        assertEquals(6, item?.quality)
    }

    @Test
    fun qualityShouldNotBecomeNegative() {
        val item = app.getFirstItem("No Quality")
        assertNotNull(item)

        assertEquals(0, item?.quality)
        app.updateQuality()
        assertEquals(0, item?.quality)
    }

    @Test
    fun brieQualityShouldIncrease() {
        val item = app.getFirstItem(brieName)
        assertNotNull(item)

        assertEquals(0, item?.quality)
        app.updateQuality()
        assertEquals(1, item?.quality)
    }

    @Test
    fun sellByDateShouldDecrease() {
        val item = app.getFirstItem(elixirName)
        assertNotNull(item)

        assertEquals(5, item?.sellIn)
        app.updateQuality()
        assertEquals(4, item?.sellIn)
    }

    @Test
    fun sellByDateShouldDecreaseIntoNegatives() {
        val item = app.getFirstItem("No SellIn")
        assertNotNull(item)

        assertEquals(0, item?.sellIn)
        app.updateQuality()
        assertEquals(-1, item?.sellIn)
    }

    @Test
    fun sellByDateShouldDecreaseWhenNegative() {
        val item = app.getFirstItem("Neg SellIn")
        assertNotNull(item)

        assertEquals(-1, item?.sellIn)
        app.updateQuality()
        assertEquals(-2, item?.sellIn)
    }

    @Test
    fun sulfurasSellByDateShouldNotDecreaseIntoNegatives() {
        val item = app.getFirstItem(sulfurasName)
        assertNotNull(item)

        assertEquals(0, item?.sellIn)
        app.updateQuality()
        assertEquals(0, item?.sellIn)
    }

    @Test
    fun sulfurasSellByDateShouldNotDecreaseWhenNegative() {
        val sulfurasItems = app.getAllItems(sulfurasName)
        assert(sulfurasItems.isNotEmpty())

        val item = sulfurasItems[1]

        assertEquals(-1, item.sellIn)
        app.updateQuality()
        assertEquals(-1, item.sellIn)
    }

    @Test
    fun qualityShouldDecreaseTwiceAsFastWhenExpired() {
        val item = app.getFirstItem("Expired Elixir")
        assertNotNull(item)

        assertEquals(5, item?.quality)
        app.updateQuality()
        assertEquals(3, item?.quality)
    }

    @Test
    fun backstagePassShouldIncreaseInQualityWhenSellByDateGreaterThanTen() {
        val item = app.getFirstItem(backstagePassName)
        assertNotNull(item)

        assertEquals(15, item?.sellIn)
        assertEquals(20, item?.quality)
        app.updateQuality()
        assertEquals(14, item?.sellIn)
        assertEquals(21, item?.quality)
    }

    @Test
    fun backstagePassShouldIncreaseInQualityWhenSellByDateLessThanTen() {
        val backstagePasses = app.getAllItems(backstagePassName)
        assert(backstagePasses.isNotEmpty())

        val item = backstagePasses[1]

        assertEquals(10, item.sellIn)
        assertEquals(20, item.quality)
        app.updateQuality()
        assertEquals(9, item.sellIn)
        assertEquals(22, item.quality)
    }

    @Test
    fun backstagePassShouldIncreaseInQualityWhenSellByDateLessThanFive() {
        val backstagePasses = app.getAllItems(backstagePassName)
        assert(backstagePasses.isNotEmpty())

        val item = backstagePasses[2]

        assertEquals(5, item.sellIn)
        assertEquals(20, item.quality)
        app.updateQuality()
        assertEquals(4, item.sellIn)
        assertEquals(23, item.quality)
    }

    @Test
    fun qualityShouldNotExceedFifty() {
        val bries = app.getAllItems(brieName)
        assert(bries.isNotEmpty())

        val item = bries[1]

        assertEquals(50, item.quality)
        app.updateQuality()
        assertEquals(50, item.quality)
    }

    @Test
    fun conjuredItemsShouldLoseDoubleQuality() {
        val item = app.getFirstItem(conjuredName)
        assertNotNull(item)

        assertEquals(6, item?.quality)
        app.updateQuality()
        assertEquals(4, item?.quality)
    }

    @Test
    fun conjuredItemsShouldLoseQuadrupleQualityWhenPastSellByDate() {
        val conjuredItems = app.getAllItems(conjuredName)
        assert(conjuredItems.isNotEmpty())

        val item = conjuredItems[1]

        assertEquals(6, item.quality)
        app.updateQuality()
        assertEquals(2, item.quality)
    }

    @Test
    fun conjuredItemsShouldNotGoBelowZeroQuality() {
        val conjuredItems = app.getAllItems(conjuredName)
        assert(conjuredItems.isNotEmpty())

        val item = conjuredItems[2]

        assertEquals(1, item.quality)
        app.updateQuality()
        assertEquals(0, item.quality)
    }
}
