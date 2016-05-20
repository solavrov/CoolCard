package com.blogspot.smallshipsinvest.coolcard.helpers;

import android.content.Context;
import android.content.Intent;

public class CodeHelper {

    public static final String[] CODE_PATTERN = {"cvcvcvdddd", "cvccvcdddd", "vcvcvcdddd"};

    public static void shareText(Context context, String text, boolean isChooser) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        if (isChooser) {
            Intent sendIntend2 = Intent.createChooser(sendIntent, text);
            sendIntend2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(sendIntend2);
        } else {
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(sendIntent);
        }

    }

    public static String generateCode(Alphabet alpha, String[] pattern) {

        String p = pattern[((int) (Math.random() * pattern.length))];

        String answer = "";

        for (int i = 0; i < p.length(); i++) {
            switch(p.charAt(i)) {
                case 'c':
                    answer += randomChar(alpha.sConsonants);
                    break;
                case 'C':
                    answer += randomChar(alpha.cConsonants);
                    break;
                case 'v':
                    answer += randomChar(alpha.sVowels);
                    break;
                case 'V':
                    answer += randomChar(alpha.cVowels);
                    break;
                case 'd':
                    answer += randomDigit();
                    break;
            }
        }

        return answer;

    }

    public static char randomChar(String pool) {
        return pool.charAt((int) (Math.random() * pool.length()));
    }

    public static int randomDigit() {
        return (int) (Math.random() * 10);
    }

    public static class Alphabet {

        private static final String CHAR_POOL =
                "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

        public String sConsonants;
        public String sVowels;
        public String cConsonants;
        public String cVowels;

        public Alphabet() {
            this.sConsonants = "qwrtpsdfghjklzxcvbnm";
            this.sVowels = "eyuioa";
            this.cConsonants = "QWRTPSDFGHJKLZXCVBNM";
            this.cVowels = "EYUIOA";
        }

        public Alphabet(boolean isNoO) {

            this.sConsonants = "qwrtpsdfghjklzxcvbnm";
            this.sVowels = "eyuioa";
            this.cConsonants = "QWRTPSDFGHJKLZXCVBNM";

            if (isNoO) {
                this.cVowels = "EYUIA";
            } else {
                this.cVowels = "EYUIOA";
            }

        }

    }

}
