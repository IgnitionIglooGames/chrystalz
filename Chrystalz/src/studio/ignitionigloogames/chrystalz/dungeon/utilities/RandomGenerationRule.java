package studio.ignitionigloogames.chrystalz.dungeon.utilities;

import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;

public interface RandomGenerationRule {
    public static final int NO_LIMIT = 0;

    public boolean shouldGenerateObject(Dungeon maze, int row, int col,
            int floor, int level, int layer);

    public int getMinimumRequiredQuantity(Dungeon maze);

    public int getMaximumRequiredQuantity(Dungeon maze);

    public boolean isRequired();
}
