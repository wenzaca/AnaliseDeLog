package servicos;

import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;

import repositorios.RepositorioProcesso;
import repositorios.RepositorioNo;
import entidades.EntidadeNo;
import entidades.EntidadeThread;
import interfaces.FrmDiretorio;
import interfaces.FrmNodos;
import repositorios.RepositorioThread;
import utilidades.ProcessaDadosDoNo;
import utilidades.ProcessaStacksUtil;

public class ServicoFachada {

	private ServicoDescompactador servicoDescompactador = new ServicoDescompactador();
	private ProcessaDadosDoNo processaDadosDoNo = new ProcessaDadosDoNo();
	ServicoValidadorDeNos servicoValidadorDeNos = new ServicoValidadorDeNos();
	private RepositorioProcesso repositorioProcesso = new RepositorioProcesso();
	private RepositorioNo repositorioNo = new RepositorioNo();
	private RepositorioThread repositorioThread = new RepositorioThread();
	




	public File solicitarServicoDescompactador(String caminho, List<String> ListaArquivo) {
		File file = servicoDescompactador.extrairLogs(caminho, ListaArquivo);
		
		solicitarServicoValidadorDeNos(file);
		
		return file;
	}
	
	public void solicitarServicoValidadorDeNos(File file){
		servicoValidadorDeNos.listar(file);

		if (ServicoValidadorDeNos.valido == true){
			solicitarProcessaDadosDoNo(file);
			solicitarProcessaDadosCpuDetalhado(file);
			FrmDiretorio formUm = new FrmDiretorio();
			FrmNodos formDois = new FrmNodos();
			formDois.setVisible(true);
			formUm.setVisible(false);
		}
		else{

			JOptionPane.showMessageDialog(null,servicoValidadorDeNos.mensagemErro +"\n POR FAVOR, ESCOLHA OUTRO DIRETORIO", "ERRO: Arquivo invalido ou em branco", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	private void solicitarProcessaDadosDoNo(File caminhoTemp) {
		processaDadosDoNo.processaDiretorio(caminhoTemp);

	}

	public void deletaRepositorios() {
		repositorioProcesso.delete();
		repositorioNo.delete();
	}

	private void solicitarProcessaDadosCpuDetalhado(File caminhoTemp) {
		processaDadosDoNo.processaDiretorio(caminhoTemp);

	}

	public List<EntidadeThread> buscarTodosObjetosRepositorioThread() {
		ServicoThread thread = new ServicoThread();
		return thread.buscarThreadDoRepositorio();
	}

	public String direcionaCPUProcess(String caminhoDiretorio) {

		return caminhoDiretorio.replace("CPUProcess_",
				"CPUProcess_Detalhado_");

	}

	public List<EntidadeNo> retornaListaEntidadeNo() {
		ServicoNo no = new ServicoNo();
		return no.buscarTodos();
	}

	public List<String> direcionaStack(EntidadeThread selectedItem) {
		String caminhoDaStack = selectedItem.getCaminho().replace("CPUProcess_Detalhado_", "ResultDumps_")
				.replace(".txt", ".log");
		int decimalLwpid = selectedItem.getLwpid();
		return ProcessaStacksUtil.processaStack(caminhoDaStack, decimalLwpid);

	}

	public void deletaStack(){
		repositorioThread.delete();
	}

}
