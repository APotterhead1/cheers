// Craig Foulkrod
// 06082026-06302026

/*

    Copyright (c) 2026 Craig Foulkrod
    
    License under the MIT License
    See LICENSE file the project root for full license information
    
 */

package me.apotterhead.cheers;

/**
 * {@code Verson} is an interface that represents the history of a {@code class}
 * over its lifetime, while it can be serialized and deserialized. Each version contains a
 * {@link Modification} array that represents what needs to happen to take an older copy
 * of an {@code Object} and update it to the latest version of that {@code class}.
 *
 * @since 1.0.0
 */
public interface Version {
    /**
     * Returns a {@code String} representing the most recent version of the
     * {@code class}.
     *
     * @return a {@code String} representing the most recent version of the
     * {@code class}
     */
    String getCurrentVersion();
    
    /**
     * Returns a {@link Modification} array that represents what needs to happen
     * to take {@code Object} from {@code originalVersion} to the
     * result of {@link #getCurrentVersion()}. The array is ordered in such a way that,
     * when starting from index {@code 0}, each {@code Modification} can be
     * successfully applied.
     *
     * @param originalVersion a {@code String} representing the version of the
     *                        {@code class} that the {@code Object} was
     *                        serialized under
     * @return a {@code Modification} list that represents what needs to happen
     * to take {@code Object} from {@code originalVersion} to the
     * result of {@code #getCurrentVersion()}
     */
    Modification[] getModifications( String originalVersion );
    
    /**
     * A utility method that returns a {@link RuntimeException} generated in a format
     * consistent with other errors thrown by this library. The error is intended to be
     * used when a {@code Verision} lacks the neccessary {@link Modification}s to
     * deserialize an {@code Object} of a given version.
     *
     * @param version a {@code String} representing the version that of the class
     *                that the {@code Object} was attempting to be deserialzied was
     *                serialized under
     * @return a {@code RuntimeException} for an unsupported version formatted
     * consistently with other errors thrown by this library
     */
    static RuntimeException versionError( String version ) {
        return new InvalidDeserializationInputException( "Version \"" + version + "\" is not supported." );
    }
}
