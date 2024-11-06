package br.com.alura.screensound.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    Optional<Artista> findByNomeContainingIgnoreCase(String nomeArtista);

    @Query("SELECT m FROM Artista a JOIN a.musicas m WHERE a = :artista ORDER BY m.anoLancamento")
    List<Musica> musicasPorArtista(Artista artista);

    @Query("SELECT m FROM Artista a JOIN a.musicas m ORDER BY m.artista")
    List<Musica> todasAsMusicas();
}
