package com.starfish;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * Main
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-08-08
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int count = scanner.nextInt();
            log.info(String.valueOf(count));
        }
        scanner.close();
    }

}
