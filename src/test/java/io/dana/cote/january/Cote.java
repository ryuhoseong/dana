package io.dana.cote.january;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cote {

    Logger logger = LoggerFactory.getLogger(Cote.class);

    /*
    https://school.programmers.co.kr/learn/courses/30/lessons/150370
     */
    @Test
    void 약관에_따른_개인정보_파기() {
        //[1, 3]
        String today = "2022.05.19";
        String[] terms = {"A 6", "B 12", "C 3", "D 5", "Z 5"};
        String[] privacies = {"2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C"};
//        String[] privacies = {"2019.12.01 D", "2019.11.15 Z", "2019.08.02 D", "2019.07.01 D", "2018.12.28 Z"};
        int[] rs = solution(today, terms, privacies);

        logger.info("rs " + Arrays.toString(rs));
    }

    public int[] solution(String today, String[] terms, String[] privacies) {

        int todayInt = Integer.parseInt(today.replace(".", ""));
        int[] answer = new int[privacies.length];

        Map<String, String> termsMap =
            Arrays.stream(terms)
                .map(e -> e.split(" "))
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));

        int i = 1;
        int j = 0;

        for (String privacy : privacies) {
            String[] privacyArr = privacy.split(" ");

            if (todayInt > afterDate(privacyArr[0], termsMap.get(privacyArr[1]))){
                answer[j] = i;
                j++;
            };
            i++;
        }

        return Arrays.stream(answer).filter(a -> a > 0).toArray();
    }

    public int afterDate(String refDay, String term) {

        String[] refDays = refDay.split("\\.");

        int afterYear = Integer.parseInt(refDays[0]);
        int afterMonth = Integer.parseInt(refDays[1]) + Integer.parseInt(term);
        int afterDay = Integer.parseInt(refDays[2]) - 1;

        if  (afterMonth > 12) {
            int addYear = afterMonth / 12;
            afterMonth %= 12;
            afterYear += addYear;
        }

        afterDay = afterDay == 0 ? 28 : afterDay;

        String mm = (String.valueOf(afterMonth).length() == 1) ? "0" + afterMonth : String.valueOf(afterMonth);
        String dd = (String.valueOf(afterDay).length() == 1) ? "0" + afterDay : String.valueOf(afterDay);

        logger.info("after date: " + afterYear + mm + dd);

        return Integer.parseInt(afterYear + mm + dd);

    }

}
