# Gilded Rose in Java with Approval Tests

This folder has a unit test that uses [Approvals](https://github.com/approvals/approvaltests.java)

There are two test cases here with different styles:

* "foo" is more similar to the unit test from the 'Java' version
* "thirtyDays" is more similar to the TextTest from the 'Java' version

I suggest choosing one style to develop and deleting the other.
# Refatoracao_DR4_TP1

1 - Todos métodos sendo separados da lógica agrupada de cadeias de 'for'

```
private void updateAgedBrie(Item item) {
        increaseQuality(item);
        item.sellIn = item.sellIn - 1;

        if (item.sellIn < 0) {
            increaseQuality(item);
        }
    }

    public void update(Item item) {
        // Sulfuras ainda não possuem regras de atualização, então não é necessário implementar nada aqui.
    }

    private void updateBackstagePasses(Item item) {
        increaseQuality(item);

        if (item.sellIn < 11) {
            increaseQuality(item);
        }

        if (item.sellIn < 6) {
            increaseQuality(item);
        }

        item.sellIn = item.sellIn - 1;

        if (item.sellIn < 0) {
            item.quality = 0;
        }
    }

    private void updateNormalItem(Item item) {
        decreaseQuality(item);
        item.sellIn = item.sellIn - 1;

        if (item.sellIn < 0) {
            decreaseQuality(item);
        }
    }
```

2 - Refatoração Gilded Rose

```

class GildedRose {
    Item[] items;
    private final ItemUpdater agedBrieUpdater = new AgedBrieUpdater();
    private final ItemUpdater sulfurasUpdater = new SulfurasUpdater();
    private final ItemUpdater backstagePassUpdater = new BackstagePassUpdater();
    private final ItemUpdater defaultUpdater = new DefaultUpdater();

    public GildedRose(Item[] items) {
        this.items = items;
    }

    // Separação de Quality e Items
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
        } else {
            return defaultUpdater;
        }
    }
}

```

3 - Implementação de Conjured e testes

```
...
@Test
    public void conjuredItemMultipleDays() {
        Item[] items = new Item[] { new Item("Conjured Elixir", 3, 10) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        assertEquals(2, items[0].sellIn);
        assertEquals(8, items[0].quality);
        
        app.updateQuality();
        assertEquals(1, items[0].sellIn);
        assertEquals(6, items[0].quality);
        
        app.updateQuality();
        assertEquals(0, items[0].sellIn);
        assertEquals(4, items[0].quality);
        
        app.updateQuality();
        assertEquals(-1, items[0].sellIn);
        assertEquals(0, items[0].quality);
        
        app.updateQuality();
        assertEquals(-2, items[0].sellIn);
        assertEquals(0, items[0].quality);
    }
...
```

4 - Implementação dos outros testes para os métodos anteriores em GildedRoseTest.java

Resultados finais:

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.gildedrose.GildedRoseTest
[INFO] Tests run: 17, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.027 s - in com.gildedrose.GildedRoseTest
[INFO] Running com.gildedrose.ConjuredItemTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.003 s - in com.gildedrose.ConjuredItemTest
[INFO] Running com.gildedrose.GildedRoseApprovalTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.445 s - in com.gildedrose.GildedRoseApprovalTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 25, Failures: 0, Errors: 0, Skipped: 0
```

5 - Perguntas e Respostas

**Pergunta 1:** 

A estrutura atual facilita a adição de novos tipos de item?
Justifique com base no Princípio Aberto-Fechado.
Resposta: Sim. Respeita o Princípio Aberto-Fechado:
Aberta para extensão → novo tipo = nova classe que implementa ItemUpdater.
Fechada para modificação → não é preciso alterar GildedRose, o loop ou a lógica de escolha do updater. 

**Pergunta 2:** 

A implementação dos ItemUpdater respeita o Princípio da Responsabilidade Única? Explique.
Resposta: Sim.
Cada classe tem uma única responsabilidade: implementar as regras de atualização de um tipo específico de item (ou família muito coesa).
Exemplos:

NormalItemUpdater → só itens comuns
AgedBrieUpdater → só Aged Brie
BackstagePassUpdater → só passes de show
SulfurasUpdater → só itens lendários
ConjuredUpdater → só itens conjurados

Se a regra de um item mudar, só aquela classe é tocada → responsabilidade única preservada.

**Pergunta 3:** 

Alguma violação do Princípio de Substituição de Liskov pode ser identificada em sua hierarquia? Se sim, corrija-a.
Resposta: Não.
Todas as classes que implementam ItemUpdater são substituíveis entre si sem quebrar o programa, porque:

Cumprem exatamente o mesmo contrato: void update(Item item), o cliente pode usar qualquer updater no lugar de outro e o comportamento esperado continua correto
