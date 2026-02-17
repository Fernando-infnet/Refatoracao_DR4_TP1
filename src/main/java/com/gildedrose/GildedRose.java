package com.gildedrose;

class GildedRose {
    Item[] items;
    private final ItemUpdater agedBrieUpdater = new AgedBrieUpdater();
    private final ItemUpdater sulfurasUpdater = new SulfurasUpdater();
    private final ItemUpdater backstagePassUpdater = new BackstagePassUpdater();
    private final ItemUpdater conjuredItemUpdater = new ConjuredItemUpdater();
    private final ItemUpdater defaultUpdater = new DefaultUpdater();

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            ItemUpdater updater = getUpdaterForItem(items[i]);
            updater.update(items[i]);
        }
    }

    private ItemUpdater getUpdaterForItem(Item item) {
        if (item.name.equals("Aged Brie")) {
            return agedBrieUpdater;
        } else if (item.name.equals("Sulfuras, Hand of Ragnaros")) {
            return sulfurasUpdater;
        } else if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            return backstagePassUpdater;
        } else if (item.name.startsWith("Conjured")) {
            return conjuredItemUpdater;
        } else {
            return defaultUpdater;
        }
    }
}