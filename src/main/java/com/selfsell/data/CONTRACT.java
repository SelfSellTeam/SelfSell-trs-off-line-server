package com.selfsell.data;

import java.math.BigDecimal;

public enum CONTRACT {
  
    // 正式链
    SMC("2W6PbuBrGcB3EGFTK81sDfJmrMUTyXqta", "SMC_SimonsChainOfficial"),
    LET("DuPQkPuKqD5NM2XwHJCjnZiiTs2GAJDLB", "LET_LinkEyeOfficial"),
    BSC("57ucNVXMqPpdiBiVC72gTLJao4CobmMNy", "BSC_BlackstoneBlockchain"),
    USC("2Z7MrZV2MP794MBX2Z8xVaB2sBj8RK3RH", "USD_Coin"),
    // 测试链
    @Deprecated
    SMC_t("7w5yDZ5K4yxKjPwfn2seQjg8h6KLUwnCj", "SMC"),;

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
