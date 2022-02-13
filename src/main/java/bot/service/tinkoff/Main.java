package bot.service.tinkoff;

import ru.tinkoff.piapi.core.InvestApi;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class Main {
    public static void main(String[] args) {
        InvestApi investApi = InvestApi.create("t.gpx53MTuLy4Z_AArHEQZn-xiGUS2TnmodTzRjyn6pkFCmABA0YLE8STKWeGYW1fgj54vsfPYAZHAE6ITPn3WuQ");
        investApi.getInstrumentsService().getInstrumentByFigiSync("BBG004730RP0").ifPresent(System.out::println);

        System.out.println(investApi.getInstrumentsService().getDividendsSync("BBG004730RP0",
                getFromDate("2020-05-26"),
                getFromDate("2022-05-26")));
    }
    private static Instant getFromDate(String localDate){
        return LocalDate.parse(localDate).atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}
