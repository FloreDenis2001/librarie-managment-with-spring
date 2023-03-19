package ro.mycode.librarieapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.mycode.librarieapi.model.Carte;

@Repository
public interface CarteRepo extends JpaRepository<Carte,Long> {


}
