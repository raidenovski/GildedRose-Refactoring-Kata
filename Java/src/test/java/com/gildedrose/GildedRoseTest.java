package com.gildedrose;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GildedRoseTest {

    private Item[] items;

    @BeforeEach
    public void setup() {
        this.items = ItemsUtils.items;
    }

    @Nested
    @DisplayName("Given normal item")
    class NormalItemTest {

        @Test
        @DisplayName("When updating quality then update all properties")
        public void whenUpdatingQualityThenUpdateAllProperties() {
            Item[] items = new Item[] { new Item("+5 Dexterity Vest", 10, 20), };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            Assertions.assertEquals(19, app.items[0].quality);
            Assertions.assertEquals(9, app.items[0].sellIn);
        }

        @Test
        @DisplayName("When Sell-In date expires then quality degrades twice as fast")
        public void whenSellInDateExpiresThenQualityDegradesTwiceAsFast() {
            final int expectedQuality = 7;
            final int expectedSellIn = -1;
            Item[] items = new Item[] { new Item("+5 Dexterity Vest", 1, 10), };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            app.updateQuality();
            Assertions.assertEquals(expectedQuality, app.items[0].quality);
            Assertions.assertEquals(expectedSellIn, app.items[0].sellIn);

        }

        @Test
        @DisplayName("When quality degrades then it never drops bellow zero")
        public void whenQualityDegradesThenItNeverDropsBellowZero() {
            final int expectedQuality = 0;
            Item[] items = new Item[] { new Item("+5 Dexterity Vest", 1, 1), };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            app.updateQuality();
            Assertions.assertEquals(expectedQuality, app.items[0].quality);

        }
    }

    @Nested
    @DisplayName("Given Aged Brie")
    class AgedBrie {

        @Test
        @DisplayName("When updating quality then increase instead of decrease")
        public void whenUpdatingQualityThenIncreaseInsteadOfDecrease() {
            final int expected = 1;
            Item[] items = new Item[] { new Item("Aged Brie", 2, 0)};
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            Assertions.assertEquals(expected, app.items[0].quality);
            Assertions.assertEquals(expected, app.items[0].sellIn);
        }

        @Test
        @DisplayName("When increasing quality then don't increase over 50")
        public void whenIncreasingQualityThenDontIncreaseOverFifty() {
            final int expected = 50;
            Item[] items = new Item[] { new Item("Aged Brie", 2, 50)};
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            Assertions.assertEquals(expected, app.items[0].quality);
        }

    }

    @Nested
    @DisplayName("Given Sulfuras")
    class Sulfuras {

        @Test
        @DisplayName("When updating quality then never decrease")
        public void whenUpdatingQUalityThenNeverDecarease() {
            final int expected = 20;
            Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 2, 20)};
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            Assertions.assertEquals(expected, app.items[0].quality);
        }
    }

    @Nested
    @DisplayName("Given Backstage Pass")
    class BackstagePass {

        @Test
        @DisplayName("When there are more than 10 days to conecrt then increase quality by 1")
        public void whenThereAreMoreThan10DaysToConcertThenIncreaseQualityByOne() {
            final int expectedQuality = 21;
            final int expectedSellIn = 14;
            Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20)};
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            Assertions.assertEquals(expectedQuality, app.items[0].quality);
            Assertions.assertEquals(expectedSellIn, app.items[0].sellIn);
        }

        @Test
        @DisplayName("When there are between 10 and 5 days to concert then increase quality by 2")
        public void whenThereAreInBetween10And5DaysThenIncreaseQualityBy2() {
            final int expectedQuality = 22;
            final int expectedSellIn = 8;
            Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 9, 20)};
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            Assertions.assertEquals(expectedQuality, app.items[0].quality);
            Assertions.assertEquals(expectedSellIn, app.items[0].sellIn);
        }

        @Test
        @DisplayName("When there are less than 5 days to concert then increase quality by 3")
        public void whenThereAreLessThan5DaysThenIncreaseQualityBy3() {
            final int expectedQuality = 23;
            final int expectedSellIn = 3;
            Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 4, 20)};
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            Assertions.assertEquals(expectedQuality, app.items[0].quality);
            Assertions.assertEquals(expectedSellIn, app.items[0].sellIn);
        }

        @Test
        @DisplayName("After the concert, drop quality  to 0")
        public void whenSellInDateIsExpiredThenDropQualityToZero() {
            final int expectedQuality = 0;
            final int expectedSellIn = -1;
            Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20)};
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            Assertions.assertEquals(expectedQuality, app.items[0].quality);
            Assertions.assertEquals(expectedSellIn, app.items[0].sellIn);
        }

        }

}
