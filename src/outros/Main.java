/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package outros;

import selecao.SimulatedAnnealing;
import selecao.AleatorioM;
import arquivo.Arquivo;
import selecao.Guloso;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import modelos.Palavra;
import modelos.Resultado;
import identificacao.Lunh;
import modelos.Sentenca;
import identificacao.GeraPontuacao;
import modelos.Texto;
import selecao.Grasp;

/**
 *
 * @author Marcelo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        /*
         * De Mim para mim mesmo:
         * Ultima alteração, inclusão de seleção. Atenção nas palavras Válidas em Arquivo. Sem stopWords
         */
        
         int num = 0;
        ArrayList <Resultado> res = new ArrayList<Resultado>();
        GeraPontuacao gp = new GeraPontuacao();
        Arquivo leArq;
        int i=0, x=0;
        float total, valorTotal;
        File arq = new File("D:\\CorpusOriginais\\ing\\med");
        File arquivos[] = arq.listFiles();
        int percs[] = {50, 70, 80, 90};
        String metodo [] = { "", "KW", "TFISF", "PR"};
        String algoritmo [] = { "Grasp", "Guloso", "SA"};
        for(i=0;i<arquivos.length;++i) {
            Lunh l = new Lunh();
            String nome = "Resumo"+(i+1)+".txt";
            Texto t = new Texto();
            t.limpa();

            // Lê o arquivo e gera o grafo bipartido
            leArq = new Arquivo(arquivos[i].getAbsolutePath(), t,false);

            // Identifica os atributos por Lunh
            l.elementos(t.getPalavras(),10, 40);

            // For dos Métodos de atribuição de valore as sentencas
            for(int j=1; j<metodo.length;++j) {
                t.zeraValor();
                GeraPontuacao ge = new GeraPontuacao();

                // Pontua as sentencas de acordo com o método
                ge.gera(j, t);

                for(int k=0; k<percs.length; ++k) {
                    t.setCompressao(100-percs[k]);
                    for(int m=0;m<algoritmo.length;++m) {
                         ArrayList<Sentenca> s = new ArrayList<Sentenca>();
                         switch(m) {
                             case 0: {
                                 Guloso resolve = new Guloso();
                                 s = resolve.gera(t);
                                 break;
                             }
                             case 1: {
                                 Grasp resolve = new Grasp();
 //                                s = resolve.gera(t);
                                 break;
                             }
                             case 2: {
                                 SimulatedAnnealing resolve = new SimulatedAnnealing();
    //                             s = resolve.gera(t);
                                 break;
                             }
                         }
                         ++num;
                         System.out.println(num+" - D:\\RStil2\\ingl\\med\\"+percs[k]+"%\\"+algoritmo[m]+metodo[j]+"\\"+nome);
                         leArq.geraArquivo("D:\\RStil2\\ingl\\med\\"+percs[k]+"%\\"+algoritmo[m]+metodo[j]+"\\"+nome, s);
                    }
                }
            }
        }



        //carregaDados(res);


















        /*
        for(Resultado r:res) {
            t.limpa(); 
            leArq = new Arquivo(r.getNomeEntrada(), t,false);
            sele.elementos(t.getPalavras(),r.getCi(), r.getCs());
            gp.gera(r.getHeuristica(), t);
            total = 0;
            valorTotal = 0;
            Guloso gu = new Guloso();
            ++x;
            t.setCompressao(r.getPercentual());
            System.out.println(x+" - Guloso - "+r.getHeuristica()+" - "+r.getPercentual()+
                              " - "+r.getNomeSaida()+" --- "+t.getQtdMaxPalavras());
            ArrayList<Sentenca> sent = new ArrayList<Sentenca>();
            ArrayList<Sentenca> sss = new ArrayList<Sentenca>();
            for(Sentenca s:t.getSentencas()) {
                sent.add(s);
            }
            sent=gu.gera(sent, t.getQtdMaxPalavras());
            for(Sentenca s: t.getSentencas()) {
                if(s.contem(sent)) {
                    total+=s.getPalavras().size();
                    valorTotal+=s.getValor();
                    sss.add(s);
                }
                i=i+1;
            }
            leArq.geraArquivo(r.getNomeSaida(), sss);
        }

        /*
        BigInteger sumario;
        ArrayList <Resultado> res = new ArrayList<Resultado>();
        ArrayList <Sentenca> sentencas = new ArrayList<Sentenca>();
        ArrayList <Palavra> palavras = new ArrayList<Palavra>();
        String nome, nomeSaidaGu, nomeSaidaSA, nomeSaidaGu2, aleatorio;
        carregaDados3(res);
        int i=0;
        int percentual = 60;
        int qtdPala;
        int heuristica = 0;
        System.out.println();
        for(int j=0;j<res.size();++j) {
            int total = 0;
            int valorTotal =0;
            Lunh sele = new Lunh();
            nome = res.get(j).getNomeEntrada();
            percentual = res.get(j).getPercentual();
            nomeSaidaSA = res.get(j).getNomeSaida1();
            nomeSaidaGu = res.get(j).getNomeSaida2();
            nomeSaidaGu2 = res.get(j).getNomeSaida3();
            aleatorio = res.get(j).getNomeSaida4();
            heuristica = res.get(j).getHeuristica();
            System.out.print(j+" - "+(100-percentual)+"% - H "+heuristica+" -- nome: "+nome);
            sentencas.clear();
            palavras.clear();
            Arquivo leArq = new Arquivo(nome, sentencas, palavras,false);
            sele.elementos(palavras, palavras.size()*30/100);
            GeraPontuacao g = new GeraPontuacao();
            g.gera(heuristica, sentencas, palavras);
            for(Sentenca s: sentencas) {
                valorTotal+=s.getValor();
                total+=s.getPalavras().size();
            }
            qtdPala = total*percentual / 100;
            total = 0;
            valorTotal = 0;
            /*
            // Aleatório 1
            System.out.print(" - Aleatorio");
            AleatorioM al =new AleatorioM();
            sumario = al.gera(sentencas, qtdPala);
            leArq.geraArquivo(res.get(j).getNomeSaida4().toString(), sumario, sentencas);
            
            SimulatedAnnealing sa = new SimulatedAnnealing();
            System.out.print(" - Simulated Annealing ");
            
            sumario = sa.gera(sentencas, qtdPala, 75000);
            for(Sentenca s: sentencas) {
                if(sumario.testBit(i)) {
                    total+=s.getPalavras().size();
                    valorTotal+=s.getValor();
               }
                i=i+1;
            }
            leArq.geraArquivo(nomeSaidaSA, sumario, sentencas);
            
            for(Sentenca sss:sentencas) {
                System.out.println(sss.getValor()+" - "+sss.getSentenca());
            }
            total = 0;
            valorTotal = 0;
            Guloso gu = new Guloso();
            System.out.print(" - Guloso ");
            ArrayList<Sentenca> sent = gu.gera(sentencas, qtdPala);
            ArrayList<Sentenca> sss = new ArrayList<Sentenca>();
            for(Sentenca s: sentencas) {
                if(s.contem(sent)) {
                    total+=s.getPalavras().size();
                    valorTotal+=s.getValor();
                    sss.add(s);
               }
                i=i+1;
            }
            leArq.geraArquivo(nomeSaidaGu, sss);

            total = 0;
            valorTotal = 0;
            Guloso2 gu2 = new Guloso2();
            System.out.println(" - Guloso 2");
            sent = gu2.gera(sentencas, qtdPala);
            sss.clear();
            for(Sentenca s: sentencas) {
                if(s.contem(sent)) {
                    total+=s.getPalavras().size();
                    valorTotal+=s.getValor();
                    sss.add(s);
               }
               i=i+1;
            }
            leArq.geraArquivo(nomeSaidaGu2, sss);
            System.out.println();
        }*/
    }
    
    private static void carregaDados(ArrayList<Resultado> r) {
        File arq = new File("D:\\CorpusOriginais\\port\\jor");
        File arquivos[] = arq.listFiles();
        int percs[] = {50, 70, 80, 90};
        int cinf[] = { 10, 10, 10, 20, 20, 20 };
        int csup[] = { 30, 40, 50, 30, 40, 50 };
        String metodo [] = { "keywords", "tfisf", "saliencia",
            "pagerank", "grau", "strength", "d_aneis",
            "aglomeracao", "prop_K", "prop_S" };
        /*
        for(int i=0; i<arquivos.length;++i) {
            String nome = "Resumo"+(i+1)+".txt";
            for(int c = 0; c<cinf.length; ++c) {
               for(int m=0; m<metodo.length;++m) {
                   for(int p=0;p<percs.length;++p){
                      r.add(new Resultado(
                             arquivos[i].getAbsolutePath(), 100-percs[p], m+1,
                             "D:\\Resumos Stil\\Port\\Jor\\luhn"+cinf[c]+"-"+csup[c]+"\\"+
                             percs[p]+"%\\"+metodo[m]+"\\"+nome,
                             cinf[c], csup[c]));
                   }
               }
            }
        }
        arq = new File("D:\\CorpusOriginais\\port\\jur");
        arquivos = arq.listFiles();
        for(int i=0; i<arquivos.length;++i) {
            String nome = "Resumo"+(i+1)+".txt";
            for(int c = 0; c<cinf.length; ++c) {
               for(int m=0; m<metodo.length;++m) {
                   for(int p=0;p<percs.length;++p){
                      r.add(new Resultado(
                             arquivos[i].getAbsolutePath(), 100-percs[p], m+1,
                             "D:\\Resumos Stil\\Port\\Jur\\luhn"+cinf[c]+"-"+csup[c]+"\\"+
                             percs[p]+"%\\"+metodo[m]+"\\"+nome,
                             cinf[c], csup[c]));
                   }
               }
            }
        }
        arq = new File("D:\\CorpusOriginais\\port\\med");
        arquivos = arq.listFiles();
        for(int i=0; i<arquivos.length;++i) {
            String nome = "Resumo"+(i+1)+".txt";
            for(int c = 0; c<cinf.length; ++c) {
               for(int m=0; m<metodo.length;++m) {
                   for(int p=0;p<percs.length;++p){
                      r.add(new Resultado(
                             arquivos[i].getAbsolutePath(), 100-percs[p], m+1,
                             "D:\\Resumos Stil\\Port\\Med\\luhn"+cinf[c]+"-"+csup[c]+"\\"+
                             percs[p]+"%\\"+metodo[m]+"\\"+nome,
                             cinf[c], csup[c]));
                   }
               }
            }
        }
        arq = new File("D:\\CorpusOriginais\\ing\\jor");
        arquivos = arq.listFiles();
        for(int i=0; i<arquivos.length;++i) {
            String nome = "Resumo"+(i+1)+".txt";
            for(int c = 0; c<cinf.length; ++c) {
               for(int m=0; m<metodo.length;++m) {
                   for(int p=0;p<percs.length;++p){
                      r.add(new Resultado(
                             arquivos[i].getAbsolutePath(), 100-percs[p], m+1,
                             "D:\\Resumos Stil\\Ingl\\Jor\\luhn"+cinf[c]+"-"+csup[c]+"\\"+
                             percs[p]+"%\\"+metodo[m]+"\\"+nome,
                             cinf[c], csup[c]));
                   }
               }
            }
        }*/
        arq = new File("D:\\CorpusOriginais\\ing\\med");
        arquivos = arq.listFiles();
        for(int i=0; i<arquivos.length;++i) {
            String nome = "Resumo"+(i+1)+".txt";
            for(int c = 0; c<cinf.length; ++c) {
               for(int m=0; m<metodo.length;++m) {
                   for(int p=0;p<percs.length;++p){
                      r.add(new Resultado(
                             arquivos[i].getAbsolutePath(), 100-percs[p], m+1,
                             "D:\\Resumos Stil\\Ingl\\med\\luhn"+cinf[c]+"-"+csup[c]+"\\"+
                             percs[p]+"%\\"+metodo[m]+"\\"+nome,
                             cinf[c], csup[c]));
                   }
               }
            }
        }
    }
    /*
    private static void carregaDados2(ArrayList<Resultado> res) {
        File arq = new File("C:\\CorpusOriginais\\port\\jor");
        File arquivos[] = arq.listFiles();
        int percs[] = {50, 70, 80, 90};

        for(int i=0; i<arquivos.length;++i) {
            String nome = "Resumo"+(i+1)+".txt";
            for(int k=1;k<3;++k) {
                for(int j=0;j<4;++j) {
                    res.add(new Resultado(
                             arquivos[i].getAbsolutePath(), 100-percs[j], k,
                             "C:\\corpus\\port\\jor\\"+percs[j]+"%\\SimAnn"+k+"\\"+nome,
                             "C:\\corpus\\port\\jor\\"+percs[j]+"%\\Guloso"+k+"\\"+nome,
                             "C:\\corpus\\port\\jor\\"+percs[j]+"%\\GulosoCorte"+k+"\\"+nome)
                     );
                }
            }
        }
        
        arq = new File("C:\\CorpusOriginais\\port\\jur");
        arquivos = arq.listFiles();
        for(int i=0; i<arquivos.length;++i) {
            String nome = "Resumo"+(i+1)+".txt";
            for(int k=1;k<3;++k) {
                for(int j=0;j<4;++j) {
                    res.add(new Resultado(
                             arquivos[i].getAbsolutePath(), 100-percs[j], k,
                             "C:\\corpus\\port\\jur\\"+percs[j]+"%\\SimAnn"+k+"\\"+nome,
                             "C:\\corpus\\port\\jur\\"+percs[j]+"%\\Guloso"+k+"\\"+nome,
                             "C:\\corpus\\port\\jur\\"+percs[j]+"%\\GulosoCorte"+k+"\\"+nome)
                     );
                }
            }
        }
        arq = new File("C:\\CorpusOriginais\\port\\med");
        arquivos = arq.listFiles();
        for(int i=0; i<arquivos.length;++i) {
            String nome = "Resumo"+(i+1)+".txt";
            for(int k=1;k<3;++k) {
                for(int j=0;j<4;++j) {
                    res.add(new Resultado(
                             arquivos[i].getAbsolutePath(), 100-percs[j], k,
                             "C:\\corpus\\port\\med\\"+percs[j]+"%\\SimAnn"+k+"\\"+nome,
                             "C:\\corpus\\port\\med\\"+percs[j]+"%\\Guloso"+k+"\\"+nome,
                             "C:\\corpus\\port\\med\\"+percs[j]+"%\\GulosoCorte"+k+"\\"+nome)
                     );
                }
            }
        }
        arq = new File("C:\\CorpusOriginais\\ing\\jor");
        arquivos = arq.listFiles();
        for(int i=0; i<arquivos.length;++i) {
            String nome = "Resumo"+(i+1)+".txt";
            for(int k=1;k<3;++k) {
                for(int j=0;j<4;++j) {
                    res.add(new Resultado(
                             arquivos[i].getAbsolutePath(), 100-percs[j], k,
                             "C:\\corpus\\ing\\jor\\"+percs[j]+"%\\SimAnn"+k+"\\"+nome,
                             "C:\\corpus\\ing\\jor\\"+percs[j]+"%\\Guloso"+k+"\\"+nome,
                             "C:\\corpus\\ing\\jor\\"+percs[j]+"%\\GulosoCorte"+k+"\\"+nome)
                     );
                }
            }
        }
        arq = new File("C:\\CorpusOriginais\\ing\\med");
        arquivos = arq.listFiles();
        for(int i=0; i<arquivos.length;++i) {
            String nome = "Resumo"+(i+1)+".txt";
            for(int k=1;k<3;++k) {
                for(int j=0;j<4;++j) {
                    res.add(new Resultado(
                             arquivos[i].getAbsolutePath(), 100-percs[j], k,
                             "C:\\corpus\\ing\\med\\"+percs[j]+"%\\SimAnn"+k+"\\"+nome,
                             "C:\\corpus\\ing\\med\\"+percs[j]+"%\\Guloso"+k+"\\"+nome,
                             "C:\\corpus\\ing\\med\\"+percs[j]+"%\\GulosoCorte"+k+"\\"+nome)
                     );
                }
            }
        }
    }
    private static void carregaDados3(ArrayList<Resultado> res) {
        File arq = new File("C:\\CorpusOriginais\\port\\jor");
        File arquivos[] = arq.listFiles();
        int percs[] = {50, 70, 80, 90};

        for(int i=0; i<arquivos.length;++i) {
            String nome = "Resumo"+(i+1)+".txt";
            int k=3;
            {
               for(int j=0;j<4;++j) {
                    res.add(new Resultado(
                             arquivos[i].getAbsolutePath(), 100-percs[j], k,
                             "C:\\corpus\\port\\jor\\"+percs[j]+"%\\SimAnn"+k+"\\"+nome,
                             "C:\\corpus\\port\\jor\\"+percs[j]+"%\\Guloso"+k+"\\"+nome,
                             "C:\\corpus\\port\\jor\\"+percs[j]+"%\\GulosoCorte"+k+"\\"+nome,
                             "C:\\corpus\\port\\jor\\"+percs[j]+"%\\AleatorioM\\"+nome)
                     );
                }
            }
        }

        arq = new File("C:\\CorpusOriginais\\port\\jur");
        arquivos = arq.listFiles();
        for(int i=0; i<arquivos.length;++i) {
            String nome = "Resumo"+(i+1)+".txt";
            int k=3;
            {
                for(int j=0;j<4;++j) {
                    res.add(new Resultado(
                             arquivos[i].getAbsolutePath(), 100-percs[j], k,
                             "C:\\corpus\\port\\jur\\"+percs[j]+"%\\SimAnn"+k+"\\"+nome,
                             "C:\\corpus\\port\\jur\\"+percs[j]+"%\\Guloso"+k+"\\"+nome,
                             "C:\\corpus\\port\\jur\\"+percs[j]+"%\\GulosoCorte"+k+"\\"+nome,
                             "C:\\corpus\\port\\jur\\"+percs[j]+"%\\AleatorioM\\"+nome)
                     );
                }
            }
        }
        arq = new File("C:\\CorpusOriginais\\port\\med");
        arquivos = arq.listFiles();
        for(int i=0; i<arquivos.length;++i) {
            String nome = "Resumo"+(i+1)+".txt";
            int k=3;
            {
                for(int j=0;j<4;++j) {
                    res.add(new Resultado(
                             arquivos[i].getAbsolutePath(), 100-percs[j], k,
                             "C:\\corpus\\port\\med\\"+percs[j]+"%\\SimAnn"+k+"\\"+nome,
                             "C:\\corpus\\port\\med\\"+percs[j]+"%\\Guloso"+k+"\\"+nome,
                             "C:\\corpus\\port\\med\\"+percs[j]+"%\\GulosoCorte"+k+"\\"+nome,
                             "C:\\corpus\\port\\med\\"+percs[j]+"%\\AleatorioM\\"+nome)
                     );
                }
            }
        }
        arq = new File("C:\\CorpusOriginais\\ing\\jor");
        arquivos = arq.listFiles();
        for(int i=0; i<arquivos.length;++i) {
            String nome = "Resumo"+(i+1)+".txt";
            int k=3;
            {
                for(int j=0;j<4;++j) {
                    res.add(new Resultado(
                             arquivos[i].getAbsolutePath(), 100-percs[j], k,
                             "C:\\corpus\\ing\\jor\\"+percs[j]+"%\\SimAnn"+k+"\\"+nome,
                             "C:\\corpus\\ing\\jor\\"+percs[j]+"%\\Guloso"+k+"\\"+nome,
                             "C:\\corpus\\ing\\jor\\"+percs[j]+"%\\GulosoCorte"+k+"\\"+nome,
                             "C:\\corpus\\ing\\jor\\"+percs[j]+"%\\AleatorioM\\"+nome)
                     );
                }
            }
        }
        arq = new File("C:\\CorpusOriginais\\ing\\med");
        arquivos = arq.listFiles();
        for(int i=0; i<arquivos.length;++i) {
            String nome = "Resumo"+(i+1)+".txt";
            int k=3;
            {
                for(int j=0;j<4;++j) {
                    res.add(new Resultado(
                             arquivos[i].getAbsolutePath(), 100-percs[j], k,
                             "C:\\corpus\\ing\\med\\"+percs[j]+"%\\SimAnn"+k+"\\"+nome,
                             "C:\\corpus\\ing\\med\\"+percs[j]+"%\\Guloso"+k+"\\"+nome,
                             "C:\\corpus\\ing\\med\\"+percs[j]+"%\\GulosoCorte"+k+"\\"+nome,
                             "C:\\corpus\\ing\\med\\"+percs[j]+"%\\AleatorioM\\"+nome)
                     );
                }
            }
        }
    }
     * 
     */
}
