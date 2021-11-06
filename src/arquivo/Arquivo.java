/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package arquivo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import modelos.Palavra;
import modelos.Sentenca;
import modelos.StopWords;
import modelos.Texto;

/**
 *
 * @author Marcelo
 */
public class Arquivo {
    public Arquivo(String nome, Texto t, boolean stopWords) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        BufferedReader le = new BufferedReader(new InputStreamReader(new FileInputStream(nome), "Cp1252"));
        String sentencaAtual="";
        while(le.ready()) {
                String linha;
                int i;
                linha=le.readLine();
                for(i=0;i<linha.length();++i) {
                    char atu = linha.charAt(i);
                    sentencaAtual=sentencaAtual+atu;
                    if((atu=='.' || atu=='!' || atu=='?'||i==linha.length()-1) &&  //{
                       (sentencaAtual.length()>=4)) {// || i==linha.length()-1)) {
                          //  if(i==linha.length()-1) {
                                adicionaSentenca(sentencaAtual, t, stopWords);
                                sentencaAtual = "";
                            }
                    //}
                }
                if(!sentencaAtual.equals("")) sentencaAtual+=" ";
            }
        le.close();
    }

    private void adicionaSentenca(String sentencaAtual, Texto t, boolean stopWords) {
        //// ATENÇÃO AQUI ... STOPWORDS 
        StopWords sw = new StopWords(0);
        String pala = "";
        Sentenca sent = new Sentenca();
        sent.setNum(t.getSentencas().size());
        sent.setSentenca(sentencaAtual);
        sent.setValor(0);
        sent.setNoSumario(false);
        for(int i=0;i<sentencaAtual.length();++i) {
            char atu = sentencaAtual.charAt(i);
            if((atu==' '||atu=='.'||atu==','||atu==':'||atu==';'||atu=='"'||
                  atu=='-'||atu=='('||atu==')'||i==sentencaAtual.length()-1)&& pala.equals("")) continue;
            if((atu==' '||atu=='.'||atu==','||atu==':'||atu==';'||atu=='"'||
                  atu=='-'||atu=='('||atu==')'||i==sentencaAtual.length()-1)&&!pala.equals("")) {
                Palavra p = new Palavra();
                boolean contemPala = false;
                boolean flag = true;
                for(int j=0;j<pala.length();++j) {
                    if(pala.charAt(j)=='0'||pala.charAt(j)=='1'||pala.charAt(j)=='2'||pala.charAt(j)=='3'||
                         pala.charAt(j)=='4'||pala.charAt(j)=='5'||pala.charAt(j)=='6'||pala.charAt(j)=='7'||
                         pala.charAt(j)=='8'||pala.charAt(j)=='9'||pala.charAt(j)==' '||pala.charAt(j)=='.') {
                        flag = false;
                        break;
                    }
                }
                if(flag) {
                    for(Palavra p1:t.getPalavras()) {
                        if(p1.getPalavra().toLowerCase().equals(pala.toLowerCase())) {
                            p=p1;
                            contemPala = true;
                        }
                    }
                    if(!contemPala) {
                        p.setPalavra(pala.toLowerCase());
                        p.adcQtdSentencas(sent.getNum());
                        p.addSentenca(sent.getNum());
                        if(stopWords)
                            p.setValida(!sw.contem(pala));
                        else
                            p.setValida(false);
                        t.adicionaPalavra(p);
                    }
                    else {
                        p.adcQtdSentencas(sent.getNum());
                        p.addSentenca(sent.getNum());
                    }
                    sent.addPalavra(p);
                    t.adicionaTotalPalavras();
                }
                pala = "";
            } else {
                pala+=atu;
            }
        }
        t.adicionaSentenca(sent);
    }
    public void geraArquivo(String nome, BigInteger sumario, ArrayList<Sentenca> sent) throws FileNotFoundException, IOException {
          FileOutputStream fDes = new FileOutputStream(nome);
          DataOutputStream dDes = new DataOutputStream(fDes);
          for(Sentenca s:sent) {
              if(sumario.testBit(s.getNum())) {
                  dDes.writeBytes(s.getSentenca().trim());
                  dDes.write('\n');
              }
          }
          dDes.close();
    }
    public void geraArquivo(String nome, ArrayList<Sentenca> sent) throws FileNotFoundException, IOException {
          FileOutputStream fDes = new FileOutputStream(nome);
          DataOutputStream dDes = new DataOutputStream(fDes);
          for(Sentenca s:sent) {
               dDes.writeBytes(s.getSentenca().trim());
               dDes.writeByte('\n');
          }
          dDes.close();
    }
}