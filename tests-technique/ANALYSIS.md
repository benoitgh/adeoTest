## Step 1 : Launching the app

* Import maven project in eclipse

* Try running the app

	errors : Restarter has not been initialized

			=> Try to remove spring dev tools

	new errors example:

			Cannot load configuration class: adeo.leroymerlin.cdp.AdeoLeroyMerlinCDPRecruitmentApplication


			=> rolled back and added a version to devtools
			

	errors : Restarter has not been initialized

			=> changed spring-boot-starter-parent version to 2.5.6 and removed the version to devtools


    errors : On the eventController

    		Error creating bean with name 'eventController' [...]
    		No property delete found for type Event!


    		=>  renamed eventService to eventRepositoryImpl
   

    errors: Circular dependencies

    		=> used @lazy on both eventController and eventRepositoryImpl


    => The app is launching



## Solution to the delete button :

	The expected behavior is not the actual one. The button delete does nothing at all.

	To get the expected behavior, I had to change the delete(Long EventId) by deleteById(Long EventId)

	For the delete to be permanent, I replaced @Transactional(readOnly = true) by @Transactional



## Solution to the review issue : 

	I start by identifying that nothing is implemented beyond the updateEvent in the EventController


	=> I added code in the classes EventController and EventRepositoryImpl

	=> I changed Repository<Event, Long> to CrudRepository<Event, Long> to use the save method


	I got the following errors:


		object references an unsaved transient instance - save the transient instance before flushing: adeo.leroymerlin.cdp.Band

		=> To solve it I added cascade=CascadeType.ALL to the bands instance in the Event Entity


		object references an unsaved transient instance - save the transient instance before flushing: adeo.leroymerlin.cdp.Member

		=> To solve it I added cascade=CascadeType.ALL to the members instance in the Band Entity


		nested exception is org.hibernate.exception.SQLGrammarException: could not prepare statement] with root cause
		org.hsqldb.HsqlException: user lacks privilege or object not found: HIBERNATE_SEQUENCE

		=> I checked if the problem did not come from the words Member and Event being protected in HSQL

		=> I solved it by Changing @GeneratedValue(strategy= GenerationType.AUTO) to
	 @GeneratedValue(strategy= GenerationType.IDENTITY) in the 3 entities



## Solution to the new feature : 

	In EventRepositoryImpl I added 3 nested 'for each' loops to create a new list with filtered items



## Solution to the Bonus feature :

  	I added counters in the loops to display the number of child items

  	I solved the use case of 'double alteration' of a band name when present in different events by adding a check for the character '['

  	No addition of this check is needed for the events since the loops begin at the event level

  	The exemple provided with 'Wa' seems incorrect since only the Metallica band has a member whose name contains 'Wa' but the title event is "GrasPop Metal Meeting [2]"



## Testing

  A bug was found on the events sharing the Band 'Pink Floyd'
  When launching the app, the events could not be deleted until at least one was updated with a rating or a comment.
  The bug was fixed by changing the CascadeType value from ALL to MERGE in the Event Entity.


