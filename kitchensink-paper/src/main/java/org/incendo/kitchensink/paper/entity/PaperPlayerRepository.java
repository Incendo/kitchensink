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

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.kitchensink.entity.PlayerRepository;

@Singleton
public final class PaperPlayerRepository implements PlayerRepository<PaperPlayer, Player>, Listener {

    private final Map<@NonNull UUID, @NonNull PaperPlayer> players = new ConcurrentHashMap<>();
    private final PaperPlayerFactory playerFactory;

    /**
     * Creates a new paper player repository.
     *
     * @param playerFactory factory used to create player instances
     */
    @Inject
    public PaperPlayerRepository(final @NonNull PaperPlayerFactory playerFactory) {
        this.playerFactory = Objects.requireNonNull(playerFactory, "playerFactory");
    }

    @Override
    public @NonNull Optional<@NonNull PaperPlayer> findById(final @NonNull UUID id) {
        return Optional.ofNullable(this.players.get(id));
    }

    @Override
    public @NonNull PaperPlayer findByPlatformPlayer(final @NonNull Player platformPlayer) {
        return this.create(platformPlayer);
    }

    @Override
    public @NonNull PaperPlayer create(final @NonNull Player platformPlayer) {
        return this.players.computeIfAbsent(platformPlayer.getUniqueId(), uuid -> this.playerFactory.create(platformPlayer));
    }

    @Override
    public @NonNull Collection<@NonNull PaperPlayer> players() {
        return List.copyOf(this.players.values());
    }

    /**
     * Listens to the login event and saves players in the player map.
     *
     * @param event the event
     */
    @EventHandler
    public void onLogin(final @NonNull PlayerJoinEvent event) {
        this.create(event.getPlayer());
    }

    /**
     * Listens to the quit event and removes players from the player map.
     *
     * @param event the event
     */
    @EventHandler
    public void onQuit(final @NonNull PlayerQuitEvent event) {
        this.players.remove(event.getPlayer().getUniqueId());
    }
}
