package br.com.auditoria.nfce;

import br.com.auditoria.nfce.modelo.ProtocoloNFCE;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TRetConsSitNFe;
import java.util.Base64;

public class ConsultarNota {

    public static ProtocoloNFCE consultarProtocoloNFCE(String chave,ConfiguracoesNfe config) throws CertificadoException, Exception {
        ProtocoloNFCE protocoloNFCE = new ProtocoloNFCE();
        TRetConsSitNFe retorno = Nfe.consultaXml(config, chave, DocumentoEnum.NFCE);
        protocoloNFCE.setId(retorno.getProtNFe().getInfProt().getId());
        protocoloNFCE.setAmbiente(retorno.getTpAmb());
        protocoloNFCE.setVersao(retorno.getVersao());
        protocoloNFCE.setChave(retorno.getChNFe());
        protocoloNFCE.setRecibo(retorno.getDhRecbto());
        protocoloNFCE.setProtocolo(retorno.getProtNFe().getInfProt().getNProt());
        byte[] digval = retorno.getProtNFe().getInfProt().getDigVal();
        byte[] base64 = Base64.getEncoder().encode(digval);
        String valor = new String(base64, "ISO-8859-1");
        protocoloNFCE.setDigival(valor);
        protocoloNFCE.setcStat(retorno.getCStat());
        protocoloNFCE.setMotivo(retorno.getXMotivo());
        return protocoloNFCE;
    }

}
