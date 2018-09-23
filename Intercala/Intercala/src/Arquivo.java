import java.io.*;
public class Arquivo {
	private OutputStream saida;
    private DataOutputStream dout;
    
    public Arquivo(String arquivo) throws IOException {
    	saida = new FileOutputStream(arquivo);
        dout = new DataOutputStream(saida);
    }

	public DataOutputStream getDout() {
		return dout;
	}
	
	public void escreverEndereco(Endereco e) throws IOException {
		e.escreveEndereco(dout);
	}
	
	public void encerrar() throws IOException {
		dout.close();
        saida.close();
	}
}
