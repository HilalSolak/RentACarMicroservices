package kodlama.io.maintenanceservice.business.abstracts;

import kodlama.io.maintenanceservice.business.dto.requests.CreateMaintenanceRequest;
import kodlama.io.maintenanceservice.business.dto.requests.UpdateMaintenanceRequest;
import kodlama.io.maintenanceservice.business.dto.responses.CreateMaintenanceResponse;
import kodlama.io.maintenanceservice.business.dto.responses.GetAllMaintenancesResponse;
import kodlama.io.maintenanceservice.business.dto.responses.GetMaintenanceResponse;
import kodlama.io.maintenanceservice.business.dto.responses.UpdateMaintenanceResponse;

import java.util.List;
import java.util.UUID;

public interface MaintenanceService {
    List<GetAllMaintenancesResponse> getAll();
    GetMaintenanceResponse getById(UUID id);
    GetMaintenanceResponse returnCarFromMaintenance(UUID carId);
    CreateMaintenanceResponse add(CreateMaintenanceRequest request);
    UpdateMaintenanceResponse update(UUID id, UpdateMaintenanceRequest request);
    void delete(UUID id);
}
