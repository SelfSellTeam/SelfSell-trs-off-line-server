package com.selfsell.data;

import java.math.BigDecimal;

public enum CONTRACT {
  


  public static final String TRANSFER_METHOD = "transfer_to";

  private static final int _scale = 6;
  private static final BigDecimal _2bd = new BigDecimal(Math.pow(10, _scale - 1));

  public static String makeTransferArgs(String toAddress, long amount) {
    String checkAddress = toAddress;
    if (checkAddress.length() > 50) {
      checkAddress = checkAddress.substring(0, checkAddress.length() - 32);
    }
    if (!toAddress.startsWith(Transaction.SSC_SYMBOL) ||
        !SSCAddress.check(checkAddress.substring(3), SSCAddress.Type.ADDRESS)) {
      throw new RuntimeException("error address");
    }
    return toAddress + "|" + new BigDecimal(amount).divide(_2bd, _scale, BigDecimal.ROUND_DOWN)
                                                   .stripTrailingZeros();
  }

  public static String makeTransferArgs(String toAddress, String amount) {
    String checkAddress = toAddress;
    if (checkAddress.length() > 50) {
      checkAddress = checkAddress.substring(0, checkAddress.length() - 32);
    }
    if (!toAddress.startsWith(Transaction.SSC_SYMBOL) ||
        !SSCAddress.check(checkAddress.substring(3), SSCAddress.Type.ADDRESS)) {
      throw new RuntimeException("error address");
    }
    return toAddress + "|" + amount;
  }

  private String contractName;
  private SSCAddress sscAddress;

  CONTRACT(String id, String contractName) {
    this.sscAddress = new SSCAddress(id, SSCAddress.Type.CONTRACT);
    this.contractName = contractName;
  }

  public SSCAddress getSscAddress() {
    return sscAddress;
  }

  public String getContractName() {
    return contractName;
  }
}
