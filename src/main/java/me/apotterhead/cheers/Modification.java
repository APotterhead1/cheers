// Craig Foulkrod
// 06232026-06252026

package me.apotterhead.cheers;

import me.apotterhead.cheers.vars.SerialObject;

public interface Modification {
    String getPath();
    void apply( SerialObject sObj );
}
