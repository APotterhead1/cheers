// Craig Foulkrod
// 06082026-06282026

package me.apotterhead.cheers;

/**
 * <code>Verson</code> is an interface that represents the history of a <code>class</code>
 * over its lifetime, while it can be serialized and deserialized. Each version contains a
 * {@link Modification} array that represents what needs to happen to take an older copy
 * of an <code>Object</code> and update it to the latest version of that <code>class</code>.
 *
 * @since 1.0.0
 */
public interface Version {
    /**
     * Returns a <code>String</code> representing the most recent version of the
     * <code>class</code>.
     *
     * @return a <code>String</code> representing the most recent version of the
     * <code>class</code>
     */
    String getCurrentVersion();
    
    /**
     * Returns a {@link Modification} array that represents what needs to happen
     * to take <code>Object</code> from <code>originalVersion</code> to the
     * result of {@link #getCurrentVersion()}. The array is ordered in such a way that,
     * when starting from index <code>0</code>, each <code>Modification</code> can be
     * successfully applied.
     *
     * @param originalVersion a <code>String</code> representing the version of the
     *                        <code>class</code> that the <code>Object</code> was
     *                        serialized under
     * @return a <code>Modification</code> list that represents what needs to happen
     * to take <code>Object</code> from <code>originalVersion</code> to the
     * result of <code>getCurrentVersion()</code>
     */
    Modification[] getModifications( String originalVersion );
    
    /**
     * A utility method that returns a {@link RuntimeException} generated in a format
     * consistent with other errors thrown by this library. The error is intended to be
     * used when a <code>Verision</code> lacks the neccessary {@link Modification}s to
     * deserialize an <code>Object</code> of a given version.
     *
     * @param version a <code>String</code> representing the version that of the class
     *                that the <code>Object</code> was attempting to be deserialzied was
     *                serialized under
     * @return a <code>RuntimeException</code> for an unsupported version formatted
     * consistently with other errors thrown by this library
     */
    static RuntimeException versionError( String version ) {
        return new InvalidDeserializationInputException( "Version \"" + version + "\" is not supported." );
    }
}
