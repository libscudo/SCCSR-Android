package ar.com.ezequielaceto.mobile.android.libraries.pki;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static android.security.keystore.KeyProperties.DIGEST_SHA256;
import static android.security.keystore.KeyProperties.PURPOSE_DECRYPT;
import static android.security.keystore.KeyProperties.PURPOSE_ENCRYPT;
import static android.security.keystore.KeyProperties.PURPOSE_SIGN;
import static android.security.keystore.KeyProperties.PURPOSE_VERIFY;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void generateValidCSR() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
            kpg.initialize(new KeyGenParameterSpec.Builder(
                    "test_keys",
                    PURPOSE_SIGN | PURPOSE_VERIFY | PURPOSE_DECRYPT | PURPOSE_ENCRYPT)
                    .setDigests(KeyProperties.DIGEST_SHA256, DIGEST_SHA256)
                    .setKeySize(2048)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1, KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                    .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1, KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                    .setUserAuthenticationRequired(false)
                    .setUserAuthenticationValidityDurationSeconds(30)
                    .build());

            KeyPair keyPair = kpg.generateKeyPair();

            SCCSRAttributes attr = new SCCSRAttributes("App", "Organization", "IT", "AR", "Buenos Aires", "Buenos Aires", "SN 1234567890");
            String csr = SCCSRHelper.generateCSR(keyPair.getPrivate(), keyPair.getPublic(), attr, SCCSRHelper.defaultSignatureAlgorithm);

            assertTrue(csr.contains("BEGIN CERTIFICATE REQUEST") && csr.contains("END CERTIFICATE REQUEST"));
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(false);
    }
}
