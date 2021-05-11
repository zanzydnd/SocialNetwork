package ru.itis.kpfu.kozlov.social_network_api;

public class Main {
    public static void findPeople() throws InterruptedException {
        System.out.println("Поиск самого классного человека в мире");
        int procent = 0;
        while (procent < 100) {
            Thread.sleep(5000);
            System.out.println("Я пытаюсь найти");
            System.out.println("*");
            Thread.sleep(1000);
            System.out.println("**");
            Thread.sleep(1000);
            System.out.println("***");
            System.out.println("Проведен анализ " + procent + "% людей");
            procent += 5;
        }
        System.out.println("Нашел , самый классный человек: Кристина Мусина =-)");
    }

    public static void main(String[] args) throws InterruptedException {
        findPeople();
    }
}
