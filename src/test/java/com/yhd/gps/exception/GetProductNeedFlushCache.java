package com.yhd.gps.exception;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GetProductNeedFlushCache {
    /**
     * @param args
     */
    public static void main(String[] args) {
        String str = "5733174,5770784,5824626,5824622,6529013,6861054,6909603,7172968,";
        str += "8441534,8814098,10613471,11044392,11498480,11569344,11687965,12196586,";
        str += "12212652,12654387,13708956,14215905,16194907,17013362,20159063,20906170,";
        str += "23281295,23273043,23818519,25456988,27105389,28087564,28286858,28288661,";
        str += "28952528,28981866,29003997,29431794,29434075,29855707,30632351,31607336,";
        str += "31608257,32175240,32241913,32240116,32241175,33307636,34123109,34764233,";
        str += "35419958,35579051,36772290,37366326,37601532,38221520,39362808,39492678,";
        str += "40493057,40552131,40866721,41756561,41837173,41843676,42024201,42228040,";
        str += "42251643,42241858,42226742,42300274,42355583,42427275,42672198,42963106,";
        str += "43348643,43350364,43751181,43772214,43902941,43903556,43905360,43959009,";
        str += "44091484,44190032,44193930,44335886,44389035,44387800,44444681,44728158,";
        str += "44726267,44729262,45118002,45138826,45136764,45387742,45410001,45407868,";
        str += "45587691,45724345,45732643,45734599,45733327,45926993,45925597,46072768,";
        str += "46330511,46335332,46326497,46395163,46446773,46448241,46457621,46722976,";
        str += "46719599,46716940,46713208,46815567,46819676,46810534,47253427,47252744,";
        str += "47404638,47406285,47407617,47408934,47410252,47834092,47949759,47948762,";
        str += "47970620,47983525,47976987,48018629,48080937,48213829,48210744,48211079,";
        str += "48291716,48477456,48474576,48482615,48472889,48499739,49546537,49553406,";
        str += "49585265,49586359,49737139,50062841,50133157,50145886,50145888,50149381,";
        str += "50145887,50197122,50335333,50389961,50570549,51037243,51203205,51202121,";
        str += "51204013,51419522,51431958,51431523,51455878,51456161,51633223,51717940,";
        str += "51829316,51871772,51887187,51955913,51956352,52131283,52282030,52282193,";
        str += "52394080,52401853,52402636,52394442,52402046,52401290";
        String[] strArr = str.split(",");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("d:\\flush_product.txt")));
            for(int j = 0; j < strArr.length; j++) {
                for(int i = 1; i < 32; i++) {
                    String s = "BUSY_DEFAULT_MERCHANT_LIST_siteId_1_provId_" + i + "_cityId_null_departmentId_null_pId_"
                               + strArr[j];
                    writer.write(s);
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException e) {
        }
        
    }

}
