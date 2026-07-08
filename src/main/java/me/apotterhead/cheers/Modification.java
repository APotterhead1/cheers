// Craig Foulkrod
// 06232026-07072026

/*

    Copyright (c) 2026 Craig Foulkrod
    
    License under the MIT License
    See LICENSE file the project root for full license information
    
 */

package me.apotterhead.cheers;

import me.apotterhead.cheers.vars.SerialObject;

import java.util.List;

/**
 * {@code Modification} is an interface that represents a change in a
 * {@code class} between versions. Each modification is applied during the
 * deserialization process to make an old {@code Object} compatible with the
 * lastest version its respective{@code class}.
 *
 * @since 1.0.0
 */
public interface Modification {
    
    /**
     * Modifies the {@link SerialObject} in
     * such a way that it is compatible with the latest version of the class. This
     * normally entails either adding or removing variables, changing variable datatypes,
     * or remaining variables, often by going through objects that exist in the variables
     * to access the necessary {@code SerialObject} to modify.
     *
     * @param serialObjects a {@code List} containing a {@code SerialObject}
     *                      for every {@code Object} that is referenced at any point
     *                      in the {@code Object} that was serialized, including the
     *                      root {@code SerialObject}
     */
    void apply( List<SerialObject> serialObjects );
}
