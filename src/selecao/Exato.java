package selecao;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelos.Sentenca;
import modelos.Texto;
public class Exato {
    int cont =0;
    BigInteger melhor = new BigInteger("0");
    ArrayList<Integer> sentencaOk = new ArrayList<Integer>();
    ArrayList<Sentenca> sentencas = new ArrayList<Sentenca>();
    int maxPalavras = 0;
    float vMelhor = 0;
    int qtdPMelhor = 0;
    public ArrayList<Sentenca> gera(Texto t) {
        BigInteger x = new BigInteger("0");
        ArrayList<Sentenca> resp = new ArrayList<Sentenca>();
        int num = 0;
        sentencas = t.getSentencas();
        maxPalavras = t.getQtdMaxPalavras();
        for(Sentenca s:t.getSentencas()) {
            if(s.getQuantidadeDePalavras()<=t.getQtdMaxPalavras()) {
                ++num;
                sentencaOk.add(t.getSentencas().indexOf(s));
            }
        }
        /*
        grayCode(0, x, num,0, 0.0f);
        for(int i=0;i<sentencas.size();++i) {
            if(melhor.testBit(i+1)) {
                resp.add(sentencas.get(i));
            }
          
        
        
        } */
        System.out.println(num+" ----- "+Math.pow(2,num));
        return resp;
    }
    public void grayCode(int i, BigInteger x, int max, int qtAtu, float vlAtu) {
        if (i<max) {
            int j = sentencaOk.get(i);
            if(qtAtu+sentencas.get(j).getQuantidadeDePalavras()<=maxPalavras) {
                grayCode(i+1, x.setBit(j), max, qtAtu+sentencas.get(j).getQuantidadeDePalavras(), vlAtu+sentencas.get(j).getValor());
                grayCode(i+1,x, max, qtAtu, vlAtu);
            }
        } else {
            if(i>0) {
                 ++cont;
                 System.out.print( cont+" --- "+x);
                 if(vlAtu>vMelhor) {
                     vlAtu=vMelhor;
                     melhor = new BigInteger(x.toString());
                     System.out.print(" --- trocou");
                 }
                 System.out.println();
                 
            }
        }
    }
}
