package test.com.selfsell.util;

import com.selfsell.data.SSCPrivateKey;
import com.selfsell.data.Transaction;
import com.selfsell.util.RPC;

import org.junit.Test;

public class RPCTest {

  @Test
  public void testNetworkBroadcastTransaction() {
    Transaction trx = new Transaction(
        new SSCPrivateKey("fda"),
        1L,
        "SSCCd7GRUr3HpGTXBBpW2cWp4mRi38kZnhEo",
        null
    );
    System.out.println(trx.toJSONString());
    RPC.Response response = RPC.NETWORK_BROADCAST_TRANSACTION.call(trx.toJSONString());
    System.out.println(response);
  }

  @Test
  public void testInfo() {
    System.out.println(RPC.INFO.call());
  }
}
