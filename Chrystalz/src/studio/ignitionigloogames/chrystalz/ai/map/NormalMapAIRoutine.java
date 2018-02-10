/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.ai.map;

import java.awt.Point;

import studio.ignitionigloogames.common.random.RandomRange;

class NormalMapAIRoutine extends AbstractMapAIRoutine {
    // Fields
    private final RandomRange randMove;
    private int failedMoveAttempts;
    private static final int MAX_VISION = 5;
    private static final int FLEE_CHANCE = 10;

    // Constructor
    public NormalMapAIRoutine() {
        super();
        this.randMove = new RandomRange(-1, 1);
        this.failedMoveAttempts = 0;
    }

    @Override
    public int getNextAction(final MapAIContext ac) {
        Point there = ac.isEnemyNearby();
        if (there != null) {
            // Something hostile is nearby, so attack it
            if (ac.getCharacter().getCurrentAT() > 0) {
                this.moveX = there.x;
                this.moveY = there.y;
                return AbstractMapAIRoutine.ACTION_MOVE;
            } else {
                this.failedMoveAttempts = 0;
                return AbstractMapAIRoutine.ACTION_END_TURN;
            }
        } else {
            if (CommonMapAIRoutines.check(ac, NormalMapAIRoutine.FLEE_CHANCE)) {
                // Flee
                final Point awayDir = ac.runAway();
                if (awayDir == null) {
                    // Wander randomly
                    this.moveX = this.randMove.generate();
                    this.moveY = this.randMove.generate();
                    // Don't attack self
                    while (this.moveX == 0 && this.moveY == 0) {
                        this.moveX = this.randMove.generate();
                        this.moveY = this.randMove.generate();
                    }
                } else {
                    this.moveX = awayDir.x;
                    this.moveY = awayDir.y;
                }
                return AbstractMapAIRoutine.ACTION_MOVE;
            } else {
                // Look further
                for (int x = CommonMapAIRoutines.MIN_VISION
                        + 1; x <= NormalMapAIRoutine.MAX_VISION; x++) {
                    there = ac.isEnemyNearby(x, x);
                    if (there != null) {
                        // Found something hostile, move towards it
                        if (this.lastResult == false) {
                            this.failedMoveAttempts++;
                            if (this.failedMoveAttempts >= CommonMapAIRoutines.STUCK_THRESHOLD) {
                                // We're stuck!
                                this.failedMoveAttempts = 0;
                                return AbstractMapAIRoutine.ACTION_END_TURN;
                            }
                            // Last move failed, try to move around object
                            final RandomRange randTurn = new RandomRange(0, 1);
                            final int rt = randTurn.generate();
                            if (rt == 0) {
                                there = CommonMapAIRoutines
                                        .turnRight45(this.moveX, this.moveY);
                            } else {
                                there = CommonMapAIRoutines
                                        .turnLeft45(this.moveX, this.moveY);
                            }
                            this.moveX = there.x;
                            this.moveY = there.y;
                        } else {
                            this.moveX = (int) Math.signum(there.x);
                            this.moveY = (int) Math.signum(there.y);
                        }
                        break;
                    }
                }
            }
            if (ac.getCharacter().getCurrentAP() > 0) {
                if (there == null) {
                    // Wander randomly
                    this.moveX = this.randMove.generate();
                    this.moveY = this.randMove.generate();
                    // Don't attack self
                    while (this.moveX == 0 && this.moveY == 0) {
                        this.moveX = this.randMove.generate();
                        this.moveY = this.randMove.generate();
                    }
                }
                return AbstractMapAIRoutine.ACTION_MOVE;
            } else {
                this.failedMoveAttempts = 0;
                return AbstractMapAIRoutine.ACTION_END_TURN;
            }
        }
    }
}
