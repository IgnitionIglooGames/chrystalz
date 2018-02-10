/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.creatures.party;

import java.io.IOException;

import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;

public class PartyManager {
    // Fields
    private static Party party;

    // Constructors
    private PartyManager() {
        // Do nothing
    }

    // Methods
    public static boolean createParty() {
        PartyManager.party = new Party();
        PartyMember pc = PartyManager.getNewPCInstance();
        PartyManager.party.addPartyMember(pc);
        return true;
    }

    public static Party getParty() {
        return PartyManager.party;
    }

    public static void loadGameHook(final FileIOReader partyFile)
            throws IOException {
        final boolean containsPCData = partyFile.readBoolean();
        if (containsPCData) {
            PartyManager.party = Party.read(partyFile);
        }
    }

    public static void saveGameHook(final FileIOWriter partyFile)
            throws IOException {
        if (PartyManager.party != null) {
            PartyManager.party.write(partyFile);
        } else {
            partyFile.writeBoolean(false);
        }
    }

    public static PartyMember getNewPCInstance() {
        return new PartyMember();
    }
}
