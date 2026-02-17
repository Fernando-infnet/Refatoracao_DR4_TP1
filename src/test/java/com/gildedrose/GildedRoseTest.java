package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class GildedRoseTest {

    // Teste 1: Aged Brie aumenta de qualidade até no máximo 50
    @Test
    public void agedBrieIncreasesQualityUpToMaximum50() {
        Item[] items = new Item[] { new Item("Aged Brie", 10, 48) };
        GildedRose app = new GildedRose(items);
        
        // Primeiro dia - deve aumentar para 49
        app.updateQuality();
        assertEquals(9, items[0].sellIn);
        assertEquals(49, items[0].quality);
        
        // Segundo dia - deve aumentar para 50
        app.updateQuality();
        assertEquals(8, items[0].sellIn);
        assertEquals(50, items[0].quality);
        
        // Terceiro dia - deve permanecer em 50 (não ultrapassa)
        app.updateQuality();
        assertEquals(7, items[0].sellIn);
        assertEquals(50, items[0].quality, "Aged Brie quality should not exceed 50");
    }

    @Test
    public void agedBrieIncreasesQualityTwiceAsFastAfterSellIn() {
        Item[] items = new Item[] { new Item("Aged Brie", 0, 10) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(-1, items[0].sellIn);
        assertEquals(12, items[0].quality, "Aged Brie should increase quality by 2 after sellIn date");
    }

    @Test
    public void agedBrieCannotExceed50EvenAfterSellIn() {
        Item[] items = new Item[] { new Item("Aged Brie", -1, 49) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(-2, items[0].sellIn);
        assertEquals(50, items[0].quality, "Aged Brie quality should not exceed 50 even after sellIn");
    }

    // Teste 2: Sulfuras não sofre alteração
    @Test
    public void sulfurasNeverChangesQuality() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 10, 80) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(10, items[0].sellIn, "Sulfuras sellIn should never change");
        assertEquals(80, items[0].quality, "Sulfuras quality should never change");
    }

    @Test
    public void sulfurasNeverChangesSellIn() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);
        
        // Executar múltiplas atualizações
        app.updateQuality();
        app.updateQuality();
        app.updateQuality();
        
        assertEquals(0, items[0].sellIn, "Sulfuras sellIn should remain unchanged");
        assertEquals(80, items[0].quality, "Sulfuras quality should remain at 80");
    }

    @Test
    public void sulfurasNeverChangesEvenWithNegativeSellIn() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", -1, 80) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(-1, items[0].sellIn, "Sulfuras sellIn should never change");
        assertEquals(80, items[0].quality, "Sulfuras quality should never change");
    }

    // Teste 3: Backstage Pass zera a qualidade após a data de venda
    @Test
    public void backstagePassDropsToZeroAfterConcert() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(-1, items[0].sellIn);
        assertEquals(0, items[0].quality, "Backstage pass should drop to 0 quality after the concert");
    }

    @Test
    public void backstagePassIncreasesQualityBy1With11DaysOrMore() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(14, items[0].sellIn);
        assertEquals(21, items[0].quality, "Backstage pass should increase by 1 when sellIn > 10");
    }

    @Test
    public void backstagePassIncreasesQualityBy2With6To10Days() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(9, items[0].sellIn);
        assertEquals(22, items[0].quality, "Backstage pass should increase by 2 when sellIn is between 6 and 10");
    }

    @Test
    public void backstagePassIncreasesQualityBy3With5DaysOrLess() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(4, items[0].sellIn);
        assertEquals(23, items[0].quality, "Backstage pass should increase by 3 when sellIn is 5 or less");
    }

    @Test
    public void backstagePassCannotExceed50Quality() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(4, items[0].sellIn);
        assertEquals(50, items[0].quality, "Backstage pass quality should not exceed 50");
    }

    // Teste 4: Conjured perde qualidade 2x mais rápido
    @Test
    public void conjuredItemDegradesTwiceAsFastBeforeSellIn() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 10, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(9, items[0].sellIn);
        assertEquals(18, items[0].quality, "Conjured item should degrade by 2 before sellIn date");
    }

    @Test
    public void conjuredItemDegradesTwiceAsFastAfterSellIn() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 0, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(-1, items[0].sellIn);
        assertEquals(16, items[0].quality, "Conjured item should degrade by 4 after sellIn date (2x the normal 2x)");
    }

    @Test
    public void conjuredItemQualityNeverGoesNegative() {
        Item[] items = new Item[] { new Item("Conjured Sword", 5, 1) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(4, items[0].sellIn);
        assertEquals(0, items[0].quality, "Conjured item quality should never go below 0");
    }

    // Teste adicional: Item normal
    @Test
    public void normalItemDegradesNormally() {
        Item[] items = new Item[] { new Item("+5 Dexterity Vest", 10, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(9, items[0].sellIn);
        assertEquals(19, items[0].quality, "Normal item should degrade by 1");
    }

    @Test
    public void normalItemDegradesTwiceAsFastAfterSellIn() {
        Item[] items = new Item[] { new Item("+5 Dexterity Vest", 0, 10) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(-1, items[0].sellIn);
        assertEquals(8, items[0].quality, "Normal item should degrade by 2 after sellIn");
    }

    @Test
    public void normalItemQualityNeverGoesNegative() {
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", 5, 0) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(4, items[0].sellIn);
        assertEquals(0, items[0].quality, "Normal item quality should never go below 0");
    }
}
