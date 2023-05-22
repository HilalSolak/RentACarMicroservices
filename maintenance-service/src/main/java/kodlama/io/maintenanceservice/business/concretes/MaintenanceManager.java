package kodlama.io.maintenanceservice.business.concretes;

import com.kodlamaio.commonpackage.events.maintenance.MaintenanceCompletedEvent;
import com.kodlamaio.commonpackage.events.maintenance.MaintenanceCreatedEvent;
import com.kodlamaio.commonpackage.events.maintenance.MaintenanceDeletedEvent;
import com.kodlamaio.commonpackage.kafka.producer.KafkaProducer;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import kodlama.io.maintenanceservice.business.abstracts.MaintenanceService;
import kodlama.io.maintenanceservice.business.dto.requests.CreateMaintenanceRequest;
import kodlama.io.maintenanceservice.business.dto.requests.UpdateMaintenanceRequest;
import kodlama.io.maintenanceservice.business.dto.responses.CreateMaintenanceResponse;
import kodlama.io.maintenanceservice.business.dto.responses.GetAllMaintenancesResponse;
import kodlama.io.maintenanceservice.business.dto.responses.GetMaintenanceResponse;
import kodlama.io.maintenanceservice.business.dto.responses.UpdateMaintenanceResponse;
import kodlama.io.maintenanceservice.business.rules.MaintenanceBusinessRules;
import kodlama.io.maintenanceservice.entity.Maintenance;
import kodlama.io.maintenanceservice.repository.MaintenanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MaintenanceManager implements MaintenanceService {
    private final MaintenanceRepository repository;
    private final ModelMapperService mapper;
    private final MaintenanceBusinessRules rules;
    private final KafkaProducer producer;
    @Override
    public List<GetAllMaintenancesResponse> getAll() {
        var maintenances=repository.findAll();
        var response=maintenances
                .stream()
                .map(maintenance -> mapper.forResponse().map(maintenance,GetAllMaintenancesResponse.class))
                .toList();
        return response;
    }

    @Override
    public GetMaintenanceResponse getById(UUID id) {
        rules.checkIfMaintenanceExists(id); //bakımda bu bakım id'ye ait araç var mı yok mu kontrol ediyor.
        var maintenance=repository.findById(id).orElseThrow();
        var response=mapper.forResponse().map(maintenance, GetMaintenanceResponse.class);
        return response;
    }

    @Override
    public CreateMaintenanceResponse add(CreateMaintenanceRequest request) {
        rules.checkIfCarIsUnderMaintenance(request.getCarId());
        rules.ensureCarIsAvailable(request.getCarId());

        var maintenance=mapper.forRequest().map(request, Maintenance.class);
        maintenance.setId(null);
        maintenance.setCarId(request.getCarId());
        maintenance.setCompleted(false);
        maintenance.setStartDate(LocalDateTime.now());
        maintenance.setEndDate(null);
        repository.save(maintenance);

        sendKafkaMaintenanceCreatedEvent(request.getCarId());

        var response=mapper.forResponse().map(maintenance,CreateMaintenanceResponse.class);

        return response;
    }



    @Override
    public UpdateMaintenanceResponse update(UUID id, UpdateMaintenanceRequest request) {
        rules.checkIfMaintenanceExists(id); //bu id'ye ait bakım var mı yoksa updateleyemez.

        var maintenance=mapper.forRequest().map(request,Maintenance.class);
        maintenance.setId(id);
        repository.save(maintenance);

        var response=mapper.forResponse().map(maintenance,UpdateMaintenanceResponse.class);

        return response;
    }

    @Override
    public GetMaintenanceResponse returnCarFromMaintenance(UUID carId) {
        rules.checkIfCarIsNotUnderMaintenance(carId); //araç bakımda değilse bakımdan döndüremeyiz.

        Maintenance maintenance=repository.findMaintenanceByCarIdAndIsCompletedIsFalse(carId);
        maintenance.setCompleted(true);
        maintenance.setEndDate(LocalDateTime.now());
        repository.save(maintenance);
        sendKafkaMaintenanceCompletedEvent(carId);

        var response=mapper.forResponse().map(maintenance,GetMaintenanceResponse.class);
        return response;
    }



    @Override
    public void delete(UUID id) {
        rules.checkIfMaintenanceExists(id);
        makeCarAvailableIfIsCompletedFalse(id);
        repository.deleteById(id);

    }

    private void makeCarAvailableIfIsCompletedFalse(UUID id) {
        UUID carId=repository.findById(id).get().getCarId();
        if(repository.existsByCarIdAndIsCompletedIsFalse(carId)){
            sendKafkaMaintenanceDeletedEvent(carId);
        }
    }



    private void sendKafkaMaintenanceCreatedEvent(UUID carId) {
        producer.sendMessage(new MaintenanceCreatedEvent(carId),"maintenance-created");
    }

    private void sendKafkaMaintenanceDeletedEvent(UUID carId) {
        producer.sendMessage(new MaintenanceDeletedEvent(carId),"maintenance-deleted");
    }
    private void sendKafkaMaintenanceCompletedEvent(UUID carId) {
        producer.sendMessage(new MaintenanceCompletedEvent(carId),"maintenance-completed");
    }
}
