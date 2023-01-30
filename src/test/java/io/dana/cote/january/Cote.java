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
        String[] terms = {"A 30", "B 12", "C 3", "D 5", "Z 5"};
        String[] privacies = {"2021.06.02 A", "2021.01.01 B", "2022.09.19 C", "2022.09.20 C"};
//        String[] privacies = {"2019.12.01 D", "2019.11.15 Z", "2019.08.02 D", "2019.07.01 D", "2018.12.28 Z"};
        int[] rs = solution(today, terms, privacies);

        logger.info("rs " + Arrays.toString(rs));
    }

    int len;

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

            if (todayInt > expireDate(privacyArr[0], termsMap.get(privacyArr[1]))){
                answer[j] = i;
                j++;
            };
            i++;
        }

        return Arrays.stream(answer).filter(a -> a > 0).toArray();
    }

    public int expireDate(String refDay, String term) {

        String[] refDays = refDay.split("\\.");

        int expireYear = Integer.parseInt(refDays[0]);
        int expireMonth = Integer.parseInt(refDays[1]) + Integer.parseInt(term);
        int expireDay = Integer.parseInt(refDays[2]) - 1;

        int addYear = expireMonth / 12;

        expireMonth = expireMonth % 12 == 0 ? 12 : expireMonth % 12;
        expireYear += (expireMonth == 12 || expireMonth == 1 && expireDay == 0) ? addYear - 1 : addYear;

        if (expireDay == 0) {
            expireMonth = expireMonth == 1 ? 12 : expireMonth - 1;
            expireDay = 28;
        }

        String mm = String.format("%02d", expireMonth);
        String dd = String.format("%02d", expireDay);

        logger.info("after date: " + expireYear + mm + dd);

        return Integer.parseInt(expireYear + mm + dd);

    }

}
