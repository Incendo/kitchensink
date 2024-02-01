//
// MIT License
//
// Copyright (c) 2024 Incendo
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
//
package org.incendo.kitchensink.paper.entity;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.kitchensink.entity.player.GameMode;
import org.incendo.kitchensink.entity.player.KitchenSinkPlayer;
import org.incendo.kitchensink.paper.guice.qualifier.MainThreadExecutor;
import org.jetbrains.annotations.NotNull;

/**
 * Paper implementation of {@link KitchenSinkPlayer}.
 */
@AutoFactory
public final class PaperPlayer implements KitchenSinkPlayer, ForwardingAudience {

    private final Player player;
    private final Executor mainThreadExecutor;

    /**
     * Creates a new player instance.
     *
     * @param player             backing Paper player
     * @param mainThreadExecutor main thread executor
     */
    public PaperPlayer(final @NonNull Player player, @Provided @MainThreadExecutor final @NonNull Executor mainThreadExecutor) {
        this.player = Objects.requireNonNull(player, "player");
        this.mainThreadExecutor = Objects.requireNonNull(mainThreadExecutor, "mainThreadExecutor");
    }

    @Override
    public @NonNull UUID uniqueId() {
        return this.player.getUniqueId();
    }

    @Override
    public @NonNull String name() {
        return this.player.getName();
    }

    @Override
    public @NonNull Component displayName() {
        return this.player.displayName();
    }

    @Override
    public @NotNull Iterable<? extends Audience> audiences() {
        return List.of(this.player);
    }

    @Override
    public @NonNull Locale locale() {
        return this.player.locale();
    }

    @Override
    public @NonNull GameMode gameMode() {
        return GameMode.fromId(this.player.getGameMode().getValue());
    }

    @Override
    public @NonNull CompletableFuture<Void> gameMode(final @NonNull GameMode gameMode) {
        return CompletableFuture.runAsync(() -> {
            this.player.setGameMode(org.bukkit.GameMode.getByValue(gameMode.id()));
        }, this.mainThreadExecutor);
    }

    /**
     * Returns the Paper player that backs this instance.
     *
     * @return Paper player
     */
    public @NonNull Player paperPlayer() {
        return this.player;
    }
}
