package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ConjuredItemTest {

    @Test
    public void conjuredItemDegradesTwiceAsFast() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 10, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(9, items[0].sellIn);
        assertEquals(18, items[0].quality);
    }

    @Test
    public void conjuredItemDegradesTwiceAsFastAfterSellIn() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 0, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(-1, items[0].sellIn);
        assertEquals(16, items[0].quality);
    }

    @Test
    public void conjuredItemQualityNeverGoesNegative() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 5, 1) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(4, items[0].sellIn);
        assertEquals(0, items[0].quality);
    }

    @Test
    public void conjuredItemQualityNeverGoesNegativeAfterSellIn() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 0, 3) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(-1, items[0].sellIn);
        assertEquals(0, items[0].quality);
    }

    @Test
    public void conjuredItemMultipleDays() {
        Item[] items = new Item[] { new Item("Conjured Elixir", 3, 10) };
        GildedRose app = new GildedRose(items);
        
        // Day 1
        app.updateQuality();
        assertEquals(2, items[0].sellIn);
        assertEquals(8, items[0].quality);
        
        // Day 2
        app.updateQuality();
        assertEquals(1, items[0].sellIn);
        assertEquals(6, items[0].quality);
        
        // Day 3
        app.updateQuality();
        assertEquals(0, items[0].sellIn);
        assertEquals(4, items[0].quality);
        
        // Day 4 (after sellIn)
        app.updateQuality();
        assertEquals(-1, items[0].sellIn);
        assertEquals(0, items[0].quality);
        
        // Day 5 (should stay at 0)
        app.updateQuality();
        assertEquals(-2, items[0].sellIn);
        assertEquals(0, items[0].quality);
    }

    @Test
    public void conjuredItemNameVariations() {
        Item[] items = new Item[] { 
            new Item("Conjured Mana Cake", 5, 10),
            new Item("Conjured Sword", 5, 10),
            new Item("Conjured Shield of Power", 5, 10)
        };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        for (Item item : items) {
            assertEquals(4, item.sellIn);
            assertEquals(8, item.quality);
        }
    }
}
