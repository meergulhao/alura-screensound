package br.com.alura.screensound.principal;

import br.com.alura.screensound.model.Artista;
import br.com.alura.screensound.model.ArtistaRepository;
import br.com.alura.screensound.model.Musica;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    Scanner leitura = new Scanner(System.in);

    List<Artista> artistas = new ArrayList<>();
    private Optional<Artista> artistaBusca;

    private ArtistaRepository repository;

    Optional<Artista> artista;

    public Principal(ArtistaRepository repository) {
        this.repository = repository;
    }

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                    1 - Cadastrar artistas
                    2 - Cadastrar músicas
                    3 - Listar artistas
                    4 - Listar músicas
                    5 - Buscar músicas por artistas
                    
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtista();
                    break;
                case 2:
                    cadastrarMusicaPorArtista();
                    break;
                case 3:
                    listarArtistas();
                    break;
                case 4:
                    listarMusicas();
                    break;
                case 5:
                    buscarMusicasPorArtista();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
            }
        }
    }

    public void cadastrarArtista() {
        var opcao = "S";
        while (opcao.equals("S")) {
            System.out.println("Informe o nome da artista:");
            var nome = leitura.nextLine();
            System.out.println("""
                    Informe o gênero musical. Disponíveis:
                    Rock
                    Pop
                    Rap
                    Samba
                    Sertanejo
                    """);
            var generoMusical = leitura.nextLine();
            System.out.println("""
                    Informe o tipo da artista. Disponíveis:
                    Solo
                    Dupla
                    Banda
                    Grupo
                    """);
            var tipo = leitura.nextLine();
            Artista artista = new Artista(nome, generoMusical, tipo);
            repository.save(artista);
            System.out.println(artista);

            System.out.println("Deseja cadastrar outra artista? (S/N)");
            opcao = leitura.nextLine().toUpperCase();
        }
    }

    public void cadastrarMusicaPorArtista() {
        var opcao = "S";
        while (opcao.equals("S")) {
            System.out.println("Artistas cadastradas:");
            listarArtistas();
            System.out.println("Digite o nome da artista:");
            var nomeArtista = leitura.nextLine();

            artista = repository.findByNomeContainingIgnoreCase(nomeArtista);

            List<Musica> musicas = new ArrayList<>();

            if (artista.isPresent()) {
                System.out.println("Digite o nome da música:");
                var nome = leitura.nextLine();

                System.out.println("Digite o nome do álbum:");
                var album = leitura.nextLine();

                System.out.println("Digite o ano em que a música foi lançada");
                var anoLancamento = leitura.nextInt();
                leitura.nextLine();

                Musica musica = new Musica(nome, album, artista.get(), anoLancamento);
                musicas.add(musica);
                artista.get().setMusicas(musicas);
                repository.save(artista.get());

                System.out.println("Deseja cadastrar outra música? (S/N)");
                opcao = leitura.nextLine().toUpperCase();

            } else {
                System.out.println("Artista não encontrada");
            }
        }
    }

    public void listarArtistas() {
        artistas = repository.findAll();
        artistas.forEach(System.out::println);
    }

    public void listarMusicas() {
        List<Musica> musicas = repository.todasAsMusicas();
        musicas.forEach(m ->
                System.out.println("Música: " + m.getNome() + ", álbum: " + m.getAlbum() +
                        ", artista: " + m.getArtista().getNome() + ", ano de lançamento: " + m.getAnoLancamento()));
    }

    public void buscarMusicasPorArtista() {
        System.out.println("Digite o nome da artista:");
        var nomeArtista = leitura.nextLine();
        artista = repository.findByNomeContainingIgnoreCase(nomeArtista);

        if(artista.isPresent()) {
            List<Musica> musicasPorArtista = repository.musicasPorArtista(artista.get());
            System.out.println("Músicas de " + artista.get().getNome());
            musicasPorArtista.forEach(m ->
                    System.out.println("Música: " + m.getNome() + ", álbum: " + m.getAlbum() +
                            ", ano de lançamento: " + m.getAnoLancamento()));
        } else {
            System.out.println("Artista não encontrada!");
        }
    }
}
