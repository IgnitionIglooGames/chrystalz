/*  TallerTower: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.dungeon.utilities;

import java.io.IOException;
import java.util.ArrayList;

import studio.ignitionigloogames.chrystalz.assetmanagers.ImageTransformer;
import studio.ignitionigloogames.chrystalz.assetmanagers.ObjectImageManager;
import studio.ignitionigloogames.chrystalz.dungeon.FormatConstants;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractGameObject;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Amulet;
import studio.ignitionigloogames.chrystalz.dungeon.objects.ArmorShop;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Bank;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Button;
import studio.ignitionigloogames.chrystalz.dungeon.objects.ClockwiseRotationTrap;
import studio.ignitionigloogames.chrystalz.dungeon.objects.ClosedDoor;
import studio.ignitionigloogames.chrystalz.dungeon.objects.ConfusionTrap;
import studio.ignitionigloogames.chrystalz.dungeon.objects.CounterclockwiseRotationTrap;
import studio.ignitionigloogames.chrystalz.dungeon.objects.DarkGem;
import studio.ignitionigloogames.chrystalz.dungeon.objects.DizzinessTrap;
import studio.ignitionigloogames.chrystalz.dungeon.objects.DrunkTrap;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Empty;
import studio.ignitionigloogames.chrystalz.dungeon.objects.EmptyVoid;
import studio.ignitionigloogames.chrystalz.dungeon.objects.EnhancementShop;
import studio.ignitionigloogames.chrystalz.dungeon.objects.FaithPowerShop;
import studio.ignitionigloogames.chrystalz.dungeon.objects.HealShop;
import studio.ignitionigloogames.chrystalz.dungeon.objects.HealTrap;
import studio.ignitionigloogames.chrystalz.dungeon.objects.HurtTrap;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Ice;
import studio.ignitionigloogames.chrystalz.dungeon.objects.ItemShop;
import studio.ignitionigloogames.chrystalz.dungeon.objects.LightGem;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Monster;
import studio.ignitionigloogames.chrystalz.dungeon.objects.OpenDoor;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Regenerator;
import studio.ignitionigloogames.chrystalz.dungeon.objects.SealingWall;
import studio.ignitionigloogames.chrystalz.dungeon.objects.SocksShop;
import studio.ignitionigloogames.chrystalz.dungeon.objects.SpellShop;
import studio.ignitionigloogames.chrystalz.dungeon.objects.StairsDown;
import studio.ignitionigloogames.chrystalz.dungeon.objects.StairsUp;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Tile;
import studio.ignitionigloogames.chrystalz.dungeon.objects.UTurnTrap;
import studio.ignitionigloogames.chrystalz.dungeon.objects.VariableHealTrap;
import studio.ignitionigloogames.chrystalz.dungeon.objects.VariableHurtTrap;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Wall;
import studio.ignitionigloogames.chrystalz.dungeon.objects.WallOff;
import studio.ignitionigloogames.chrystalz.dungeon.objects.WallOn;
import studio.ignitionigloogames.chrystalz.dungeon.objects.WarpTrap;
import studio.ignitionigloogames.chrystalz.dungeon.objects.WeaponsShop;
import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.images.BufferedImageIcon;

public class GameObjectList {
    // Fields
    private final ArrayList<AbstractGameObject> allObjectList;

    // Constructor
    public GameObjectList() {
        final AbstractGameObject[] allObjects = { new ArmorShop(), new Bank(),
                new ClockwiseRotationTrap(), new ClosedDoor(),
                new ConfusionTrap(), new CounterclockwiseRotationTrap(),
                new DarkGem(), new DizzinessTrap(), new DrunkTrap(),
                new Empty(), new EmptyVoid(), new EnhancementShop(),
                new FaithPowerShop(), new HealShop(), new HealTrap(),
                new HurtTrap(), new Ice(), new ItemShop(), new LightGem(),
                new Monster(), new OpenDoor(), new Regenerator(),
                new SealingWall(), new SocksShop(), new SpellShop(), new Tile(),
                new UTurnTrap(), new VariableHealTrap(), new VariableHurtTrap(),
                new Wall(), new WarpTrap(), new WeaponsShop(), new StairsUp(),
                new StairsDown(), new WallOff(), new WallOn(), new Button(),
                new Amulet() };
        this.allObjectList = new ArrayList<>();
        // Add all predefined objects to the list
        for (final AbstractGameObject allObject : allObjects) {
            this.allObjectList.add(allObject);
        }
    }

    // Methods
    public AbstractGameObject[] getAllObjects() {
        return this.allObjectList
                .toArray(new AbstractGameObject[this.allObjectList.size()]);
    }

    public String[] getAllDescriptions() {
        final AbstractGameObject[] objects = this.getAllObjects();
        final String[] allDescriptions = new String[objects.length];
        for (int x = 0; x < objects.length; x++) {
            allDescriptions[x] = objects[x].getDescription();
        }
        return allDescriptions;
    }

    public BufferedImageIcon[] getAllEditorAppearances() {
        final AbstractGameObject[] objects = this.getAllObjects();
        final BufferedImageIcon[] allEditorAppearances = new BufferedImageIcon[objects.length];
        for (int x = 0; x < allEditorAppearances.length; x++) {
            allEditorAppearances[x] = ImageTransformer.getTransformedImage(
                    ObjectImageManager.getImage(objects[x].getName(),
                            objects[x].getBaseID(),
                            AbstractGameObject.getTemplateColor()),
                    ImageTransformer.getGraphicSize());
        }
        return allEditorAppearances;
    }

    public final AbstractGameObject[] getAllRequired(final int layer) {
        final AbstractGameObject[] objects = this.getAllObjects();
        final AbstractGameObject[] tempAllRequired = new AbstractGameObject[objects.length];
        int x;
        int count = 0;
        for (x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == layer && objects[x].isRequired()) {
                tempAllRequired[count] = objects[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final AbstractGameObject[] allRequired = new AbstractGameObject[count];
            for (x = 0; x < count; x++) {
                allRequired[x] = tempAllRequired[x];
            }
            return allRequired;
        }
    }

    public final AbstractGameObject[] getAllWithoutPrerequisiteAndNotRequired(
            final int layer) {
        final AbstractGameObject[] objects = this.getAllObjects();
        final AbstractGameObject[] tempAllWithoutPrereq = new AbstractGameObject[objects.length];
        int x;
        int count = 0;
        for (x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == layer && !objects[x].isRequired()) {
                tempAllWithoutPrereq[count] = objects[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final AbstractGameObject[] allWithoutPrereq = new AbstractGameObject[count];
            for (x = 0; x < count; x++) {
                allWithoutPrereq[x] = tempAllWithoutPrereq[x];
            }
            return allWithoutPrereq;
        }
    }

    public final AbstractGameObject getNewInstanceByName(final String name) {
        final AbstractGameObject[] objects = this.getAllObjects();
        AbstractGameObject instance = null;
        int x;
        for (x = 0; x < objects.length; x++) {
            if (objects[x].getName().equals(name)) {
                instance = objects[x];
                break;
            }
        }
        if (instance == null) {
            return null;
        } else {
            return instance.clone();
        }
    }

    public AbstractGameObject readGameObject(final FileIOReader reader,
            final int formatVersion) throws IOException {
        final AbstractGameObject[] objects = this.getAllObjects();
        AbstractGameObject o = null;
        String UID = "";
        if (formatVersion == FormatConstants.MAZE_FORMAT_LATEST) {
            UID = reader.readString();
        }
        for (final AbstractGameObject object : objects) {
            AbstractGameObject instance;
            instance = object.clone();
            if (formatVersion == FormatConstants.MAZE_FORMAT_LATEST) {
                o = instance.readGameObjectV1(reader, UID);
                if (o != null) {
                    return o;
                }
            }
        }
        return null;
    }

    public AbstractGameObject readSavedGameObject(final FileIOReader reader,
            final String UID, final int formatVersion) throws IOException {
        final AbstractGameObject[] objects = this.getAllObjects();
        AbstractGameObject o = null;
        for (final AbstractGameObject object : objects) {
            AbstractGameObject instance;
            instance = object.clone();
            if (formatVersion == FormatConstants.MAZE_FORMAT_LATEST) {
                o = instance.readGameObjectV1(reader, UID);
                if (o != null) {
                    return o;
                }
            }
        }
        return null;
    }
}
