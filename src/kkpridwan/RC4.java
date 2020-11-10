/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkpridwan;

/**
 *
 * @author Ridwan
 */
/**
 * @(#)RC4.java
 *
 *
 * @author 
 * @version 1.00 2015/11/24
 */


public class RC4 {
	private final byte[] S = new byte[256];
    private final byte[] T = new byte[256];
    private final int keylen;

    public RC4(final byte[] key) {
    	        if (key.length < 1 || key.length > 256) {
            throw new IllegalArgumentException("key must be between  and 256 bytes");
        } else {
            keylen = key.length;
            for (int i = 0; i < 256; i++) {
                S[i] = (byte) i;
                T[i] = key[i % keylen];
            }
            int j = 0;
            byte tmp;
            for (int i = 0; i < 256; i++) {
                j = (j + S[i] + T[i]) & 0xFF;
                tmp = S[j];
                S[j] = S[i];
                S[i] = tmp;
            }
        }
    	
    }
    
    
    public static String byteToString(byte[] data) {
        return data.toString();
    }

    /**
     * Converts a string to a byte array
     *
     * @param data
     * @return
     */
    public static byte[] stringToByte(String data) {
        return data.getBytes();
    }

    /**
     * RC4 Encryption
     *
     * @param plaintext
     * @return
     */
    public byte[] encrypt(final byte[] plaintext) {
        final byte[] ciphertext = new byte[plaintext.length];
        byte[] s;
        int i = 0, j = 0, k, t;
        byte tmp;
        s = S.clone();
        for (int counter = 0; counter < plaintext.length; counter++) {
            i = ((i + 1) % 256) & 0xFF;
            j = ((j + s[i]) % 256) & 0xFF;
            tmp = s[j];
            s[j] = s[i];
            s[i] = tmp;
            t = ((s[i] + s[j]) % 256) & 0xFF;
            k = s[t];
            ciphertext[counter] = (byte) (plaintext[counter] ^ k);
        }
        return ciphertext;
    }

    /**
     * Same as encryption
     *
     * @param ciphertext
     * @return
     */
    public byte[] decrypt(final byte[] ciphertext) {
        return encrypt(ciphertext);
    }
    
    
}