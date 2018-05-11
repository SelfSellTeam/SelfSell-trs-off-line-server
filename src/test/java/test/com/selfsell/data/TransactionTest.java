package test.com.selfsell.data;

import com.selfsell.data.SSCPrivateKey;
import com.selfsell.data.CONTRACT;
import com.selfsell.data.Transaction;

import org.junit.Test;

public class TransactionTest {

  @Test
  public void testTransfer() {
    Transaction trx = new Transaction(
        new SSCPrivateKey("dsfa"),
        10000L,
        "CONfda",
        ""
    );
    System.out.println(trx.toJSONString());
  }

  @Test
  public void testContractTransfer() {
    Transaction trx = new Transaction(
        new SSCPrivateKey("fdsa"),
        CONTRACT.SMC_t,
        "SSCfdsa",
        1L,
        1000L
    );
    System.out.println(trx.toJSONString());
  }

  @Test
  public void testContractTransferAll() {
    Transaction trx = new Transaction(
        new SSCPrivateKey("fadsf"),
        "",
        "SSCafsdfasd",
        "1",
        1000L
    );
    System.out.println(trx.toJSONString());
  }

  @Test
  public void testContractTransaction(){
    Transaction trx = new Transaction(
            new SSCPrivateKey("fsdaf"),
            "CONsadff",
            10000L,
            1000L
    );
    System.out.println(trx.toJSONString());
  }
}
