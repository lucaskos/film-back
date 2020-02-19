import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class JunitObjectsTest {

    private static final String KONSULTANT = "KONSULTANT";
    private static final String SEKRETARZ = "SEKRETARZ";

    private static boolean isSekretarz(PknEmployee pknEmployee) {
        return pknEmployee.getType().equals(SEKRETARZ);
    }

    @Test
    public void assertAllEmplyeesFromDateAfterStartDate() {
        Command command = new Command(LocalDate.of(2009, Month.JANUARY, 1), LocalDate.of(2013, Month.JANUARY, 1));
        PknEmployee konsultant1 = new PknEmployee(KONSULTANT, LocalDate.of(2009, Month.OCTOBER, 1), LocalDate.of(2012, Month.AUGUST, 30));
        PknEmployee sekretarz1 = new PknEmployee(SEKRETARZ, LocalDate.of(2009, Month.OCTOBER, 1), LocalDate.of(2012, Month.AUGUST, 30));
        PknEmployee konsultant2 = new PknEmployee(KONSULTANT, LocalDate.of(2009, Month.OCTOBER, 1), LocalDate.of(2012, Month.AUGUST, 30));
        PknEmployee sekretarz2 = new PknEmployee(SEKRETARZ, LocalDate.of(2002, Month.OCTOBER, 1), LocalDate.of(2011, Month.MAY, 19));
        PknEmployee konsultant3 = new PknEmployee(KONSULTANT, LocalDate.of(2009, Month.OCTOBER, 1), LocalDate.of(2010, Month.APRIL, 22));
        PknEmployee konsultant4 = new PknEmployee(KONSULTANT, LocalDate.of(2009, Month.OCTOBER, 30), LocalDate.of(2010, Month.JANUARY, 25));
        PknEmployee sekretarz3 = new PknEmployee(SEKRETARZ, LocalDate.of(2010, Month.JANUARY, 25), LocalDate.of(2012, Month.AUGUST, 30));
        List<PknEmployee> pracownicy = List.of(konsultant1, konsultant2, konsultant3, konsultant4, sekretarz1, sekretarz2, sekretarz3);

        setFromDateAfterStartDate(command, pracownicy);

        pracownicy.forEach(pracownik -> Assert.assertTrue(pracownik.getFromLocalDate().isAfter(command.getStartLocalDate())
                || pracownik.getFromLocalDate().equals(command.getStartLocalDate())));
    }

    @Test
    public void assertAllEmplyeesThruDateBeforeEndDate() {
        Command command = new Command(LocalDate.of(2009, Month.JANUARY, 1), LocalDate.of(2013, Month.JANUARY, 1));
        PknEmployee konsultant1 = new PknEmployee(KONSULTANT, LocalDate.of(2009, Month.OCTOBER, 1), LocalDate.of(2012, Month.AUGUST, 30));
        PknEmployee sekretarz1 = new PknEmployee(SEKRETARZ, LocalDate.of(2009, Month.OCTOBER, 1), LocalDate.of(2012, Month.AUGUST, 30));
        PknEmployee konsultant2 = new PknEmployee(KONSULTANT, LocalDate.of(2009, Month.OCTOBER, 1), LocalDate.of(2012, Month.AUGUST, 30));
        PknEmployee sekretarz2 = new PknEmployee(SEKRETARZ, LocalDate.of(2002, Month.OCTOBER, 1), LocalDate.of(2011, Month.MAY, 19));
        PknEmployee konsultant3 = new PknEmployee(KONSULTANT, LocalDate.of(2009, Month.OCTOBER, 1), LocalDate.of(2010, Month.APRIL, 22));
        PknEmployee konsultant4 = new PknEmployee(KONSULTANT, LocalDate.of(2009, Month.OCTOBER, 30), LocalDate.of(2010, Month.JANUARY, 25));
        PknEmployee sekretarz3 = new PknEmployee(SEKRETARZ, LocalDate.of(2010, Month.JANUARY, 25), LocalDate.of(2012, Month.AUGUST, 30));
        List<PknEmployee> pracownicy = List.of(konsultant1, konsultant2, konsultant3, konsultant4, sekretarz1, sekretarz2, sekretarz3);

        setThruDateBeforeEndDate(command, pracownicy);
        pracownicy.forEach(pracownik -> Assert.assertTrue(pracownik.getThruLocalDate().isBefore(command.getEndLocalDate())
                || pracownik.getThruLocalDate().equals(command.getEndLocalDate())));
    }
    @Test
    public void test () {
        Command command = new Command(LocalDate.of(2009, Month.JANUARY, 1), LocalDate.of(2009, Month.JANUARY, 31));
        PknEmployee konsultant1 = new PknEmployee(KONSULTANT, LocalDate.of(2009, Month.JANUARY, 1), LocalDate.of(2009, Month.JANUARY, 8));
        PknEmployee sekretarz1 = new PknEmployee(SEKRETARZ, LocalDate.of(2009, Month.JANUARY, 4), LocalDate.of(2009, Month.JANUARY, 12));
        PknEmployee konsultant2 = new PknEmployee(KONSULTANT, LocalDate.of(2009, Month.JANUARY, 11), LocalDate.of(2009, Month.JANUARY, 27));
        List<PknEmployee> pracownicy = List.of(konsultant1, konsultant2, sekretarz1);
        setFromDateAfterStartDate(command, pracownicy);
        setThruDateBeforeEndDate(command, pracownicy);

        pracownicy.forEach(pracownik ->
                {
                    Assert.assertTrue(pracownik.getThruLocalDate().isBefore(command.getEndLocalDate())
                            || pracownik.getThruLocalDate().equals(command.getEndLocalDate()));
                    Assert.assertTrue(pracownik.getThruLocalDate().isBefore(command.getEndLocalDate())
                            || pracownik.getThruLocalDate().equals(command.getEndLocalDate()));
                }
                );

        pracownicy
                .stream()
//                .filter(JunitObjectsTest::isSekretarz)
        .forEach(System.out::println);
    }

    private void setThruDateBeforeEndDate(Command command, List<PknEmployee> pknEmployees) {
        pknEmployees.stream().filter(pracownik -> pracownik.getThruLocalDate().isAfter(command.getStartLocalDate()))
                .forEach(pracownik -> pracownik.setThruLocalDate(command.getEndLocalDate()));
    }

    private void setFromDateAfterStartDate(Command command, List<PknEmployee> pracownicy) {
        pracownicy.stream().filter(pracownik -> pracownik.getFromLocalDate().isBefore(command.getStartLocalDate())).forEach(pracownik -> pracownik.setFromLocalDate(command.getStartLocalDate()));
    }

}


@Data
@AllArgsConstructor
class PknEmployee {
    private String type;
    private LocalDate fromLocalDate;
    private LocalDate thruLocalDate;


    public boolean isKonsultant() {
        return this.type.equals("KONSULTANT");
    }
}

@Data
@AllArgsConstructor
class Command {
    private LocalDate startLocalDate;
    private LocalDate endLocalDate;
}

