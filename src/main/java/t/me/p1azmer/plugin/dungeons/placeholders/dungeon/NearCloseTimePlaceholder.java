package t.me.p1azmer.plugin.dungeons.placeholders.dungeon;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import t.me.p1azmer.engine.api.placeholder.AbstractPlaceholder;
import t.me.p1azmer.engine.api.placeholder.PlaceholderExpansion;
import t.me.p1azmer.engine.utils.NumberUtil;
import t.me.p1azmer.plugin.dungeons.DungeonPlugin;
import t.me.p1azmer.plugin.dungeons.dungeon.impl.Dungeon;
import t.me.p1azmer.plugin.dungeons.dungeon.stage.DungeonStage;

import java.util.regex.Matcher;

public class NearCloseTimePlaceholder extends AbstractPlaceholder<DungeonPlugin> {

  public NearCloseTimePlaceholder(@NotNull PlaceholderExpansion<DungeonPlugin> expansion) {
    super(expansion);
  }

  @Override
  public String parse(@NotNull Matcher matcher, @Nullable Player player) {
    Dungeon dungeon = plugin.getDungeonManager().getNearestDungeon();
    if (dungeon == null) return "";

    if (dungeon.getStage().isOpened())
      return NumberUtil.format(dungeon.getStageSettings().getTime(DungeonStage.OPENED) - dungeon.getNextStageTime());
    return "";
  }

  @Override
  public @NotNull String getRegex() {
    return "nearest_close_time";
  }
}