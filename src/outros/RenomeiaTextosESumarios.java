/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package outros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 *
 * @author Marcelo
 */
public class RenomeiaTextosESumarios {
    public static void main(String [] args) throws FileNotFoundException, IOException {
        FileChannel origem = null, destino= null;
        File arq = new File("D:\\Artigos Meus\\Sumarização\\Corpus\\Corpus_Medicina_Ingles_Original\\Textos Fontes\\Tudo");
      //  File arq = new File("D:\\Artigos Meus\\Sumarização\\TeMario-ULTIMA VERSAO out2004\\Sumários\\Sumários manuais");
        File arquivos[] = arq.listFiles();
        for(int i=0;i<arquivos.length;++i) {
            int j=i+1;
            String comp;
            if(j<10) comp = "00"+j;
            else if(j<100) comp = "0" + j; else comp = "" + j;
            origem = new FileInputStream(arquivos[i]).getChannel();
            destino = new FileOutputStream("D:\\CorpusOriginais\\ing\\med\\Texto"+comp+".txt").getChannel();
            destino.transferFrom(origem, 0, origem.size());
            origem.close();
            destino.close();
        }
    }
}
