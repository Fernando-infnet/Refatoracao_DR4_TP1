package com.gildedrose;

/**
 * STRATEGY PATTERN - Interface para atualização de itens
 * 
 * Esta interface define o contrato que todas as estratégias de atualização
 * devem seguir. Cada tipo de item (Aged Brie, Sulfuras, etc) tem sua própria
 * implementação com lógica específica.
 * 
 * PADRÃO: Strategy Pattern (Behavioral)
 * PRINCÍPIOS: Open/Closed, Liskov Substitution, Dependency Inversion
 * 
 * Implementações:
 * • AgedBrieUpdater: Aumenta qualidade com o tempo
 * • SulfurasUpdater: Nunca muda (implementação vazia)
 * • BackstagePassUpdater: Aumenta qualidade progressivamente até o evento
 * • ConjuredItemUpdater: Decresce qualidade 2x mais rápido
 * • DefaultUpdater: Comportamento padrão (decresce qualidade)
 */
interface ItemUpdater {
    /**
     * Atualiza as propriedades de qualidade e sellIn do item.
     * 
     * @param item O item a ser atualizado. Pode ser modificado in-place.
     */
    void update(Item item);
}

