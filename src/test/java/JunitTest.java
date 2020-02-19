import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.tomcat.jni.Local;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.Collectors;

public class JunitTest {

    static Sekretarz sekretarz1;
    static Sekretarz sekretarz2;
    static Sekretarz sekretarz3;
    static Konsultant konsultant1;
    static Konsultant konsultant2;
    static Konsultant konsultant3;

    static List<Konsultant> konsultantList = new ArrayList<>();
    static List<Sekretarz> sekretarzList = new ArrayList<>();
    static List<Rola> roleList = new ArrayList<>();
    boolean isChange = false;

    static int year = 1990;

    static LocalDate startDate = LocalDate.of(1990, 01, 01);
    private LocalDate finishDate = LocalDate.of(1999, 02, 01);

    /**
     * k1 k2 k3
     * s1 s2
     */
    @BeforeClass
    public static void init() {

        konsultant1 = new Konsultant(
                LocalDate.of(1990, Month.JANUARY, 01),
                LocalDate.of(year, Month.JANUARY, 12)
        );
        sekretarz1 = new Sekretarz(
                LocalDate.of(year, Month.JANUARY, 6),
                LocalDate.of(year, Month.JANUARY, 14)
        );
        konsultant2 = new Konsultant(
                LocalDate.of(year, Month.JANUARY, 13),
                LocalDate.of(year, Month.JANUARY, 24)
        );

        sekretarz2 = new Sekretarz(
                LocalDate.of(year, Month.JANUARY, 16),
                LocalDate.of(year, Month.JANUARY, 17)
        );

        sekretarz2 = new Sekretarz(
                LocalDate.of(year, Month.JANUARY, 27),
                LocalDate.of(year, Month.JANUARY, 29)
        );

        konsultant3 = new Konsultant(
                LocalDate.of(year, Month.JANUARY, 25),
                LocalDate.of(year, Month.JANUARY, 31)
        );

        konsultantList.add(konsultant1);
        konsultantList.add(konsultant2);
        konsultantList.add(konsultant3);
        sekretarzList.add(sekretarz1);
        sekretarzList.add(sekretarz2);


        LocalDate startDate = LocalDate.of(1990, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(1990, Month.JANUARY, 31);



    }

    @Test
    public void test() {


        List<Konsultant> lista = new ArrayList<>();
        lista = sprawdzKonsultatnow(konsultantList, sekretarzList);

        roleList.addAll(lista);
        roleList.addAll(sekretarzList);

        List<Rola> collect = roleList.stream().sorted(Comparator.comparing(Rola::getDateFrom)).collect(Collectors.toList());

        System.out.println(collect);
    }

    private List<Konsultant> sprawdzKonsultatnow(List<Konsultant> konsultantList, List<Sekretarz> sekretarzList) {
        System.out.println("LISTA ROZMIAR : " + konsultantList.size());

        List<Konsultant> lista = new ArrayList<>();
        for (Konsultant konsultant : konsultantList) {
            for (Sekretarz sekretarz : sekretarzList) {
                Period betweenStart = Period.between(sekretarz.getDateFrom(), konsultant.getDateFrom());
                Period betweenFinish = Period.between(sekretarz.getDateTo(), konsultant.getDateTo());

                //data sekretarza jest przed konsultantem
                if (betweenStart.getDays() < 0 && (sekretarz.getDateFrom().isBefore(konsultant.getDateTo()))) {
                    isChange = true;
                    //TODO do funkcji + opis
                    if (konsultant.getDateTo().isAfter(sekretarz.getDateTo())) {
                        /** tworze nowego konsultanta bo zostaje na wykresie pusty przebieg */
                        lista.add(new Konsultant(sekretarz.getDateFrom().plusDays(1), konsultant.getDateTo()));
                    }
                    konsultant.setDateTo(sekretarz.getDateFrom().minusDays(1));
                    break;
                }

                /**data konca u konsultanta jest równa sekretarza, trzeba zmienic date na mniej ważnym konsultancie */
                if (konsultant.getDateTo().equals(sekretarz.getDateTo())) {
                    LocalDate dateTo = konsultant.getDateTo();
                    konsultant.setDateTo(dateTo.minusDays(1L));
                    isChange = true;
                    break;
                }

                /** data rozpoczecia konsultanta jest przed data zakonczenia sekretarza*/
                if (konsultant.getDateFrom().isBefore(sekretarz.getDateTo()) && konsultant.getDateTo().isAfter(sekretarz.getDateFrom())) {
                    konsultant.setDateFrom(sekretarz.getDateTo().plusDays(1L));
                    isChange = true;
                    break;
                }

                /** sekretarz zaczyna się po zakończeniu konsultanta, można kontynuować
                 *  konsultant zaczyna się po rozpoczęciu
                 * */
                if (sekretarz.getDateTo().isAfter(konsultant.getDateFrom()) && sekretarz.getDateFrom().isBefore(konsultant.getDateFrom())) {
                    isChange = false;
                    continue;
                }

                if (konsultant.getDateTo().equals(finishDate) || konsultant.getDateTo().equals(sekretarz.getDateTo())) {
                    isChange = false;
                    continue;
                }
            }
        }

        lista.addAll(konsultantList);

        if (isChange) {
            isChange = false;
            konsultantList = sprawdzKonsultatnow(lista, sekretarzList);
        }

        return konsultantList;
    }

    public static class Sekretarz extends Rola {
        public Sekretarz(LocalDate dateFrom, LocalDate dateTo) {
            super(null, dateFrom, dateTo);
        }
    }

    public static class Konsultant extends Rola {
        public Konsultant(LocalDate dateFrom, LocalDate dateTo) {
            super(null, dateFrom, dateTo);
        }
    }

    @Data
    @AllArgsConstructor
    public static class Rola {
        private String type;
        private LocalDate dateFrom;
        private LocalDate dateTo;

    }
}
