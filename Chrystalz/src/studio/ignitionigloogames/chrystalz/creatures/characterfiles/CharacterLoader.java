/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.creatures.characterfiles;

import java.io.File;
import java.io.IOException;

import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.VersionException;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyMember;
import studio.ignitionigloogames.chrystalz.dungeon.Extension;
import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;
import studio.ignitionigloogames.common.fileio.UnexpectedTagException;

public class CharacterLoader {
    private static PartyMember loadCharacter(final String name) {
        final String basePath = CharacterRegistration.getBasePath();
        final String loadPath = basePath + File.separator + name
                + Extension.getCharacterExtensionWithPeriod();
        try (FileIOReader loader = new FileIOReader(loadPath, "character")) {
            return PartyMember.read(loader);
        } catch (VersionException | UnexpectedTagException e) {
            CharacterRegistration.autoremoveCharacter(name);
            return null;
        } catch (final IOException e) {
            Chrystalz.getErrorLogger().logError(e);
            return null;
        }
    }

    public static PartyMember[] loadAllRegisteredCharacters() {
        final String[] registeredNames = CharacterRegistration
                .getCharacterNameList();
        if (registeredNames != null) {
            final PartyMember[] res = new PartyMember[registeredNames.length];
            // Load characters
            for (int x = 0; x < registeredNames.length; x++) {
                final String name = registeredNames[x];
                final PartyMember characterWithName = CharacterLoader
                        .loadCharacter(name);
                if (characterWithName != null) {
                    res[x] = characterWithName;
                } else {
                    // Auto-removed character
                    return CharacterLoader.loadAllRegisteredCharacters();
                }
            }
            return res;
        }
        return null;
    }

    public static void saveCharacter(final PartyMember character) {
        final String basePath = CharacterRegistration.getBasePath();
        final String name = character.getName();
        final String characterFile = basePath + File.separator + name
                + Extension.getCharacterExtensionWithPeriod();
        try (FileIOWriter saver = new FileIOWriter(characterFile,
                "character")) {
            character.write(saver);
        } catch (final IOException e) {
            Chrystalz.getErrorLogger().logError(e);
        }
    }

    static void deleteCharacter(final String name) {
        final String basePath = CharacterRegistration.getBasePath();
        final String characterFile = basePath + File.separator + name
                + Extension.getCharacterExtensionWithPeriod();
        final File toDelete = new File(characterFile);
        if (toDelete.exists()) {
            toDelete.delete();
        }
    }
}
