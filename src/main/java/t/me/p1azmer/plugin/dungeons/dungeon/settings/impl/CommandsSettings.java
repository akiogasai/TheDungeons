package t.me.p1azmer.plugin.dungeons.dungeon.settings.impl;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import t.me.p1azmer.engine.api.config.JYML;
import t.me.p1azmer.engine.api.placeholder.PlaceholderMap;
import t.me.p1azmer.engine.utils.StringUtil;
import t.me.p1azmer.plugin.dungeons.api.settings.AbstractSettings;
import t.me.p1azmer.plugin.dungeons.dungeon.impl.Dungeon;
import t.me.p1azmer.plugin.dungeons.dungeon.stage.DungeonStage;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CommandsSettings extends AbstractSettings {
    private final Map<DungeonStage, List<String>> commandsMap;

    public CommandsSettings(
            @NotNull Dungeon dungeon,
            @NotNull Map<DungeonStage, List<String>> commandsMap
    ) {
        super(dungeon);
        this.commandsMap = commandsMap;

        this.placeholderMap = new PlaceholderMap();

    }

    @NotNull
    public static CommandsSettings read(@NotNull Dungeon dungeon, @NotNull JYML cfg, @NotNull String path) {
        Map<DungeonStage, List<String>> map = new HashMap<>();
        for (String sId : cfg.getSection(path + ".Map")) {
            DungeonStage stage = StringUtil.getEnum(sId, DungeonStage.class).orElse(null);
            if (stage == null) continue;
            List<String> commands = cfg.getStringList(path + ".Map." + sId);
            map.put(stage, commands);
        }
        return new CommandsSettings(dungeon, map);
    }

    public void write(@NotNull JYML cfg, @NotNull String path) {
        for (Map.Entry<DungeonStage, List<String>> entry : this.getCommandsMap().entrySet()) {
            cfg.set(path + ".Map." + entry.getKey().name(), entry.getValue());
        }
    }

    @NotNull
    public List<String> getCommands(@NotNull DungeonStage stage) {
        return this.getCommandsMap().getOrDefault(stage, Collections.emptyList());
    }

    public void setCommands(@NotNull DungeonStage stage, @NotNull List<String> commands) {
        this.getCommandsMap().put(stage, commands);
    }
}
