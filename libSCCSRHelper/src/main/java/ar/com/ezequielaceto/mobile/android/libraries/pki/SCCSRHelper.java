package ar.com.ezequielaceto.mobile.android.libraries.pki;

import org.jetbrains.annotations.NotNull;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x500.X500NameBuilder;
import org.spongycastle.asn1.x500.style.BCStyle;
import org.spongycastle.openssl.MiscPEMGenerator;
import org.spongycastle.operator.ContentSigner;
import org.spongycastle.operator.jcajce.JcaContentSignerBuilder;
import org.spongycastle.pkcs.PKCS10CertificationRequest;
import org.spongycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.spongycastle.util.encoders.Base64;
import org.spongycastle.util.io.pem.PemObjectGenerator;
import org.spongycastle.util.io.pem.PemWriter;

import java.io.StringWriter;
import java.security.PrivateKey;
import java.security.PublicKey;


public final class SCCSRHelper {

    public static String defaultSignatureAlgorithm = "SHA256withRSA";

    @org.jetbrains.annotations.NotNull
    public static String generateCSR(PrivateKey privateKey, PublicKey publicKey, SCCSRAttributes attributes, String signatureAlgorithm) throws Exception {

        X500Name name = x500NameFrom(attributes);

        JcaPKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(name, publicKey);
        ContentSigner signer = new JcaContentSignerBuilder(signatureAlgorithm).build(privateKey);
        PKCS10CertificationRequest csr = p10Builder.build(signer);

        StringWriter csrString = new StringWriter();
        PemWriter pemWriter = new PemWriter(csrString);
        PemObjectGenerator objGen = new MiscPEMGenerator(csr);
        pemWriter.writeObject(objGen);
        pemWriter.close();

        return csrString.toString();
    }

    private static X500Name x500NameFrom(@NotNull SCCSRAttributes attributes) {
        X500NameBuilder nameBuilder = new X500NameBuilder();

        if (!attributes.getCommonName().isEmpty()) {
            nameBuilder.addRDN(BCStyle.CN, attributes.getCommonName());
        }

        if (!attributes.getOrganization().isEmpty()) {
            nameBuilder.addRDN(BCStyle.O, attributes.getOrganization());
        }

        if (!attributes.getOrganization().isEmpty()) {
            nameBuilder.addRDN(BCStyle.OU, attributes.getOrganizationUnit());
        }

        if (!attributes.getLocality().isEmpty()) {
            nameBuilder.addRDN(BCStyle.L, attributes.getLocality());
        }

        if (!attributes.getStateOrProvince().isEmpty()) {
            nameBuilder.addRDN(BCStyle.ST, attributes.getStateOrProvince());
        }

        if (!attributes.getCountry().isEmpty()) {
            nameBuilder.addRDN(BCStyle.C, attributes.getCountry());
        }

        if (!attributes.getSerialNumber().isEmpty()) {
            nameBuilder.addRDN(BCStyle.SERIALNUMBER, attributes.getSerialNumber());
        }

        return nameBuilder.build();
    }


}
