// Craig Foulkrod
// 06082026-06252026

package me.apotterhead.cheers;

public interface Version {
    String getCurrentVersion();
    Modification[] getModifications( String originalVersion );
}
