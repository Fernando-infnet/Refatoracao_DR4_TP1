# Gilded Rose in Java with Approval Tests

This folder has a unit test that uses [Approvals](https://github.com/approvals/approvaltests.java)

There are two test cases here with different styles:

* "foo" is more similar to the unit test from the 'Java' version
* "thirtyDays" is more similar to the TextTest from the 'Java' version

I suggest choosing one style to develop and deleting the other.
# Refatoracao_DR4_TP1

1- Todos métodos sendo separados da lógica agrupada de cadeias de 'for'

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
