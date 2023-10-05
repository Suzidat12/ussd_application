package com.zik.ussd_application.accountRepo;

import javax.swing.*;

public class Assignment {
    public static void main(String[] args) {
        //1. Initialize a variable to hold the score
        // 2. <,>,==,<=, >=, &&
        // Data type to use
//        String result = JOptionPane.showInputDialog("Enter your score");
//        double score= Double.valueOf(result);
//        if(score >= 75){
//            System.out.println("A");
//        }else if((score >= 60) && (score < 75) ){
//            System.out.println("B");
//        }else if ((score >=40) && (score <60)){
//            System.out.println("C");
//        }else{
//            System.out.println("F");
//        }

//       Integer day = 2;
//        switch (day){
//            case 1:
//                System.out.println("Monday");
//                break;
//            case 2:
//                System.out.println("Tuesday");
//                break;
//            case 3:
//                System.out.println("Wednesday");
//                break;
//            case 4:
//                System.out.println("Thursday");
//                break;
//            case 5:
//                System.out.println("Friday");
//                break;
//            default:
//                System.out.println("No weeks exist");
//        }
        //For loop,for-each loop, while -loop and do-while, break and continue (1,2,3,4,5)
        int y = 20;

//        for(int i=1;i<y;++i){
//            System.out.println(i);
//        }
//        while (i<y){
//            System.out.println(i);
//            i++;
//        }
//        do {
//            System.out.println(i);
//            i++;
//        }while (i<y);
        for(int i=1;i<y;i++){
            if(i==5){
                break;
            }
//            if(i>4 && i<10){
//                continue;
//            }
            System.out.println(i);
        }

    }
}
