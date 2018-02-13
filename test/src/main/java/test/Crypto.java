package test;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509v1CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v1CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.StringJoiner;
import java.util.zip.CRC32;

public class Crypto {

    static final String rootCAUrl = "https://www.symantec.com/content/en/us/enterprise/verisign/roots/VeriSign-Class%203-Public-Primary-Certification-Authority-G5.pem";


    void crc32() throws IOException {
        RandomAccessFile raf= new RandomAccessFile("D:/svn/iot/avalon/trunk/avalon", "r");

        CRC32 chksum = new CRC32();

        System.out.println("firmware size: "+raf.length());
        int cnt;
        byte[] buf = new byte[1024];

        while ((cnt = raf.read(buf, 0,  1024)) > 0) {
            chksum.update(buf, 0, cnt);
            if (cnt < 1024) {
                System.out.println("last fragment: " + cnt);
            }
        }
        long csum = chksum.getValue();
        System.out.println("CRC32 checksum: " + csum);
    }

    static String getRootCA() {
        String lineFromRootCA;
        final StringJoiner stringJoiner = new StringJoiner("\n");

        final URL url;

        try {
            url = new URL(rootCAUrl);
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()))) {
                while ((lineFromRootCA = in.readLine()) != null) {
                    stringJoiner.add(lineFromRootCA);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return stringJoiner.toString();
    }

    public static KeyPair generateKeyPair() throws GeneralSecurityException {
        String sec = Security.getProperty("securerandom.strongAlgorithms");
        SecureRandom randomStrong = SecureRandom.getInstanceStrong();
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        KeyPairGenerator keyPair = KeyPairGenerator.getInstance("RSA",  "BC");
        keyPair.initialize(2048, random);//keyPair.initialize(new RSAKeyGenParameterSpec(3072, RSAKeyGenParameterSpec.F4));
        return keyPair.generateKeyPair();
    }

    static final long THIRTY_DAYS = 1000L * 60 * 60 * 24 * 30;

    public static String getCertificatePem(PrivateKey caSignerKey, PublicKey caPublicKey)
            throws GeneralSecurityException, IOException, OperatorCreationException {

        X509v1CertificateBuilder v1CertBldr = new JcaX509v1CertificateBuilder(
                new X500Name("CN=Issuer CA"),
                BigInteger.valueOf(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() - 1000L * 5),
                new Date(System.currentTimeMillis() + THIRTY_DAYS),
                new X500Name("CN=Issuer CA"),
                caPublicKey);

        JcaContentSignerBuilder signerBuilder = new JcaContentSignerBuilder("SHA512WithRSA").setProvider("BC");

        X509Certificate cert = new JcaX509CertificateConverter()
                .setProvider("BC").getCertificate(v1CertBldr.build(signerBuilder.build(caSignerKey)));

        StringWriter sWrt = new StringWriter();
        JcaPEMWriter pemWriter = new JcaPEMWriter(sWrt);
        pemWriter.writeObject(cert);
        pemWriter.close();
        return sWrt.toString();
    }

    public static void main(String[] args) throws GeneralSecurityException, IOException, OperatorCreationException {

        Security.addProvider(new BouncyCastleProvider());

        KeyPair keyPair = generateKeyPair();
        PrivateKey priv = keyPair.getPrivate();
        PublicKey pub = keyPair.getPublic();
        BASE64Encoder b64 = new BASE64Encoder();

        PrintWriter raf= new PrintWriter("certificate.pem");
        raf.write(getCertificatePem(keyPair.getPrivate(), keyPair.getPublic()));
        raf.close();

        StringWriter sWrt = new StringWriter();
        JcaPEMWriter pemWriter = new JcaPEMWriter(sWrt);
        pemWriter.writeObject(keyPair.getPrivate());
        pemWriter.close();

        raf= new PrintWriter("private.pem");
        raf.write(sWrt.toString());
        /*raf.println("-----BEGIN RSA PRIVATE KEY-----");
        raf.println(b64.encode(keyPair.getPrivate().getEncoded()));
        raf.println("-----END RSA PRIVATE KEY-----");*/
        raf.close();

        sWrt = new StringWriter();
        pemWriter = new JcaPEMWriter(sWrt);
        pemWriter.writeObject(keyPair.getPublic());
        pemWriter.close();

        raf= new PrintWriter("public.pem");
        raf.write(sWrt.toString());
        /*raf.println("-----BEGIN RSA PUBLIC KEY-----");
        raf.println(b64.encode(keyPair.getPublic().getEncoded()));
        raf.println("-----END RSA PUBLIC KEY-----");*/
        raf.close();

        raf= new PrintWriter("rootCA.pem");
        raf.write(getRootCA());
        raf.close();
    }
}