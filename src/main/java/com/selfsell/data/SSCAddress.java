package com.selfsell.data;

import com.selfsell.util.Base58;
import com.selfsell.util.MyByte;
import com.selfsell.util.RIPEMD160;

public class SSCAddress {
  private String addressStr;
  private byte[] encoded;

  private static final int checksum_len = 4;

  public enum Type {
    ADDRESS(20),
    CONTRACT(20),
    BALANCE_ID(20),
    PUBLIC_KEY(33),;
    private int length;

    Type(int length) {
      this.length = length;
    }

    public boolean checkLen(int len) {
      return this.length == len;
    }
  }

  /**
   * 字符串转地址
   */
  public SSCAddress(String addressStr, Type type) {
    byte[] addressDecode = Base58.decode(addressStr);
    if (!check(addressDecode, type)) {
      throw new RuntimeException("error address");
    }
    this.encoded = MyByte.copyBytes(addressDecode, addressDecode.length - checksum_len);
    this.addressStr = addressStr;
  }

  public SSCAddress(byte[] encoded, Type type) {
    if (!type.checkLen(encoded.length)) {
      throw new RuntimeException("error address");
    }
    this.encoded = encoded;
  }

  private static boolean check(byte[] addressDecode, Type type) {
    int coreLen = addressDecode.length - checksum_len;
    if (!type.checkLen(coreLen)) {
      return false;
    }
    byte[] checksum = RIPEMD160.hash(addressDecode, coreLen);
    for (int i = 0; i < checksum_len; i++) {
      if (addressDecode[coreLen + i] != checksum[i]) {
        return false;
      }
    }
    return true;
  }

  public static boolean check(String address, Type type) {
    return check(Base58.decode(address), type);
  }

  public String getAddressStr() {
    if (addressStr == null) {
      addressStr = Base58.encode(
          MyByte.builder()
                .copy(encoded)
                .copy(RIPEMD160.hash(encoded), checksum_len)
                .getData());
    }
    return addressStr;
  }

  public String getAddressStrStartWithSymbol() {
    return Transaction.SSC_SYMBOL + getAddressStr();
  }

  public String getContractStrStartWithSymbol() {
    return Transaction.CONTRACT_SYMBOL + getAddressStr();
  }

  public byte[] getEncoded() {
    return encoded;
  }
}
