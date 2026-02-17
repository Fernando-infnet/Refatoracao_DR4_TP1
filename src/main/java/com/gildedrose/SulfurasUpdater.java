package com.gildedrose;

class SulfurasUpdater implements ItemUpdater {
    @Override
    public void update(Item item) {
        // Sulfuras never has to be sold nor does it decrease in Quality
        // So it doesn't change at all
    }
}
