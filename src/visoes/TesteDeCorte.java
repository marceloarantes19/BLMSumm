/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visoes;

import arquivo.Arquivo;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import modelos.Palavra;
import modelos.StopWords;
import modelos.Texto;

/**
 *
 * @author marcelo
 */
public class TesteDeCorte {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        // TODO code application logic here
        StopWords sw=new StopWords(1);
        Texto t = new Texto();
        TreeSet<TC> tc = new TreeSet<TC>();
        Arquivo leArq = new Arquivo("/media/Dados/CorpusOriginais/port/jor/Texto002.txt", t, false);
        for(Palavra p:t.getPalavras()){
            tc.add(new TC(p.getPalavra(), p.getSentencas().size(), sw.contem(p.getPalavra())));
        }
        System.out.println("_______________________________________________");
        for(TC tt:tc){
            System.out.println(tt.getPalavra()+ " " + tt.getQuantidade()+ " "+tt.isSw());
        }
        
    }
}

