package jdbc_demo.mocktest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jdbc_demo.BelegungService;
import jdbc_demo.dm.BelegungServiceDMImpl;
import jdbc_demo.dm.Veranstaltung;

public class BelegungServiceMockTest {

	private BelegungService service;
	private int matrNr = 50105;

	private VeranstaltungDaoMock vm;
	private BelegungDaoMock bm;
	private StudentDaoMock sm;

	@BeforeClass
	static public void initialize() {
	}

	@Before
	public void setUp() {
		setUpDM();

		vm.insert(new Veranstaltung(67312, "Mathe WI", 1));
		vm.insert(new Veranstaltung(67313, "Prog 1", 1));
		vm.insert(new Veranstaltung(67314, "DMDB", 2));
	}

	private void setUpDM() {
		BelegungServiceDMImpl service = new BelegungServiceDMImpl();
		sm = new StudentDaoMock();
		vm = new VeranstaltungDaoMock();
		bm = new BelegungDaoMock();
		service.setbDao(bm);
		service.setsDao(sm);
		service.setvDao(vm);
		this.service = service;
	}

	@AfterClass
	static public void uninitialize() {
	}

	@Test
	public void testBelegeErstsemester() {
		// Arrange
		String name = "Kemper";
		String vorname = "Martin";
		// Act
		service.belegeErstsemester(matrNr, name, vorname);
		// Assert
		Assert.assertTrue(sm.count() == 1);
		Assert.assertTrue(bm.count() == 2);
	}

}
