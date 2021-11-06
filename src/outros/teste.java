/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package outros;

import arquivo.Arquivo;
import identificacao.GeraPontuacao;
import identificacao.Lunh;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import modelos.Resultado;
import modelos.Sentenca;
import modelos.Texto;
import selecao.Grasp;
import selecao.Guloso;

/**
 *
 * @author Marcelo
 */
public class teste {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Lunh sele = new Lunh();
        ArrayList <Resultado> res = new ArrayList<Resultado>();
        GeraPontuacao gp = new GeraPontuacao();
        Arquivo leArq;
        int i=0, x=0;
        float total, valorTotal;

        Texto t = new Texto();
        t.limpa();
        leArq = new Arquivo("d:\\Cardiologia01_car.txt", t,false);
        sele.elementos(t.getPalavras(),10, 50);
        gp.gera(4, t);
        total = 0;
        valorTotal = 0;
        Grasp gu = new Grasp();
        ++x;
         t.setCompressao(50);
        ArrayList<Sentenca> sent = new ArrayList<Sentenca>();
        ArrayList<Sentenca> sss = new ArrayList<Sentenca>();
        for(Sentenca s:t.getSentencas()) {
            sent.add(s);
        }
//        sent=gu.gera(t);
        for(Sentenca s: t.getSentencas()) {
            if(s.contem(sent)) {
                total+=s.getPalavras().size();
                valorTotal+=s.getValor();
                sss.add(s);
            }
            i=i+1;
        }
        leArq.geraArquivo("D:\\ResumoG.txt", sss);
    }
}
