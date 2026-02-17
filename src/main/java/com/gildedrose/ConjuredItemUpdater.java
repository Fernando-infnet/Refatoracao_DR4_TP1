package com.gildedrose;

class ConjuredItemUpdater implements ItemUpdater {
    @Override
    public void update(Item item) {
        decreaseQuality(item);
        decreaseQuality(item);
        item.sellIn = item.sellIn - 1;

        if (item.sellIn < 0) {
            decreaseQuality(item);
            decreaseQuality(item);
        }
    }

    private void decreaseQuality(Item item) {
        if (item.quality > 0) {
            item.quality = item.quality - 1;
        }
    }
}
