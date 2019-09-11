package br.com.joaovitor.jobs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.joaovitor.Mail;

public class JobRollout implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		Path caminho = Paths.get(System.getProperty("user.home"), "Desktop/QuartzApp/RPA/arquivos/ROLLOUT.TXT");
		Stream<String> lines = null;
		Map<String, String> rollout = new HashMap<String, String>();

		try {
			lines = Files.lines(caminho, StandardCharsets.ISO_8859_1);
			List<Object> collect = lines.collect(Collectors.toList());

			for (Object o : collect) {
				String string = o.toString();
				string = string.trim();
				int espaco = string.indexOf(",");
				rollout.put(string.substring(espaco + 1, string.length()), string.substring(0, espaco));
			}

			if (rollout.containsKey(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {

				String posto = rollout
						.get(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());

				Mail mail = new Mail.ConstroiEmail().emailRemetente("SEU_EMAIL")
						.senhaRemetente("SUA_SENHA").destinatario("EMAIL_DESTINATARIO").tituloEmail("TITULO_EMAIL")
						.conteudoEmail("CORPO_EMAIL").build();

				mail.enviaEmail();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
