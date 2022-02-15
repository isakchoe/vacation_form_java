package kakaoStyle.vacation.domain.web.dto;

import kakaoStyle.vacation.domain.entity.user.User;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


@Setter
@Getter
public class VacationDto {

    private User user;
    private float dayoff;
    private java.sql.Date startday;
    private java.sql.Date endday;



    // 출 처: https://jmseo.tistory.com/56  일부수정
    public float calculateHoliday(java.sql.Date startday, java.sql.Date endday ) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

        String startDate = fmt.format(startday);
        String endDate = fmt.format(endday);



        float workDt = 0;

        //실제 공휴일을 구한다.
        List<HashMap<String, Object>> holidayList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> holidayMap = new HashMap<String, Object>();

        holidayMap.put("holidayDt", "20220101");    //신정
        holidayList.add(holidayMap);

        holidayMap = new HashMap<String, Object>();
        holidayMap.put("holidayDt", "20220131");    //설날연휴
        holidayList.add(holidayMap);

        holidayMap = new HashMap<String, Object>();
        holidayMap.put("holidayDt", "20220201");    //설날
        holidayList.add(holidayMap);

        holidayMap = new HashMap<String, Object>();
        holidayMap.put("holidayDt", "20220202");    //설날연휴
        holidayList.add(holidayMap);


        holidayMap = new HashMap<String, Object>();
        holidayMap.put("holidayDt", "20200301");    //삼일절
        holidayList.add(holidayMap);

        holidayMap = new HashMap<String, Object>();
        holidayMap.put("holidayDt", "20220309");    //대선
        holidayList.add(holidayMap);


        holidayMap = new HashMap<String, Object>();
        holidayMap.put("holidayDt", "20220505");    //어린이날
        holidayList.add(holidayMap);

        holidayMap = new HashMap<String, Object>();
        holidayMap.put("holidayDt", "20220606");    // 현충일
        holidayList.add(holidayMap);

        holidayMap = new HashMap<String, Object>();
        holidayMap.put("holidayDt", "20220815");    // 광복절
        holidayList.add(holidayMap);


        holidayMap = new HashMap<String, Object>();
        holidayMap.put("holidayDt", "20220909");    // 추석
        holidayList.add(holidayMap);

        holidayMap = new HashMap<String, Object>();
        holidayMap.put("holidayDt", "20220910");    //추석
        holidayList.add(holidayMap);


        holidayMap = new HashMap<String, Object>();
        holidayMap.put("holidayDt", "20220911");    // 추석
        holidayList.add(holidayMap);

        holidayMap = new HashMap<String, Object>();
        holidayMap.put("holidayDt", "20220912");    // 추석
        holidayList.add(holidayMap);

        holidayMap = new HashMap<String, Object>();
        holidayMap.put("holidayDt", "20221003");    // 개천절
        holidayList.add(holidayMap);




        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            Calendar start = Calendar.getInstance();
            start.setTime(sdf.parse(startDate)); //시작일 날짜 설정

            Calendar end = Calendar.getInstance();
            end.setTime(sdf.parse(endDate)); //종료일 날짜 설정

            Calendar hol = Calendar.getInstance();

            float workingDays = 0;
            float holDays = 0;
            while (!start.after(end)) {
                int day = start.get(Calendar.DAY_OF_WEEK);     //주말인지 구한다. 1이면 일요일 7이면 토요일
                int holday = 0;

                if ((day != Calendar.SATURDAY) && (day != Calendar.SUNDAY)) {
                    workingDays++;    //평일 수
                }
                //시작일과 공휴일이 같을때 공휴일이 주말인지 체크한다.
                //공휴일이 주말이 아니면 +1
                if (!holidayList.isEmpty()) {
                    for (int i = 0; i < holidayList.size(); i++) {
                        hol.setTime(sdf.parse((String) holidayList.get(i).get("holidayDt").toString()));    //실제 공휴일 날짜 설정
                        holday = hol.get(Calendar.DAY_OF_WEEK); //주말인지 구한다. 1이면 일요일 7이면 토요일
                        if (start.equals(hol) && (holday != Calendar.SATURDAY) && (holday != Calendar.SUNDAY)) {    //공휴일수: 공휴일이 평일인경우 +1
                            holDays++;
                        }
                    }
                }
                start.add(Calendar.DATE, 1);
            }

            workDt = workingDays - holDays;

            return workDt;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }



}
