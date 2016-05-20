package com.blogspot.smallshipsinvest.coolcard;

import java.util.HashMap;

public class Card {

    //    templates
    public static final String SPACE_TEMPLATE = "space";
    public static final String LOVE_TEMPLATE = "love";
    public static final String SAIL_TEMPLATE = "sail";
    public static final String FLOWERS_TEMPLATE = "flowers";
    public static final String FIREWORK_TEMPLATE = "firework";


    //    fontSize sizes
    public static final float SMALL_FONT_RELATIVE_SIZE = 0.045f;
    public static final float NORMAL_FONT_RELATIVE_SIZE = 0.06f;
    public static final float BIG_FONT_RELATIVE_SIZE = 0.09f;


    //    melodies
    public static final String BIRTHDAY_MUSIC = "birthday";
    public static final String BIRTHDAY_2_MUSIC = "birthday2";
    public static final String STAR_WARS_MUSIC = "star_wars";
    public static final String ROMANTIC_MUSIC = "romantic";
    public static final String SUNRISE_MUSIC = "sunrise";
    public static final String POSITIVE_MUSIC = "positive";
    public static final String TRIUMPH_MUSIC = "triumph";


    //    paths
    public static final String CARDS = "cards";
    public static final String TEMPLATE = "template";
    public static final String FONT_SIZE = "fontSize";
    public static final String MUSIC = "music";
    public static final String GREETING = "greeting";

//    instance attributes
    public String code;
    public String template;
    public int fontSize;
    public String music;
    public String greeting;
    public HashMap<String, Integer> musicMap;


    public Card() {
        code = "";
        greeting = "";
        fontSize = 1;
        musicMap = new HashMap<>();
        musicMap.put(SPACE_TEMPLATE, 5);
        musicMap.put(LOVE_TEMPLATE, 3);
        musicMap.put(SAIL_TEMPLATE,4);
        musicMap.put(FLOWERS_TEMPLATE,6);
        musicMap.put(FIREWORK_TEMPLATE,2);
    }

    public Card copy() {

        Card card = new Card();
        card.code = code;
        card.template = template;
        card.fontSize = fontSize;
        card.music = music;
        card.greeting = greeting;

        return card;

    }

    public float fontSize() {
        float answer = 0;
        switch (fontSize) {
            case 0:
                answer = SMALL_FONT_RELATIVE_SIZE;
                break;
            case 1:
                answer = NORMAL_FONT_RELATIVE_SIZE;
                break;
            case 2:
                answer = BIG_FONT_RELATIVE_SIZE;
                break;
        }
        return answer;
    }

    public int musicID() {
        int answer = 0;
        switch (music) {
            case BIRTHDAY_MUSIC:
                answer = R.raw.birthday;
                break;
            case BIRTHDAY_2_MUSIC:
                answer = R.raw.birthday2;
                break;
            case ROMANTIC_MUSIC:
                answer = R.raw.romantic;
                break;
            case STAR_WARS_MUSIC:
                answer = R.raw.star_wars;
                break;
            case SUNRISE_MUSIC:
                answer = R.raw.sunrise;
                break;
            case POSITIVE_MUSIC:
                answer = R.raw.positive;
                break;
            case TRIUMPH_MUSIC:
                answer = R.raw.triumph;
                break;
        }
        return answer;
    }

    public static String musicFromSpinner(int position) {
        String answer = "";
        switch (position) {
            case 0:
                answer = BIRTHDAY_MUSIC;
                break;
            case 1:
                answer = BIRTHDAY_2_MUSIC;
                break;
            case 2:
                answer = TRIUMPH_MUSIC;
                break;
            case 3:
                answer = ROMANTIC_MUSIC;
                break;
            case 4:
                answer = SUNRISE_MUSIC;
                break;
            case 5:
                answer = STAR_WARS_MUSIC;
                break;
            case 6:
                answer = POSITIVE_MUSIC;
                break;
        }
        return answer;
    }

}
