package adeo.leroymerlin.cdp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

//@Transactional(readOnly = true)
//public interface EventRepository extends Repository<Event, Long> {

@Transactional
public interface EventRepository extends CrudRepository<Event, Long> {
	
    //void delete(Long eventId);
    
    //void deleteById(long eventId);

    List<Event> findAllBy();
}
