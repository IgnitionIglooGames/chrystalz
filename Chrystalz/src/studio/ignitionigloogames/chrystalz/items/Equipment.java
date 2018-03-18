/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.items;

import java.io.IOException;

import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;

public class Equipment extends Item {
    // Properties
    private final int equipCat;
    private final int materialID;
    private int slotUsed;
    private final int hitSound;

    // Constructors
    private Equipment(final Item i, final int equipCategory,
            final int newMaterialID) {
        super(i.getName(), i.getInitialUses(), i.getWeightPerUse());
        this.equipCat = equipCategory;
        this.materialID = newMaterialID;
        this.slotUsed = EquipmentSlotConstants.SLOT_NONE;
        this.hitSound = -1;
    }

    Equipment(final String itemName, final int itemInitialUses,
            final int itemWeightPerUse, final int equipCategory,
            final int newMaterialID) {
        super(itemName, itemInitialUses, itemWeightPerUse);
        this.equipCat = equipCategory;
        this.materialID = newMaterialID;
        this.slotUsed = EquipmentSlotConstants.SLOT_NONE;
        this.hitSound = -1;
    }

    Equipment(final String itemName, final int itemInitialUses,
            final int itemWeightPerUse, final int equipCategory,
            final int newMaterialID, final int hitSoundID) {
        super(itemName, itemInitialUses, itemWeightPerUse);
        this.equipCat = equipCategory;
        this.materialID = newMaterialID;
        this.slotUsed = EquipmentSlotConstants.SLOT_NONE;
        this.hitSound = hitSoundID;
    }

    Equipment(final Equipment e) {
        super(e.getName(), e);
        this.equipCat = e.equipCat;
        this.materialID = e.materialID;
        this.slotUsed = e.slotUsed;
        this.hitSound = e.hitSound;
    }

    // Methods
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + this.equipCat;
        result = prime * result + this.slotUsed;
        return prime * result + this.materialID;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Equipment)) {
            return false;
        }
        final Equipment other = (Equipment) obj;
        if (this.equipCat != other.equipCat) {
            return false;
        }
        if (this.slotUsed != other.slotUsed) {
            return false;
        }
        if (this.materialID != other.materialID) {
            return false;
        }
        return true;
    }

    public final int getHitSound() {
        return this.hitSound;
    }

    public final int getSlotUsed() {
        return this.slotUsed;
    }

    public final void setSlotUsed(final int newSlotUsed) {
        this.slotUsed = newSlotUsed;
    }

    public final int getEquipCategory() {
        return this.equipCat;
    }

    public final int getMaterial() {
        return this.materialID;
    }

    static Equipment readEquipment(final FileIOReader dr) throws IOException {
        final Item i = Item.readItem(dr);
        if (i == null) {
            // Abort
            return null;
        }
        final int matID = dr.readInt();
        final int eCat = dr.readInt();
        final Equipment ei = new Equipment(i, eCat, matID);
        ei.slotUsed = dr.readInt();
        return ei;
    }

    final void writeEquipment(final FileIOWriter dw) throws IOException {
        super.writeItem(dw);
        dw.writeInt(this.materialID);
        dw.writeInt(this.equipCat);
        dw.writeInt(this.slotUsed);
    }
}
