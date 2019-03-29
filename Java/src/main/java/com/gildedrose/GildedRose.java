package com.gildedrose;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.ToIntFunction;

class GildedRose {

    private static final int MAX_QUALITY = 50;
    private static final int MIN_QUALITY = 0;
    private static final String AGED_BRIE = "Aged Brie";
    private static final String CONCERT_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {

        for (Item i : items) {

            if (!isItemAgedBrie(i) && !isItemConcertPass(i)) {
                if (i.quality > MIN_QUALITY) {
                    if (!isItemSulfuras(i)) {
                        i.quality = i.quality - 1;
                    }
                }
            } else {
                if (i.quality < MAX_QUALITY) {
                    i.quality = i.quality + 1;

                    if (isItemConcertPass(i)) {
                        if (i.sellIn < 11) {
                            if (i.quality < MAX_QUALITY) {
                                i.quality = i.quality + 1;
                            }
                        }

                        if (i.sellIn < 6) {
                            if (i.quality < MAX_QUALITY) {
                                i.quality = i.quality + 1;
                            }
                        }
                    }
                }
            }

            if (!isItemSulfuras(i)) {
                i.sellIn = i.sellIn - 1;
            }

            if (i.sellIn < 0) {
                if (!isItemAgedBrie(i)) {
                    if (!isItemConcertPass(i)) {
                        if (i.quality > MIN_QUALITY) {
                            if (!isItemSulfuras(i)) {
                                i.quality = i.quality - 1;
                            }
                        }
                    } else {
                        i.quality = i.quality - i.quality;
                    }
                } else {
                    if (i.quality < MAX_QUALITY) {
                        applyIntFunc(i, j -> j.quality + 1);
                    }
                }
            }
        }
    }

    private boolean isItemAgedBrie(Item i) {
        return AGED_BRIE.equals(i.name);
    }

    private boolean isItemConcertPass(Item i) {
        return CONCERT_PASS.equals(i.name);
    }

    private boolean isItemSulfuras(Item i) {
        return SULFURAS.equals(i.name);
    }

    private void applyIntFunc(Item i, ToIntFunction<Item> func) {
        func.applyAsInt(i);
    }
}