package t.me.p1azmer.plugin.dungeons.dungeon.modules.impl;

import org.jetbrains.annotations.NotNull;
import t.me.p1azmer.plugin.dungeons.api.handler.hologram.HologramHandler;
import t.me.p1azmer.plugin.dungeons.dungeon.impl.Dungeon;
import t.me.p1azmer.plugin.dungeons.dungeon.modules.AbstractModule;
import t.me.p1azmer.plugin.dungeons.dungeon.modules.ModuleManager;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class HologramModule extends AbstractModule {
    private ChestModule chestModule;
    private final HologramHandler handler;

    public HologramModule(
            @NotNull Dungeon dungeon,
            @NotNull String id
    ) {
        super(dungeon, id, true);
        this.handler = plugin().getHologramHandler();
    }

    @Override
    protected Predicate<Boolean> onLoad() {
        return aBoolean -> {
            Optional<ChestModule> module = this.getManager().getModule(ChestModule.class);
            this.chestModule = module.orElse(null);
            return handler != null && this.chestModule != null && !this.chestModule.getChests().isEmpty();
        };
    }

    @Override
    protected void onShutdown() {
        this.chestModule = null;
    }

    @Override
    public CompletableFuture<Boolean> onActivate(boolean force) {
        this.debug("Starting initialize holograms");
        handler.create(this.getDungeon(), this.chestModule);

        return CompletableFuture.completedFuture(true);
    }

    @Override
    public boolean onDeactivate(boolean force) {
        if (handler != null) handler.delete(this.getDungeon());
        return true;
    }
}
