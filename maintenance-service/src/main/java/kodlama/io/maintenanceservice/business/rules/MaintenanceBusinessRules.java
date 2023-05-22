package kodlama.io.maintenanceservice.business.rules;

import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import kodlama.io.maintenanceservice.api.clients.CarClient;
import kodlama.io.maintenanceservice.repository.MaintenanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@AllArgsConstructor
public class MaintenanceBusinessRules {
    private final MaintenanceRepository repository;
    private final CarClient carClient;
    public void checkIfMaintenanceExists(UUID id) {
        if(!repository.existsById(id)){
            throw new BusinessException("MAINTENANCE NOT EXISTS");
        }
    }
    public void ensureCarIsAvailable(UUID carId) {
        var response = carClient.checkIfCarAvailable(carId);
        if (!response.isSuccess()) {
            throw new BusinessException(response.getMessage());
        }
    }

    public void checkIfCarIsUnderMaintenance(UUID carId) {
        if(repository.existsByCarIdAndIsCompletedIsFalse(carId))
            throw new BusinessException("Car_IS_ALREADY_UNDER_MAINTENANCE");
        //araba bakımdaysa tekrar bakıma gönderemeyiz.
    }

    public void checkIfCarIsNotUnderMaintenance(UUID carId) {
        //Aracı bakımdan döndüreceğimiz zaman kontrol ediyoruz bakımda değilse exception fırlatcak bakımdaysa sorun yok.
        if(repository.existsById(carId)){
         throw new BusinessException("CAR_NOT_EXISTS_UNDER_MAINTENANCE");
    }
    }
}
