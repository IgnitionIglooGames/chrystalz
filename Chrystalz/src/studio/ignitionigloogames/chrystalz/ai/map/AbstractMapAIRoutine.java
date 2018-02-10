/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.ai.map;

public abstract class AbstractMapAIRoutine {
    // Fields
    protected int moveX;
    protected int moveY;
    protected boolean lastResult;
    public static final int ACTION_MOVE = 0;
    public static final int ACTION_CAST_SPELL = 1;
    public static final int ACTION_STEAL = 2;
    public static final int ACTION_DRAIN = 3;
    static final int ACTION_END_TURN = 4;

    // Constructor
    protected AbstractMapAIRoutine() {
        this.moveX = 0;
        this.moveY = 0;
        this.lastResult = true;
    }

    // Methods
    public abstract int getNextAction(MapAIContext ac);

    public void newRoundHook() {
        // Do nothing
    }

    public final int getMoveX() {
        return this.moveX;
    }

    public final int getMoveY() {
        return this.moveY;
    }

    public final void setLastResult(final boolean res) {
        this.lastResult = res;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.lastResult ? 1231 : 1237);
        result = prime * result + this.moveX;
        return prime * result + this.moveY;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractMapAIRoutine)) {
            return false;
        }
        final AbstractMapAIRoutine other = (AbstractMapAIRoutine) obj;
        if (this.lastResult != other.lastResult) {
            return false;
        }
        if (this.moveX != other.moveX) {
            return false;
        }
        if (this.moveY != other.moveY) {
            return false;
        }
        return true;
    }
}
