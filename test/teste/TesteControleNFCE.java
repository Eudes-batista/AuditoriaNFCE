package teste;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TesteControleNFCE {

    public static void main(String[] args) throws Exception {

        File file = new File("xmls/201903");
        File[] files = file.listFiles();
        long tamanhoDoArquivoXML = 0;
        long mega = 1024 * 1024;
        long converterByteEmMega;
        List<File> arquivos = new ArrayList<>();
        for (File xml : files) {
            tamanhoDoArquivoXML += xml.length();
            converterByteEmMega = tamanhoDoArquivoXML / mega;
            arquivos.add(xml);
            if (converterByteEmMega >= 20) {
                break;
            }
        }
        System.out.println(arquivos.size());

    }

}
