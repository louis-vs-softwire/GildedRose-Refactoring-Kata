package com.gildedrose

class GildedRose(var items: Array<Item>) {

    fun updateQuality() {
        for (i in items.indices) {
            if (items[i].name.contains(Regex("conjured", RegexOption.IGNORE_CASE))) {
                items[i].quality -= if (items[i].sellIn < 1) 4 else 2
                if (items[i].quality < 0) items[i].quality = 0
                items[i].sellIn -= 1
            } else {
                if (items[i].name != "Aged Brie" && items[i].name != "Backstage passes to a TAFKAL80ETC concert") {
                    if (items[i].quality > 0) {
                        if (items[i].name != "Sulfuras, Hand of Ragnaros") {
                            items[i].quality = items[i].quality - 1
                        }
                    }
                } else {
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1

                        if (items[i].name == "Backstage passes to a TAFKAL80ETC concert") {
                            if (items[i].sellIn < 11) {
                                if (items[i].quality < 50) {
                                    items[i].quality = items[i].quality + 1
                                }
                            }

                            if (items[i].sellIn < 6) {
                                if (items[i].quality < 50) {
                                    items[i].quality = items[i].quality + 1
                                }
                            }
                        }
                    }
                }

                if (items[i].name != "Sulfuras, Hand of Ragnaros") {
                    items[i].sellIn = items[i].sellIn - 1
                }

                if (items[i].sellIn < 0) {
                    if (items[i].name != "Aged Brie") {
                        if (items[i].name != "Backstage passes to a TAFKAL80ETC concert") {
                            if (items[i].quality > 0) {
                                if (items[i].name != "Sulfuras, Hand of Ragnaros") {
                                    items[i].quality = items[i].quality - 1
                                }
                            }
                        } else {
                            items[i].quality = items[i].quality - items[i].quality
                        }
                    } else {
                        if (items[i].quality < 50) {
                            items[i].quality = items[i].quality + 1
                        }
                    }
                }
            }
        }
    }

    // helper functions for items
    fun getFirstItem(name: String): Item? {
        for (item in items) {
            if (item.name == name) {
                return item
            }
        }
        return null
    }

    fun getAllItems(name: String): ArrayList<Item> {
        val selectedItems = ArrayList<Item>()
        for (item in items) {
            if (item.name == name) {
                selectedItems.add(item)
            }
        }
        return selectedItems
    }

}

