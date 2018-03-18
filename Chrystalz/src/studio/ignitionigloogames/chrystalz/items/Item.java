/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.items;

import java.io.IOException;

import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;

public class Item {
    // Properties
    private String name;
    private int buyPrice;
    private int sellPrice;
    private int weight;
    private int potency;

    // Constructors
    public Item() {
        super();
        this.name = "Un-named Item";
        this.buyPrice = 0;
        this.sellPrice = 0;
        this.weight = 0;
        this.potency = 0;
    }

    public Item(final String itemName) {
        super();
        this.name = itemName;
        this.buyPrice = 0;
        this.sellPrice = 0;
        this.weight = 0;
        this.potency = 0;
    }

    protected Item(final String iName, final Item i) {
        super();
        this.name = iName;
        this.buyPrice = i.buyPrice;
        this.sellPrice = i.sellPrice;
        this.weight = i.weight;
        this.potency = i.potency;
    }

    // Methods
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.buyPrice;
        result = prime * result
                + (this.name == null ? 0 : this.name.hashCode());
        result = prime * result + this.potency;
        result = prime * result + this.sellPrice;
        return prime * result + this.weight;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.buyPrice != other.buyPrice) {
            return false;
        }
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        if (this.potency != other.potency) {
            return false;
        }
        if (this.sellPrice != other.sellPrice) {
            return false;
        }
        if (this.weight != other.weight) {
            return false;
        }
        return true;
    }

    public final void setName(final String newName) {
        this.name = newName;
    }

    public final void setPotency(final int newPotency) {
        this.potency = newPotency;
    }

    public final void setBuyPrice(final int newBuyPrice) {
        this.buyPrice = newBuyPrice;
    }

    public final void setSellPrice(final int newSellPrice) {
        this.sellPrice = newSellPrice;
    }

    public String getName() {
        return this.name;
    }

    public final int getBuyPrice() {
        return this.buyPrice;
    }

    public final int getPotency() {
        return this.potency;
    }

    public final int getWeight() {
        return this.weight;
    }

    protected static Item readItem(final FileIOReader dr) throws IOException {
        final String itemName = dr.readString();
        if (itemName.equals("null")) {
            // Abort
            return null;
        }
        final Item i = new Item(itemName);
        i.buyPrice = dr.readInt();
        i.sellPrice = dr.readInt();
        i.weight = dr.readInt();
        i.potency = dr.readInt();
        return i;
    }

    final void writeItem(final FileIOWriter dw) throws IOException {
        dw.writeString(this.name);
        dw.writeInt(this.buyPrice);
        dw.writeInt(this.sellPrice);
        dw.writeInt(this.weight);
        dw.writeInt(this.potency);
    }
}
