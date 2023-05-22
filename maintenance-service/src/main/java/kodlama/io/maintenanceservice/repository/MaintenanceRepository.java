package kodlama.io.maintenanceservice.repository;

import kodlama.io.maintenanceservice.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, UUID> {
    boolean existsByCarIdAndIsCompletedIsFalse(UUID carId); //Araç bakımda mı onu kontrol ediyor.Bu yüzden iscompleted false dedik
    Maintenance findMaintenanceByCarIdAndIsCompletedIsFalse(UUID carId);
    //repositoryde yazdık çünkü databaseden verileri getirecek.
}
