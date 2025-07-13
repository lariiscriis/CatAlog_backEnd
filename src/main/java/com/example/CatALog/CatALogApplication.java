package com.example.CatALog;

import com.example.CatALog.domain.user.Estante;
import com.example.CatALog.domain.user.Estante.TipoRelacao; // Importar o enum TipoRelacao
import com.example.CatALog.domain.user.Livro;
import com.example.CatALog.domain.user.User;
import com.example.CatALog.repositories.BookRepository;
import com.example.CatALog.repositories.EstanteRepository;
import com.example.CatALog.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder; // Importar PasswordEncoder

import java.time.LocalDateTime; // Importar LocalDateTime
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableScheduling
public class CatALogApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final BookRepository bookRepository;
	private final EstanteRepository estanteRepository;
	private final PasswordEncoder passwordEncoder;

	public CatALogApplication(
			UserRepository userRepository,
			BookRepository bookRepository,
			EstanteRepository estanteRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
		this.estanteRepository = estanteRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(CatALogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		System.out.println("--- Iniciando a execução do CommandLineRunner ---");
//
//		System.out.println("\n--- Operações com Usuários ---");
//
//		User newUser = new User();
//		newUser.setName("João Silva");
//		newUser.setEmail("joao.silva@example.com");
//		newUser.setPassword(passwordEncoder.encode("senha123"));
//		newUser.setBio("Amante de livros de ficção científica.");
//
//		System.out.println("Salvando novo usuário...");
//		userRepository.save(newUser);
//		System.out.println("Usuário salvo: " + newUser.getName() + " (ID: " + newUser.getId() + ")");
//
//		Optional<User> foundUser = userRepository.findByEmail("joao.silva@example.com");
//		foundUser.ifPresent(user -> System.out.println("Usuário encontrado pelo email: " + user.getName()));
//
//		long userCount = userRepository.count();
//		System.out.println("Total de usuários no banco: " + userCount);
//
//		// LIVROS
//		System.out.println("\n--- Operações com Livros ---");
//
//		// Criando alguns livros
//		Livro livro1 = new Livro();
//		// ID será gerado automaticamente com GenerationType.UUID
//		livro1.setIsbn("978-0132350884"); // Exemplo de ISBN
//		livro1.setTitulo("Clean Code: A Handbook of Agile Software Craftsmanship");
//		livro1.setEditora("Prentice Hall");
//		livro1.setData_publicacao("2008-08-01"); // Data como String, conforme seu campo
//		livro1.setAutores("Robert C. Martin");
//		livro1.setDescricao("Even bad code can function... but if code isn't clean, it can bring a development organization to its knees.");
//		livro1.setCapa("https://example.com/clean-code-capa.jpg");
//		livro1.setQtdeLivro(5); // ATENÇÃO: Adicionado este campo!
//		livro1.setDisponibilidade(true); // Se qtdeLivro > 0, geralmente é true
//		livro1.setNumeroPaginas(464);
//		livro1.setCategoria("Programação");
//
//		Livro livro2 = new Livro();
//		// ID será gerado automaticamente com GenerationType.UUID
//		livro2.setIsbn("978-0618260274");
//		livro2.setTitulo("The Lord of the Rings");
//		livro2.setAutores("J.R.R. Tolkien");
//		livro2.setEditora("Houghton Mifflin Harcourt");
//		livro2.setData_publicacao("2002-10-01");
//		livro2.setDescricao("A classic high-fantasy adventure novel.");
//		livro2.setCapa("https://example.com/lotr-capa.jpg");
//		livro2.setQtdeLivro(3); // ATENÇÃO: Adicionado este campo!
//		livro2.setDisponibilidade(true);
//		livro2.setNumeroPaginas(1178);
//		livro2.setCategoria("Fantasia");
//
//		Livro livro3 = new Livro();
//		// ID será gerado automaticamente com GenerationType.UUID
//		livro3.setIsbn("978-0201633610");
//		livro3.setTitulo("Design Patterns: Elements of Reusable Object-Oriented Software");
//		livro3.setAutores("Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides");
//		livro3.setEditora("Addison-Wesley Professional");
//		livro3.setData_publicacao("1994-10-21");
//		livro3.setDescricao("The classic work on software design patterns.");
//		livro3.setCapa("https://example.com/design-patterns-capa.jpg");
//		livro3.setQtdeLivro(0); // ATENÇÃO: Adicionado este campo!
//		livro3.setDisponibilidade(false); // Se qtdeLivro é 0, disponibilidade é false
//		livro3.setNumeroPaginas(395);
//		livro3.setCategoria("Engenharia de Software");
//
//		System.out.println("Salvando livros...");
//		bookRepository.save(livro1);
//		bookRepository.save(livro2);
//		bookRepository.save(livro3);
//		System.out.println("Livros salvos.");
//
//		// Buscando livro pelo título (exato)
//		Optional<Livro> livroEncontrado = bookRepository.findByTitulo("Clean Code: A Handbook of Agile Software Craftsmanship");
//		livroEncontrado.ifPresent(l -> System.out.println("Livro encontrado por título: " + l.getTitulo()));
//
//		// Buscando livros por título contendo "code" (ignorando case)
//		List<Livro> livrosComCode = bookRepository.findByTituloContainingIgnoreCase("code");
//		System.out.println("Livros com 'code' no título: " + livrosComCode.size());
//		livrosComCode.forEach(l -> System.out.println("- " + l.getTitulo()));
//
//		// Contando livros disponíveis
//		int livrosDisponiveis = bookRepository.countLivrosDisponiveis();
//		System.out.println("Total de livros disponíveis: " + livrosDisponiveis);
//
//		// Listando todos os livros por autor
//		List<Livro> todosLivrosOrdenados = bookRepository.listarTodosPorAutor();
//		System.out.println("Livros ordenados por autor:");
//		todosLivrosOrdenados.forEach(l -> System.out.println("- " + l.getAutores() + " - " + l.getTitulo()));
//
//		// --- 3. Exemplo de uso de EstanteRepository ---
//		System.out.println("\n--- Operações com Estantes ---");
//
//		// Criando algumas entradas na estante para o novo usuário
//		Estante estante1 = new Estante();
//		estante1.setId(newUser.getId());
//		estante1.setIdLivro(livro1); // Use getId_livro()
//		estante1.setTipoRelacao(TipoRelacao.favorito); // Usando um valor válido do enum
//		estante1.setDataInteracao(LocalDateTime.now()); // Adicione a data de interação
//
//		Estante estante2 = new Estante();
//		estante2.setId(newUser.getId());
//		estante2.setIdLivro(livro2); // Use getId_livro()
//		estante2.setTipoRelacao(TipoRelacao.desejado); // Usando um valor válido do enum
//		estante2.setDataInteracao(LocalDateTime.now()); // Adicione a data de interação
//
//		System.out.println("Salvando entradas na estante...");
//		estanteRepository.save(estante1);
//		estanteRepository.save(estante2);
//		System.out.println("Entradas na estante salvas.");
//
//		// Buscando entradas na estante por ID do usuário e tipo de relação
//		List<Estante> favoritosPeloUsuario = estanteRepository.findByIdAndTipoRelacao(newUser.getId(), TipoRelacao.favorito);
//		System.out.println("Livros favoritos por " + newUser.getName() + ": " + favoritosPeloUsuario.size());
//		favoritosPeloUsuario.forEach(e -> System.out.println("- ID Livro: " + e.getLivro()));
//
//		// Buscando uma entrada específica na estante
//		Optional<Estante> relacaoEspecifica = estanteRepository.findByIdAndLivro_IdLivroAndTipoRelacao
//				(
//				newUser.getId(), livro1.getIdLivro(), TipoRelacao.favorito);
//		relacaoEspecifica.ifPresent(e -> System.out.println("Relação específica encontrada: " + e.getTipoRelacao()));
//
//		// Buscando todas as relações para um livro específico e tipo (e.g., quem quer ler "The Lord of the Rings")
//		List<Estante> quemQuerLerLotr = estanteRepository.findByIdAndTipoRelacao(livro2.getIdLivro(), TipoRelacao.desejado);
//		System.out.println("Pessoas que querem ler 'The Lord of the Rings': " + quemQuerLerLotr.size());
//		quemQuerLerLotr.forEach(e -> System.out.println("- ID Usuário: " + e.getId()));
//
//
//		System.out.println("\n--- divamos <3 ---");
	}
}