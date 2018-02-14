/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.shops;

import javax.swing.JOptionPane;

import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyMember;
import studio.ignitionigloogames.chrystalz.items.Equipment;
import studio.ignitionigloogames.chrystalz.items.EquipmentFactory;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundManager;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;

public class Shop {
    // Fields
    final int type;
    int index;
    int defaultChoice;
    String[] choices;
    String[] typeChoices;
    int typeDefault;
    String typeResult;
    int typeIndex;
    boolean handIndex;
    String result;
    int cost;
    boolean twoHanded;
    private final ShopDialogGUI ui;
    static final int MAX_ENHANCEMENTS = 9;

    // Constructors
    public Shop(final int shopType) {
        super();
        this.ui = new ShopDialogGUI();
        this.type = shopType;
        this.index = 0;
        this.twoHanded = false;
    }

    // Methods
    public static int getEquipmentCost(final int x) {
        return 10 * x * x * x + 10 * x * x + 10 * x + 10;
    }

    static int getHealingCost(final int x, final int y, final int z) {
        return (int) (Math.log10(x) * (z - y));
    }

    static int getRegenerationCost(final int x, final int y, final int z) {
        final int diff = z - y;
        if (diff == 0) {
            return 0;
        } else {
            final int cost = (int) (Math.log(x) / Math.log(2) * diff);
            if (cost < 1) {
                return 1;
            } else {
                return cost;
            }
        }
    }

    static int getSpellCost(final int i) {
        if (i == -1) {
            return 0;
        } else {
            return 20 * i * i + 20;
        }
    }

    String getShopNameFromType() {
        return ShopTypes.SHOP_NAMES[this.type - 1];
    }

    public void showShop() {
        this.ui.showShop();
    }

    private class ShopDialogGUI {
        public ShopDialogGUI() {
            // Do nothing
        }

        public void showShop() {
            Shop.this.index = 0;
            Shop.this.defaultChoice = 0;
            Shop.this.choices = null;
            Shop.this.typeChoices = null;
            Shop.this.typeDefault = 0;
            Shop.this.typeResult = null;
            Shop.this.typeIndex = 0;
            Shop.this.handIndex = false;
            Shop.this.result = null;
            Shop.this.cost = 0;
            Shop.this.twoHanded = false;
            boolean valid = this.shopStage1();
            if (valid) {
                valid = this.shopStage2();
                if (valid) {
                    valid = this.shopStage3();
                    if (valid) {
                        valid = this.shopStage4();
                        if (valid) {
                            this.shopStage5();
                        }
                    }
                }
            }
        }

        private boolean shopStage1() {
            // Stage 2
            final PartyMember playerCharacter = PartyManager.getParty()
                    .getLeader();
            if (Shop.this.type == ShopTypes.SHOP_TYPE_WEAPONS) {
                Shop.this.choices = EquipmentFactory.createWeaponNames(
                        playerCharacter.getCaste().getCasteID());
            } else if (Shop.this.type == ShopTypes.SHOP_TYPE_ARMOR) {
                Shop.this.choices = EquipmentFactory
                        .createArmorNames(Shop.this.typeIndex);
            } else if (Shop.this.type == ShopTypes.SHOP_TYPE_HEALER
                    || Shop.this.type == ShopTypes.SHOP_TYPE_REGENERATOR) {
                Shop.this.choices = new String[10];
                int x;
                for (x = 0; x < Shop.this.choices.length; x++) {
                    Shop.this.choices[x] = Integer.toString((x + 1) * 10) + "%";
                }
                Shop.this.defaultChoice = 9;
            } else if (Shop.this.type == ShopTypes.SHOP_TYPE_SPELLS) {
                Shop.this.choices = playerCharacter.getSpellBook()
                        .getAllSpellsToLearnNames();
                if (Shop.this.choices == null) {
                    Shop.this.choices = new String[1];
                    Shop.this.choices[0] = "No Spells Left To Learn";
                }
            } else {
                // Invalid shop type
                return false;
            }
            return true;
        }

        private boolean shopStage2() {
            // Stage 3
            final PartyMember playerCharacter = PartyManager.getParty()
                    .getLeader();
            // Check
            if (Shop.this.type == ShopTypes.SHOP_TYPE_HEALER && playerCharacter
                    .getCurrentHP() == playerCharacter.getMaximumHP()) {
                CommonDialogs.showDialog("You don't need healing.");
                return false;
            } else if (Shop.this.type == ShopTypes.SHOP_TYPE_REGENERATOR
                    && playerCharacter.getCurrentMP() == playerCharacter
                            .getMaximumMP()) {
                CommonDialogs.showDialog("You don't need regeneration.");
                return false;
            } else if (Shop.this.type == ShopTypes.SHOP_TYPE_SPELLS
                    && playerCharacter.getSpellBook()
                            .getSpellsKnownCount() == playerCharacter
                                    .getSpellBook()
                                    .getMaximumSpellsKnownCount()) {
                CommonDialogs.showDialog("There are no more spells to learn.");
                return false;
            }
            Shop.this.result = CommonDialogs.showInputDialog("Select",
                    Shop.this.getShopNameFromType(), Shop.this.choices,
                    Shop.this.choices[Shop.this.defaultChoice]);
            if (Shop.this.result == null) {
                return false;
            }
            if (Shop.this.index == -1) {
                return false;
            }
            Shop.this.index = 0;
            if (Shop.this.type != ShopTypes.SHOP_TYPE_SPELLS) {
                for (Shop.this.index = 0; Shop.this.index < Shop.this.choices.length; Shop.this.index++) {
                    if (Shop.this.result
                            .equals(Shop.this.choices[Shop.this.index])) {
                        break;
                    }
                }
            } else {
                Shop.this.index = playerCharacter.getSpellBook()
                        .getSpellIDByName(Shop.this.result);
            }
            return true;
        }

        private boolean shopStage3() {
            // Stage 4
            final PartyMember playerCharacter = PartyManager.getParty()
                    .getLeader();
            Shop.this.cost = 0;
            if (Shop.this.type == ShopTypes.SHOP_TYPE_WEAPONS
                    || Shop.this.type == ShopTypes.SHOP_TYPE_ARMOR) {
                Shop.this.cost = Shop.getEquipmentCost(Shop.this.index);
                if (Shop.this.type == ShopTypes.SHOP_TYPE_WEAPONS) {
                    if (Shop.this.typeResult != null) {
                        if (Shop.this.typeIndex == 1) {
                            // Two-Handed Weapon, price doubled
                            Shop.this.cost *= 2;
                        }
                    }
                }
            } else if (Shop.this.type == ShopTypes.SHOP_TYPE_HEALER) {
                Shop.this.cost = Shop.getHealingCost(playerCharacter.getLevel(),
                        playerCharacter.getCurrentHP(),
                        playerCharacter.getMaximumHP());
            } else if (Shop.this.type == ShopTypes.SHOP_TYPE_REGENERATOR) {
                Shop.this.cost = Shop.getRegenerationCost(
                        playerCharacter.getLevel(),
                        playerCharacter.getCurrentMP(),
                        playerCharacter.getMaximumMP());
            } else if (Shop.this.type == ShopTypes.SHOP_TYPE_SPELLS) {
                Shop.this.cost = Shop.getSpellCost(Shop.this.index);
            }
            // Confirm
            final int stage4Confirm = CommonDialogs.showConfirmDialog(
                    "This will cost " + Shop.this.cost + " Gold. Are you sure?",
                    Shop.this.getShopNameFromType());
            if (stage4Confirm == JOptionPane.NO_OPTION
                    || stage4Confirm == JOptionPane.CLOSED_OPTION) {
                return false;
            }
            return true;
        }

        private boolean shopStage4() {
            // Stage 5
            final PartyMember playerCharacter = PartyManager.getParty()
                    .getLeader();
            if (playerCharacter.getGold() < Shop.this.cost) {
                CommonDialogs.showErrorDialog("Not Enough Gold!",
                        Shop.this.getShopNameFromType());
                return false;
            }
            return true;
        }

        private void shopStage5() {
            // Stage 6
            final PartyMember playerCharacter = PartyManager.getParty()
                    .getLeader();
            // Play transact sound
            SoundManager.playSound(SoundConstants.SOUND_TRANSACT);
            if (Shop.this.type == ShopTypes.SHOP_TYPE_WEAPONS) {
                playerCharacter.offsetGold(-Shop.this.cost);
                final Equipment bought = EquipmentFactory.createWeapon(
                        Shop.this.index,
                        playerCharacter.getCaste().getCasteID());
                playerCharacter.getItems().equipWeapon(playerCharacter, bought,
                        true);
            } else if (Shop.this.type == ShopTypes.SHOP_TYPE_ARMOR) {
                playerCharacter.offsetGold(-Shop.this.cost);
                final Equipment bought = EquipmentFactory
                        .createArmor(Shop.this.index, Shop.this.typeIndex);
                playerCharacter.getItems().equipArmor(playerCharacter, bought,
                        true);
            } else if (Shop.this.type == ShopTypes.SHOP_TYPE_HEALER) {
                playerCharacter.offsetGold(-Shop.this.cost);
                playerCharacter.healPercentage((Shop.this.index + 1) * 10);
            } else if (Shop.this.type == ShopTypes.SHOP_TYPE_REGENERATOR) {
                playerCharacter.offsetGold(-Shop.this.cost);
                playerCharacter
                        .regeneratePercentage((Shop.this.index + 1) * 10);
            } else if (Shop.this.type == ShopTypes.SHOP_TYPE_SPELLS) {
                playerCharacter.offsetGold(-Shop.this.cost);
                if (Shop.this.index != -1) {
                    playerCharacter.getSpellBook()
                            .learnSpellByID(Shop.this.index);
                }
            }
        }
    }
}
