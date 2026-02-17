package com.gildedrose;

/**
 * Estratégia de atualização para "Aged Brie"
 * 
 * REGRAS DE NEGÓCIO:
 * • Aged Brie aumenta de qualidade conforme envelhece
 * • Qualidade máxima: 50
 * • Após vencimento (sellIn < 0): aumenta qualidade 2x mais rápido
 * 
 * EXEMPLO DE COMPORTAMENTO:
 * Dia 0: sellIn=10, quality=20
 * Dia 1: sellIn=9,  quality=21  (aumenta +1)
 * Dia 10: sellIn=0, quality=30
 * Dia 11: sellIn=-1, quality=32 (aumenta +2 após vencimento)
 * 
 * PRINCÍPIOS APLICADOS:
 * • SRP: Responsável APENAS pela lógica de Aged Brie
 * • OCP: Pode ser estendida sem modificar código existente
 * • LSP: Substitui ItemUpdater sem quebrar contratos
 */
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

