package kkpridwan;

import java.util.Arrays;

/**
 * Created by havi on 07/11/2016.
 */
public class BitCollection {

    private final int DEFAULT_SIZE = 256;

    private byte[] bytes;
    private boolean[] bits = new boolean[8];

    private int bytePos;
    private int bitPos;

    private byte padBits;

    private boolean end = false;
    private boolean closed = false;

    public BitCollection() {
        bytes = new byte[DEFAULT_SIZE];
    }

    public BitCollection(byte[] bytes) {
        this.bytes = bytes;
        this.padBits = bytes[bytes.length-1];
        pullByte();
    }

    // returnerer bytes-tabellen
    public byte[] getBytes() { return this.bytes; }

    // metode gjør byte-tabellen større
    public byte[] grow() {
        return Arrays.copyOf(bytes, bytes.length + DEFAULT_SIZE);
    }

    // legger byten i bits til bytes-tabellen
    public void flush() {
        int newByte = 0;
        for (int i = 0; i < 8; i++) {
            if (bits[i]) newByte |= 1 << 7 - i;
        }
        bytes[bytePos] = (byte) (newByte & 0xFF);
        bytePos++;
        if (bytePos >= bytes.length) bytes = grow();
        bitPos = 0;
        clearBits();
    }

    // nullstill bits-tabellen
    public void clearBits() {
        for (int i = 0; i < bits.length; i++)
            bits[i] = false;
    }

    // legger til en bit
    public void pushBit(boolean bit) {
        if (closed) return;
        bits[bitPos] = bit;
        bitPos++;
        if (bitPos > 7) {
            flush();
        }
    }

    // legger til flere bits
    public void pushBits(int value, int length) {
        for (int i = length-1; i >= 0; i--) {
            pushBit((value >> i & 1) == 1);
        }
    }

    // legger neste byte inn i bits-tabellen
    public void pullByte() {
        clearBits();
        byte current = bytes[bytePos];
        for (int i = 7, j = 0; i >= 0; i--, j++) {
            int bit = 1 << i;
            if ((current & bit) == bit) bits[j] = true;
        }
        bytePos++;
        if (bytePos > bytes.length-1) end = true;
        bitPos = 0;
    }

    // leser neste bit
    public boolean readBit() {
        boolean bit = bits[bitPos];
        bitPos++;
        if (bytePos == bytes.length-1 && bitPos > 7-padBits) end = true;
        if (bitPos > 7)
            pullByte();
        return bit;
    }

    // leser x antall bits videre
    public int readBits(int length) {
        int val = 0;
        for (int i = length-1; i >= 0; i--) {
            val |= ((readBit()) ? 1 : 0) << i;
        }
        return val;
    }

    // stenger for skriveoperasjoner
    public void close() {
        if (closed) return;
        closed = true;
        padBits = (byte)((-(bitPos) + 8) % 8);
        if (padBits > 0) {
            flush();
        }
        bytes[bytePos] = padBits;
        bytes = Arrays.copyOfRange(bytes, 0, bytePos+1);
        bitPos = 0;
        bytePos = 0;
        pullByte();
    }

}
