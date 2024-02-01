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
package org.incendo.kitchensink.entity;

import java.util.Optional;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Repository that contains {@link KitchenSinkPlayer players}.
 *
 * @param <T> player type
 * @param <U> platform player type
 */
public interface PlayerRepository<T extends KitchenSinkPlayer, U> {

    /**
     * Finds the player by its {@code id}, if it exists.
     *
     * @param id id to find the player by
     * @return the found player, or an empty optional
     */
    @NonNull Optional<@NonNull T> findById(@NonNull UUID id);

    /**
     * Finds the player by its corresponding {@code platformPlayer}.
     *
     * @param platformPlayer platform player
     * @return the player
     */
    @NonNull T findByPlatformPlayer(@NonNull U platformPlayer);

    /**
     * Creates and returns the player that corresponds to the given {@code platformPlayer}.
     *
     * @param platformPlayer platform player
     * @return created player
     */
    @NonNull T create(@NonNull U platformPlayer);
}
