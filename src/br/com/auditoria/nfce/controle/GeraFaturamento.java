/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.auditoria.nfce.controle;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import nfceutils.TotalizarNFCe;
import nfceutils.XMLNFCe;

public class GeraFaturamento {

    PdfPTable pdfPTable = new PdfPTable(5);
    DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");

    public boolean gerarFaturamento(String caminhoXMLS, String tituloPDF) throws Exception {
        Document document = null;
        try {
            File file = new File("FATURAMENTO.pdf");
            if (file.exists()) {
                file.delete();
            }
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file,false));
            document.open();
            PdfPCell pdfPCellCabelacho = new PdfPCell();

            Paragraph cabecalho = new Paragraph(tituloPDF);
            cabecalho.setAlignment(Paragraph.ALIGN_CENTER);
            pdfPCellCabelacho.addElement(cabecalho);
            pdfPCellCabelacho.setColspan(5);
            pdfPCellCabelacho.setBorder(0);
            pdfPTable.addCell(pdfPCellCabelacho);

            cabecalhoTabela();

            File[] files = new File(caminhoXMLS).listFiles();
            XMLNFCe xmlnfc = new XMLNFCe();
            TotalizarNFCe totalizarNFCe = new TotalizarNFCe();

            int count = 1;

            for (File arquivo : files) {
                try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                    String linha = "";
                    while (br.ready()) {
                        linha += br.readLine();
                    }
                    String chave, protocolo = "";
                    if (linha.contains("<chNFe>")) {
                        chave = xmlnfc.procurarNoXML(linha, "chNFe");
                    } else {
                        chave = linha.substring(115, 159);
                    }
                    if (linha.contains("<nProt>")) {
                        protocolo = xmlnfc.procurarNoXML(linha, "nProt");
                    }
                    String datas[] = xmlnfc.procurarNoXML(linha, "dhEmi").split("T")[0].split("-");
                    String data = datas[2] + "/" + datas[1] + "/" + datas[0];
                    String valor = xmlnfc.procurarNoXML(linha, "vNF");
                    escrever(count, chave, data, decimalFormat.format(Double.parseDouble(valor)), protocolo);
                    count++;
                } catch (IOException ex) {
                    System.out.println("ex = " + ex.getMessage());
                }
            }
            total(decimalFormat.format(totalizarNFCe.realizarCalculo(new File(caminhoXMLS).getAbsolutePath()).getTotalNFCe()));
            pdfPTable.setWidths(new float[]{0.2f, 0.4f, 0.2f, 0.2f, 0.2f});
            pdfPTable.setWidthPercentage(100.0f);
            document.add(pdfPTable);
            return true;
        } catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de.getMessage());
        } finally {
            if(document != null)
                document.close();
        }
        return false;
    }

    private void cabecalhoTabela() {
        // Nome das colunas
        PdfPCell pdfPCellLinhaHeader = new PdfPCell();
        PdfPCell pdfPCellChaveHeader = new PdfPCell();
        PdfPCell pdfPCellProtocoloHeader = new PdfPCell();
        PdfPCell pdfPCellDataHeader = new PdfPCell();
        PdfPCell pdfPCellValorHeader = new PdfPCell();

        Paragraph paragrafoLinhaHeader = new Paragraph("Linha");
        paragrafoLinhaHeader.setAlignment(Paragraph.ALIGN_LEFT);
        pdfPCellLinhaHeader.addElement(paragrafoLinhaHeader);
        pdfPCellLinhaHeader.setBorder(0);
        pdfPTable.addCell(pdfPCellLinhaHeader);

        Paragraph paragrafochaveHeader = new Paragraph("Chave");
        paragrafochaveHeader.setAlignment(Paragraph.ALIGN_LEFT);
        pdfPCellChaveHeader.addElement(paragrafochaveHeader);
        pdfPCellChaveHeader.setBorder(0);
        pdfPTable.addCell(pdfPCellChaveHeader);

        Paragraph paragrafoProtocoloHeader = new Paragraph("Protocolo ");
        paragrafoProtocoloHeader.setAlignment(Paragraph.ALIGN_LEFT);
        pdfPCellProtocoloHeader.addElement(paragrafoProtocoloHeader);
        pdfPCellProtocoloHeader.setBorder(0);
        pdfPTable.addCell(pdfPCellProtocoloHeader);

        Paragraph paragrafoDataHeader = new Paragraph("Data ");
        paragrafoDataHeader.setAlignment(Paragraph.ALIGN_LEFT);
        pdfPCellDataHeader.addElement(paragrafoDataHeader);
        pdfPCellDataHeader.setBorder(0);
        pdfPTable.addCell(pdfPCellDataHeader);

        Paragraph paragrafoValorHeader = new Paragraph("Valor");
        paragrafoValorHeader.setAlignment(Paragraph.ALIGN_LEFT);
        pdfPCellValorHeader.addElement(paragrafoValorHeader);
        pdfPCellValorHeader.setBorder(0);
        pdfPTable.addCell(pdfPCellValorHeader);
    }

    private void escrever(int i, String... values) {

        PdfPCell pdfPCellLinha = new PdfPCell();
        PdfPCell pdfPCellChave = new PdfPCell();
        PdfPCell pdfPCellData = new PdfPCell();
        PdfPCell pdfPCellProtocolo = new PdfPCell();
        PdfPCell pdfPCellValor = new PdfPCell();

        //linhas do pdf
        Font font = new Font(Font.FontFamily.HELVETICA, 8);

        Paragraph paragrafoLinha = new Paragraph(String.valueOf(i), font);
        paragrafoLinha.setAlignment(Paragraph.ALIGN_LEFT);
        pdfPCellLinha.addElement(paragrafoLinha);
        pdfPCellLinha.setBorder(0);
        if (i % 2 == 0) {
            pdfPCellLinha.setBackgroundColor(new BaseColor(Color.decode("#e8e8e8")));
        }
        pdfPCellLinha.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCellLinha);

        Paragraph paragrafochave = new Paragraph(values[0], font);
        paragrafochave.setAlignment(Paragraph.ALIGN_LEFT);
        pdfPCellChave.addElement(paragrafochave);
        pdfPCellChave.setBorder(0);
        if (i % 2 == 0) {
            pdfPCellChave.setBackgroundColor(new BaseColor(Color.decode("#e8e8e8")));
        }
        pdfPCellChave.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCellChave);

        Paragraph paragrafoProtocolo = new Paragraph(values[3], font);
        paragrafoProtocolo.setAlignment(Paragraph.ALIGN_LEFT);
        pdfPCellProtocolo.addElement(paragrafoProtocolo);
        pdfPCellProtocolo.setBorder(0);
        if (i % 2 == 0) {
            pdfPCellProtocolo.setBackgroundColor(new BaseColor(Color.decode("#e8e8e8")));
        }
        pdfPTable.addCell(pdfPCellProtocolo);

        Paragraph paragrafoData = new Paragraph(values[1], font);
        paragrafoData.setAlignment(Paragraph.ALIGN_LEFT);
        pdfPCellData.addElement(paragrafoData);
        pdfPCellData.setBorder(0);
        if (i % 2 == 0) {
            pdfPCellData.setBackgroundColor(new BaseColor(Color.decode("#e8e8e8")));
        }
        pdfPTable.addCell(pdfPCellData);

        Paragraph paragrafoValor = new Paragraph(values[2], font);
        paragrafoValor.setAlignment(Paragraph.ALIGN_LEFT);
        pdfPCellValor.addElement(paragrafoValor);
        pdfPCellValor.setBorder(0);
        if (i % 2 == 0) {
            pdfPCellValor.setBackgroundColor(new BaseColor(Color.decode("#e8e8e8")));
        }

        pdfPTable.addCell(pdfPCellValor);
    }

    private void total(String valor) {
        Font font = new Font(Font.FontFamily.HELVETICA, 11);

        PdfPCell pdfPCell = new PdfPCell();
        Paragraph paragraph = new Paragraph("", font);
        paragraph.setAlignment(Paragraph.ALIGN_LEFT);
        pdfPCell.addElement(paragraph);
        pdfPCell.setColspan(5);
        pdfPCell.setBorder(0);
        pdfPTable.addCell(pdfPCell);

        PdfPCell pdfPCellLabel = new PdfPCell();
        PdfPCell pdfPCellValor = new PdfPCell();

        Paragraph paragraphLabel = new Paragraph("Total", font);
        paragraphLabel.setAlignment(Paragraph.ALIGN_LEFT);
        pdfPCellLabel.addElement(paragraphLabel);
        pdfPCellLabel.setBorder(0);
        pdfPCellLabel.setColspan(4);
        pdfPCellLabel.setBackgroundColor(new BaseColor(Color.decode("#cccccc")));
        pdfPTable.addCell(pdfPCellLabel);

        Paragraph paragraphValor = new Paragraph(valor, font);
        paragraphValor.setAlignment(Paragraph.ALIGN_RIGHT);
        pdfPCellValor.addElement(paragraphValor);
        pdfPCellValor.setBorder(0);
        pdfPCellValor.setColspan(1);
        pdfPCellValor.setBackgroundColor(new BaseColor(Color.decode("#cccccc")));
        pdfPTable.addCell(pdfPCellValor);
    }

}
