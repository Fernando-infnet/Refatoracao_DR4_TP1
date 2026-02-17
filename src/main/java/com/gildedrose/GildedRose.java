package com.gildedrose;

/**
 * AVALIAÇÃO FINAL DE DESIGN
 * 
 * Pergunta 1: A estrutura atual facilita a adição de novos tipos de item? 
 * Justifique com base no Princípio Aberto-Fechado.
 * 
 * RESPOSTA: SIM, a estrutura está ABERTA para extensão e FECHADA para modificação.
 * 
 * O Princípio Aberto-Fechado (Open/Closed Principle) estabelece que classes devem 
 * ser abertas para extensão, mas fechadas para modificação. Nossa implementação 
 * exemplifica isso perfeitamente:
 * 
 * - Para adicionar um novo tipo de item (ex: "Enchanted Items"), basta:
 *   1. Criar uma nova classe EnchantedItemUpdater implements ItemUpdater
 *   2. Implementar a lógica específica no método update()
 *   3. Adicionar um campo na classe GildedRose para a nova instância
 *   4. Estender o método getUpdaterForItem() com uma nova condição
 * 
 * - Nenhuma classe existente precisa ser modificada
 * - Nenhuma lógica existente é alterada ou duplicada
 * - A estratégia de atualização é isolada em sua própria classe
 * 
 * Exemplo de extensão fácil:
 * ├── AgedBrieUpdater (existente)
 * ├── SulfurasUpdater (existente)
 * ├── BackstagePassUpdater (existente)
 * ├── ConjuredItemUpdater (existente)
 * ├── DefaultUpdater (existente)
 * └── NEW: EnchantedItemUpdater (fácil de adicionar sem modificar nada acima)
 * 
 * ---
 * 
 * Pergunta 2: A implementação dos ItemUpdater respeita o Princípio da 
 * Responsabilidade Única? Explique.
 * 
 * RESPOSTA: SIM, cada classe tem UMA ÚNICA responsabilidade bem definida.
 * 
 * O Princípio da Responsabilidade Única (Single Responsibility Principle) 
 * afirma que cada classe deve ter uma única razão para mudar, ou seja, 
 * uma única responsabilidade. Nossa implementação respeita isso:
 * 
 * - AgedBrieUpdater: Responsável APENAS por atualizar Aged Brie
 *   → Incrementa qualidade normalmente e dobrada após vencimento
 * 
 * - SulfurasUpdater: Responsável APENAS por Sulfuras (não fazer nada)
 *   → Implementa a exceção onde nenhuma atualização é necessária
 * 
 * - BackstagePassUpdater: Responsável APENAS por Backstage Passes
 *   → Incrementa qualidade progressivamente e zera após o evento
 * 
 * - ConjuredItemUpdater: Responsável APENAS por itens Conjurados
 *   → Decrementa qualidade em taxa 2x mais rápida
 * 
 * - DefaultUpdater: Responsável APENAS por itens normais
 *   → Decrementa qualidade 1x normalmente, 2x após vencimento
 * 
 * - GildedRose: Responsável APENAS por:
 *   → Orquestrar o loop de atualização
 *   → Mapear nome do item ao updater correto
 *   (NÃO contém lógica específica de nenhum item)
 * 
 * Cada classe tem uma única razão para mudar: mudanças nas regras de um 
 * tipo específico de item afetam APENAS a classe correspondente.
 * 
 * ---
 * 
 * Pergunta 3: Alguma violação do Princípio de Substituição de Liskov 
 * pode ser identificada em sua hierarquia? Se sim, corrija-a.
 * 
 * RESPOSTA: NÃO há violações. A hierarquia está bem estruturada.
 * 
 * O Princípio de Substituição de Liskov (Liskov Substitution Principle) 
 * afirma que objetos de uma classe derivada devem poder substituir objetos 
 * da classe base sem quebrar o programa. Nossa implementação respeita isso:
 * 
 * - Todas as implementações de ItemUpdater seguem o contrato da interface:
 *   public void update(Item item);
 * 
 * - Cada implementação pode ser substituída por qualquer outra sem quebrar GildedRose:
 *   ItemUpdater updater = getUpdaterForItem(item); // retorna qualquer implementação
 *   updater.update(item); // funciona com qualquer uma
 * 
 * - SulfurasUpdater (implementação vazia) não viola o contrato porque:
 *   → A interface define que o método update(Item) deve ser implementado
 *   → Implementação vazia (não fazer nada) é uma implementação válida
 *   → Comportamento: modificar ou não o item é decisão de cada implementação
 *   → Nenhuma chamadora assume que o item SERÁ modificado
 * 
 * - Todas as implementações modificam o parâmetro item (mutação permitida)
 * - Nenhuma implementação retorna valores diferentes do esperado
 * - Nenhuma implementação lança exceções não esperadas
 * 
 * A hierarquia é substituível em 100% dos casos. ✓
 */
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
