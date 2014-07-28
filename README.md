Spring-JMS-AMQ
==============

Very quick Spring-JMS-AMQ Demo project

Assumes you are running a A-MQ  broker on tcp://localhost:61616

Unit Test puts 10 messages on a test queue and consumes them. 

To build with tests run: mvn clean install -DskipTests=false

Added new unit test that starts a AMQ embedded broker in memory, but havent cleaned up the other tests to reflect this.   
