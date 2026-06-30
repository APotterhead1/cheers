// Craig Foulkrod
// 06082026-06302026

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
    
    exports me.apotterhead.cheers;
    exports me.apotterhead.cheers.vars;
}