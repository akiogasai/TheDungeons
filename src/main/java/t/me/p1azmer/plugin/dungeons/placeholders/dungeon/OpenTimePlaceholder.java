package t.me.p1azmer.plugin.dungeons.placeholders.dungeon;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import t.me.p1azmer.engine.api.placeholder.AbstractPlaceholder;
import t.me.p1azmer.engine.api.placeholder.PlaceholderExpansion;
import t.me.p1azmer.engine.utils.NumberUtil;
import t.me.p1azmer.plugin.dungeons.DungeonPlugin;
import t.me.p1azmer.plugin.dungeons.dungeon.impl.Dungeon;
import t.me.p1azmer.plugin.dungeons.dungeon.chest.ChestBlock;
import t.me.p1azmer.plugin.dungeons.dungeon.stage.DungeonStage;

import java.util.regex.Matcher;

public class OpenTimePlaceholder extends AbstractPlaceholder<DungeonPlugin> {

    public OpenTimePlaceholder(@NotNull PlaceholderExpansion<DungeonPlugin> expansion) {
        super(expansion);
    }

    @Override
    public String parse(@NotNull Matcher matcher, @NotNull OfflinePlayer player) {
        String dungeonId = matcher.group(1);
        Dungeon dungeon = plugin.getDungeonManager().getDungeonById(dungeonId);
        if (dungeon == null) return "";
        ChestBlock chest = null;//dungeon.getNearestChest();
        if (chest != null)
            return NumberUtil.format( (int) dungeon.getStageSettings().getTime(DungeonStage.OPENED) - chest.getCurrentTick());
        return "";
    }

    @Override
    public @NotNull String getRegex() {
        return "open_time_(.*)";
    }
}
