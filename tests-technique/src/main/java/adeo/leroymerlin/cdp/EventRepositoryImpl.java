package adeo.leroymerlin.cdp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class EventRepositoryImpl {

    private final EventRepository eventRepository;

    @Autowired
    public EventRepositoryImpl(@Lazy EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    // Adding the save method
    public Event save(Event event) {
    	
    	return eventRepository.save(event);
    }
    
    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAllBy();
        
        // Filter the events list in pure JAVA here
        
		List<Event> filteredEvents = new ArrayList<Event>();
		int countMembers = 0;
		int countBands = 0;
		
		// Using a 3 level nested 'for each' loops
		for (Event event : events) {
			// Clearing the value of the previous loop
			countBands = 0;

			for (Band band : event.getBands()) {

				countMembers = 0;

				for (Member member : band.getMembers()) {

					if (member.getName().contains(query)) {

						countMembers++;

						if (filteredEvents.contains(event)) {
							// Do nothing since the event is already in the list
						} else {
							// Add the event to the filtered list
							filteredEvents.add(event);
						}
					}

				}
				
				// Altering the band name to add the counter
				if (countMembers != 0) {

					countBands++;

					String bandName = band.getName();

					if (bandName.contains("[")) {
						// Do nothing if the bandName is already altered
					} else {
						band.setName(bandName + " [" + countMembers + "]");
					}
				}

			}
			
			// Altering the event title to add the counter
			if (countBands != 0) {
				String eventTitle = event.getTitle();
				event.setTitle(eventTitle + " [" + countBands + "]");
			}

		}
		
		// Return the filtered list of events
		return filteredEvents;

    }
    
}
