package com.gildedrose;

class AgedBrieUpdater implements ItemUpdater {
    @Override
    public void update(Item item) {
        increaseQuality(item);
        item.sellIn = item.sellIn - 1;

        if (item.sellIn < 0) {
            increaseQuality(item);
        }
    }

    private void increaseQuality(Item item) {
        if (item.quality < 50) {
            item.quality = item.quality + 1;
        }
    }
}
