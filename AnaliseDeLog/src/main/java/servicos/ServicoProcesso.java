package servicos;

import entidades.EntidadeProcesso;
import fabricas.FabricaProcesso;
import repositorios.RepositorioProcesso;

public class ServicoProcesso {

	private RepositorioProcesso repositorioProcesso = new RepositorioProcesso();

	public ServicoProcesso() {

	}

	public EntidadeProcesso solicitarCriacaoProcesso(int pid, String username, String time, String cpu, int nlwp,
			String process, String hora) {
		return FabricaProcesso.nova().criarProcesso(pid, username, time, cpu, nlwp, process, hora);
	}

	public EntidadeProcesso solicitarselectByHora(String hora) {
		return repositorioProcesso.selectByHora(hora);
	}
}
