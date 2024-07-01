package tn.isetsf.presence;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class CalculDate {
    public int indexJour(){
        Date now=new Date();
        int index=0;
        char[] ls=now.toString().trim().toCharArray();
        if(ls[0]=='M'){
            index=1;
        }if(ls[0]=='T'&&ls[1]=='u'){
            index=2;
        }if(ls[0]=='W'){
            index=3;
        }if(ls[0]=='T'&&ls[1]=='h'){
            index=4;
        }
        if (ls[0]=='F') {
            index=5;
        }
        if (ls[0]=='S'&&ls[1]=='a') {
            index=6;
        }if(ls[0]=='S'&&ls[1]=='u'){
            index=7;
        }
        return index;
    }
    public int getYear(){
        Calendar calendar=Calendar.getInstance();
        int mois=calendar.get(Calendar.MONTH);
        if(mois>=8) {
            return Calendar.getInstance().getWeekYear();
        }else {
            return Calendar.getInstance().getWeekYear()-1;
        }

    }
    public int getSemestre(){
        Calendar calendar=Calendar.getInstance();
        if( calendar.get(Calendar.MONTH)+1>=2&&calendar.get(Calendar.MONTH)+1<=9){
            return 2;
        }else {
            return 1;
        }
    }
    public int getSeance() {
        LocalTime time = LocalTime.now();
        if (!time.isBefore(LocalTime.of(8, 15)) && time.isBefore(LocalTime.of(9, 45))) {
            return 1;
        } else if (!time.isBefore(LocalTime.of(10, 0)) && time.isBefore(LocalTime.of(11, 30))) {
            return 2;
        } else if (!time.isBefore(LocalTime.of(11, 45)) && time.isBefore(LocalTime.of(13, 15))) {
            return 3;
        } else if (!time.isBefore(LocalTime.of(13, 20)) && time.isBefore(LocalTime.of(14, 50))) {
            return 4;
        } else if (!time.isBefore(LocalTime.of(14, 55)) && time.isBefore(LocalTime.of(16, 25))) {
            return 5;
        } else if (!time.isBefore(LocalTime.of(16, 30)) && time.isBefore(LocalTime.of(18, 0))) {
            return 6;
        } else {
            return 0;
        }
    }

    public int getSeanceDouble() {
        LocalTime time = LocalTime.now();
        if (!time.isBefore(LocalTime.of(7, 0)) && time.isBefore(LocalTime.of(10, 0))) {
            return 7;
        } else if (!time.isBefore(LocalTime.of(10, 0)) && time.isBefore(LocalTime.of(13, 20))) {
            return 8;
        } else if (!time.isBefore(LocalTime.of(11, 45)) && time.isBefore(LocalTime.of(15, 0))) {
            return 9;
        } else if (!time.isBefore(LocalTime.of(13, 20)) && time.isBefore(LocalTime.of(16, 45))) {
            return 10;
        } else if (!time.isBefore(LocalTime.of(14, 55)) && time.isBefore(LocalTime.of(18, 0))) {
            return 11;
        } else {
            return 0;
        }
    }
        public String getDate(){
            LocalDateTime date = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            return date.format(formatter);
        }
        public int dateJourMapping(String dateStr){


            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            DayOfWeek dayOfWeek = date.getDayOfWeek();

            int index=0;
            char[] ls=dayOfWeek.toString().trim().toCharArray();
            if(ls[0]=='M'){
                index=1;
            }if(ls[0]=='T'&&ls[1]=='U'){
                index=2;
            }if(ls[0]=='W'){
                index=3;
            }if(ls[0]=='T'&&ls[1]=='H'){
                index=4;
            }
            if (ls[0]=='F') {
                index=5;
            }
            if (ls[0]=='S'&&ls[1]=='A') {
                index=6;
            }if(ls[0]=='S'&&ls[1]=='U'){
                index=7;
            }



            return index;
        }

}
