package br.com.auditoria.nfce.controle;

import br.com.auditoria.nfce.modelo.Empresa;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.ini4j.Config;
import org.ini4j.Ini;

public class EmpresaControle {

    public Empresa buscarEmpresa() throws IOException {
        Ini ini = new Ini(new File("SigmaSAT.ini"));
        Config config = new Config();
        config.setFileEncoding(Charset.forName("UTF-8"));
        config.setEscapeKeyOnly(true);
        ini.setConfig(config);
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(ini.get("NFCe", "Emitente_RazaoSocial"));
        empresa.setNomeFantasia(ini.get("NFCe", "Emitente_Fantasia"));
        empresa.setTelefone(ini.get("NFCe", "Emitente_Fone").replaceAll("\\D", ""));
        empresa.setCep(ini.get("NFCe", "Emitente_CEP").replaceAll("\\D", ""));
        empresa.setLogradouro(ini.get("NFCe", "Emitente_Logradouro"));
        empresa.setNumero(ini.get("NFCe", "Emitente_Numero"));
        empresa.setComplemento(ini.get("NFCe", "Emitente_Complemento"));
        empresa.setBairro(ini.get("NFCe", "Emitente_Bairro"));
        empresa.setCidade(ini.get("NFCe", "Emitente_Cidade"));
        empresa.setEstado(ini.get("NFCe", "Emitente_UF"));
        empresa.setCnpj(ini.get("NFCe", "Emitente_CNPJ"));
        empresa.setInscricaoEstadual(ini.get("NFCe", "Emitente_IE").replaceAll("\\D", ""));
        empresa.setCodigoCidade(ini.get("NFCe", "Emitente_CodCidade"));
        empresa.setCSC(ini.get("NFCe", "Geral_Token"));
        empresa.setToken(ini.get("NFCe", "Geral_IdToken"));
        empresa.setSenhaCertificado(ini.get("NFCe", "Certificado_Senha"));
        empresa.setSerieCertificado(ini.get("NFCe", "Certificado_NumSerie"));
        empresa.setNumeroSerie(ini.get("PDV", "NUM_CXA"));
        empresa.setRegimeTributario(ini.get("NFCe", "Emitente_RegTributario"));
        return empresa;
    }

}
