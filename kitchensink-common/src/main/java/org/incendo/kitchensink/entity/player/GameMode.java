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
package org.incendo.kitchensink.entity.player;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class GameMode {

    public static final GameMode SURVIVAL = new GameMode("survival", 0);
    public static final GameMode CREATIVE = new GameMode("creative", 1);
    public static final GameMode ADVENTURE = new GameMode("adventure", 2);
    public static final GameMode SPECTATOR = new GameMode("spectator", 3);

    // Has to come after because of the forward referencing that would otherwise occur.
    private static final List<@NonNull GameMode> GAME_MODES = List.of(SURVIVAL, CREATIVE, ADVENTURE, SPECTATOR);

    /**
     * Returns the game mode that corresponds to the given {@code key}.
     *
     * @param key game mode key
     * @return the game mode
     * @throws IllegalArgumentException if the game mode does not exist
     */
    public static @NonNull GameMode fromKey(final @NonNull String key) {
        return switch (key.toLowerCase(Locale.ENGLISH)) {
            case "survival" -> GameMode.SURVIVAL;
            case "creative" -> GameMode.CREATIVE;
            case "adventure" -> GameMode.ADVENTURE;
            case "spectator" -> GameMode.SPECTATOR;
            default -> throw new IllegalArgumentException("Unknown game mode: " + key);
        };
    }

    /**
     * Returns the game mode that corresponds to the given {@code id}.
     *
     * @param id game mode id
     * @return the game mode
     * @throws IllegalArgumentException if the game mode does not exist
     */
    public static @NonNull GameMode fromId(final int id) {
        return switch (id) {
            case 0 -> GameMode.SURVIVAL;
            case 1 -> GameMode.CREATIVE;
            case 2 -> GameMode.ADVENTURE;
            case 3 -> GameMode.SPECTATOR;
            default -> throw new IllegalArgumentException("Unknown game mode: " + id);
        };
    }

    /**
     * Returns the available game modes.
     *
     * @return the game modes
     */
    public static @NonNull Collection<@NonNull GameMode> gameModes() {
        return List.copyOf(GAME_MODES);
    }

    private final String key;
    private final int id;

    private GameMode(
            final @NonNull String key,
            final int id
    ) {
        this.key = Objects.requireNonNull(key, "key");
        this.id = id;
    }

    /**
     * Returns the game mode key.
     *
     * @return the key
     */
    public @NonNull String key() {
        return this.key;
    }

    /**
     * Returns the numerical id that represents this game mode.
     *
     * @return the id
     */
    public int id() {
        return this.id;
    }
}
