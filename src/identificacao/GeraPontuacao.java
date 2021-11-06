/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package identificacao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import modelos.Biblioteca;
import modelos.Palavra;
import modelos.Sentenca;
import modelos.Texto;

/**
 *
 * @author Marcelo
 */
public class GeraPontuacao {
    public void gera(int i, Texto t) {
        switch(i) {
            case 1:keywords(t); break;
            case 2:tsisf(t); break;
            case 3:pageRank(t); break;
            case 4:saliencia(t); break;
            case 5:
              {
                float [] vet = new float[t.getSentencas().size()];
                grau(t, vet);
                for(int x=0;x<t.getSentencas().size();++x) 
                   t.getSentencas().get(x).setValor(vet[x]);
                break;
              }
            case 6:
              {
                float [] vet = new float[t.getSentencas().size()];
                strenght(t, vet);
                for(int x=0;x<t.getSentencas().size();++x) 
                   t.getSentencas().get(x).setValor(vet[x]);
                break;
              }
            case 7:dAneis(t); break;
            case 8:
              {
                float [] vet = new float[t.getSentencas().size()];
                coeficienteAglomeracao(t, vet);
                for(int x=0;x<t.getSentencas().size();++x) 
                   t.getSentencas().get(x).setValor(vet[x]);
                break;
              }
                
            case 9:proporcionalTamanhoK(t); break;
            case 10:proporcionalTamanhoS(t); break;
        }
    }

    //************************************************* 1 - KEYWORDS
    private void keywords(Texto t) {
        for(Palavra pala: t.getPalavras()) {
            if(pala.isValida()) {
                pala.setValor(pala.frequencia());
                for(Integer i:pala.getSentencas()) {
                    Sentenca s = t.getSentencas().get(i);
                    s.setValor(s.getValor()+pala.frequencia());
                }
            }
        }
    }
    
    //************************************************* 2 - TF-ISF
    private void tsisf(Texto t) {
        for(Sentenca s:t.getSentencas()) {
            ArrayList<Palavra> verificada = new ArrayList<Palavra>();
            float valor = 0;
            for(Palavra p:s.getPalavras()) {
                if(p.isValida() && !verificada.contains(p)) {
                    valor = valor + (float)((p.qtdNaSentenca(s.getNum()))*
                                    (float) Math.log(s.getPalavras().size()))/
                                    (float)p.frequencia();
                    verificada.add(p);
                }
            }
            s.setValor(valor);
        }
    }
    
    //*************************************************  3 - SALIENCIA
    private void saliencia(Texto t) {
        int grafo[][] = Biblioteca.geraGrafo(t,1);
        for(int i=0;i<t.getSentencas().size();++i) {
           int grf[][] = grafo.clone();
           int Ni = calculaNi(grf, i, t.getSentencas().size());
           float x = Biblioteca.calculaGrau(grafo,i,t.getSentencas().size())*(t.getSentencas().size()-Ni);
           t.getSentencas().get(i).setValor(x);
        }
    }

    private int calculaNi(int grf[][], int j, int tam) {
        int [][] grafo = new int[tam][tam];
        int maior = 0;
        for(int i=0;i<tam;++i) {
            for(int k=0;k<tam;++k) {
               grafo[i][k]=grf[i][k];
            }
        }
        
        for(int i=0;i<tam; ++i) {
           grafo[i][j] = 0;
           grafo[j][i] = 0;
        }
        for(int i=0; i<tam; ++i) {
            int visitado[] = new int[tam];
            int matu = 0;
            for(int k=0;k<tam;++k) visitado[k]=0;
            visitado[i] = 1;
            mConectado(grafo, visitado, i, tam, 1);
            for(int k=0; k<tam;++k) {
                if(visitado[k]>matu) matu=visitado[k];
            }
            --matu;
            maior=matu>maior?matu:maior;
        }
        return maior;
    }
    private void mConectado(int [][] grafo, int [] visitado, int i, int tam, int prof) {
        ++prof;
        for(int j=0;j<tam; ++j) {
           if(grafo[i][j]==1 && visitado[j]==0) {
              visitado[j] = prof;
              mConectado(grafo, visitado, j, tam, prof);
           }
        }
    }  

    //************************************************* 4 - PAGERANK
    private void pageRank(Texto t) {
        int i, j;
        int grafo[][]=Biblioteca.geraGrafo(t, 3);
        float PR[] = new float[t.getSentencas().size()];
        float d = 0.08f;
        for(i=0;i<t.getSentencas().size();++i) {
            PR[i] = -1;
            for(j=0;j<t.getSentencas().size();++j) grafo[i][j]=grafo[i][j]/2;
        }
        do {
           for(i=0;i<PR.length;++i) {
               if(PR[i]==-1) {
                 calculaPR(i, grafo, PR, d);
                 break;
               }
           }
        } while(!verificaPR(PR));
        for(i=0;i<t.getSentencas().size();++i) 
            t.getSentencas().get(i).setValor(PR[i]);
    }

    private boolean verificaPR(float[] PR) {
        boolean ok=true;
        for(int i=0;i<PR.length;++i) 
            if(PR[i]==-1) {
                ok=false;
                break;
            }
        return ok;
    }

    private float calculaPR(int i, int[][] grafo, float[] PR, float d) {
        float cPR = 0;
        for(int j=0;j<PR.length;++j) {
            if(grafo[j][i]>0 && PR[j]!=-1) {
               cPR = cPR + PR[j];
            } else if(grafo[j][i]>0 && PR[j]==-1) { 
               cPR = cPR + grafo[j][i]*(calculaPR(j,grafo,PR,d)/outVizinho(grafo, j));
            }
        }
        PR[i] = (1-d)+d*cPR;
        return cPR;
    }

    private float outVizinho(int[][] grafo, int j) {
        int tot = 0;
        for(int i=0;i<grafo[j].length;++i) {
            if(grafo[j][i]>0) tot+=grafo[j][i];
        }
        return tot;
    }

    //************************************************* 5 - GRAU
    private void grau(Texto t, float[] k) {
        int grafo[][] = Biblioteca.geraGrafo(t, 1);
        for(int i=0;i<t.getSentencas().size();++i) {
           k[i]=Biblioteca.calculaGrau(grafo,i,t.getSentencas().size());
        }
    }

    //************************************************* 6 - STRENGHT
    private void strenght(Texto t, float [] s) {
        int grafo[][] = Biblioteca.geraGrafo(t, 2);
        for(int i=0;i<t.getSentencas().size();++i) {
           s[i]=Biblioteca.calculaGrau(grafo,i,t.getSentencas().size());
        }
    }
    
    //************************************************* 7 - D-ANÉIS
    private void dAneis(Texto t) {
        float [] conex = new float[t.getSentencas().size()];
        int grafo[][]=Biblioteca.geraGrafo(t, 1);
        Queue <Integer> fila = new LinkedList<Integer>();
        int visitado[] = new int[t.getSentencas().size()];
        int pai[] = new int[t.getSentencas().size()];
        int indMa = 0;
        int i=0;
        grau(t, conex);
        for(i=0;i<conex.length;++i) {
            visitado[i]=-1;
            pai[i] = 0;
            if(conex[i]>conex[indMa]) {
                indMa = i;
            }
        }
        fila.add(indMa);
        while(!fila.isEmpty()) {
            i = fila.remove();
            if(visitado[i]<0) {
               int j;
               visitado[i]=pai[i]+1;
               for(j=0;j<t.getSentencas().size();++j) {
                  // System.out.println(i+" ---- "+j+" -- "+grafo[i][j]);
                  if(grafo[i][j]==1) {
                      fila.add(j);
                      pai[j]=visitado[i];
                  }
               }
            }
        }
        i=0;
        for(Sentenca s:t.getSentencas()) {
           if(visitado[i]>0) {
              s.setValor((float)1/(float)visitado[i]);
           }
           ++i;
        }
        
    }

    //************************************************* 8 - COEFICIENTE DE AGLOMERAÇÃO
    private void coeficienteAglomeracao(Texto t, float[] ss) {
        int i, j, l;
        float [] s = new float[t.getSentencas().size()];
        float [] k = new float[t.getSentencas().size()];
        int [][] gp = Biblioteca.geraGrafo(t, 2);
        int [][] g  = Biblioteca.geraGrafo(t, 1);
        strenght(t, s);
        grau(t, k);
        for(i=0;i<s.length;++i) {
            float soma=0;
            float valor;
            for(j=0;j<s.length-1;++j){
                for(l=j+1;l<s.length;++l) {
                   if(i!=j && i!=l && j!=l) {
                      soma=soma + ((gp[i][j]+gp[i][l])/2)*g[i][j]*g[i][l]*g[j][l];
                   }
                }
            }
            if(s[i]!=0 && k[i]>1) 
                valor=(1/(s[i]*(k[i]-1)))*soma;
            else
                valor = 0;
            ss[i]=valor;
        }
    }

    //************************************************* 9 - PROPORCIONALTAMANHO_K
    private void proporcionalTamanhoK(Texto t) {
        float [] k=new float[t.getSentencas().size()];
        float ma = 0;
        int i=0;
        grau(t, k);
        for(i=0;i<k.length;++i) {
            if(k[i] >ma) {
                ma = k[i];
            }
        }
        i=0;
        for(Sentenca s:t.getSentencas()) {
            if((((k[i])/ma)*(float)s.getPalavras().size())>0)
              s.setValor(((float)k[i]/ma)*s.getPalavras().size());
            else
              s.setValor(0);
            ++i;
        }
    }

    //************************************************* 10 - PROPORCIONALTAMANHO_S
    private void proporcionalTamanhoS(Texto t) {
        float [] k=new float[t.getSentencas().size()];
        float ma = 0;
        int i=0;
        strenght(t, k);
        for(i=0;i<k.length;++i) {
            if(k[i]>ma) {
                ma = k[i];
            }
        }
        i=0;
        for(Sentenca s:t.getSentencas()) {
            if((((k[i])/ma)*(float)s.getPalavras().size())>0)
              s.setValor(((float)k[i]/ma)*s.getPalavras().size());
            else
              s.setValor(0);
            ++i;
        }
    }
}