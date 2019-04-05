package teste;

import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.TipoCertificadoA3;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.List;

public class Teste {

    public static void main(String[] args) throws CertificadoException {
        listaCertificadosWindows();

    }

    private static Certificado certificadoA3Alias() throws CertificadoException {
        String marcaA3 = TipoCertificadoA3.TOKEN_ALADDIN.getMarca();
        String dllA3 = TipoCertificadoA3.TOKEN_ALADDIN.getDll();
        String aliasCertificado = "Alias blablalba";
        String senha = "123456";

        return CertificadoService.certificadoA3(marcaA3, dllA3, senha, aliasCertificado);
    }

    public static List<Certificado> listaCertificadosWindows() throws CertificadoException {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
            KeyStore keyStore = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
            keyStore.load(null, null);
            Enumeration<String> al = keyStore.aliases();
            while (al.hasMoreElements()) {
                String alias = al.nextElement();
                info("--------------------------------------------------------");
                if (keyStore.containsAlias(alias)) {
                    info("Emitido para........: " + alias);
                    X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
                    info("SubjectDN...........: " + cert.getSubjectDN().toString());
                    info("Version.............: " + cert.getVersion());
                    info("SerialNumber........: " + cert.getSerialNumber());
                    info("SigAlgName..........: " + cert.getSigAlgName());
                    info("Válido a partir de..: " + dateFormat.format(cert.getNotBefore()));
                    info("Válido até..........: " + dateFormat.format(cert.getNotAfter()));
                } else {
                    info("Alias doesn't exists : " + alias);
                }
            }
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | CertificateException e) {
            error(e.toString());
        }
        return null;
    }
    
    

    private static void info(String log) {
        System.out.println("INFO: " + log);
    }

    /**
     * Error.
     *
     * @param log
     */
    private static void error(String log) {
        System.out.println("ERROR: " + log);
    }

}
