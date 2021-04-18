package railway;

//import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
//import org.apache.commons.lang3.StringUtils;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import railway.service.BackEndController;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Railway.class})
@AutoConfigureEmbeddedDatabase(beanName = "dataSource")
@SpringBootApplication
@ComponentScan(basePackages = "railway")
@FlywayTest//(locationsForMigrate = "db/migration")
public class Railway {
	private static final String CITY_FROM = "krasnoyrsk";
	private static final String CITY_TO = "habarovsk";
	private static final String DEPARTURE_DATE_PATTERN = "00:00:00 10.05.2018";
	@Autowired
	private BackEndController backEndController;


	@Test
	public void testConnection() throws ClassNotFoundException {
		City cityF = new City(CITY_FROM);
		City cityT = new City(CITY_TO);

		HashSet<TicketWeb> freeTickets = backEndController.saleTickets(cityF, cityT, DEPARTURE_DATE_PATTERN);


		Assert.assertEquals(freeTickets
				.stream()
				.findFirst()
				.map(TicketWeb::getTripeNumber)
				.orElse(StringUtils.EMPTY), "A-1");
	}

}
