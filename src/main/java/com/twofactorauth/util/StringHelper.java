package com.twofactorauth.util;

public final class StringHelper {

    public static String systemIdentifierCode( Object x ) {
        if ( x == null ) {
            return "null";
        }
        return x.getClass().getSimpleName() + "@" +
                Integer.toHexString(System.identityHashCode(x));
    }
}