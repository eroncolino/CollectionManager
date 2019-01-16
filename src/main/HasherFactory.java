package main;

/**
 * Factory that creates hasher instances.
 *
 * @author Elena Roncolino
 */
public class HasherFactory {

    /**
     * Instantiates a hasher of the desired type.
     *
     * @param hasherType The type of hashing algorithm you want to use.
     * @return main.Hasher The instance of a hasher of the specified type.
     */
    public Hasher getHasher(String hasherType) {
        if (hasherType.equalsIgnoreCase("MD5"))
            return new MD5Hasher();

        return null;
    }
}


