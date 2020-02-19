//import java.time.LocalDate;
//import java.time.Month;
//import java.util.List;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//
//class PknEmployee {
//    PknEmployee(String type, LocalDate fromLocalDate, LocalDate thruLocalDate) {
//        this.type = type;
//        this.fromLocalDate = fromLocalDate;
//        this.thruLocalDate = thruLocalDate;
//    }
//
//    private String type;
//    private LocalDate fromLocalDate;
//    private LocalDate thruLocalDate;
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public LocalDate getFromLocalDate() {
//        return fromLocalDate;
//    }
//
//    public void setFromLocalDate(LocalDate fromLocalDate) {
//        this.fromLocalDate = fromLocalDate;
//    }
//
//    public LocalDate getThruLocalDate() {
//        return thruLocalDate;
//    }
//
//    public void setThruLocalDate(LocalDate thruLocalDate) {
//        this.thruLocalDate = thruLocalDate;
//    }
//
//    public boolean isKonsultant() {
//        return this.type.equals("KONSULTANT");
//    }
//}
//
//class Command {
//    private LocalDate startLocalDate;
//    private LocalDate endLocalDate;
//
//    public Command(LocalDate startLocalDate, LocalDate endLocalDate) {
//        this.startLocalDate = startLocalDate;
//        this.endLocalDate = endLocalDate;
//    }
//
//    public LocalDate getStartLocalDate() {
//        return startLocalDate;
//    }
//
//    public void setStartLocalDate(LocalDate startLocalDate) {
//        this.startLocalDate = startLocalDate;
//    }
//
//    public LocalDate getEndLocalDate() {
//        return endLocalDate;
//    }
//
//    public void setEndLocalDate(LocalDate endLocalDate) {
//        this.endLocalDate = endLocalDate;
//    }
//}
//
//class Scratch {
//    private static final String KONSULTANT = "KONSULTANT";
//    private static final String SEKRETARZ = "SEKRETARZ";
//
//    public static void main(String[] args) {
//        Command command = new Command(LocalDate.of(2009, Month.JANUARY, 1), LocalDate.of(2013, Month.JANUARY, 1));
//        PknEmployee konsultant1 = new PknEmployee(KONSULTANT, LocalDate.of(2009, Month.OCTOBER, 1), LocalDate.of(2012, Month.AUGUST, 30));
//        PknEmployee sekretarz1 = new PknEmployee(SEKRETARZ, LocalDate.of(2009, Month.OCTOBER, 1), LocalDate.of(2012, Month.AUGUST, 30));
//        PknEmployee konsultant2 = new PknEmployee(KONSULTANT, LocalDate.of(2009, Month.OCTOBER, 1), LocalDate.of(2012, Month.AUGUST, 30));
//        PknEmployee sekretarz2 = new PknEmployee(SEKRETARZ, LocalDate.of(2009, Month.OCTOBER, 1), LocalDate.of(2011, Month.MAY, 19));
//        PknEmployee konsultant3 = new PknEmployee(KONSULTANT, LocalDate.of(2009, Month.OCTOBER, 1), LocalDate.of(2010, Month.APRIL, 22));
//        PknEmployee konsultant4 = new PknEmployee(KONSULTANT, LocalDate.of(2009, Month.OCTOBER, 30), LocalDate.of(2010, Month.JANUARY, 25));
//        PknEmployee sekretarz3 = new PknEmployee(SEKRETARZ, LocalDate.of(2010, Month.JANUARY, 25), LocalDate.of(2012, Month.AUGUST, 30));
//        List<PknEmployee> pracownicy = List.of(konsultant1, konsultant2, konsultant3, konsultant4, sekretarz1, sekretarz2, sekretarz3);
//
//        System.out.println();
//
//        pracownicy.stream().filter(pracownik -> pracownik.getFromLocalDate().isBefore(command.getStartLocalDate())).forEach(pracownik -> pracownik.setFromLocalDate(command.getStartLocalDate()));
//        pracownicy.stream().filter(pracownik -> pracownik.getThruLocalDate().isAfter(command.getStartLocalDate())).forEach(pracownik -> pracownik.setThruLocalDate(command.getEndLocalDate()));
//
//        pracownicy.forEach(praPknEmployee -> {
//            if ((praPknEmployee.getFromLocalDate().compareTo(command.getStartLocalDate()) < 0)
//                    || ((praPknEmployee.getThruLocalDate().compareTo(command.getEndLocalDate())) < 0)) {
//                throw new RuntimeException("Błąd.");
//            }
//        });
//
//
//
//        List<PknEmployee> konsultanci = pracownicy.stream().filter(PknEmployee::isKonsultant).collect(Collectors.toList());
//        List<PknEmployee> sekretarze = pracownicy.stream().filter(Predicate.not(PknEmployee::isKonsultant)).collect(Collectors.toList());
//
//        sekretarze.forEach(sek -> {
//            konsultanci.forEach(kon -> {
//                if (sek.getFromLocalDate().compareTo(kon.getFromLocalDate()) > 0) {
//
//                }
//            });
//        });
//
//        /** sekretarze ważniejsi **/
//
//    }
//
//    private static void changeToRightStartLocalDate(List<PknEmployee> pracownicy, LocalDate startLocalDate) {
//        pracownicy.forEach(pknEmployee -> {
//            if (pknEmployee.getFromLocalDate().compareTo(startLocalDate) > 0) {
//                pknEmployee.setFromLocalDate(startLocalDate);
//            }
//        });
//    }
//
//    private void changetToRightStopLocalDate(List<PknEmployee> pracownicy, LocalDate stopLocalDate) {
//        pracownicy.forEach(pknEmployee -> {
//            if (pknEmployee.getFromLocalDate().compareTo(stopLocalDate) > 0) {
//                pknEmployee.setFromLocalDate(stopLocalDate);
//            }
//        });
//    }
//}
//
//
//
