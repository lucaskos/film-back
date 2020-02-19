import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class PknEmployee {
    PknEmployee(String type, Date fromDate, Date thruDate) {
        this.type = type;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
    }

    private String type;
    private Date fromDate;
    private Date thruDate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getThruDate() {
        return thruDate;
    }

    public void setThruDate(Date thruDate) {
        this.thruDate = thruDate;
    }

    public boolean isKonsultant() {
        return this.type.equals("KONSULTANT");
    }
}

class Command {
    private Date startDate;
    private Date endDate;

    public Command(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

class Scratch {
    private static final String KONSULTANT = "KONSULTANT";
    private static final String SEKRETARZ = "SEKRETARZ";

    public static void main(String[] args) {
        Command command = new Command(new Date(2009, Calendar.JANUARY, 1), new Date(2013, Calendar.JANUARY, 1));
        PknEmployee konsultant1 = new PknEmployee(KONSULTANT, new Date(2009, Calendar.OCTOBER, 1), new Date(2012, Calendar.AUGUST, 30));
        PknEmployee sekretarz1 = new PknEmployee(SEKRETARZ, new Date(2009, Calendar.OCTOBER, 1), new Date(2012, Calendar.AUGUST, 30));
        PknEmployee konsultant2 = new PknEmployee(KONSULTANT, new Date(2009, Calendar.OCTOBER, 1), new Date(2012, Calendar.AUGUST, 30));
        PknEmployee sekretarz2 = new PknEmployee(SEKRETARZ, new Date(2009, Calendar.OCTOBER, 1), new Date(2011, Calendar.MAY, 19));
        PknEmployee konsultant3 = new PknEmployee(KONSULTANT, new Date(2009, Calendar.OCTOBER, 1), new Date(2010, Calendar.APRIL, 22));
        PknEmployee konsultant4 = new PknEmployee(KONSULTANT, new Date(2009, Calendar.OCTOBER, 30), new Date(2010, Calendar.JANUARY, 25));
        PknEmployee sekretarz3 = new PknEmployee(SEKRETARZ, new Date(2010, Calendar.JANUARY, 25), new Date(2012, Calendar.AUGUST, 30));
        List<PknEmployee> pracownicy = List.of(konsultant1, konsultant2, konsultant3, konsultant4, sekretarz1, sekretarz2, sekretarz3);

        System.out.println();

        changeToRightStartDate(pracownicy, command.getStartDate());
        changeToRightStartDate(pracownicy, command.getEndDate());

        pracownicy.forEach(praPknEmployee -> {
            if ((praPknEmployee.getFromDate().compareTo(command.getStartDate()) < 0)
                    || (praPknEmployee.getThruDate().compareTo(command.getEndDate())) < 0) {
                throw new RuntimeException("Błąd.");
            }
        });

        List<PknEmployee> konsultanci = pracownicy.stream().filter(PknEmployee::isKonsultant).collect(Collectors.toList());
        List<PknEmployee> sekretarze = pracownicy.stream().filter(Predicate.not(PknEmployee::isKonsultant)).collect(Collectors.toList());

        sekretarze.forEach(sek -> {
            konsultanci.forEach(kon -> {
                if (sek.getFromDate().compareTo(kon.getFromDate()) > 0) {

                }
            });
        });

        /** sekretarze ważniejsi **/

    }

    private static void changeToRightStartDate(List<PknEmployee> pracownicy, Date startDate) {
        pracownicy.forEach(pknEmployee -> {
            if (pknEmployee.getFromDate().compareTo(startDate) > 0) {
                pknEmployee.setFromDate(startDate);
            }
        });
    }

    private void changetToRightStopDate(List<PknEmployee> pracownicy, Date stopDate) {
        pracownicy.forEach(pknEmployee -> {
            if (pknEmployee.getFromDate().compareTo(stopDate) > 0) {
                pknEmployee.setFromDate(stopDate);
            }
        });
    }
}



