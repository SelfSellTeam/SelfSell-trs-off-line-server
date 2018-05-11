package test.com.selfsell.util;

import com.selfsell.data.SSCPrivateKey;
import com.selfsell.util.ECC;
import com.selfsell.util.MyByte;
import com.selfsell.util.SHA;

import org.junit.Test;

public class ECCTest {

  @Test
  public void testGenerateSharedSecret() {
    byte[] prv1byte = MyByte.fromHex("fdsaf");
    SSCPrivateKey prv1 = new SSCPrivateKey(prv1byte);
    System.out.println(MyByte.toHex(prv1.getEncoded()) + " -> prv1");
//    System.out.println(MyByte.toHex(prv1.getPublicKey(true)) + " -> pub1");
    byte[] pub2 = MyByte.fromHex("fdas");
    System.out.println(MyByte.toHex(pub2) + " -> pub2");
    byte[] sharedSecret = ECC.generateSharedSecret(
        ECC.loadPrivateKey(prv1.getEncoded()),
//        prv1.getECPrivateKey(),
        ECC.loadPublicKey(pub2));

    System.out.println(MyByte.toHex(sharedSecret) + " -> sharedSecret");


    byte[] _ss = SHA._512hash(sharedSecret);
    System.out.println(MyByte.toHex(_ss) + " -> my");
    System.out.println("fasdf");
  }

  @Test
  public void testSignCompact() {
    SSCPrivateKey key = new SSCPrivateKey("fasd");
    System.out.println(MyByte.toHex(key.getEncoded()));
    byte[] data = MyByte.fromHex("fsdaf");
    byte[] sign = ECC.signCompact(key, SHA._256hash(data));
    System.out.println(MyByte.toHex(sign));
  }
}
