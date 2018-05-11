package com.selfsell.data;

import com.selfsell.util.Base58;
import com.selfsell.util.ECC;
import com.selfsell.util.MyByte;
import com.selfsell.util.RIPEMD160;
import com.selfsell.util.SHA;

import org.bouncycastle.jce.interfaces.ECPrivateKey;

import java.math.BigInteger;

public class SSCPrivateKey {

  private String keyStr;
  private byte[] encoded;
  private BigInteger d;
  private ECPrivateKey ecPrivateKey;
  private byte[] publicKey;
  private byte[] publicKeyCompressed;
  private SSCAddress sscAddress;

  public SSCPrivateKey(String keyStr) {
    this.keyStr = keyStr;
    encoded = Base58.decode(keyStr);
    if (!check(encoded)) {
      throw new RuntimeException("wrong private key format!");
    }
    encoded = MyByte.copyBytes(encoded, 1, 32);
  }

  public SSCPrivateKey(byte[] encoded) {
    if (encoded.length != 32) {
      throw new RuntimeException("the length of private key must be 32 byte");
    }
    this.encoded = encoded;
  }

  public SSCPrivateKey() {
    d = ((ECPrivateKey) ECC.generate().getPrivate()).getD();
    while (d.toByteArray().length < 32){
      d = ((ECPrivateKey) ECC.generate().getPrivate()).getD();
    }
    encoded = MyByte.copyBytesR(d.toByteArray(), 32);
  }

  private boolean check(byte[] wifBytes) {
    if (wifBytes.length != 37) {
      return false;
    }
    byte[] checksum = SHA._256hash(MyByte.copyBytes(wifBytes, 33));
    return checksum(wifBytes, checksum) ||
           checksum(wifBytes, SHA._256hash(checksum));
  }

  private boolean checksum(byte[] wifBytes, byte[] checksum) {
    for (int i = 0; i < 4; i++) {
      if (wifBytes[wifBytes.length - 4 + i] != checksum[i]) {
        return false;
      }
    }
    return true;
  }

  public String getKeyStr() {
    if (keyStr == null) {
      byte[] temp = MyByte.builder().copy((byte) 0x80).copy(encoded).getData();
      keyStr = Base58.encode(
          MyByte.builder()
                .copy(temp)
                .copy(SHA._256hash(SHA._256hash(temp)), 4)
                .getData());
    }
    return keyStr;
  }

  public byte[] getEncoded() {
    return encoded;
  }

  public ECPrivateKey getECPrivateKey() {
    if (ecPrivateKey == null) {
      ecPrivateKey = ECC.loadPrivateKey(encoded);
    }
    return ecPrivateKey;
  }

  //
  public byte[] getPublicKey(boolean compressed) {
    byte[] key = compressed ? publicKeyCompressed : publicKey;
    if (key == null) {
      key = ECC.calculatePublicKey(getD(), compressed);
      if (compressed) {
        publicKeyCompressed = key;
      } else {
        publicKey = key;
      }
    }
    return key;
  }


  public SSCAddress getAddress() {
    if (sscAddress == null) {
      sscAddress = new SSCAddress(RIPEMD160.hash(SHA._512hash(getPublicKey(true))), SSCAddress.Type.ADDRESS);
    }
    return sscAddress;
  }

  public BigInteger getD() {
    if (d == null) {
      d = new BigInteger(1, encoded);
    }
    return d;
  }
}
