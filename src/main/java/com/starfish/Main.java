package com.starfish;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Test
 *
 * @author sunny
 * @version 1.0.0
 * @since 2020-10-28
 */
public class Main {

    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int days = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < days; i++) {
            int num  = scanner.nextInt();
            scanner.nextLine();
            List<Model> l = new ArrayList<>();
            for (int j = 0; j < num; j++) {
                String records = scanner.nextLine();
                String[] s = records.split(" ");
                Model m = new Model();
                m.setName(s[0]);
                m.setzTime(s[1]);
                m.setwTime(s[2]);
                l.add(m);
            }
            print(l);
        }
    }

    public static void print(List<Model> list) throws ParseException {
        Model z = null;
        Model w = null;
        for (Model m : list) {
            if (z == null) {
                z = m;
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

                Date z1 = simpleDateFormat.parse(z.getzTime());
                Date z2 = simpleDateFormat.parse(m.getzTime());
                if (z1.after(z2)) {
                    z = m;
                }

            }

            if (w == null) {
                w = m;
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

                Date w1 = simpleDateFormat.parse(w.getzTime());
                Date w2 = simpleDateFormat.parse(m.getzTime());
                if (w1.before(w2)) {
                    w = m;
                }
            }
        }
        System.out.println(z.getName() + " " + w.getName());
    }

    static class Model {

        private String name;
        private String zTime;
        private String wTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getzTime() {
            return zTime;
        }

        public void setzTime(String zTime) {
            this.zTime = zTime;
        }

        public String getwTime() {
            return wTime;
        }

        public void setwTime(String wTime) {
            this.wTime = wTime;
        }
    }

}
