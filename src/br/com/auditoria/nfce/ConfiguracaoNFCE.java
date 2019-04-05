package br.com.auditoria.nfce;

import br.com.auditoria.nfce.controle.EmpresaControle;
import br.com.auditoria.nfce.controle.GerenciaArquivo;
import br.com.auditoria.nfce.modelo.Empresa;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import java.io.IOException;
import java.util.List;

public class ConfiguracaoNFCE {

    public static ConfiguracoesNfe iniciaConfiguracoes() throws NfeException, CertificadoException, Exception {
        Certificado certificado;
        if (new GerenciaArquivo().buscarConfiguracaoDeEnvio().isCertificadoA3()) {
            certificado = certificadosRepositorio();
        } else {
            certificado = certifidoA1Pfx();
        }
        return ConfiguracoesNfe.criarConfiguracoes(EstadosEnum.PE, AmbienteEnum.PRODUCAO, certificado, "schemas");
    }

    private static Certificado certifidoA1Pfx() throws CertificadoException, IOException {
        String caminhoCertificado = new GerenciaArquivo().buscarConfiguracaoDeEnvio().getCaminhoCertificado();
        String senha = new EmpresaControle().buscarEmpresa().getSenhaCertificado();
        return CertificadoService.certificadoPfx(caminhoCertificado, senha);
    }

    private static Certificado certificadosRepositorio() throws CertificadoException, IOException {
        List<Certificado> certificadosRepositorio = CertificadoService.listaCertificadosWindows();
        if (certificadosRepositorio != null) {
            Empresa empresa = new EmpresaControle().buscarEmpresa();
            String razaoSocial = empresa.getRazaoSocial();
            for (Certificado certificado : certificadosRepositorio) {
                if (certificado.getNome().contains(razaoSocial)) {
                    certificado.setSenha(empresa.getSenhaCertificado());
                    return certificado;
                }
            }
        }
        return null;
    }

}
