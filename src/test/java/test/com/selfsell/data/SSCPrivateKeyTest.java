package test.com.selfsell.data;

import com.selfsell.data.SSCPrivateKey;
import com.selfsell.util.MyByte;

import org.junit.Test;

public class SSCPrivateKeyTest {

  @Test
  public void testCreate() {
    print(new SSCPrivateKey());
  }

  @Test
  public void testFromStr() {
    print(new SSCPrivateKey("fsda"));
  }

  @Test
  public void testFromHex() {
    print(new SSCPrivateKey(MyByte.fromHex("fasdf")));
  }

  private void print(SSCPrivateKey p) {
    System.out.println("prv: " + MyByte.toHex(p.getEncoded()));
    System.out.println("str: " + p.getKeyStr());
    System.out.println("pub: " + MyByte.toHex(p.getPublicKey(true)));
    System.out.println("add: SSC" + p.getAddress().getAddressStr());
  }
}
