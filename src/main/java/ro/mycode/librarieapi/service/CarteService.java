package ro.mycode.librarieapi.service;

import org.springframework.stereotype.Service;
import ro.mycode.librarieapi.exceptii.CarteAlreadyExist;
import ro.mycode.librarieapi.exceptii.EmptyDataBase;
import ro.mycode.librarieapi.model.Carte;
import ro.mycode.librarieapi.repository.CarteRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CarteService {

    private CarteRepo carteRepo;

    public CarteService(CarteRepo carteRepo) {
        this.carteRepo = carteRepo;
    }


    @Transactional
    public void add(Carte carte) throws CarteAlreadyExist {
        Optional<Carte> findCarte = carteRepo.findById(carte.getId());
        if (findCarte.isEmpty()) {
            carteRepo.save(carte);
        } else {
            throw new CarteAlreadyExist("Book already exist! ");
        }
    }

    public List<Carte> getAllBook() throws EmptyDataBase {
        if (carteRepo.findAll().size() != 0) {
            return carteRepo.findAll();
        } else {
            throw new EmptyDataBase("No books in database !");
        }
    }
}
