// Craig Foulkrod
// 06082026-08052026

/*

    Copyright (c) 2026 Craig Foulkrod. All rights reserved.
    
    Licensed under the MIT License
    See LICENSE file the project root for full license information
    
*/

/**
 * Defines a serialization/deserialization library.
 * <p>
 * This library is capable of serializing into a human-readable format
 * while still providing support for cyclical referencing. In addition,
 * this comes with support for upgrading an older {@code Object} to a newer
 * version of the respective {@code class}.
 *
 * @since 1.0.0
 */
module me.apotterhead.cheers {
    requires org.objenesis;
    
    exports io.github.apotterhead1.cheers;
    exports io.github.apotterhead1.cheers.vars;
}