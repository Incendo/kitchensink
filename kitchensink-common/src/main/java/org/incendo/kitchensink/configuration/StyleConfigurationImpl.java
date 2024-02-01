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
package org.incendo.kitchensink.configuration;

import jakarta.inject.Singleton;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.checkerframework.checker.nullness.qual.NonNull;

// TODO(City): Use configurate.
@Singleton
public final class StyleConfigurationImpl implements StyleConfiguration {

    @Override
    public @NonNull String prefix() {
        return "<dark_aqua>[KitchenSink] </dark_aqua>";
    }

    @Override
    public @NonNull TextColor primaryColor() {
        return NamedTextColor.GRAY;
    }

    @Override
    public @NonNull TextColor accentColor() {
        return NamedTextColor.DARK_AQUA;
    }

    @Override
    public @NonNull TextColor highlightColor() {
        return NamedTextColor.GOLD;
    }

    @Override
    public @NonNull TextColor errorColor() {
        return NamedTextColor.RED;
    }

    @Override
    public @NonNull TextColor warningColor() {
        return TextColor.color(0xFF8033);
    }
}
