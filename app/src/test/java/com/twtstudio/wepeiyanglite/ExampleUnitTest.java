package com.twtstudio.wepeiyanglite;

import android.util.Log;


import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.twtstudio.wepeiyanglite.model.BikeUserInfo;
import com.twtstudio.wepeiyanglite.support.TimeStampUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void url()
    {
        Observable.from(new String[]{"This", "is", "RxJava"})
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        printLog( "Transform Data toUpperCase: ", s);
                        return s.toUpperCase();
                    }
                })
                //转成List
                .toList()
                .map(new Func1<List<String>, List<String>>() {
                    @Override
                    public List<String> call(List<String> strings) {
                        printLog("Transform Data Reverse List: ", strings.toString());
                        Collections.reverse(strings);
                        return strings;
                    }
                })
                .observeOn(Schedulers.immediate())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> s) {
                        System.out.println(s.toString());
                        printLog( "Consume Data ", s.toString());
                    }
                });

    }
    public void printLog(String s1,String s2){
        System.out.println(s1+s2);
    }

    @Test
    public void getTime(){
        System.out.println(TimeStampUtils.getDateString("1469322135"));
    }

    @Test
    public void test1(){
        List<BikeUserInfo> list = new ArrayList<>();
        List<String> array=Stream.of(list)
                .map(str -> str.balance)
                .collect(Collectors.toList());
        System.out.println(array);
    }
}