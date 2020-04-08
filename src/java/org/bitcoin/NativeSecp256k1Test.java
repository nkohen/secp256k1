package org.bitcoin;

import javax.xml.bind.DatatypeConverter;

import static org.bitcoin.NativeSecp256k1Util.*;

/**
 * This class holds test cases defined for testing this library.
 */
public class NativeSecp256k1Test {

    //TODO improve comments/add more tests
    /**
      * This tests verify() for a valid signature
      */
    public static void testVerifyPos() throws AssertFailException{
        byte[] data = DatatypeConverter.parseHexBinary("CF80CD8AED482D5D1527D7DC72FCEFF84E6326592848447D2DC0B0E87DFC9A90"); //sha256hash of "testing"
        byte[] sig = DatatypeConverter.parseHexBinary("3044022079BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F817980220294F14E883B3F525B5367756C2A11EF6CF84B730B36C17CB0C56F0AAB2C98589");
        byte[] pub = DatatypeConverter.parseHexBinary("040A629506E1B65CD9D2E0BA9C75DF9C4FED0DB16DC9625ED14397F0AFC836FAE595DC53F8B0EFE61E703075BD9B143BAC75EC0E19F82A2208CAEB32BE53414C40");

        boolean result = NativeSecp256k1.verify( data, sig, pub);
        assertEquals( result, true , "testVerifyPos");
    }

    /**
      * This tests verify() for a non-valid signature
      */
    public static void testVerifyNeg() throws AssertFailException{
        byte[] data = DatatypeConverter.parseHexBinary("CF80CD8AED482D5D1527D7DC72FCEFF84E6326592848447D2DC0B0E87DFC9A91"); //sha256hash of "testing"
        byte[] sig = DatatypeConverter.parseHexBinary("3044022079BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F817980220294F14E883B3F525B5367756C2A11EF6CF84B730B36C17CB0C56F0AAB2C98589");
        byte[] pub = DatatypeConverter.parseHexBinary("040A629506E1B65CD9D2E0BA9C75DF9C4FED0DB16DC9625ED14397F0AFC836FAE595DC53F8B0EFE61E703075BD9B143BAC75EC0E19F82A2208CAEB32BE53414C40");

        boolean result = NativeSecp256k1.verify( data, sig, pub);
        assertEquals( result, false , "testVerifyNeg");
    }

    /**
      * This tests secret key verify() for a valid secretkey
      */
    public static void testSecKeyVerifyPos() throws AssertFailException{
        byte[] sec = DatatypeConverter.parseHexBinary("67E56582298859DDAE725F972992A07C6C4FB9F62A8FFF58CE3CA926A1063530");

        boolean result = NativeSecp256k1.secKeyVerify( sec );
        assertEquals( result, true , "testSecKeyVerifyPos");
    }

    /**
      * This tests secret key verify() for an invalid secretkey
      */
    public static void testSecKeyVerifyNeg() throws AssertFailException{
        byte[] sec = DatatypeConverter.parseHexBinary("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");

        boolean result = NativeSecp256k1.secKeyVerify( sec );
        assertEquals( result, false , "testSecKeyVerifyNeg");
    }

    /**
      * This tests public key create() for a valid secretkey
      */
    public static void testPubKeyCreatePos() throws AssertFailException{
        byte[] sec = DatatypeConverter.parseHexBinary("67E56582298859DDAE725F972992A07C6C4FB9F62A8FFF58CE3CA926A1063530");

        byte[] resultArr = NativeSecp256k1.computePubkey(sec, false);
        String pubkeyString = DatatypeConverter.printHexBinary(resultArr);
        assertEquals( pubkeyString , "04C591A8FF19AC9C4E4E5793673B83123437E975285E7B442F4EE2654DFFCA5E2D2103ED494718C697AC9AEBCFD19612E224DB46661011863ED2FC54E71861E2A6" , "testPubKeyCreatePos");
    }

    /**
      * This tests public key create() for a invalid secretkey
      */
    public static void testPubKeyCreateNeg() throws AssertFailException{
       byte[] sec = DatatypeConverter.parseHexBinary("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");

       byte[] resultArr = NativeSecp256k1.computePubkey(sec, false);
       String pubkeyString = DatatypeConverter.printHexBinary(resultArr);
       assertEquals( pubkeyString, "" , "testPubKeyCreateNeg");
    }

    /**
      * This tests sign() for a valid secretkey
      */
    public static void testSignPos() throws AssertFailException{

        byte[] data = DatatypeConverter.parseHexBinary("CF80CD8AED482D5D1527D7DC72FCEFF84E6326592848447D2DC0B0E87DFC9A90"); //sha256hash of "testing"
        byte[] sec = DatatypeConverter.parseHexBinary("67E56582298859DDAE725F972992A07C6C4FB9F62A8FFF58CE3CA926A1063530");

        byte[] resultArr = NativeSecp256k1.sign(data, sec);
        String sigString = DatatypeConverter.printHexBinary(resultArr);
        assertEquals( sigString, "30440220182A108E1448DC8F1FB467D06A0F3BB8EA0533584CB954EF8DA112F1D60E39A202201C66F36DA211C087F3AF88B50EDF4F9BDAA6CF5FD6817E74DCA34DB12390C6E9" , "testSignPos");
    }

    /**
      * This tests sign() for a invalid secretkey
      */
    public static void testSignNeg() throws AssertFailException{
        byte[] data = DatatypeConverter.parseHexBinary("CF80CD8AED482D5D1527D7DC72FCEFF84E6326592848447D2DC0B0E87DFC9A90"); //sha256hash of "testing"
        byte[] sec = DatatypeConverter.parseHexBinary("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");

        byte[] resultArr = NativeSecp256k1.sign(data, sec);
        String sigString = DatatypeConverter.printHexBinary(resultArr);
        assertEquals( sigString, "" , "testSignNeg");
    }

    /**
     * This tests signWithEntropy() for a valid secretkey
     */
    public static void testSignWithEntropyPos() throws AssertFailException{

        byte[] data = DatatypeConverter.parseHexBinary("53702647283D86B3D6410ADEF184EC608372CC3DD8B9202795D731EB1EA54275");
        byte[] sec = DatatypeConverter.parseHexBinary("B4F62DE42D38D5D24B66FF01761C3FD0A6E7C8B719E0DC54D168FA013BFAF97F");
        byte[] entropy = DatatypeConverter.parseHexBinary("EDF312C904B610B11442320FFB94C4F976831051A481A17176CE2B81EB3A8B6F");

        byte[] resultArr = NativeSecp256k1.signWithEntropy(data, sec, entropy);
        String sigString = DatatypeConverter.printHexBinary(resultArr);
        assertEquals( sigString, "30450221009D9714BE0CE9A3FD08497125C6D01362FDE2FF118FC817FDB14EE4C38CADFB7A022033B082E161F7D75ABC25642ED71226049DC59EC14AB19DF2A8EFEA47A6C75FAC" , "testSignWithEntropyPos");
    }

    /**
     * This tests signWithEntropy() for a invalid secretkey
     */
    public static void testSignWithEntropyNeg() throws AssertFailException{
        byte[] data = DatatypeConverter.parseHexBinary("CF80CD8AED482D5D1527D7DC72FCEFF84E6326592848447D2DC0B0E87DFC9A90"); //sha256hash of "testing"
        byte[] sec = DatatypeConverter.parseHexBinary("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
        byte[] entropy = DatatypeConverter.parseHexBinary("EDF312C904B610B11442320FFB94C4F976831051A481A17176CE2B81EB3A8B6F");

        byte[] resultArr = NativeSecp256k1.signWithEntropy(data, sec, entropy);
        String sigString = DatatypeConverter.printHexBinary(resultArr);
        assertEquals( sigString, "" , "testSignWithEntropyNeg");
    }

    /**
      * This tests private key tweak-add
      */
    public static void testPrivKeyTweakAdd() throws AssertFailException {
        byte[] sec = DatatypeConverter.parseHexBinary("67E56582298859DDAE725F972992A07C6C4FB9F62A8FFF58CE3CA926A1063530");
        byte[] data = DatatypeConverter.parseHexBinary("3982F19BEF1615BCCFBB05E321C10E1D4CBA3DF0E841C2E41EEB6016347653C3"); //sha256hash of "tweak"

        byte[] resultArr = NativeSecp256k1.privKeyTweakAdd( sec , data );
        String seckeyString = DatatypeConverter.printHexBinary(resultArr);
        assertEquals( seckeyString , "A168571E189E6F9A7E2D657A4B53AE99B909F7E712D1C23CED28093CD57C88F3" , "testPrivKeyTweakAdd");
    }

    /**
      * This tests private key tweak-mul
      */
    public static void testPrivKeyTweakMul() throws AssertFailException {
        byte[] sec = DatatypeConverter.parseHexBinary("67E56582298859DDAE725F972992A07C6C4FB9F62A8FFF58CE3CA926A1063530");
        byte[] data = DatatypeConverter.parseHexBinary("3982F19BEF1615BCCFBB05E321C10E1D4CBA3DF0E841C2E41EEB6016347653C3"); //sha256hash of "tweak"

        byte[] resultArr = NativeSecp256k1.privKeyTweakMul( sec , data );
        String seckeyString = DatatypeConverter.printHexBinary(resultArr);
        assertEquals( seckeyString , "97F8184235F101550F3C71C927507651BD3F1CDB4A5A33B8986ACF0DEE20FFFC" , "testPrivKeyTweakMul");
    }

    /**
      * This tests public key tweak-add
      */
    public static void testPubKeyTweakAdd() throws AssertFailException {
        byte[] pub = DatatypeConverter.parseHexBinary("040A629506E1B65CD9D2E0BA9C75DF9C4FED0DB16DC9625ED14397F0AFC836FAE595DC53F8B0EFE61E703075BD9B143BAC75EC0E19F82A2208CAEB32BE53414C40");
        byte[] data = DatatypeConverter.parseHexBinary("3982F19BEF1615BCCFBB05E321C10E1D4CBA3DF0E841C2E41EEB6016347653C3"); //sha256hash of "tweak"

        byte[] resultArr = NativeSecp256k1.pubKeyTweakAdd( pub , data, false);
        String pubkeyString = DatatypeConverter.printHexBinary(resultArr);
        byte[] resultArrCompressed = NativeSecp256k1.pubKeyTweakAdd( pub , data, true);
        String pubkeyStringCompressed = DatatypeConverter.printHexBinary(resultArrCompressed);
        assertEquals(pubkeyString , "0411C6790F4B663CCE607BAAE08C43557EDC1A4D11D88DFCB3D841D0C6A941AF525A268E2A863C148555C48FB5FBA368E88718A46E205FABC3DBA2CCFFAB0796EF" , "testPubKeyTweakAdd");
        assertEquals(pubkeyStringCompressed , "0311C6790F4B663CCE607BAAE08C43557EDC1A4D11D88DFCB3D841D0C6A941AF52" , "testPubKeyTweakAdd (compressed)");
    }

    /**
      * This tests public key tweak-mul
      */
    public static void testPubKeyTweakMul() throws AssertFailException {
        byte[] pub = DatatypeConverter.parseHexBinary("040A629506E1B65CD9D2E0BA9C75DF9C4FED0DB16DC9625ED14397F0AFC836FAE595DC53F8B0EFE61E703075BD9B143BAC75EC0E19F82A2208CAEB32BE53414C40");
        byte[] data = DatatypeConverter.parseHexBinary("3982F19BEF1615BCCFBB05E321C10E1D4CBA3DF0E841C2E41EEB6016347653C3"); //sha256hash of "tweak"

        byte[] resultArr = NativeSecp256k1.pubKeyTweakMul( pub , data, false);
        String pubkeyString = DatatypeConverter.printHexBinary(resultArr);
        byte[] resultArrCompressed = NativeSecp256k1.pubKeyTweakMul( pub , data, true);
        String pubkeyStringCompressed = DatatypeConverter.printHexBinary(resultArrCompressed);
        assertEquals(pubkeyString , "04E0FE6FE55EBCA626B98A807F6CAF654139E14E5E3698F01A9A658E21DC1D2791EC060D4F412A794D5370F672BC94B722640B5F76914151CFCA6E712CA48CC589" , "testPubKeyTweakMul");
        assertEquals(pubkeyStringCompressed , "03E0FE6FE55EBCA626B98A807F6CAF654139E14E5E3698F01A9A658E21DC1D2791" , "testPubKeyTweakMul (compressed)");
    }

    /**
      * This tests seed randomization
      */
    public static void testRandomize() throws AssertFailException {
        byte[] seed = DatatypeConverter.parseHexBinary("A441B15FE9A3CF56661190A0B93B9DEC7D04127288CC87250967CF3B52894D11"); //sha256hash of "random"
        boolean result = NativeSecp256k1.randomize(seed);
        assertEquals( result, true, "testRandomize");
    }

    /**
     * Tests that we can decompress valid public keys
     * @throws AssertFailException
     */
    public static void testDecompressPubKey() throws AssertFailException {
        byte[] compressedPubKey = DatatypeConverter.parseHexBinary("0315EAB529E7D5EB637214EA8EC8ECE5DCD45610E8F4B7CC76A35A6FC27F5DD981");

        byte[] result1 = NativeSecp256k1.decompress(compressedPubKey);
        byte[] result2 = NativeSecp256k1.decompress(result1); // this is a no-op
        String resultString1 = DatatypeConverter.printHexBinary(result1);
        String resultString2 = DatatypeConverter.printHexBinary(result2);
        assertEquals(resultString1, "0415EAB529E7D5EB637214EA8EC8ECE5DCD45610E8F4B7CC76A35A6FC27F5DD9817551BE3DF159C83045D9DFAC030A1A31DC9104082DB7719C098E87C1C4A36C19", "testDecompressPubKey (compressed)");
        assertEquals(resultString2, "0415EAB529E7D5EB637214EA8EC8ECE5DCD45610E8F4B7CC76A35A6FC27F5DD9817551BE3DF159C83045D9DFAC030A1A31DC9104082DB7719C098E87C1C4A36C19", "testDecompressPubKey (no-op)");
    }

    /**
     * Tests that we can check validity of public keys
     * @throws AssertFailException
     */
    public static void testIsValidPubKeyPos() throws AssertFailException {
        byte[] pubkey = DatatypeConverter.parseHexBinary("0456b3817434935db42afda0165de529b938cf67c7510168a51b9297b1ca7e4d91ea59c64516373dd2fe6acc79bb762718bc2659fa68d343bdb12d5ef7b9ed002b");
        byte[] compressedPubKey = DatatypeConverter.parseHexBinary("03de961a47a519c5c0fc8e744d1f657f9ea6b9a921d2a3bceb8743e1885f752676");

        boolean result1 = NativeSecp256k1.isValidPubKey(pubkey);
        boolean result2 = NativeSecp256k1.isValidPubKey(compressedPubKey);
        assertEquals(result1, true, "testIsValidPubKeyPos");
        assertEquals(result2, true, "testIsValidPubKeyPos (compressed)");
    }

    public static void testIsValidPubKeyNeg() throws AssertFailException {
        //do we have test vectors some where to test this more thoroughly?
        byte[] pubkey = DatatypeConverter.parseHexBinary("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");

        boolean result1 = NativeSecp256k1.isValidPubKey(pubkey);
        assertEquals(result1, false, "testIsValidPubKeyNeg");
    }

    public static void testCreateECDHSecret() throws AssertFailException{
        byte[] sec = DatatypeConverter.parseHexBinary("67E56582298859DDAE725F972992A07C6C4FB9F62A8FFF58CE3CA926A1063530");
        byte[] pub = DatatypeConverter.parseHexBinary("040A629506E1B65CD9D2E0BA9C75DF9C4FED0DB16DC9625ED14397F0AFC836FAE595DC53F8B0EFE61E703075BD9B143BAC75EC0E19F82A2208CAEB32BE53414C40");

        byte[] resultArr = NativeSecp256k1.createECDHSecret(sec, pub);
        String ecdhString = DatatypeConverter.printHexBinary(resultArr);
        assertEquals( ecdhString, "2A2A67007A926E6594AF3EB564FC74005B37A9C8AEF2033C4552051B5C87F043" , "testCreateECDHSecret");
    }

    public static void testSchnorrSign() throws AssertFailException{
        byte[] data = DatatypeConverter.parseHexBinary("E48441762FB75010B2AA31A512B62B4148AA3FB08EB0765D76B252559064A614");
        byte[] secKey = DatatypeConverter.parseHexBinary("688C77BC2D5AAFF5491CF309D4753B732135470D05B7B2CD21ADD0744FE97BEF");
        byte[] auxRand = DatatypeConverter.parseHexBinary("02CCE08E913F22A36C5648D6405A2C7C50106E7AA2F1649E381C7F09D16B80AB");

        byte[] sigArr = NativeSecp256k1.schnorrSign(data, secKey, auxRand);
        String sigStr = DatatypeConverter.printHexBinary(sigArr);
        String expectedSig = "6470FD1303DDA4FDA717B9837153C24A6EAB377183FC438F939E0ED2B620E9EE5077C4A8B8DCA28963D772A94F5F0DDF598E1C47C137F91933274C7C3EDADCE8";
        assertEquals(sigStr, expectedSig, "testSchnorrSign");
    }

    public static void testSchnorrSignWithNonce() throws AssertFailException{
        byte[] data = DatatypeConverter.parseHexBinary("E48441762FB75010B2AA31A512B62B4148AA3FB08EB0765D76B252559064A614");
        byte[] secKey = DatatypeConverter.parseHexBinary("688C77BC2D5AAFF5491CF309D4753B732135470D05B7B2CD21ADD0744FE97BEF");
        byte[] nonce = DatatypeConverter.parseHexBinary("8C8CA771D3C25EB38DE7401818EEDA281AC5446F5C1396148F8D9D67592440FE");

        byte[] sigArr = NativeSecp256k1.schnorrSignWithNonce(data, secKey, nonce);
        String sigStr = DatatypeConverter.printHexBinary(sigArr);
        String expectedSig = "5DA618C1936EC728E5CCFF29207F1680DCF4146370BDCFAB0039951B91E3637A958E91D68537D1F6F19687CEC1FD5DB1D83DA56EF3ADE1F3C611BABD7D08AF42";
        assertEquals(sigStr, expectedSig, "testSchnorrSignWithNonce");
    }
    
    public static void testSchnorrComputeSigPoint() throws AssertFailException{
        byte[] data = DatatypeConverter.parseHexBinary("E48441762FB75010B2AA31A512B62B4148AA3FB08EB0765D76B252559064A614");
        byte[] nonce = DatatypeConverter.parseHexBinary("F14D7E54FF58C5D019CE9986BE4A0E8B7D643BD08EF2CDF1099E1A457865B547");
        byte[] pubKey = DatatypeConverter.parseHexBinary("B33CC9EDC096D0A83416964BD3C6247B8FECD256E4EFA7870D2C854BDEB33390");

        byte[] pointArr = NativeSecp256k1.schnorrComputeSigPoint(data, nonce, pubKey, true);
        String pointStr = DatatypeConverter.printHexBinary(pointArr);
        String expectedPoint = "03735ACF82EEF9DA1540EFB07A68251D5476DABB11AC77054924ECCBB4121885E8";
        assertEquals(pointStr, expectedPoint, "testSchnorrComputeSigPoint");
    }

    public static void testSchnorrVerify() throws AssertFailException{
        byte[] sig = DatatypeConverter.parseHexBinary("6470FD1303DDA4FDA717B9837153C24A6EAB377183FC438F939E0ED2B620E9EE5077C4A8B8DCA28963D772A94F5F0DDF598E1C47C137F91933274C7C3EDADCE8");
        byte[] data = DatatypeConverter.parseHexBinary("E48441762FB75010B2AA31A512B62B4148AA3FB08EB0765D76B252559064A614");
        byte[] pubx = DatatypeConverter.parseHexBinary("B33CC9EDC096D0A83416964BD3C6247B8FECD256E4EFA7870D2C854BDEB33390");

        boolean result = NativeSecp256k1.schnorrVerify(sig, data, pubx);

        assertEquals(result, true, "testSchnorrVerify");
    }

    public static void main(String[] args) throws AssertFailException{
        
        System.out.println("\n libsecp256k1 enabled: " + Secp256k1Context.isEnabled() + "\n");

        assertEquals( Secp256k1Context.isEnabled(), true, "isEnabled" );

        //Test verify() success/fail
        testVerifyPos();
        testVerifyNeg();

        //Test secKeyVerify() success/fail
        testSecKeyVerifyPos();
        testSecKeyVerifyNeg();

        //Test computePubkey() success/fail
        testPubKeyCreatePos();
        testPubKeyCreateNeg();

        //Test sign() success/fail
        testSignPos();
        testSignNeg();

        //Test signWithEntropy() success/fail
        testSignWithEntropyPos();
        testSignWithEntropyNeg();

        //Test privKeyTweakAdd()
        testPrivKeyTweakAdd();

        //Test privKeyTweakMul()
        testPrivKeyTweakMul();

        //Test testPubKeyTweakAdd()
        testPubKeyTweakAdd();

        //Test testPubKeyTweakMul()
        testPubKeyTweakMul();

        // Test parsing public keys
        testDecompressPubKey();
        testIsValidPubKeyPos();
        testIsValidPubKeyNeg();

        //Test randomize()
        testRandomize();

        //Test ECDH
        testCreateECDHSecret();

        //Test Schnorr Signing
        testSchnorrSign();
        testSchnorrSignWithNonce();
        testSchnorrComputeSigPoint();
        testSchnorrVerify();

        NativeSecp256k1.cleanup();

        System.out.println(" All tests passed." );

    }
}
