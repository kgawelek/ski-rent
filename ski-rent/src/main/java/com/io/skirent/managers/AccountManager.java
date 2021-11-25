package com.io.skirent.managers;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class AccountManager {

    /** Length of the cryptographic salt. */
    private static final int saltLength = 16;

    /**
     * Hashes password using PBKDF2 algorithm and generated salt.
     * @param password password to be hashed
     * @return password and salt concatenated and encoded as Base64 string
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws IOException
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        // generate salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltLength];
        random.nextBytes(salt);

        // hash password
        byte[] hashedPassword = hashUsingPBKDF2(salt, password);

        // concat password + salt
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        bs.write(hashedPassword);
        bs.write(salt);
        byte[] passwordAndSalt = bs.toByteArray();

        // make string from byte array, return salt and password
        return toBase64(passwordAndSalt);
    }

    /**
     * Validates password and hash.
     * @param password password to validate
     * @param hash correct hash (password and salt)
     * @return true if password is correct, false otherwise
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static boolean validatePassword(String password, String hash) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // decode and split hash to password and salt
        byte[] decodedHash = fromBase64(hash);
        int hashLength = decodedHash.length;
        byte[] hashedPassword = Arrays.copyOfRange(decodedHash, 0, hashLength - saltLength);
        byte[] salt = Arrays.copyOfRange(decodedHash, hashLength - saltLength, hashLength);

        // hash the provided password
        byte[] testHash = hashUsingPBKDF2(salt, password);

        // compare hashes, return result
        return Arrays.equals(fromBase64(hash), testHash);
    }

    /* INTERNAL METHODS */

    private static byte[] hashUsingPBKDF2(byte[] salt, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = null;
        factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();

    }

    private static byte[] fromBase64(String hex) throws IllegalArgumentException
    {
        return Base64.getDecoder().decode(hex);
    }

    private static String toBase64(byte[] array)
    {
        return Base64.getEncoder().encodeToString(array);
    }
}


